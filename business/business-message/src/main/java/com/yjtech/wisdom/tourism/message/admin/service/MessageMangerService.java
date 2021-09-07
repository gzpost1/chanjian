package com.yjtech.wisdom.tourism.message.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.tencent.xinge.push.app.PushAppResponse;
import com.yjtech.wisdom.tourism.common.constant.MessageConstants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.sms.MessageCall;
import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageDto;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageRecordDto;
import com.yjtech.wisdom.tourism.message.admin.entity.MessageEntity;
import com.yjtech.wisdom.tourism.message.admin.entity.MessageRecordEntity;
import com.yjtech.wisdom.tourism.message.admin.mapper.MessageMapper;
import com.yjtech.wisdom.tourism.message.admin.mapper.MessageRecordMapper;
import com.yjtech.wisdom.tourism.message.admin.vo.QueryMessageVo;
import com.yjtech.wisdom.tourism.message.admin.vo.SendMessageVo;
import com.yjtech.wisdom.tourism.message.app.bo.TpnsPushBO;
import com.yjtech.wisdom.tourism.message.app.service.TpnsPushService;
import com.yjtech.wisdom.tourism.message.common.PageHelpUtil;
import com.yjtech.wisdom.tourism.message.sms.service.SmsUseService;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 消息管理中心
 *
 * @author renguangqian
 * @date 2021/7/23 9:24
 */
@Service
@Slf4j
public class MessageMangerService extends ServiceImpl<MessageMapper, MessageEntity> {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TpnsPushService tpnsPushService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SmsUseService smsService;

    @Autowired
    private MessageRecordMapper messageRecordMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${message.dictType.admin}")
    private String adminDictType;

    /**
     * 查询消息列表
     */
    public IPage<MessageDto> queryPageMessage (QueryMessageVo vo, boolean isRecord, MessageCall... messageCall) {

        Long userId = tokenService.getLoginUser(ServletUtils.getRequest()).getUser().getUserId();

        List<MessageEntity> result = baseMapper.selectList(
                new LambdaQueryWrapper<MessageEntity>()
                .like(MessageEntity::getEventDealPersonId, "\"" + userId + "\""));

        List<MessageDto> messageDtoList = JSONObject.parseArray(JSONObject.toJSONString(result), MessageDto.class);

        List<MessageDto> record = Lists.newArrayList();

        // 应急事件 id
        List<Long> emergencyEventIdList = Lists.newArrayList();
        // 旅游投诉 id
        List<Long> touristComplaintsEventIdList = Lists.newArrayList();

        messageDtoList.forEach(v -> {
            // 旅游投诉
            if (MessageConstants.EVENT_TYPE_TOUR.equals(v.getEventType())) {
                touristComplaintsEventIdList.add(v.getEventId());
            }
            // 应急事件
            else if (MessageConstants.EVENT_TYPE_EMERGENCY.equals(v.getEventType())) {
                emergencyEventIdList.add(v.getEventId());
            }
        });

        // 应急事件 id
        Long[] emergencyEventIds = emergencyEventIdList.toArray(new Long[0]);
        // 旅游投诉 id
        Long[] touristComplaintsEventIds = touristComplaintsEventIdList.toArray(new Long[0]);

        // 应急事件信息 查询
        List<MessageCallDto> emergencyEventList = Lists.newArrayList();
        // 旅游投诉信息 查询
        List<MessageCallDto> touristComplaintsEventList = Lists.newArrayList();

        if (!ArrayUtils.isEmpty(emergencyEventIds)) {
            emergencyEventList = messageCall[0].queryEvent(emergencyEventIds);
        }
        if (!ArrayUtils.isEmpty(touristComplaintsEventIds)) {
            touristComplaintsEventList = messageCall[1].queryEvent(touristComplaintsEventIds);
        }

        Integer queryType = vo.getQueryType();

        // 组合数据  每个事件类型不同，应该使用业务模块的事件状态进行判断
        for (MessageDto item : messageDtoList) {
            // 应急事件 数据分类放入结果集
            setEventInfo(record, emergencyEventList, queryType, item);
            // 旅游投诉事件 数据分类放入结果集
            setEventInfo(record, touristComplaintsEventList, queryType, item);
        }

        // 数据排序
        Collections.reverse(record);

        // 进行自主分页
        IPage<MessageDto> page = PageHelpUtil.page(vo.getPageNo(), vo.getPageSize(), record);

        if (isRecord) {
            // 设置 本次查询的结果记录总数
            redisTemplate.opsForValue().set(MessageConstants.MESSAGE_RECORD_NUM + userId, page.getTotal());
        }
        return page;
    }

    /**
     * 设置事件属性
     *
     * @param record 结果集
     * @param emergencyEventList 各业务模块通过事件id查询到的返回结果集
     * @param queryType 查询类型
     * @param item 数据源
     */
    private void setEventInfo(List<MessageDto> record,
                              List<MessageCallDto> emergencyEventList,
                              Integer queryType,
                              MessageDto item
                              )
    {
        // 待处理数据
        List<MessageDto> pending = Lists.newArrayList();
        // 已处理数据
        List<MessageDto> deal = Lists.newArrayList();
        for (MessageCallDto emergencyItem : emergencyEventList) {
            if (item.getEventId().equals(emergencyItem.getEventId())) {
                // 构造数据
                MessageDto messageDto = JSONObject.parseObject(JSONObject.toJSONString(emergencyItem), MessageDto.class);
                messageDto.setId(item.getId());
                messageDto.setEventType(item.getEventType());
                messageDto.setEventDealPersonId(item.getEventDealPersonId());
                messageDto.setCreateTime(item.getCreateTime());
                messageDto.setTitle(item.getTitle());

                // 已处理数据
                if (MessageConstants.EVENT_STATUS_COMPLETE.equals(emergencyItem.getEventStatus())) {
                    deal.add(messageDto);
                }
                // 查待指派、处理
                else {
                    pending.add(messageDto);
                }
            }
        }
        // 查全部消息
        if (MessageConstants.QUERY_ALL.equals(queryType)) {
            record.addAll(deal);
        }
        record.addAll(pending);
    }

    /**
     * GET 查询是否存在新的消息记录
     *
     * @return
     */
    public MessageRecordDto queryNewMessageNum (MessageCall... messageCall) {
        // 获取消息记录总数
        IPage<MessageDto> page = queryPageMessage(QueryMessageVo.builder().queryType(MessageConstants.MESSAGE_LIST_PENDING).build(), false, messageCall);
        if (page.getTotal() > 0) {
            return MessageRecordDto.builder().isAdd(true).addNumber(page.getTotal()).build();
        }
        return MessageRecordDto.builder().isAdd(false).addNumber(0L).build();
    }

    /**
     * 发送通知，且调用通知方式api
     */
    public void sendMessage (SendMessageVo vo) {
        sendAndRecord(vo);
    }

    /**
     * 发送 并 记录
     *
     * @param vo
     */
    private void sendAndRecord(SendMessageVo vo) {
        Long[] eventDealPersonIdArray = vo.getEventDealPersonIdArray();
        for (Integer sendType : vo.getSendType()) {
            // 历史记录
            MessageRecordEntity recordEntity = MessageRecordEntity.builder()
                    .content(vo.getContent())
                    .eventId(vo.getEventId())
                    .build();

            switch (sendType) {
                // 后台
                case 0:
                    // 处理人id
                    List<Long> list = Arrays.stream(eventDealPersonIdArray).collect(Collectors.toList());
                    MessageEntity messageEntity = MessageEntity.builder()
                            .eventDealPersonId(list)
                            .eventId(vo.getEventId())
                            .eventType(vo.getEventType())
                            .title(vo.getPcTitle())
                            .build();
                    int records = baseMapper.insert(messageEntity);

                    // 0-后台
                    recordEntity.setSendType(0);
                    recordEntity.setSendObject(JSONObject.toJSONString(eventDealPersonIdArray));
                    if (records > 0) {
                        recordEntity.setSuccess((byte) 1);
                    }else {
                        //更新失败
                        log.error("【发送通知-后台通知】插入失败-事件ID：{}",vo.getEventId());
                        // 失败记录
                        recordEntity.setSuccess((byte) 0);
                    }
                    messageRecordMapper.insert(recordEntity);
                    break;

                // app
                case 1:
                    for (Long dealId : eventDealPersonIdArray) {
                        // 查询处理人id 是否有pushToken，如果存在pushToken，则进行app消息推送
                        SysUser sysUser = sysUserService.selectUserById(dealId);
                        String pushToken = sysUser.getPushToken();
                        // 1 - app
                        recordEntity.setSendType(1);
                        recordEntity.setSendObject(pushToken);

                        if (!StringUtils.isEmpty(pushToken)) {
                            ArrayList<String> tokenList = Lists.newArrayList();
                            tokenList.add(pushToken);
                            PushAppResponse pushAppResponse = tpnsPushService.pushTokenListAndroid(
                                    TpnsPushBO.builder()
                                            .title(vo.getTitle())
                                            .content(vo.getContent())
                                            .tokenList(tokenList)
                                            .build()
                            );
                            recordEntity.setSuccess((byte) 1);
                            recordEntity.setResponse(JSONObject.toJSONString(pushAppResponse));
                        }
                        else {
                            recordEntity.setSuccess((byte) 0);
                        }
                        recordEntity.setId(null);
                        messageRecordMapper.insert(recordEntity);
                    }
                    break;

                // 短信
                case 2:
                    for (Long dealId : eventDealPersonIdArray) {
                        // 查询处理人id 是否有pushToken，如果存在pushToken，则进行app消息推送
                        SysUser sysUser = sysUserService.selectUserById(dealId);
                        String phoneNumber = sysUser.getPhonenumber();
                        // 2-短信
                        recordEntity.setSendType(2);
                        recordEntity.setSendObject(phoneNumber);
                        try {
                            if (!StringUtils.isEmpty(phoneNumber)) {
                                smsService.smsSend(phoneNumber, vo.getEventType(), vo.getSmsContent());
                                recordEntity.setSuccess((byte) 1);
                            }else {
                                recordEntity.setSuccess((byte) 0);
                            }
                        }catch (CustomException e) {
                            recordEntity.setSuccess((byte) 0);
                            throw e;
                        }finally {
                            recordEntity.setId(null);
                            messageRecordMapper.insert(recordEntity);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    }


    /**
     * 查询当前配置管理员id
     *
     * @return
     */
    public Long queryAdminId() {
        String adminId = sysConfigService.selectConfigByKey(adminDictType);
        return Long.parseLong(adminId);
    }

}
