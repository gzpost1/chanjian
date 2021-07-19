package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.command.dto.EmergencyPlanTypeCreateDto;
import com.yjtech.wisdom.tourism.command.entity.EmergencyPlanTypeEntity;
import com.yjtech.wisdom.tourism.command.query.EmergencyPlanTypeQuery;
import com.yjtech.wisdom.tourism.command.service.EmergencyPlanTypeService;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 应急预案-类型管理
 *
 * @author xulei
 * @since 2021-07-19
 */
@RestController
@RequestMapping("/emergency/plan/type")
public class EmergencyPlanTypeController {

    @Autowired
    private EmergencyPlanTypeService emergencyPlanTypeService;

    /**
     * 列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<EmergencyPlanTypeEntity>> queryForList(@RequestBody EmergencyPlanTypeQuery query) {
        LambdaQueryWrapper<EmergencyPlanTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EmergencyPlanTypeEntity::getCreateTime);
        List<EmergencyPlanTypeEntity> list = emergencyPlanTypeService.list(queryWrapper);
        return JsonResult.success(list);
    }

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<EmergencyPlanTypeEntity>> queryForPage(@RequestBody EmergencyPlanTypeQuery query) {
        LambdaQueryWrapper<EmergencyPlanTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EmergencyPlanTypeEntity::getCreateTime);
        IPage<EmergencyPlanTypeEntity> pageResult = emergencyPlanTypeService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid EmergencyPlanTypeCreateDto createDto) {
        EmergencyPlanTypeEntity entity = BeanMapper.map(createDto, EmergencyPlanTypeEntity.class);
        emergencyPlanTypeService.save(entity);
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
        //检查是否被引用

        LambdaUpdateWrapper<EmergencyPlanTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(EmergencyPlanTypeEntity::getDeleted, EntityConstants.DELETED);
        updateWrapper.eq(EmergencyPlanTypeEntity::getId, deleteParam.getId());
        emergencyPlanTypeService.update(new EmergencyPlanTypeEntity(), updateWrapper);
        return JsonResult.ok();
    }


}
