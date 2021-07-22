package com.yjtech.wisdom.tourism.command.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.command.dto.event.EventCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.mapper.event.EventMapper;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
public class EventService extends ServiceImpl<EventMapper, EventEntity> {

    public EventEntity create(EventCreateDto createDto) {
        AssertUtil.isFalse(createDto.getEventDate().isAfter(LocalDate.now()), "事件日期只能选择早于或等于当前日期的日期");
        EventEntity EventEntity = new EventEntity();
        BeanUtils.copyProperties(createDto, EventEntity);
        //默认事件状态为未处理
        EventEntity.setEventStatus(EventContants.UNTREATED);
        //默认为未指派
        EventEntity.setAppointStatus(EventContants.UNASSIGNED);
        this.save(EventEntity);

        //TODO 发送消息
        return EventEntity;
    }

    public void handle( EventUpdateDto updateDto) {
        //只有指定处理人才能处理该事件
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EventEntity::getId,updateDto.getId());
        queryWrapper.apply("JSON_CONTAINS(appoint_handle_personnel,JSON_ARRAY({0}))",String.valueOf(SecurityUtils.getUserId()));
        AssertUtil.isFalse( this.count(queryWrapper) == 0,"只有指定处理人员才能处理该事件");
        //有多个处理人的情况 避免后一个将前一个覆盖
        synchronized(this){
            EventEntity eventEntity = this.getById(updateDto.getId());
            AssertUtil.isFalse(Objects.equals(EventContants.PROCESSED,eventEntity.getEventStatus()),"已有指定处理人员处理");
            BeanUtils.copyProperties(updateDto,eventEntity);
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
        queryWrapper.ge(Objects.nonNull(query.getBeginTime()), EventEntity::getEventDate, query.getBeginTime());
        queryWrapper.le(Objects.nonNull(query.getEndTime()), EventEntity::getEventDate, query.getEndTime());
        queryWrapper.eq(StringUtils.isNotBlank(query.getAppointStatus()), EventEntity::getAppointStatus, query.getAppointStatus());
        queryWrapper.orderByAsc(EventEntity::getAppointStatus);
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
                    vo.setAppointStatusName(DictUtils.getDictLabel(EventContants.EVENT_APPOINT_STATUS, vo.getAppointStatus()));
                });
    }
}
