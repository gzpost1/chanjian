package com.yjtech.wisdom.tourism.portal.controller.systemconfig.menu;


import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsService;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.TbSystemconfigH5MenuParam;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.TbSystemconfigH5MenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.TbSystemconfigH5MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 *
 * H5页面配置
 *
 * @author Mujun
 * @since 2021-11-08
 */
@Slf4j
@RestController
@RequestMapping("/systemconfigH5Menu")
    public class TbSystemconfigH5MenuController extends BaseCurdController<TbSystemconfigH5MenuService,TbSystemconfigH5MenuEntity,TbSystemconfigH5MenuParam> {

    @Autowired
    private SystemconfigChartsService systemconfigChartsService;
    @Autowired
    private SystemconfigMenuService systemconfigMenuService;

    @PreAuthorize("@ss.hasPermi('h5:page:add')")
    @Override
    public JsonResult create( @RequestBody @Validated(BeanValidationGroup.Create.class) TbSystemconfigH5MenuEntity entity) {
        return super.create(entity);
    }

    @PreAuthorize("@ss.hasPermi('h5:page:edit')")
    @Override
    public JsonResult update(@RequestBody @Validated(BeanValidationGroup.Update.class) TbSystemconfigH5MenuEntity entity) {
        return super.update(entity);
    }


    @PreAuthorize("@ss.hasPermi('h5:page:remove')")
    @Override
    public JsonResult delete(@RequestBody @Valid  IdParam idParam) {
         Long id = idParam.getId();
//     //图表库判断是否绑定
        int chartexistnums = Optional.ofNullable(systemconfigChartsService.findMenuIsExistChart(id)).orElse(0);
        //判断大屏架构是否存在应有该页面
        int jiagouexistnum = Optional.ofNullable(systemconfigMenuService.findMenuIsExistJiagou(id)).orElse(0);

        if (chartexistnums > 0 || jiagouexistnum > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "已有大屏菜单或图表关联，请取消关联后删除");
        }

        return super.delete(idParam);
    }

    /**
     * 更新模拟数据状态
     *
     * @param updateStatusParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('h5:page:isMock')")
    @PostMapping("/updateSimulationStatus")
    public JsonResult updateSimulationStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        TbSystemconfigH5MenuEntity updateEntity = service.getById(updateStatusParam.getId());
        updateEntity.setIsSimulation(updateStatusParam.getStatus());
        service.updateById(updateEntity);
        return JsonResult.ok();
    }
}
