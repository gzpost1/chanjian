package com.yjtech.wisdom.tourism.portal.controller.alarm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.alarm.dto.AlarmPostCreateDto;
import com.yjtech.wisdom.tourism.resource.alarm.dto.AlarmPostUpdateDto;
import com.yjtech.wisdom.tourism.resource.alarm.entity.AlarmPostEntity;
import com.yjtech.wisdom.tourism.resource.alarm.query.AlarmPostQuery;
import com.yjtech.wisdom.tourism.resource.alarm.service.AlarmPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 报警柱
 *
 * @author xulei
 * @since 2021-07-06
 */
@RestController
@RequestMapping("/alarm-post")
public class AlarmPostController {

    @Autowired
    private AlarmPostService alarmPostService;


    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<AlarmPostEntity>> queryForPage(@RequestBody AlarmPostQuery query) {
        LambdaQueryWrapper<AlarmPostEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), AlarmPostEntity::getName, query.getName());
        queryWrapper.eq(Objects.nonNull(query.getStatus()), AlarmPostEntity::getStatus, query.getStatus());
        queryWrapper.orderByAsc(AlarmPostEntity::getCreateTime);
        IPage<AlarmPostEntity> pageResult = alarmPostService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<AlarmPostEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(alarmPostService.getById(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid AlarmPostCreateDto createDto) {
        alarmPostService.create(createDto);
        return JsonResult.ok();
    }

    /**
     * 更新
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid AlarmPostUpdateDto updateDto) {
        AlarmPostEntity entity = BeanMapper.map(updateDto, AlarmPostEntity.class);
        alarmPostService.updateById(entity);
        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        alarmPostService.removeById(deleteParam.getId());
        return JsonResult.ok();
    }


    /**
     * 状态更新
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        AlarmPostEntity entity = alarmPostService.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        alarmPostService.updateById(entity);
        return JsonResult.ok();
    }

}
