package com.yjtech.wisdom.tourism.chat.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.ImmutableTable;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbRegisterInfoMapper;
import com.yjtech.wisdom.tourism.chat.conver.ChatRecordCover;
import com.yjtech.wisdom.tourism.chat.dto.MessageRecordQuery;
import com.yjtech.wisdom.tourism.chat.entity.ChatRecordEntity;
import com.yjtech.wisdom.tourism.chat.mapper.ChatRecordMapper;
import com.yjtech.wisdom.tourism.chat.redis.ChatRecordRedisDao;
import com.yjtech.wisdom.tourism.chat.vo.EnterpriseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2022-05-12
 */
@Service
@Slf4j
public class ChatRecordService extends ServiceImpl<ChatRecordMapper, ChatRecordEntity> {

    private volatile ImmutableTable<Long, Long, ChatRecordEntity> localCache;

//    private volatile Multimap<Long, ChatRecordEntity> chatObjectCache;

    @Autowired
    private TbRegisterInfoMapper tbRegisterInfoMapper;

    @Autowired
    private ChatMessageService chatMessageService;

    @Resource
    private ChatRecordRedisDao chatRecordRedisDao;

    @PostConstruct
    public synchronized void initLocalCache() {
        List<ChatRecordEntity> recordEntities = this.baseMapper.selectList(new QueryWrapper<ChatRecordEntity>().lambda().eq(ChatRecordEntity::getLogDel, "N"));
        if (CollUtil.isEmpty(recordEntities)) {
            ImmutableTable.Builder<Long, Long, ChatRecordEntity> localCacheBuilder = ImmutableTable.builder();
            localCache = localCacheBuilder.build();
            return;
        }
        ImmutableTable.Builder<Long, Long, ChatRecordEntity> localCacheBuilder = ImmutableTable.builder();
//        ImmutableMultimap.Builder<Long, ChatRecordEntity> chatObjectCacheBuilder = ImmutableMultimap.builder();
        recordEntities.forEach(chatRecordEntity -> {
            localCacheBuilder.put(chatRecordEntity.getInitiatorId(), chatRecordEntity.getSessionId(), chatRecordEntity);
//            chatObjectCacheBuilder.put(chatRecordEntity.getInitiatorId(), chatRecordEntity);

        });
        localCache = localCacheBuilder.build();
//        chatObjectCache = chatObjectCacheBuilder.build();
        log.info("初始化互聊记录，共{}条", recordEntities.size());
    }

    /**
     * 插入互聊记录
     *
     * @param fromId
     * @param toId
     */
    @Transactional(rollbackFor = Exception.class)
    public ChatRecordEntity insertRecord(Long fromId, Long toId, Date sendTime) {
        ChatRecordEntity initiatorEntity = getRecord(fromId, toId);
        Boolean hasUpdate = false;
        if (initiatorEntity == null) {
            //1.被删除
            Boolean delStatus = updateDelStatus(fromId, toId, sendTime);
            if (false == delStatus) {
                //2.不存在，插入
                initiatorEntity = buildeRecordEntity(fromId, toId, sendTime);
                this.save(initiatorEntity);
            }
            hasUpdate = true;
        }
        ChatRecordEntity sessionEntity = getRecord(toId, fromId);
        if (sessionEntity == null) {
            //1.被删除
            Boolean delStatus = updateDelStatus(toId, fromId, sendTime);
            sessionEntity = this.baseMapper.selectOne(buildeQueryWrapper(toId, fromId));
            if (false == delStatus) {
                //2.不存在，插入
                sessionEntity = buildeRecordEntity(toId, fromId, sendTime);
                this.save(sessionEntity);
            }
            hasUpdate = true;
        }
        if (hasUpdate) {
            this.initLocalCache();
        }
        return sessionEntity;
    }

    public Boolean updateDelStatus(Long fromId, Long toId, Date sendTime) {
        LambdaUpdateWrapper<ChatRecordEntity> updateWrapper = new LambdaUpdateWrapper<ChatRecordEntity>().eq(ChatRecordEntity::getInitiatorId, fromId)
                .eq(ChatRecordEntity::getSessionId, toId).set(ChatRecordEntity::getLogDel, "N").set(ChatRecordEntity::getDelTime, sendTime);
        return update(updateWrapper);
    }

    public ChatRecordEntity getRecord(Long fromId, Long toId) {
        ChatRecordEntity chatRecordEntity = localCache.get(fromId, toId);
        if (chatRecordEntity == null) {
            //再查一遍
            LambdaQueryWrapper<ChatRecordEntity> queryWrapper = buildeQueryWrapper(fromId, toId);
            chatRecordEntity = this.baseMapper.selectOne(queryWrapper);
            if (chatRecordEntity != null) {
                //更新缓存
                this.initLocalCache();
            }
        }
        return chatRecordEntity;
    }

    private ChatRecordEntity buildeRecordEntity(Long fromId, Long toId, Date sendTime) {
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setInitiatorId(fromId);
        chatRecordEntity.setSessionId(toId);
        chatRecordEntity.setDelTime(sendTime);
        chatRecordEntity.setLogDel("N");
        chatRecordEntity.setLastChatTime(sendTime);
        return chatRecordEntity;
    }

    /**
     * 获取与当前用户聊天的对象
     *
     * @param
     * @return
     */
    public Page<EnterpriseVo> queryChatObject(MessageRecordQuery messageRecordQuery) {
        Page<ChatRecordEntity> page = new Page<>(messageRecordQuery.getPageNo(), messageRecordQuery.getPageSize());
        //查数据库
        LambdaQueryWrapper<ChatRecordEntity> queryWrapper = new LambdaQueryWrapper<ChatRecordEntity>().eq(ChatRecordEntity::getInitiatorId, messageRecordQuery.getFromUserId())
                .eq(ChatRecordEntity::getLogDel, "N").orderByDesc(ChatRecordEntity::getLastChatTime);

        IPage<ChatRecordEntity> entityIPage = this.baseMapper.selectPage(page, queryWrapper);
        Map<Long, TbRegisterInfoEntity> infoEntityMap = tbRegisterInfoMapper.selectMap();
        List<EnterpriseVo> enterpriseVos = ChatRecordCover.INSTANCE.conver2EnterpriseVo(entityIPage.getRecords(), infoEntityMap);
        return new Page<EnterpriseVo>().setRecords(enterpriseVos);
    }

    public List<EnterpriseVo> queryChatObject(Long initiatorId, String companyName) {
        Set<Long> ids = new HashSet<>();
        if (StrUtil.isNotEmpty(companyName)) {
            LambdaQueryWrapper<TbRegisterInfoEntity> registerInfoQueryWrapper = new LambdaQueryWrapper<TbRegisterInfoEntity>();
            registerInfoQueryWrapper.like(TbRegisterInfoEntity::getCompanyName, companyName);
            List<TbRegisterInfoEntity> registerInfoEntities = tbRegisterInfoMapper.selectList(registerInfoQueryWrapper);
            if (CollUtil.isEmpty(registerInfoEntities)) {
                return null;
            }
            ids = registerInfoEntities.stream().map(tbRegisterInfoEntity -> tbRegisterInfoEntity.getId()).collect(Collectors.toSet());
        }
        //查数据库
        LambdaQueryWrapper<ChatRecordEntity> queryWrapper = new LambdaQueryWrapper<ChatRecordEntity>().eq(ChatRecordEntity::getInitiatorId, initiatorId)
                .eq(ChatRecordEntity::getLogDel, "N").in(CollUtil.isNotEmpty(ids), ChatRecordEntity::getSessionId, ids)
                .orderByDesc(ChatRecordEntity::getLastChatTime);
        List<ChatRecordEntity> entityList = this.baseMapper.selectList(queryWrapper);
        //走缓存
//        List<ChatRecordEntity> entityList = new ArrayList<>(chatObjectCache.get(initiatorId));
        Map<Long, TbRegisterInfoEntity> infoEntityMap = tbRegisterInfoMapper.selectMap();
        List<EnterpriseVo> enterpriseVos = ChatRecordCover.INSTANCE.conver2EnterpriseVo(entityList, infoEntityMap);
        return enterpriseVos;
    }

    public ChatRecordEntity getChatRecordEntity(Long fromUserId, Long toUserId) {
//        LambdaQueryWrapper<ChatRecordEntity> queryWrapper = this.buildeQueryWrapper(fromUserId, toUserId);
//        ChatRecordEntity chatRecordEntity = this.baseMapper.selectOne(queryWrapper);
        return localCache.get(fromUserId, toUserId);
    }

    private LambdaQueryWrapper<ChatRecordEntity> buildeQueryWrapper(Long fromUserId, Long toUserId) {
        return new LambdaQueryWrapper<ChatRecordEntity>().eq(ChatRecordEntity::getInitiatorId, fromUserId)
                .eq(ChatRecordEntity::getSessionId, toUserId).eq(ChatRecordEntity::getLogDel, "N");
    }

    public Boolean deleteRecord(Long id) {
        ChatRecordEntity chatRecordEntity = new ChatRecordEntity();
        chatRecordEntity.setLogDel("Y");
        chatRecordEntity.setDelTime(new Date());
        chatRecordEntity.setId(id);
        boolean result = updateById(chatRecordEntity);
        this.initLocalCache();
        //将相关消息置为已读
        chatMessageService.readMesg(id);
        return result;
    }

    public void updateLastChatTime(Long fromUserId, Long toId, Date sendTime) {
        UpdateWrapper<ChatRecordEntity> updateWrapper = new UpdateWrapper<ChatRecordEntity>();
        updateWrapper.lambda().eq(ChatRecordEntity::getInitiatorId, fromUserId)
                .eq(ChatRecordEntity::getSessionId, toId).eq(ChatRecordEntity::getLogDel, "N")
                .set(ChatRecordEntity::getLastChatTime, sendTime)
                .set(ChatRecordEntity::getHasUnread, "Y");
        update(updateWrapper);
    }

    /**
     * 查询当前用户是否有未读
     *
     * @return
     */
    public Set<Long> queryHasUnread(Long initiatorId) {
        return chatRecordRedisDao.getAll(initiatorId);
    }
}
