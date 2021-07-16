package com.yjtech.wisdom.tourism.event.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.event.dto.EmergencyEventCreateDto;
import com.yjtech.wisdom.tourism.event.entity.EmergencyEventEntity;
import com.yjtech.wisdom.tourism.event.mapper.EmergencyEventMapper;
import com.yjtech.wisdom.tourism.event.query.EmergencyEventQuery;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @since 2021-02-22
 */
@Service
public class EmergencyEventService extends ServiceImpl<EmergencyEventMapper, EmergencyEventEntity> {



    public EmergencyEventEntity create(EmergencyEventCreateDto createDto) {
        AssertUtil.isFalse(createDto.getEventDate().isAfter(LocalDate.now()),"事件日期只能选择早于或等于当前日期的日期");
        EmergencyEventEntity emergencyEventEntity = new EmergencyEventEntity();
        BeanUtils.copyProperties(createDto, emergencyEventEntity);
        //默认事件等级为一般
        emergencyEventEntity.setEventLevel(EventContants.FORTH_CLASS);
        //默认事件状态为未处理
        emergencyEventEntity.setEventStatus(EventContants.UNTREATED);
        //如果有直播 直播状态默认为“直播中”  心跳连接时最新的时间
        if(StringUtils.isNotBlank(createDto.getVideoPath())){
            emergencyEventEntity.setVideoStatus(EventContants.LIVE);
            emergencyEventEntity.setVideoHeartTime(LocalDateTime.now());
        }
        this.save(emergencyEventEntity);
        return emergencyEventEntity;
    }

    public LambdaQueryWrapper getQueryWrapper() {
        LambdaQueryWrapper<EmergencyEventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EmergencyEventEntity::getCreateUser, SecurityUtils.getUserId());
        queryWrapper.orderByDesc(EmergencyEventEntity::getCreateTime);
        return queryWrapper;
    }

    public LambdaQueryWrapper<EmergencyEventEntity> getQueryWrapper(EmergencyEventQuery query) {
        LambdaQueryWrapper<EmergencyEventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), EmergencyEventEntity::getName, query.getName());
        queryWrapper.eq(StringUtils.isNotBlank(query.getEventType()), EmergencyEventEntity::getEventType, query.getEventType());
        queryWrapper.eq(StringUtils.isNotBlank(query.getEventStatus()), EmergencyEventEntity::getEventStatus, query.getEventStatus());
        queryWrapper.ge(Objects.nonNull(query.getBeginTime()), EmergencyEventEntity::getEventDate, query.getBeginTime());
        queryWrapper.le(Objects.nonNull(query.getEndTime()), EmergencyEventEntity::getEventDate, query.getEndTime());
        queryWrapper.orderByAsc(EmergencyEventEntity::getEventStatus);
        queryWrapper.orderByDesc(EmergencyEventEntity::getCreateTime);
        return queryWrapper;
    }

    /**
     * 转化数据字典
     *
     * @param list
     */
    public void tranDict(List<EmergencyEventEntity> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.stream().forEach(
                vo -> {
                    vo.setEventLevelName(DictUtils.getDictLabel(EventContants.EVENT_LEVEL, vo.getEventLevel()));
                    vo.setEventTypeName(DictUtils.getDictLabel(EventContants.EVENT_TYPE, vo.getEventType()));
                    vo.setEventStatusName(DictUtils.getDictLabel(EventContants.EVENT_STATUS, String.valueOf(vo.getEventStatus())));
                    vo.setEventSourceName(DictUtils.getDictLabel(EventContants.EVENT_SOURCE, String.valueOf(vo.getEventSource())));
                });
        Set<Long> userIds = list.stream().filter(vo -> Objects.nonNull(vo.getCreateUser())).map(EmergencyEventEntity::getCreateUser).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        Map<Long, SysUser> longSysUserMap = this.getBaseMapper().queryUserById(userIds);
        if (MapUtils.isNotEmpty(longSysUserMap)) {
            list.stream().map(vo ->{
                SysUser sysUser = longSysUserMap.get(vo.getCreateUser());
                vo.setCreateUserName(Objects.isNull(sysUser) ? null : sysUser.getNickName());
                return vo;
            }).collect(Collectors.toList());
        }
    }
}
