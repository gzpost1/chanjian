package com.yjtech.wisdom.tourism.command.service.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.event.EventCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
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
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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
public class EventService extends ServiceImpl<EventMapper, EventEntity> {


    public List<BaseVO> queryEventQuantity(){
        ArrayList<BaseVO> result = Lists.newArrayList();
        LocalDate now = LocalDate.now();
        EventSumaryQuery monthQuery = new EventSumaryQuery();
        monthQuery.setBeginTime( LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN));
        monthQuery.setEndTime( LocalDateTime.of(now, LocalTime.MAX));
        Integer month = this.getBaseMapper().queryQuantity(monthQuery);
        result.add(BaseVO.builder().name("month").value(String.valueOf(month)).build());

        EventSumaryQuery yearQuery = new EventSumaryQuery();
        yearQuery.setBeginTime( LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));
        yearQuery.setEndTime( LocalDateTime.of(now, LocalTime.MAX));
        Integer year = this.getBaseMapper().queryQuantity(yearQuery);
        result.add(BaseVO.builder().name("year").value(String.valueOf(year)).build());

        EventSumaryQuery statusQuery = new EventSumaryQuery();
        statusQuery.setEventStatus(EventContants.UNTREATED);
        Integer untreated = this.getBaseMapper().queryQuantityByStatus(statusQuery);

        statusQuery.setEventStatus(EventContants.PROCESSED);
        Integer processed = this.getBaseMapper().queryQuantityByStatus(statusQuery);
        BigDecimal sum = new BigDecimal(String.valueOf(untreated + processed));

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


    public List<BaseVO> queryEventLevel(EventSumaryQuery query){

        List<BaseVO> list = Optional.ofNullable(this.getBaseMapper().queryEventLevel(query)).orElse(Lists.newArrayList());
        Map<String, String> map = list.stream().collect(Collectors.toMap(item -> item.getName(), item -> item.getValue()));
        BigDecimal sum = list.stream().map(vo -> new BigDecimal(vo.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        //补全数据  没有的类型补为0
        List<SysDictData> dictCache = DictUtils.getDictCache(EventContants.EVENT_LEVEL);
        ArrayList<BaseVO> result = Lists.newArrayList();
        for(SysDictData sysDictData:dictCache){
            if(map.containsKey(sysDictData.getDictValue())){
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal(map.get(sysDictData.getDictValue())), sum, 3).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value(map.get(sysDictData.getDictValue()))
                        .rate(value).build());
            }else {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.calPercent(new BigDecimal("0"), sum, 3).doubleValue();
                result.add(BasePercentVO .builder().name(sysDictData.getDictLabel()).value("0")
                        .rate(value).build());
            }
        }
        return result;
    }

    public EventEntity create(EventCreateDto createDto) {
        AssertUtil.isFalse(createDto.getEventDate().isAfter(LocalDate.now()), "事件日期只能选择早于或等于当前日期的日期");
        EventEntity EventEntity = new EventEntity();
        BeanUtils.copyProperties(createDto, EventEntity);
        //默认事件状态为待指派
        EventEntity.setEventStatus(EventContants.UNASSIGNED);
        this.save(EventEntity);
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
        queryWrapper.eq(Objects.nonNull(query.getStatus()), EventEntity::getStatus, query.getStatus());
        queryWrapper.eq( EventEntity::getDeleted, EntityConstants.NOT_DELETED);
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

        for(AppEventDetail event : list){
            if(CollectionUtils.isNotEmpty(event.getAppointHandlePersonnel())){
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
                ArrayList<String> userName = Lists.newArrayList();
                if(CollectionUtils.isNotEmpty(vo.getAppointHandlePersonnel())){
                    for(String userId : vo.getAppointHandlePersonnel()){
                        SysUser sysUser1 = longSysUserMap.get(Long.valueOf(userId));
                        userName.add(Objects.isNull(sysUser1) ? null : sysUser.getNickName());
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
}
