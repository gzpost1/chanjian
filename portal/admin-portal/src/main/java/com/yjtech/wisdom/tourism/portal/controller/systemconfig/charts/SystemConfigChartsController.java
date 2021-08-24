package com.yjtech.wisdom.tourism.portal.controller.systemconfig.charts;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemConfigChartQueryPageDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemconfigChartsCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemconfigChartsUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListQueryVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.list.SystemconfigChartsListVo;
import com.yjtech.wisdom.tourism.systemconfig.chart.entity.SystemconfigChartsEntity;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsListService;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsPointService;
import com.yjtech.wisdom.tourism.systemconfig.chart.service.SystemconfigChartsService;
import com.yjtech.wisdom.tourism.systemconfig.chart.vo.SystemconfigChartsVo;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 后台管理-系统配置-图表库
 *
 * @author 李波
 * @description: 后台管理-系统配置-图标库
 * @date 2021/7/211:40
 */
@Validated
@RestController
@RequestMapping("/systemconfig/charts")
public class SystemConfigChartsController {
    @Autowired
    private SystemconfigChartsService systemconfigChartsService;
    @Autowired
    private SystemconfigChartsListService systemconfigChartsListService;
    @Autowired
    private SystemconfigMenuService systemconfigMenuService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PreAuthorize("@ss.hasPermi('chart:base:list')")
    @PostMapping("/queryForPage")
    public JsonResult<IPage<SystemconfigChartsVo>> queryForPage(@RequestBody SystemConfigChartQueryPageDto query) {
        return JsonResult.success(systemconfigChartsService.queryForPage(query));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<SystemconfigChartsVo> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(systemconfigChartsService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('chart:base:add')")
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid SystemconfigChartsCreateDto createDto) {

        SystemconfigChartsEntity entity = BeanMapper.map(createDto, SystemconfigChartsEntity.class);
        entity.setId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        systemconfigChartsService.save(entity);

        //初始化列表数据
        if(StringUtils.isNotBlank(entity.getListType())){
            SystemconfigChartsListQueryVo systemconfigChartsListQueryVo = new SystemconfigChartsListQueryVo();
            systemconfigChartsListQueryVo.setChartId(entity.getId());
            systemconfigChartsListQueryVo.setListType(entity.getListType());
            List<SystemconfigChartsListVo> systemconfigChartsListVos = systemconfigChartsListService.queryChartsListsBYCharts(systemconfigChartsListQueryVo);
            if(CollectionUtils.isNotEmpty(systemconfigChartsListVos)){
                List<SystemconfigChartsListCreateDto> collect = systemconfigChartsListVos.stream().map(e -> {
                    SystemconfigChartsListCreateDto systemconfigChartsListCreateDto = new SystemconfigChartsListCreateDto();
                    systemconfigChartsListCreateDto.setChartId(entity.getId());
                    systemconfigChartsListCreateDto.setIsShow(e.getIsShow());
                    systemconfigChartsListCreateDto.setFiledKey(e.getFiledKey());
                    return systemconfigChartsListCreateDto;
                }).collect(Collectors.toList());
                this.systemconfigChartsListService.saveOrUpdateListsPointsBYCharts(collect);
            }
        }

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('chart:base:edid')")
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid SystemconfigChartsUpdateDto updateDto) {

        SystemconfigChartsEntity entity = BeanMapper.map(updateDto, SystemconfigChartsEntity.class);

        systemconfigChartsService.updateById(entity);

        //如果不是列表，删除以前的列表配置
        if (!StringUtils.equals(entity.getChartType(), "1")) {
            systemconfigChartsListService.removeChartListBYChartId(entity.getId());
        }

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('chart:base:remove')")
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {

        //首先校验模板是否被使用，如果已经被使用则不可删除
        int existnums = Optional.ofNullable(systemconfigChartsService.findChartMenusNum(idParam.getId())).orElse(0);
        if (existnums > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "已有大屏菜单关联该模板，请取消关联后删除！");
        } else {
            systemconfigChartsService.removeById(idParam.getId());
        }

        return JsonResult.ok();
    }

    /**
     * 获取图表的列表配置
     *
     * @param queryVo
     * @return
     */
    @PostMapping("/queryChartsListsBYCharts")
    public JsonResult<List<SystemconfigChartsListVo>> queryChartsListsBYCharts(@RequestBody @Valid SystemconfigChartsListQueryVo queryVo) {
        return JsonResult.success(systemconfigChartsListService.queryChartsListsBYCharts(queryVo));
    }

    /**
     * 新增或者修改图表的列表配置
     *
     * @param list
     * @return
     */
    @PostMapping("/saveOrUpdateListsPointsBYCharts")
    public JsonResult saveOrUpdateListsPointsBYCharts(@RequestBody @Valid List<SystemconfigChartsListCreateDto> list) {
        AssertUtil.notEmpty(list, "列表配置不能为空");

        systemconfigChartsListService.saveOrUpdateListsPointsBYCharts(list);
        return JsonResult.ok();
    }

    /**
     * 大屏页面配置
     *
     * @param
     * @return
     */
    @PostMapping("/queryPageList")
    public JsonResult<List<BaseVO>> queryForDetail() {
        return JsonResult.success(systemconfigMenuService.queryPageList());
    }

}
