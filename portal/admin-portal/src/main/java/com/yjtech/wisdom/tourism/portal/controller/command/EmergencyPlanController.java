package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.command.dto.EmergencyPlanCreateDto;
import com.yjtech.wisdom.tourism.command.dto.EmergencyPlanUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.query.EmergencyPlanQuery;
import com.yjtech.wisdom.tourism.command.service.EmergencyPlanService;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.DeleteParam;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 应急预案 前端控制器
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@RestController
@RequestMapping("/emergency/plan")
public class EmergencyPlanController {

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    @PostMapping("/queryForList")
    public JsonResult<List<EmergencyPlanEntity>> queryForList(@RequestBody EmergencyPlanQuery query) {
        LambdaQueryWrapper<EmergencyPlanEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EmergencyPlanEntity::getCreateTime);
        List<EmergencyPlanEntity> list = emergencyPlanService.list(queryWrapper);
        emergencyPlanService.tranDic(list);
        return JsonResult.success(list);
    }

    @PostMapping("/queryForPage")
    public JsonResult<IPage<EmergencyPlanEntity>> queryForPage(@RequestBody EmergencyPlanQuery query) {
        LambdaQueryWrapper<EmergencyPlanEntity> queryWrapper =emergencyPlanService.getQueryWrapper(query);
        IPage<EmergencyPlanEntity> pageResult = emergencyPlanService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        emergencyPlanService.tranDic(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<EmergencyPlanEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(emergencyPlanService.getById(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid EmergencyPlanCreateDto createDto) {
        EmergencyPlanEntity entity = BeanMapper.map(createDto, EmergencyPlanEntity.class);
        emergencyPlanService.save(entity);
        return JsonResult.ok();
    }

    /**
     * 修改
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid EmergencyPlanUpdateDto updateDto) {
        EmergencyPlanEntity entity = BeanMapper.map(updateDto, EmergencyPlanEntity.class);
        emergencyPlanService.updateById(entity);
        return JsonResult.ok();
    }

    /**
     * 删除
     * @param deleteParam
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        LambdaUpdateWrapper<EmergencyPlanEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(EmergencyPlanEntity::getDeleted, EntityConstants.DELETED);
        updateWrapper.eq(EmergencyPlanEntity::getId, deleteParam.getId());
        emergencyPlanService.update(new EmergencyPlanEntity(), updateWrapper);
        return JsonResult.ok();
    }


}
