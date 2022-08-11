package com.yjtech.wisdom.tourism.chat.service;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbRegisterInfoMapper;
import com.yjtech.wisdom.tourism.chat.conver.ChatMessageCover;
import com.yjtech.wisdom.tourism.chat.dto.Message;
import com.yjtech.wisdom.tourism.chat.dto.MessageHistoryQuery;
import com.yjtech.wisdom.tourism.chat.dto.MessageRecordQuery;
import com.yjtech.wisdom.tourism.chat.entity.ChatMessageEntity;
import com.yjtech.wisdom.tourism.chat.entity.ChatRecordEntity;
import com.yjtech.wisdom.tourism.chat.mapper.ChatMessageMapper;
import com.yjtech.wisdom.tourism.chat.mapper.ChatRecordMapper;
import com.yjtech.wisdom.tourism.chat.redis.ChatRecordRedisDao;
import com.yjtech.wisdom.tourism.chat.uitl.MessageUtil;
import com.yjtech.wisdom.tourism.chat.vo.ChatMessageExportVo;
import com.yjtech.wisdom.tourism.chat.vo.ChatMessageVo;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChatMessageService extends ServiceImpl<ChatMessageMapper, ChatMessageEntity> {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private ChatRecordMapper chatRecordMapper;

    @Autowired
    private ChatRecordService chatRecordService;

    @Autowired
    private TbRegisterInfoMapper tbRegisterInfoMapper;

    @Resource
    private ChatRecordRedisDao chatRecordRedisDao;

    /**
     * 查询两人间的聊天记录
     *
     * @param messageRecordQuery
     * @return
     */
    @Transactional
    public List<ChatMessageVo> getMessageHistory(MessageRecordQuery messageRecordQuery) {
        //查询记录
        ChatRecordEntity chatRecordEntity = chatRecordService.getChatRecordEntity(messageRecordQuery.getFromUserId(), messageRecordQuery.getToUserId());
        String md5Userid = MessageUtil.getMd5Userid(messageRecordQuery.getFromUserId(), messageRecordQuery.getToUserId(), redisCache);
        MessageHistoryQuery messageHistoryQuery = MessageHistoryQuery.builder().delTime(chatRecordEntity.getDelTime())
                .md5Userid(md5Userid).dataLimit(messageRecordQuery.getDataLimit()).build();
        List<ChatMessageEntity> chatMessageVoList = chatMessageMapper.selectMessageHistory(messageHistoryQuery);
        //将消息 已读
        readMesg(chatRecordEntity.getId());
        return ChatMessageCover.INSTANCE.cover2ChatMessageVo(chatMessageVoList, getRegisterInfoEntityMap());
    }

    public Page<ChatMessageVo> querySendMessage(MessageRecordQuery messageRecordQuery) throws ParseException {
        Page<ChatMessageEntity> page = new Page<>(messageRecordQuery.getPageNo(), messageRecordQuery.getPageSize());
        disQueryDate(messageRecordQuery);
        LambdaQueryWrapper<ChatMessageEntity> queryWrapper = buildSendMsgQueryWrapper(messageRecordQuery);
        IPage<ChatMessageEntity> messageEntityIPage = this.baseMapper.selectPage(page, queryWrapper);
        List<ChatMessageVo> chatMessageVoList = ChatMessageCover.INSTANCE.cover2ChatMessageVo(messageEntityIPage.getRecords(), getRegisterInfoEntityMap());
        return new Page<ChatMessageVo>().setRecords(chatMessageVoList).setTotal(messageEntityIPage.getTotal());
    }

    private void disQueryDate(MessageRecordQuery messageRecordQuery) throws ParseException {
        if (messageRecordQuery.getStartTime() !=null && messageRecordQuery.getEndTime() != null){
            Date startDate = DateUtils.parseDate(DateFormatUtils.format(messageRecordQuery.getStartTime(), "yy-MM-dd") + " 00:00:00", "yy-MM-dd HH:mm:ss");
            messageRecordQuery.setStartTime(startDate);
            Date endDate = DateUtils.parseDate(DateFormatUtils.format(messageRecordQuery.getEndTime(), "yy-MM-dd") + " 23:59:59", "yy-MM-dd HH:mm:ss");
            messageRecordQuery.setEndTime(endDate);
        }
    }

    private LambdaQueryWrapper<ChatMessageEntity> buildSendMsgQueryWrapper(MessageRecordQuery messageRecordQuery) {
        LambdaQueryWrapper<ChatMessageEntity> queryWrapper = new LambdaQueryWrapper<ChatMessageEntity>();
        queryWrapper.in(CollectionUtil.isNotEmpty(messageRecordQuery.getIds()), ChatMessageEntity::getId, messageRecordQuery.getIds())
                .in(CollectionUtil.isNotEmpty(messageRecordQuery.getFromUserIdList()), ChatMessageEntity::getFromUserId, messageRecordQuery.getFromUserIdList())
                .between(messageRecordQuery.getStartTime() != null && messageRecordQuery.getEndTime() != null
                        , ChatMessageEntity::getSendTime, messageRecordQuery.getStartTime(), messageRecordQuery.getEndTime())
                .orderByDesc(ChatMessageEntity::getSendTime);
        return queryWrapper;
    }

    private Map<Long, TbRegisterInfoEntity> getRegisterInfoEntityMap() {
        Map<Long, TbRegisterInfoEntity> infoEntityMap = tbRegisterInfoMapper.selectMap();
        return infoEntityMap;
    }

    /**
     * TODO 该接口还需考虑会话被删除的情况 暂时为删除即为已读
     *
     * @param recipient 接收人，当前用户
     * @return
     */
    public List<ChatMessageVo> queryUnreadMesg(Long recipient) {
        LambdaQueryWrapper<ChatMessageEntity> queryWrapper = new LambdaQueryWrapper<ChatMessageEntity>();
        queryWrapper.eq(ChatMessageEntity::getReadStatus, "N").eq(ChatMessageEntity::getToUserId, recipient);
        List<ChatMessageEntity> chatMessageEntities = this.baseMapper.selectList(queryWrapper);
        return ChatMessageCover.INSTANCE.cover2ChatMessageVo(chatMessageEntities, getRegisterInfoEntityMap());
    }

    /**
     * 设置消息已读
     *
     * @param fromId 发送人
     * @param toId   接收人
     */
    @Transactional
    public void readMesg(Long id) {
        ChatRecordEntity recordEntity = chatRecordService.getById(id);
        LambdaUpdateWrapper<ChatMessageEntity> updateWrapper = new LambdaUpdateWrapper<ChatMessageEntity>();
        updateWrapper.eq(ChatMessageEntity::getFromUserId, recordEntity.getSessionId())
                .eq(ChatMessageEntity::getToUserId, recordEntity.getInitiatorId())
                .set(ChatMessageEntity::getReadStatus, "Y");
        update(updateWrapper);
        ChatRecordEntity updateEntity = new ChatRecordEntity();
        updateEntity.setId(id);
        updateEntity.setHasUnread("N");
        chatRecordMapper.updateById(updateEntity);

        chatRecordRedisDao.remove(recordEntity.getInitiatorId(), id);
    }

    /**
     * 查询两人的新消息
     *
     * @param messageRecordQuery
     * @return
     */
    public List<ChatMessageVo> queryNewMessage(MessageRecordQuery messageRecordQuery) {
        LambdaQueryWrapper<ChatMessageEntity> queryWrapper = new LambdaQueryWrapper<ChatMessageEntity>();
        queryWrapper.eq(ChatMessageEntity::getReadStatus, "N")
                .eq(ChatMessageEntity::getToUserId, messageRecordQuery.getToUserId())
                .eq(ChatMessageEntity::getFromUserId, messageRecordQuery.getFromUserId());
        List<ChatMessageEntity> chatMessageEntities = this.baseMapper.selectList(queryWrapper);
        return ChatMessageCover.INSTANCE.cover2ChatMessageVo(chatMessageEntities, getRegisterInfoEntityMap());
    }

    @Transactional
    public Boolean sendMessage(Message message) {
        Date sendTime = new Date();
        //插入互聊记录
        ChatRecordEntity chatRecordEntity = chatRecordService.insertRecord(message.getFromId(), message.getToId(), sendTime);
        //更新最新聊天时间,消息未读
        chatRecordService.updateLastChatTime(message.getFromId(), message.getToId(), sendTime);
        chatRecordService.updateLastChatTime(message.getToId(), message.getFromId(), sendTime);
        //消息入库
        ChatMessageEntity chatMessageEntity = buildChatMessageEntity(message, sendTime);
        save(chatMessageEntity);
        //未读状态插入redis
        chatRecordRedisDao.add(message.getToId(), chatRecordEntity.getId());
        return true;
    }

    private ChatMessageEntity buildChatMessageEntity(Message message, Date sendTime) {
        String md5Userid = MessageUtil.getMd5Userid(message.getFromId(), message.getToId(), redisCache);
        return ChatMessageEntity.builder().fromUserId(message.getFromId()).toUserId(message.getToId())
                .sendTime(sendTime).content(message.getContent()).logDel("N").md5UserId(md5Userid)
                .readStatus("N").build();
    }


    public List<ChatMessageExportVo> querySendMessageList(MessageRecordQuery messageRecordQuery) throws ParseException {
        disQueryDate(messageRecordQuery);
        Page<ChatMessageEntity> page = new Page<>(messageRecordQuery.getPageNo(), messageRecordQuery.getPageSize());
        LambdaQueryWrapper<ChatMessageEntity> queryWrapper = buildSendMsgQueryWrapper(messageRecordQuery);
        IPage<ChatMessageEntity> messageEntityIPage = this.baseMapper.selectPage(page, queryWrapper);
        return ChatMessageCover.INSTANCE.cover2ChatMessageExportVo(messageEntityIPage.getRecords(), getRegisterInfoEntityMap());
    }

    /**
     * 查询留言数统计
     *
     * @param companyId
     * @return
     */
    @Transactional(readOnly = true)
    public int queryMessageStatistics(String companyId){
        return baseMapper.queryMessageStatistics(companyId);
    }

    /**
     * 查询趋势
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<BaseVO> queryAnalysis(ProjectDataStatisticsQueryVO vo){
        return baseMapper.queryAnalysis(vo);
    }
}
