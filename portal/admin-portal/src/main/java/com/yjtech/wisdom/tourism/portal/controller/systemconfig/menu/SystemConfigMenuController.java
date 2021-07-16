package com.yjtech.wisdom.tourism.portal.controller.systemconfig.menu;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemConfigChartQueryPageDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsService;
import com.yjtech.wisdom.tourism.systemconfig.chart.vo.SystemconfigChartsVo;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuSortDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuPageQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigMenuUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuPageVo;
import com.yjtech.wisdom.tourism.systemconfig.temp.service.SystemconfigTempService;
import com.yjtech.wisdom.tourism.systemconfig.temp.vo.SystemconfigTempVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理-系统配置-大屏菜单配置
 *
 * @author 李波
 * @description: 后台管理-系统配置-大屏菜单配置
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/systemconfig/menu")
public class SystemConfigMenuController {
    @Autowired
    private SystemconfigChartsService systemconfigChartsService;
    @Autowired
    private SystemconfigMenuService systemconfigMenuService;
    @Autowired
    private SystemconfigTempService systemconfigTempService;
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
        return JsonResult.success(systemconfigMenuService.queryForPage(query));
    }

    /**
     * 菜单展示排序-列表查询
     *
     * @param
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<SystemconfigMenuPageVo>> queryForList() {
        return JsonResult.success(systemconfigMenuService.queryForList());
    }

    /**
     * 菜单展示排序-保存列表
     *
     * @param
     * @return
     */
    @PostMapping("/saveSortList")
    public JsonResult saveSortList(@RequestBody @Valid List<MenuSortDto> list) {

        systemconfigMenuService.saveSortList(list);

        return JsonResult.ok();
    }

    /**
     * 获取模板列表
     *
     * @param
     * @return
     */
    @PostMapping("/queryForTempList")
    public JsonResult<List<SystemconfigTempVo>> queryForTempList() {
        return JsonResult.success(systemconfigTempService.queryForTempList());
    }

    /**
     * 根据大屏菜单类型获取图表
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
        return JsonResult.success(systemconfigMenuService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
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

        systemconfigMenuService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid SystemconfigMenuUpdateDto updateDto) {

        SystemconfigMenuEntity entity = new SystemconfigMenuEntity();
        BeanUtils.copyProperties(updateDto, entity);

        systemconfigMenuService.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {

        systemconfigMenuService.removeById(idParam.getId());

        return JsonResult.ok();
    }

    /**
     * 更新模拟数据状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateSimulationStatus")
    public JsonResult updateSimulationStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        systemconfigMenuService.updateSimulationStatus(updateStatusParam);
        return JsonResult.ok();
    }

    /**
     * 更新展示状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateShowStatus")
    public JsonResult updateShowStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        systemconfigMenuService.updateShowStatus(updateStatusParam);
        return JsonResult.ok();
    }

}
