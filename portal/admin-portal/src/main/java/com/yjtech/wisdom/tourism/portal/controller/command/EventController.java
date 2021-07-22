package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.event.EventAppointDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventAppointService;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanService;
import com.yjtech.wisdom.tourism.command.vo.event.AppEmergencyPlanVO;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 应急事件  后端
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/emergency/event")
public class EventController {


    @Autowired
    private EventService eventService;

    @Autowired
    private EventAppointService eventAppointService;

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<EventListVO>> queryForPage(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapper(query);
        IPage<EventListVO> pageResult = eventService.getBaseMapper().queryForList(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        eventService.tranDictVo(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('event:queryForDetail')")
    @PostMapping("/queryForDetail")
    public JsonResult<AppEventDetail> queryForDetail(@RequestBody @Valid IdParam idParam) {
        AppEventDetail appEventDetail = eventService.getBaseMapper().queryForDetail(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(appEventDetail),"该记录不存在");
        eventService.tranDict(Lists.newArrayList(appEventDetail));

        //设置预案
        if(Objects.nonNull(appEventDetail.getPlanId())){
            EmergencyPlanEntity entity = emergencyPlanService.getById(appEventDetail.getPlanId());
            emergencyPlanService.tranDic(Lists.newArrayList(entity));
            AppEmergencyPlanVO planVO = new AppEmergencyPlanVO();
            BeanUtils.copyProperties(entity, planVO);
            appEventDetail.setPlan(planVO);
        }
        //当前登录人是否是指定处理人员
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("JSON_CONTAINS(appoint_handle_personnel,JSON_ARRAY({0}))",String.valueOf(SecurityUtils.getUserId()));
        appEventDetail.setAppointPersonnel(eventService.count(queryWrapper) >0);
        return JsonResult.success(appEventDetail);
    }

    /**
     * 指派
     *
     * @param dto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('event:appoint')")
    @PostMapping("/appoint")
    public JsonResult appoint(@RequestBody @Valid EventAppointDto dto) {
        //判断是否在指派人员中
        Boolean isAppoint = eventAppointService.queryUserAppoint();
        //判断是否有按钮权限
        boolean admin = SecurityUtils.isAdmin(SecurityUtils.getUserId());
        AssertUtil.isFalse(!isAppoint && !admin,"请在配置指派人员中配置或者使用管理员账号");
        synchronized(this) {
            EventEntity eventEntity = eventService.getById(dto.getId());
            AssertUtil.isFalse(Objects.isNull(eventEntity), "该事件不存在");
            AssertUtil.isFalse(Objects.equals(eventEntity.getAppointStatus(), EventContants.ASSIGNED), "该事件已指派");
            EventEntity entity = BeanMapper.map(dto, EventEntity.class);
            entity.setAppointStatus(EventContants.ASSIGNED);
            eventService.updateById(entity);
        }
        //TODO 发送消息

        return JsonResult.ok();
    }

    /**
     * 处理
     *
     * @param updateDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('event:handle')")
    @PostMapping("/handle")
    public JsonResult handle(@RequestBody @Valid EventUpdateDto updateDto) {
        eventService.handle(updateDto);
        return JsonResult.ok();
    }
    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('event:deleted')")
    @PostMapping("/deleted")
    public JsonResult update(@RequestBody @Valid DeleteParam deleteParam) {
         eventService.removeById(deleteParam.getId());
        return JsonResult.ok();
    }

    /**
     * 更新状态
     * @param updateStatusParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('event:updateStatus')")
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        EventEntity entity = eventService.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        eventService.updateById(entity);
        return JsonResult.ok();
    }


}
