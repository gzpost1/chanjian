package com.yjtech.wisdom.tourism.portal.controller.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.event.dto.EmergencyEventUpdateDto;
import com.yjtech.wisdom.tourism.event.entity.EmergencyEventEntity;
import com.yjtech.wisdom.tourism.event.query.EmergencyEventQuery;
import com.yjtech.wisdom.tourism.event.service.EmergencyEventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmergencyEventController {


    @Autowired
    private EmergencyEventService emergencyEventService;


    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<EmergencyEventEntity>> queryForPage(@RequestBody EmergencyEventQuery query) {
        LambdaQueryWrapper queryWrapper = emergencyEventService.getQueryWrapper(query);
        IPage<EmergencyEventEntity> pageResult = emergencyEventService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        emergencyEventService.tranDict(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<EmergencyEventEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        EmergencyEventEntity eventEntity = emergencyEventService.getById(idParam.getId());
        emergencyEventService.tranDict(Lists.newArrayList(eventEntity));
        return JsonResult.success(eventEntity);
    }

    /**
     * 处理
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/handle")
    public JsonResult update(@RequestBody @Valid EmergencyEventUpdateDto updateDto) {
        EmergencyEventEntity eventEntity = emergencyEventService.getById(updateDto.getId());
        AssertUtil.isFalse(Objects.equals(EventContants.PROCESSED,eventEntity.getEventStatus()),"该事件不能被处理");
        BeanUtils.copyProperties(updateDto,eventEntity);
        eventEntity.setEventStatus(EventContants.PROCESSED);
        emergencyEventService.updateById(eventEntity);
        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("/deleted")
    public JsonResult update(@RequestBody @Valid DeleteParam deleteParam) {
         emergencyEventService.removeById(deleteParam.getId());
        return JsonResult.ok();
    }




}
