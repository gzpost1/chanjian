package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.command.dto.plan.EmergencyPlanTypeUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanTypeEntity;
import com.yjtech.wisdom.tourism.command.query.plan.EmergencyPlanTypeQuery;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanService;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanTypeService;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.ValidList;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.apache.commons.collections.CollectionUtils;
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

    @Autowired
    private EmergencyPlanService emergencyPlanService;

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
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public JsonResult create(@RequestBody @Valid ValidList<EmergencyPlanTypeUpdateDto> dtos) {
        AssertUtil.isFalse(CollectionUtils.isEmpty(dtos), "不能为空");
        List<EmergencyPlanTypeEntity> emergencyPlanTypeEntities = BeanMapper.mapList(dtos, EmergencyPlanTypeEntity.class);
        emergencyPlanTypeService.saveOrUpdateBatch(emergencyPlanTypeEntities);
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
        int count = emergencyPlanService.count(new LambdaQueryWrapper<EmergencyPlanEntity>().eq(EmergencyPlanEntity::getType, deleteParam.getId()));
        AssertUtil.isFalse(count >= 1, "该类型被引用不能被删除");
        LambdaUpdateWrapper<EmergencyPlanTypeEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(EmergencyPlanTypeEntity::getDeleted, EntityConstants.DELETED);
        updateWrapper.eq(EmergencyPlanTypeEntity::getId, deleteParam.getId());
        emergencyPlanTypeService.update(new EmergencyPlanTypeEntity(), updateWrapper);
        return JsonResult.ok();
    }


}
