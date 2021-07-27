package com.yjtech.wisdom.tourism.message.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.tencent.xinge.push.app.PushAppResponse;
import com.yjtech.wisdom.tourism.command.entity.event.EventAppointEntity;
import com.yjtech.wisdom.tourism.command.mapper.event.EventAppointMapper;
import com.yjtech.wisdom.tourism.common.bean.AssignUserInfo;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.enums.MessageAppointStatusEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.message.admin.dto.MessageDto;
import com.yjtech.wisdom.tourism.message.admin.entity.MessageEntity;
import com.yjtech.wisdom.tourism.message.admin.entity.MessageRecordEntity;
import com.yjtech.wisdom.tourism.message.admin.mapper.MessageMapper;
import com.yjtech.wisdom.tourism.message.admin.mapper.MessageRecordMapper;
import com.yjtech.wisdom.tourism.message.admin.vo.ChangeMessageStatusVo;
import com.yjtech.wisdom.tourism.message.admin.vo.InitMessageVo;
import com.yjtech.wisdom.tourism.message.admin.vo.QueryMessageVo;
import com.yjtech.wisdom.tourism.message.admin.vo.SendMessageVo;
import com.yjtech.wisdom.tourism.message.app.bo.TpnsPushBO;
import com.yjtech.wisdom.tourism.message.app.service.TpnsPushService;
import com.yjtech.wisdom.tourism.message.sms.service.SmsService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    private SysDictTypeService dictTypeService;

    @Autowired
    private TpnsPushService tpnsPushService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private MessageRecordMapper messageRecordMapper;

    @Autowired
    private EventAppointMapper eventAppointMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${message.dictType.admin}")
    private String adminDictType;

    /**
     * 查询消息列表
     */
    public IPage<MessageDto> queryPageMessage (QueryMessageVo vo) {

        // 获取用户信息
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();

        //获取当前用户的处理方式状态
        MessageAppointStatusEnum status = findDealStatus(userId);

        // 根据当前用户处理方式状态，封装查询条件
        QueryWrapper<MessageEntity> queryWrapper = new QueryWrapper<>();
        HandleEventDealStatus(vo.getQueryType(), userId, status, queryWrapper);

        return baseMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()), queryWrapper)
                .convert(v -> JSONObject.parseObject(JSONObject.toJSONString(v), MessageDto.class));
    }


    /**
     * 修改消息状态  根据事件id
     */
    public void changeMessageStatus (ChangeMessageStatusVo vo) {
        baseMapper.update(null,
                new LambdaUpdateWrapper<MessageEntity>()
                        .eq(MessageEntity::getEventId, vo.getEventId())
                        .set(MessageEntity::getEventStatus, vo.getEventStatus()));
    }

    /**
     * 发送通知，且调用通知方式api
     */
    public void sendMessage (SendMessageVo vo) {
        // 根据事件id,获取初始化的消息数据
        MessageEntity messageEntity = baseMapper.selectOne(new LambdaQueryWrapper<MessageEntity>().eq(MessageEntity::getEventId, vo.getEventId()));
        if (ObjectUtils.isEmpty(messageEntity)) {
            log.error("【发送通知-后台通知】事件ID：{} 不存在！请先初始化事件！", vo.getEventId());
            throw new CustomException("事件ID不存在，请先初始化事件！");
        }

        Long[] eventDealPersonId = vo.getEventDealPersonId();
        String eventDealPersonIdStr = "";
        for (Long dealId : eventDealPersonId) {
            eventDealPersonIdStr += "," + dealId;
        }
        sendAndRecord(vo, messageEntity, eventDealPersonId, eventDealPersonIdStr);
    }

    /**
     * 发送 并 记录
     *
     * @param vo
     * @param messageEntity
     * @param eventDealPersonId
     * @param eventDealPersonIdStr
     */
    public void sendAndRecord(SendMessageVo vo, MessageEntity messageEntity, Long[] eventDealPersonId, String eventDealPersonIdStr) {
        // 历史记录
        MessageRecordEntity recordEntity = MessageRecordEntity.builder()
                .content(vo.getContent())
                .eventId(vo.getEventId())
                .build();

        for (Integer sendType : vo.getSendType()) {
            switch (sendType) {
                // 后台
                case 0:
                    int updateRecords = baseMapper.update(null,
                            new LambdaUpdateWrapper<MessageEntity>()
                                    .eq(MessageEntity::getEventId, vo.getEventId())
                                    // 处理人id 使用逗号“,”分割
                                    .set(MessageEntity::getEventDealPersonId, messageEntity.getEventDealPersonId() + "," + eventDealPersonIdStr)
                    );
                    // 0-后台
                    recordEntity.setSendType(0);
                    recordEntity.setSendObject(JSONObject.toJSONString(vo.getEventDealPersonId()));
                    if (updateRecords > 0) {
                        recordEntity.setSuccess((byte) 1);
                    }else {
                        //更新失败
                        log.error("【发送通知-后台通知】更新失败-事件ID：{}",vo.getEventId());
                        // 失败记录
                        recordEntity.setSuccess((byte) 0);
                    }
                    messageRecordMapper.insert(recordEntity);
                    break;

                // app
                case 1:
                    for (Long dealId : eventDealPersonId) {
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
                        messageRecordMapper.insert(recordEntity);
                    }
                    break;

                // 短信
                case 2:
                    for (Long dealId : eventDealPersonId) {
                        // 查询处理人id 是否有pushToken，如果存在pushToken，则进行app消息推送
                        SysUser sysUser = sysUserService.selectUserById(dealId);
                        String phoneNumber = sysUser.getPhonenumber();
                        // 2-短信
                        recordEntity.setSendType(2);
                        recordEntity.setSendObject(phoneNumber);
                        try {
                            if (!StringUtils.isEmpty(phoneNumber)) {
                                smsService.smsSend(phoneNumber, messageEntity.getEventType());
                                recordEntity.setSuccess((byte) 1);
                            }else {
                                recordEntity.setSuccess((byte) 0);
                            }
                        }catch (CustomException e) {
                            recordEntity.setSuccess((byte) 0);
                            throw e;
                        }finally {
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
     * 初始化事件-消息通知
     *
     * @param vo
     */
    public void initMessage (InitMessageVo vo) {
        MessageEntity messageEntity = JSONObject.parseObject(JSONObject.toJSONString(vo), MessageEntity.class);
        baseMapper.insert(messageEntity);
    }

    /**
     * 根据事件处理状态封装查询条件
     *
     * @param queryType
     * @param userId
     * @param status
     * @param queryWrapper
     * @param <T>
     */
    private <T> void HandleEventDealStatus(Integer queryType, Long userId, MessageAppointStatusEnum status, QueryWrapper<T> queryWrapper) {
        // 如果是 查询待指派/处理
        if (1 == queryType) {
            queryWrapper.eq("eventStatus", 0);
            queryWrapper.eq("eventStatus", 1);
        }

        // 根据处理方式状态进行相应的处理
        switch (status) {
            // 当前用户是管理员
            case ADMIN:
                queryWrapper.isNull("eventHappenPersonId");
                break;

            // 指派回管理员，管理员可查询
            case TO_ADMIN :
                queryWrapper.isNull("eventHappenPersonId");
                break;

            // 游客投诉指派人
            case TOURIST_COMPLAINTS_ADMIN :
                queryWrapper.isNull("eventHappenPersonId");
                queryWrapper.eq("eventType", 0);
                break;

            // 应急事件的指派人
            case EMERGENCY_ADMIN :
                queryWrapper.isNull("eventHappenPersonId");
                queryWrapper.eq("eventType", 1);
                break;

            // 既是游客投诉指派人 又是 应急事件的指派人
            case TOURIST_COMPLAINTS_ADMIN_AND_EMERGENCY_ADMIN :
                queryWrapper.isNull("eventHappenPersonId");
                break;

            // 被指派处理人员
            case EVENT_DEAL_PERSON :
                //由于可能有多个处理人员，每个处理人员id采用逗号“,”分割，采用模糊匹配
                queryWrapper.like(!StringUtils.isEmpty(userId), "eventDealPersonId", userId);
                break;

            default:
                break;
        }
    }

    /**
     * 获取处理方式状态
     *
     * @param userId
     * @return
     */
    private MessageAppointStatusEnum findDealStatus(Long userId) {
        // 对接游客投诉的指派人员用户id列表
        Long[] touristComplaintsAdminList = findTouristComplaintsAdminList();

        // 对接应急事件的指派人员用户id列表
        Long[] emergencyAdminList = findEmergencyAdminList();

        // 查询是否管理员
        List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(adminDictType);
        boolean isAdmin = false;
        for (SysDictData dictData : sysDictData) {
            if (userId.equals(dictData.getDictValue())) {
                isAdmin = true;
            }
        }

        // 如果是管理员
        if (isAdmin) {
            return MessageAppointStatusEnum.ADMIN;
        }

        // 如果指派人员列表为空，则指给超管账号
        if (touristComplaintsAdminList.length == 0 && emergencyAdminList.length == 0) {
            return MessageAppointStatusEnum.TO_ADMIN;
        }

        // 是否游客投诉指派人
        boolean isTouristComplaintsAdmin = isFindUserId(userId, touristComplaintsAdminList);

        // 是否应急事件的指派人
        boolean isEmergencyAdmin = isFindUserId(userId, emergencyAdminList);

        // 如果是游客投诉指派人
        if (isTouristComplaintsAdmin) {

            // 如果 既是游客投诉指派人 又是 应急事件的指派人
            if (isEmergencyAdmin) {
                return MessageAppointStatusEnum.TOURIST_COMPLAINTS_ADMIN_AND_EMERGENCY_ADMIN;
            }

            return MessageAppointStatusEnum.TOURIST_COMPLAINTS_ADMIN;
        }
        // 如果是应急事件的指派人
        else if (isEmergencyAdmin) {
            return MessageAppointStatusEnum.EMERGENCY_ADMIN;
        }

        // 被指派处理人员
        return MessageAppointStatusEnum.EVENT_DEAL_PERSON;
    }

    /**
     * 获取应急事件的指派人员用户id列表
     *
     * @return
     */
    private Long[] findEmergencyAdminList() {
        AssignUserInfo assignUserInfo = JSONObject.parseObject(
                JSONObject.toJSONString(redisTemplate.opsForValue().get(CacheKeyContants.KEY_ASSIGN_TRAVEL_COMPLAINT)),
                AssignUserInfo.class);
        List<Long> assignUserIdList = assignUserInfo.getAssignUserIdList();
        Long[] result = new Long[assignUserIdList.size()];
        for (int i = 0; i < assignUserIdList.size(); i++) {
            result[i] = assignUserIdList.get(i);
        }
        return result;
    }

    /**
     * 获取游客投诉的指派人员用户id列表
     *
     * @return
     */
    private Long[] findTouristComplaintsAdminList() {
        EventAppointEntity eventAppointEntity = eventAppointMapper.selectOne(null);
        List<String> appointPersonnel = eventAppointEntity.getAppointPersonnel();
        Long[] result = new Long[appointPersonnel.size()];
        for (int i = 0; i < appointPersonnel.size(); i++) {
            result[i] = Long.parseLong(appointPersonnel.get(i));
        }
        return result;
    }

    /**
     * 查询匹配的UserId
     *
     * @param userId
     * @param list
     * @return
     */
    private boolean isFindUserId(Long userId, Long[] list) {
        // 是否找到标识
        boolean isFind = false;
        for (Long touristComplaintsAdminId : list) {
            if (userId.equals(touristComplaintsAdminId)) {
                isFind = true;
                break;
            }
        }
        return isFind;
    }

}
