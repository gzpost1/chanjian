package com.yjtech.wisdom.tourism.portal.controller.systemconfig.menu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemConfigChartQueryPageDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsService;
import com.yjtech.wisdom.tourism.systemconfig.chart.vo.SystemconfigChartsVo;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuPageQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigAppMenuService;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuPageVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 后台管理-系统配置-大屏菜单配置
 *
 * @author 李波
 * @description: 后台管理-系统配置-大屏菜单配置
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/systemconfig/appmenu")
public class SystemConfigAppMenuController {
    @Autowired
    private SystemconfigChartsService systemconfigChartsService;
    @Autowired
    private SystemconfigAppMenuService systemconfigAppMenuService;
    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<SystemconfigMenuPageVo>> queryForPage(@RequestBody SystemconfigMenuPageQueryDto query) {
        return JsonResult.success(systemconfigAppMenuService.queryForPage(query));
    }

    /**
     * 根据页面类型获取图表
     *
     * @param
     * @return
     */
    @PostMapping("/queryChartListForMenu")
    public JsonResult<IPage<SystemconfigChartsVo>> queryChartListForMenu(@RequestBody @Valid SystemConfigChartQueryPageDto queryDto) {
        return JsonResult.success(systemconfigChartsService.queryForPage(queryDto));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<SystemconfigMenuDetailDto> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(systemconfigAppMenuService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('datav:page:add')")
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid SystemconfigMenuCreateDto createDto) {

        SystemconfigMenuEntity entity = new SystemconfigMenuEntity();
        BeanUtils.copyProperties(createDto, entity);
        entity.setId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        entity.setIsShow(Byte.valueOf("1"));
        entity.setIsSimulation(Byte.valueOf("1"));

        //查找菜单的排序
        List<SysDictData> sysDictData = sysDictTypeService.selectDictDataByType("config_menu_type");
        if (CollectionUtils.isNotEmpty(sysDictData)) {
            for (SysDictData sysDictDatum : sysDictData) {
                if (StringUtils.equals(sysDictDatum.getDictValue(), entity.getMenuType())) {
                    Long dictSort = sysDictDatum.getDictSort();

                    entity.setSortNum(Integer.valueOf(String.valueOf(dictSort)));
                }
            }
        } else {
            entity.setSortNum(0);
        }

        systemconfigAppMenuService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('datav:page:edit')")
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid SystemconfigMenuUpdateDto updateDto) {

        SystemconfigMenuEntity entity = new SystemconfigMenuEntity();
        BeanUtils.copyProperties(updateDto, entity);

        systemconfigAppMenuService.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     * @todo
     */
    @PreAuthorize("@ss.hasPermi('datav:page:remove')")
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {

        //首先校验模板是否被使用，如果已经被使用则不可删除
        int tempexistnums = Optional.ofNullable(systemconfigChartsService.findMenuIsExistChart(idParam.getId())).orElse(0);
        //判断大屏架构是否存在应有该页面
        int jiagouexistnum = Optional.ofNullable(systemconfigAppMenuService.findMenuIsExistJiagou(idParam.getId())).orElse(0);

        if (tempexistnums > 0 || jiagouexistnum > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "已有大屏菜单或图表关联，请取消关联后删除");
        }

        systemconfigAppMenuService.removeById(idParam.getId());

        return JsonResult.ok();
    }

    /**
     * 图表配置-跳转页面
     *
     * @param
     * @return
     */
    @PostMapping("/queryPageList")
    public JsonResult<List<BaseVO>> queryForDetail() {
        return JsonResult.success(systemconfigAppMenuService.queryPageList());
    }

    /**
     * 更新模拟数据状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateSimulationStatus")
    public JsonResult updateSimulationStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        systemconfigAppMenuService.updateSimulationStatus(updateStatusParam);
        return JsonResult.ok();
    }
}
