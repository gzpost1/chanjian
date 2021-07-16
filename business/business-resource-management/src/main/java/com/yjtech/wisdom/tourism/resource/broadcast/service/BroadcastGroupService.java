package com.yjtech.wisdom.tourism.resource.broadcast.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.CommonLogicDeleteObj;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupComposeEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastGroupSaveDto;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastGroupUpdateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastGroupComposeMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastGroupMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.yjtech.wisdom.tourism.common.utils.CommonPreconditions.checkCollectionEmpty;
import static com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils.getUserId;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * 广播分组业务模块
 * @author zc
 */
@Service
public class BroadcastGroupService extends ServiceImpl<BroadcastGroupMapper, BroadcastGroupEntity> {

    @Resource
    private BroadcastGroupComposeMapper groupComposeMapper;
    @Resource
    private BroadcastMapper broadcastMapper;

    /**
     * 查询分组
     */
    public List<BroadcastGroupEntity> queryGroupList() {
        List<BroadcastGroupEntity> groupList = list(
                new LambdaQueryWrapper<BroadcastGroupEntity>().orderByDesc(BroadcastGroupEntity::getCreateTime));

        if (!checkCollectionEmpty(groupList)) {
            // 分组的广播信息
            ArrayListMultimap<Long, BroadcastEntity> broadcastInfos =
                    queryGroupBroadcast(groupList.stream().map(BroadcastGroupEntity::getGroupId).collect(toList()));
            groupList.forEach(group -> group.setBroadcastList(broadcastInfos.get(group.getGroupId())));
        }

        return groupList;
    }

    /**
     * 保存分组
     */
    @Transactional(rollbackFor = Exception.class)
    public void createGroup(BroadcastGroupSaveDto dto) {
        BroadcastGroupEntity groupEntity = new BroadcastGroupEntity();
        Long groupId = IdWorker.getId();
        groupEntity.setGroupId(groupId);
        groupEntity.setName(dto.getName());
        groupEntity.setStatus((byte) 1);
        baseMapper.insert(groupEntity);

        List<Long> broadcastIds = dto.getBroadcastIds();
        if (checkCollectionEmpty(broadcastIds))
            return;

        List<BroadcastGroupComposeEntity> composeList = new ArrayList<>();
        broadcastIds.forEach(broadcastId -> {
            BroadcastGroupComposeEntity compose = new BroadcastGroupComposeEntity();
            compose.setBroadcastId(broadcastId);
            compose.setComposeId(IdWorker.getId());
            compose.setGroupId(groupId);
            composeList.add(compose);
        });
        groupComposeMapper.batchInsertCompose(composeList);
    }

    /**
     * 编辑分组
     */
    @Transactional(rollbackFor = Exception.class)
    public void modifyGroup(BroadcastGroupUpdateDto dto) {

        BroadcastGroupEntity groupInDb = baseMapper.selectById(dto.getGroupId());
        if (isNull(groupInDb))
            return;

        if (!groupInDb.getName().equals(dto.getName())) {
            groupInDb.setName(dto.getName());
            baseMapper.updateById(groupInDb);
        }

        List<Long> broadcastIds = dto.getBroadcastIds();
        groupComposeMapper.deleteByGroupIds(Lists.newArrayList(groupInDb.getGroupId()));

        if (!checkCollectionEmpty(broadcastIds)) {
            List<BroadcastGroupComposeEntity> composeList = new ArrayList<>();
            broadcastIds.forEach(broadcastId -> {
                BroadcastGroupComposeEntity compose = new BroadcastGroupComposeEntity();
                compose.setBroadcastId(broadcastId);
                compose.setComposeId(IdWorker.getId());
                compose.setGroupId(groupInDb.getGroupId());
                composeList.add(compose);
            });

            groupComposeMapper.batchInsertCompose(composeList);
        }
    }

    /**批量删除分组*/
    @Transactional(rollbackFor = Exception.class)
    public void deleteGroup(CommonLogicDeleteObj logicDeleteObj) {
        if (isNull(logicDeleteObj) || checkCollectionEmpty(logicDeleteObj.getIds()))
            return;

        logicDeleteObj.setUpdateTime(new Date());
        logicDeleteObj.setUpdateUser(getUserId());
        baseMapper.deleteBatchIds(logicDeleteObj.getIds());

        groupComposeMapper.deleteByGroupIds(logicDeleteObj.getIds());
    }

    private ArrayListMultimap<Long, BroadcastEntity> queryGroupBroadcast(List<Long> groupIds) {
        ArrayListMultimap<Long, BroadcastEntity> result = ArrayListMultimap.create();
        if (checkCollectionEmpty(groupIds))
            return result;

        List<BroadcastGroupComposeEntity> broadcastGroupComposeList = queryComposeByGroupIds(groupIds);
        if (checkCollectionEmpty(broadcastGroupComposeList))
            return result;

        List<Long> broadcastIds = broadcastGroupComposeList.stream().map(BroadcastGroupComposeEntity::getBroadcastId).collect(toList());
        if (checkCollectionEmpty(broadcastIds))
            return result;

        List<BroadcastEntity> broadcastEntityList = broadcastMapper.selectList(new LambdaQueryWrapper<BroadcastEntity>().in(BroadcastEntity::getBroadcastId, broadcastIds));
        if (checkCollectionEmpty(broadcastEntityList))
            return result;

        Map<Long, BroadcastEntity> broadcastEntityMap = new HashMap<>();
        broadcastEntityList.forEach(broadcast -> broadcastEntityMap.put(broadcast.getBroadcastId(), broadcast));
        broadcastGroupComposeList.forEach(compose -> result.put(compose.getGroupId(), broadcastEntityMap.get(compose.getBroadcastId())));

        return result;
    }

    private List<BroadcastGroupComposeEntity> queryComposeByGroupIds(List<Long> ids) {
        if (checkCollectionEmpty(ids))
            return new ArrayList<>();

        return groupComposeMapper.queryByGroupIds(ids);
    }
}