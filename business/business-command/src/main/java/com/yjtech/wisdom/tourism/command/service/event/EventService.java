package com.yjtech.wisdom.tourism.command.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.event.EventAppointDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventAppointEntity;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.mapper.event.EventMapper;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.constant.TemplateConstants;
import com.yjtech.wisdom.tourism.common.enums.MessageEventTypeEnum;
import com.yjtech.wisdom.tourism.common.enums.MessagePlatformTypeEnum;
import com.yjtech.wisdom.tourism.common.sms.MessageCall;
import com.yjtech.wisdom.tourism.common.sms.MessageCallDto;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.framework.manager.AsyncManager;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.message.admin.service.MessageMangerService;
import com.yjtech.wisdom.tourism.message.admin.vo.SendMessageVo;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 应急事件 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Service
public class EventService extends ServiceImpl<EventMapper, EventEntity> implements MessageCall {

    @Autowired
    private MessageMangerService messageMangerService;

    @Autowired
    private EventAppointService eventAppointService;

    public List<BaseVO> queryEventQuantity(EventSumaryQuery query) {
        ArrayList<BaseVO> result = Lists.newArrayList();

        EventSumaryQuery totalQuery = new EventSumaryQuery();
        Integer total = this.getBaseMapper().queryQuantity(totalQuery);
        totalQuery.setBeginTime(query.getBeginTime());
        totalQuery.setEndTime(query.getEndTime());
        result.add(BaseVO.builder().name("total").value(String.valueOf(total)).build());

        EventSumaryQuery statusQuery = new EventSumaryQuery();
        statusQuery.setEventStatus(EventContants.UNTREATED);
        statusQuery.setBeginTime(query.getBeginTime());
        statusQuery.setEndTime(query.getEndTime());
        Integer untreated = this.getBaseMapper().queryQuantityByStatus(statusQuery);

        statusQuery.setEventStatus(EventContants.PROCESSED);
        Integer processed = this.getBaseMapper().queryQuantityByStatus(statusQuery);
        BigDecimal sum = new BigDecimal(String.valueOf(total));

        double untreatedRate = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal(String.valueOf(untreated)), sum, 3).doubleValue();
        result.add(BasePercentVO.builder().name("untreated").value(String.valueOf(untreated))
                .rate(untreatedRate).build());

        double processedRate = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal(String.valueOf(processed)), sum, 3).doubleValue();
        result.add(BasePercentVO.builder().name("processed").value(String.valueOf(processed))
                .rate(processedRate).build());
        return result;
    }

    public List<BaseVO> queryEventType(EventSumaryQuery query) {

        List<BasePercentVO> list = Optional.ofNullable(this.getBaseMapper().queryEventType(query)).orElse(Lists.newArrayList());
        BigDecimal sum = list.stream().map(vo -> new BigDecimal(vo.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        //补全数据  没有的类型补为0
        Map<String, String> map = list.stream().collect(Collectors.toMap(item -> item.getName(), item -> item.getValue()));
        List<SysDictData> dictCache = DictUtils.getDictCache(EventContants.EVENT_TYPE);
        ArrayList<BaseVO> result = Lists.newArrayList();
        for (SysDictData sysDictData : dictCache) {
            if (map.containsKey(sysDictData.getDictValue())) {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal(map.get(sysDictData.getDictValue())), sum, 3).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value(map.get(sysDictData.getDictValue()))
                        .rate(value).build());
            } else {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal("0"), sum, 3).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value("0")
                        .rate(value).build());
            }
        }
        return result;
    }


    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {

        List<BaseVO> list = Optional.ofNullable(this.getBaseMapper().queryEventLevel(query)).orElse(Lists.newArrayList());
        Map<String, String> map = list.stream().collect(Collectors.toMap(item -> item.getName(), item -> item.getValue()));
        BigDecimal sum = list.stream().map(vo -> new BigDecimal(vo.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        //补全数据  没有的类型补为0
        List<SysDictData> dictCache = DictUtils.getDictCache(EventContants.EVENT_LEVEL);
        ArrayList<BaseVO> result = Lists.newArrayList();
        for (SysDictData sysDictData : dictCache) {
            if (map.containsKey(sysDictData.getDictValue())) {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal(map.get(sysDictData.getDictValue())), sum, 3).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value(map.get(sysDictData.getDictValue()))
                        .rate(value).build());
            } else {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal("0"), sum, 3).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value("0")
                        .rate(value).build());
            }
        }
        return result;
    }

    public EventEntity create(EventCreateDto createDto) {
        AssertUtil.isFalse(createDto.getEventDate().isAfter(LocalDate.now()), "事件日期只能选择早于或等于当前日期的日期");
        EventEntity eventEntity = new EventEntity();
        BeanUtils.copyProperties(createDto, eventEntity);
        //默认事件状态为待指派
        eventEntity.setEventStatus(EventContants.UNASSIGNED);
        this.save(eventEntity);

        AsyncManager.me().execute(
                new TimerTask() {
                    @Override
                    public void run() {
                        //构建用户id列表
                        List<Long> eventDealPersonIdArray = Lists.newArrayList();
                        //构建发送类型
                        List<Integer> sendType = Lists.newArrayList();
                        // 新增时，若配置了指派人员且勾选消息通知的，给指派人员发送后台通知，若未配置，给超级管理员发生通知。重新编辑提交不进行消息通知
                        EventAppointEntity eventAppointEntity = eventAppointService.getOne(null);
                        // 未配置指派人员  未勾选消息  都发送给超级
                        if (Objects.isNull(eventAppointEntity) || CollectionUtils.isEmpty(eventAppointEntity.getAppointPersonnel()) || CollectionUtils.isEmpty(eventAppointEntity.getNotice())) {
                            eventDealPersonIdArray.add(messageMangerService.queryAdminId());
                            // 默认发送后台消息
                            sendType.add(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_BACK.getValue().intValue());
                        }  else {
                            List<String> appointPersonnel = eventAppointEntity.getAppointPersonnel();
                            eventDealPersonIdArray.addAll(appointPersonnel.stream().map(vo -> Long.valueOf(vo)).collect(Collectors.toList()));
                            sendType.addAll(eventAppointEntity.getNotice().stream().map(vo -> Integer.valueOf(vo)).collect(Collectors.toList()));
                        }

                        tranDictEntity(Lists.newArrayList(eventEntity));
                        String platformTemplate = MessageFormat.format(
                                TemplateConstants.TEMPLATE_PLATFORM_EVENT_INSERT,
                                eventEntity.getName(),
                                eventEntity.getCreateUserName(),
                                eventEntity.getEventTypeName(),
                                eventEntity.getEventDate(),
                                eventEntity.getAddress());
                        messageMangerService.sendMessage(new SendMessageVo(sendType.toArray(new Integer[0]), eventEntity.getId(),
                                MessageEventTypeEnum.MESSAGE_EVENT_TYPE_EMERGENCY_EVENT.getValue().intValue(),
                                null, null, platformTemplate, null,
                                eventDealPersonIdArray.toArray(new Long[0])));
                    }
                }
        );
        return eventEntity;
    }


    public void appoint(EventAppointDto dto) {
        //判断是否在指派人员中
        Boolean isAppoint = eventAppointService.queryUserAppoint();
        //判断是否有按钮权限
        boolean admin = SecurityUtils.isAdmin(SecurityUtils.getUserId());
        AssertUtil.isFalse(!isAppoint && !admin,"请在配置指派人员中配置或者使用管理员账号");
        EventEntity eventEntity;
        synchronized(this) {
            eventEntity = this.getById(dto.getId());
            AssertUtil.isFalse(Objects.isNull(eventEntity), "该事件不存在");
            AssertUtil.isFalse(!Objects.equals(eventEntity.getEventStatus(), EventContants.UNASSIGNED), "该事件已指派");
            EventEntity entity = BeanMapper.map(dto, EventEntity.class);
            entity.setEventStatus(EventContants.UNTREATED);
            this.updateById(entity);
        }
        AsyncManager.me().execute(
                new TimerTask() {
                    @Override
                    public void run() {
                        if (CollectionUtils.isEmpty(dto.getNotice())){
                            return;
                        }

                        tranDictEntity(Lists.newArrayList(eventEntity));

                        //构建用户id列表
                        List<Long> eventDealPersonIdArray = Lists.newArrayList();
                        List<String> appointHandlePersonnel = dto.getAppointHandlePersonnel();
                        eventDealPersonIdArray.addAll(appointHandlePersonnel.stream().map(vo -> Long.valueOf(vo)).collect(Collectors.toList()));
                        //构建发送类型
                        List<Integer> sendType = Lists.newArrayList();
                        List<String> notice = dto.getNotice();
                        sendType.addAll(notice.stream().map(vo -> Integer.valueOf(vo)).collect(Collectors.toList()));

                        String platformTemplate =null, appTemplate = null,messageTemplate = null;
                        if(sendType.contains(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_BACK.getValue().intValue())){
                             platformTemplate = MessageFormat.format(
                                    TemplateConstants.TEMPLATE_PLATFORM_EVENT_ASSIGN,
                                    eventEntity.getName(),
                                    eventEntity.getCreateUserName(),
                                    eventEntity.getEventTypeName(),
                                    eventEntity.getEventDate(),
                                    eventEntity.getAddress());
                        }

                        if(sendType.contains(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_APP.getValue().intValue())){
                             appTemplate = MessageFormat.format(
                                    TemplateConstants.TEMPLATE_APP_EVENT_ASSIGN,
                                    eventEntity.getName());
                        }

                        if(sendType.contains(MessagePlatformTypeEnum.MESSAGE_PLATFORM_TYPE_SHORT_MESSAGE.getValue().intValue())){
                             messageTemplate = MessageFormat.format(
                                    TemplateConstants.TEMPLATE_MESSAGE_EVENT_ASSIGN,
                                    eventEntity.getName());
                        }

                        messageMangerService.sendMessage(new SendMessageVo(sendType.toArray(new Integer[0]), eventEntity.getId(),
                                MessageEventTypeEnum.MESSAGE_EVENT_TYPE_EMERGENCY_EVENT.getValue().intValue(),
                                appTemplate, appTemplate, platformTemplate, Arrays.asList(messageTemplate),
                                eventDealPersonIdArray.toArray(new Long[0])));
                    }
                }
        );
    }


    public void handle(EventUpdateDto updateDto) {
        //只有指定处理人才能处理该事件
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EventEntity::getId, updateDto.getId());
        queryWrapper.apply("JSON_CONTAINS(appoint_handle_personnel,JSON_ARRAY({0}))", String.valueOf(SecurityUtils.getUserId()));
        AssertUtil.isFalse(this.count(queryWrapper) == 0, "只有指定处理人员才能处理该事件");
        //有多个处理人的情况 避免后一个将前一个覆盖
        synchronized (this) {
            EventEntity eventEntity = this.getById(updateDto.getId());
            AssertUtil.isFalse(Objects.equals(EventContants.PROCESSED, eventEntity.getEventStatus()), "已有指定处理人员处理");
            BeanUtils.copyProperties(updateDto, eventEntity);
            eventEntity.setEventStatus(EventContants.PROCESSED);
            eventEntity.setActualHandlePersonnel(SecurityUtils.getUserId());
            this.updateById(eventEntity);
        }

    }

    /**
     * 查看当前用户上报的事件
     *
     * @return
     */
    public LambdaQueryWrapper getQueryWrapperUser() {
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EventEntity::getCreateUser, SecurityUtils.getUserId());
        queryWrapper.orderByDesc(EventEntity::getCreateTime);
        return queryWrapper;
    }

    /**
     * 查看当前用户是处理人的事件
     *
     * @return
     */
    public LambdaQueryWrapper getQueryWrapperHandler(EventQuery query) {
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(query.getEventStatus()), EventEntity::getEventStatus, query.getEventStatus());
        queryWrapper.apply("JSON_CONTAINS(appoint_handle_personnel,JSON_ARRAY({0}))", String.valueOf(SecurityUtils.getUserId()));
        queryWrapper.orderByDesc(EventEntity::getCreateTime);
        return queryWrapper;
    }

    public LambdaQueryWrapper<EventEntity> getQueryWrapper(EventQuery query) {
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), EventEntity::getName, query.getName());
        queryWrapper.eq(StringUtils.isNotBlank(query.getEventType()), EventEntity::getEventType, query.getEventType());
        queryWrapper.eq(StringUtils.isNotBlank(query.getEventStatus()), EventEntity::getEventStatus, query.getEventStatus());
        queryWrapper.eq(Objects.nonNull(query.getStatus()), EventEntity::getStatus, query.getStatus());
        queryWrapper.eq(EventEntity::getDeleted, EntityConstants.NOT_DELETED);
        queryWrapper.ge(Objects.nonNull(query.getBeginTime()), EventEntity::getEventDate, query.getBeginTime());
        queryWrapper.le(Objects.nonNull(query.getEndTime()), EventEntity::getEventDate, query.getEndTime());
        queryWrapper.orderByAsc(EventEntity::getEventStatus);
        queryWrapper.orderByDesc(EventEntity::getCreateTime);
        return queryWrapper;
    }

    /**
     * 转化数据字典
     *
     * @param list
     */
    public void tranDict(List<AppEventDetail> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(
                vo -> {
                    vo.setEventLevelName(DictUtils.getDictLabel(EventContants.EVENT_LEVEL, vo.getEventLevel()));
                    vo.setEventTypeName(DictUtils.getDictLabel(EventContants.EVENT_TYPE, vo.getEventType()));
                    vo.setEventStatusName(DictUtils.getDictLabel(EventContants.EVENT_STATUS, String.valueOf(vo.getEventStatus())));
                });
        Set<Long> userIds = list.stream().filter(vo -> Objects.nonNull(vo.getCreateUser())).map(AppEventDetail::getCreateUser).collect(Collectors.toSet());

        for (AppEventDetail event : list) {
            if (CollectionUtils.isNotEmpty(event.getAppointHandlePersonnel())) {
                userIds.addAll(new HashSet(event.getAppointHandlePersonnel()));
            }
        }
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        Map<Long, SysUser> longSysUserMap = this.getBaseMapper().queryUserById(userIds);
        if (MapUtils.isNotEmpty(longSysUserMap)) {
            list.stream().map(vo -> {
                SysUser sysUser = longSysUserMap.get(vo.getCreateUser());
                vo.setCreateUserName(Objects.isNull(sysUser) ? null : sysUser.getNickName());

                SysUser actualHandlePersonnel = longSysUserMap.get(vo.getActualHandlePersonnel());
                vo.setActualHandlePersonnelName(Objects.isNull(actualHandlePersonnel) ? null : actualHandlePersonnel.getNickName());

                ArrayList<String> userName = Lists.newArrayList();
                if (CollectionUtils.isNotEmpty(vo.getAppointHandlePersonnel())) {
                    for (String userId : vo.getAppointHandlePersonnel()) {
                        SysUser sysUser1 = longSysUserMap.get(Long.valueOf(userId));
                        userName.add(Objects.isNull(sysUser1) ? null : sysUser1.getNickName());
                    }
                }
                vo.setAppointHandlePersonnel(userName);
                return vo;
            }).collect(Collectors.toList());
        }
    }

    /**
     * 转化数据字典
     *
     * @param list
     */
    public void tranDictEntity(List<EventEntity> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(
                vo -> {
                    vo.setEventLevelName(DictUtils.getDictLabel(EventContants.EVENT_LEVEL, vo.getEventLevel()));
                    vo.setEventTypeName(DictUtils.getDictLabel(EventContants.EVENT_TYPE, vo.getEventType()));
                    vo.setEventStatusName(DictUtils.getDictLabel(EventContants.EVENT_STATUS, String.valueOf(vo.getEventStatus())));
                });
        Set<Long> userIds = list.stream().filter(vo -> Objects.nonNull(vo.getCreateUser())).map(EventEntity::getCreateUser).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        Map<Long, SysUser> longSysUserMap = this.getBaseMapper().queryUserById(userIds);
        if (MapUtils.isNotEmpty(longSysUserMap)) {
            list.stream().map(vo -> {
                SysUser sysUser = longSysUserMap.get(vo.getCreateUser());
                vo.setCreateUserName(Objects.isNull(sysUser) ? null : sysUser.getNickName());
                return vo;
            }).collect(Collectors.toList());
        }
    }


    /**
     * 转化数据字典
     *
     * @param list
     */
    public void tranDictVo(List<EventListVO> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(
                vo -> {
                    vo.setEventTypeName(DictUtils.getDictLabel(EventContants.EVENT_TYPE, vo.getEventType()));
                    vo.setEventStatusName(DictUtils.getDictLabel(EventContants.EVENT_STATUS, String.valueOf(vo.getEventStatus())));
                    vo.setStatusName(Objects.equals(EntityConstants.ENABLED, vo.getStatus()) ? "启用" : "停用");
                });
    }


    @Override
    public List<MessageCallDto> queryEvent(Long[] ids) {
        List<MessageCallDto> messageCallDtos = this.getBaseMapper().queryEvent(ids);
        if (CollectionUtils.isEmpty(messageCallDtos)) {
            return messageCallDtos;
        }
        Set<Long> userIds = messageCallDtos.stream().filter(vo -> Objects.nonNull(vo.getEventHappenPerson())).map(vo -> Long.valueOf(vo.getEventHappenPerson())).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userIds)) {
            return messageCallDtos;
        }
        Map<Long, SysUser> longSysUserMap = this.getBaseMapper().queryUserById(userIds);
        if (MapUtils.isNotEmpty(longSysUserMap)) {
            messageCallDtos.stream().map(vo -> {
                SysUser sysUser = longSysUserMap.get(Long.valueOf(vo.getEventHappenPerson()));
                vo.setEventHappenPerson(Objects.isNull(sysUser) ? null : sysUser.getNickName());
                return vo;
            }).collect(Collectors.toList());
        }
        return messageCallDtos;
    }
}
