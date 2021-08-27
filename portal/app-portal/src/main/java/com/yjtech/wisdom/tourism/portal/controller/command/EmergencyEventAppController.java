package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.event.EventCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanService;
import com.yjtech.wisdom.tourism.command.vo.event.AppEmergencyPlanVO;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 应急事件 app H5 小程序
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/emergency/event/app")
public class EmergencyEventAppController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EmergencyPlanService emergencyPlanService;


    /**
     * 我的上报  分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPageByCreateUser")
    public JsonResult<IPage<EventListVO>> queryForPage(@RequestBody PageQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperUser();
        IPage<EventListVO> pageResult = eventService.getBaseMapper().queryForList(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        eventService.tranDictVo(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 获得事件数量
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForNumByHandler")
    public JsonResult<Integer> queryForNumByHandler(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperHandler(query);
        return JsonResult.success(eventService.count(queryWrapper));
    }

    /**
     * 待处理 已处理  分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPageByHandler")
    public JsonResult<IPage<EventListVO>> queryForPageByHandler(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperHandler(query);
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
     * 处理
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/handle")
    public JsonResult update(@RequestBody @Valid EventUpdateDto updateDto) {
        eventService.handle(updateDto);
        return JsonResult.ok();
    }

    /**
     * 事件上报
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult<EventEntity> create(@RequestBody @Valid EventCreateDto createDto) {
        EventEntity eventEntity = eventService.create(createDto);
        return JsonResult.success(eventEntity);
    }


}
