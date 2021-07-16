package com.yjtech.wisdom.tourism.resource.broadcast.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupComposeEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastGroupComposeMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastGroupMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.mapper.BroadcastMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.query.BroadcastMicrophoneQuery;
import com.yjtech.wisdom.tourism.resource.broadcast.query.BroadcastQuery;
import com.yjtech.wisdom.tourism.resource.broadcast.query.BroadcastSoundQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yjtech.wisdom.tourism.common.utils.CommonPreconditions.checkCollectionEmpty;
import static java.util.stream.Collectors.toList;

/**
 * 广播信息业务模块
 *
 * @author zc
 */
@Service
public class BroadcastService extends ServiceImpl<BroadcastMapper, BroadcastEntity> {

    @Resource
    private BroadcastGroupMapper groupMapper;
    @Resource
    private BroadcastGroupComposeMapper groupComposeMapper;

    /**分页查询*/
    public IPage<BroadcastEntity> queryForPage(BroadcastQuery query) {

        LambdaQueryWrapper<BroadcastEntity> queryWrapper = new LambdaQueryWrapper<BroadcastEntity>();
        queryWrapper.eq(BroadcastEntity::getStatus, 1);
        queryWrapper.like(BroadcastEntity::getName, query.getSearchKey());
        IPage<BroadcastEntity> iPage = baseMapper.selectPage(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);

        List<BroadcastEntity> broadcastList = iPage.getRecords();
        if (!checkCollectionEmpty(broadcastList)) {

            // 分组信息
            ArrayListMultimap<Long, BroadcastGroupEntity> groupInfos =
                    queryBroadcastGroup(broadcastList.stream().map(BroadcastEntity::getBroadcastId).collect(toList()));

            broadcastList.forEach(broadcast -> {
                broadcast.setGroups(groupInfos.get(broadcast.getBroadcastId()));
                if (StringUtils.isNotNull(broadcast.getThirdId())) {
                    //没有对接，设置假数据
                    broadcast.setVolume(40);
                }
            });
        }
        return iPage;
    }

    public IPage<BroadcastEntity> queryBroadcastMicrophones(BroadcastMicrophoneQuery query){
        LambdaQueryWrapper<BroadcastEntity> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), BroadcastEntity::getName, query.getName());
        queryWrapper.eq(BroadcastEntity::getBroadcastType, 1);
        queryWrapper.eq(BroadcastEntity::getStatus, 1);
        queryWrapper.ne(BroadcastEntity::getEquipStatus, 0);
        IPage<BroadcastEntity> iPage = baseMapper.selectPage(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return iPage;
    }

    private ArrayListMultimap<Long, BroadcastGroupEntity> queryBroadcastGroup(List<Long> broadcastIds) {
        ArrayListMultimap<Long, BroadcastGroupEntity> result = ArrayListMultimap.create();
        if (checkCollectionEmpty(broadcastIds))
            return result;

        List<BroadcastGroupComposeEntity> broadcastGroupComposeList = queryComposeByBroadcastIds(broadcastIds);
        if (checkCollectionEmpty(broadcastGroupComposeList))
            return result;

        List<Long> groupIds = broadcastGroupComposeList.stream().map(BroadcastGroupComposeEntity::getGroupId).collect(toList());
        if (checkCollectionEmpty(groupIds))
            return result;

        List<BroadcastGroupEntity> groupList = groupMapper.selectList(new LambdaQueryWrapper<BroadcastGroupEntity>().in(BroadcastGroupEntity::getGroupId, groupIds));
        if (checkCollectionEmpty(groupList))
            return result;

        Map<Long, BroadcastGroupEntity> groupEntityMap = new HashMap<>();
        groupList.forEach(group -> groupEntityMap.put(group.getGroupId(), group));
        broadcastGroupComposeList.forEach(compose -> result.put(compose.getBroadcastId(), groupEntityMap.get(compose.getGroupId())));

        return result;
    }

    private List<BroadcastGroupComposeEntity> queryComposeByBroadcastIds(List<Long> ids) {
        if (checkCollectionEmpty(ids))
            return new ArrayList<>();

        return groupComposeMapper.queryByBroadcastIds(ids);
    }
}