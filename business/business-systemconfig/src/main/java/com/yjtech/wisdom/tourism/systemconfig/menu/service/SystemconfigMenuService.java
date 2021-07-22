package com.yjtech.wisdom.tourism.systemconfig.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.domain.IconDetail;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.*;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.mapper.SystemconfigMenuMapper;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDatavlDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuPageVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemconfigMenuService extends ServiceImpl<SystemconfigMenuMapper, SystemconfigMenuEntity> {

    @Autowired
    private SysDictTypeService service;
    @Autowired
    private IconService iconService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    public IPage<SystemconfigMenuPageVo> queryForPage(SystemconfigMenuPageQueryDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * 查询列表
     *
     * @return
     */
    public List<SystemconfigMenuPageVo> queryForList() {
        return baseMapper.queryForList();
    }

    /**
     * 修改菜单排序
     *
     * @param list
     */
    public void saveSortList(List<MenuSortDto> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(e -> {
                this.baseMapper.updateMenuSort(e);
            });
        }
    }

    public SystemconfigMenuDetailDto queryForDetail(Long id) {
        SystemconfigMenuEntity byId = Optional.ofNullable(this.baseMapper.getById(id)).orElse(new SystemconfigMenuEntity());
        SystemconfigMenuDetailDto systemconfigChartsVo = new SystemconfigMenuDetailDto();
        BeanUtils.copyProperties(byId, systemconfigChartsVo);

        if (CollectionUtils.isEmpty(byId.getPointData()) && Objects.isNull(byId.getId())) {
            //点位字典
            List<SysDictData> pointDictData = service.selectDictDataByType("config_spot_type");
            List<MenuPointDetalDto> collect = pointDictData.stream().map(e -> {
                MenuPointDetalDto menuPointDetalDto = new MenuPointDetalDto();
                menuPointDetalDto.setPointType(e.getDictValue());
                menuPointDetalDto.setIsShow(Byte.valueOf("0"));
                return menuPointDetalDto;
            }).collect(Collectors.toList());
            systemconfigChartsVo.setPointData(collect);
        }

        return systemconfigChartsVo;
    }

    public List<SystemconfigMenuDatavlDto> getDatavMenu() {
        //查找所有菜单

        List<SystemconfigMenuEntity> list = Optional.ofNullable(this.queryForAppList()).orElse(new ArrayList<>());

        //查找所有点位图标
        List<Icon> icons = iconService.querMenuIconList();

        //点位字典
        List<SysDictData> pointDictData = service.selectDictDataByType("config_spot_type");

        List<SystemconfigMenuDatavlDto> collect = list.stream().map(e -> {
            SystemconfigMenuDatavlDto dto = new SystemconfigMenuDatavlDto();
            BeanUtils.copyProperties(e, dto);


            //查找地图点位名称和图标
            if (CollectionUtils.isNotEmpty(e.getPointData())) {
                //循环菜单中的地图点位
                List<MenuPointDetalDatavDto> pointData = e.getPointData().stream().filter(p ->
                        //只需要显示的
                        Byte.valueOf("1").equals(p.getIsShow()))
                        //循环处理点位
                        .map(d -> {
                            MenuPointDetalDatavDto menuPointDetalDatavDto = new MenuPointDetalDatavDto();
                            menuPointDetalDatavDto.setPointType(d.getPointType());
                            menuPointDetalDatavDto.setIsShow(d.getIsShow());

                            //查找点位类型名称
                            for (SysDictData sysDictDatum : pointDictData) {
                                if (StringUtils.equals(sysDictDatum.getDictValue(), d.getPointType())) {
                                    menuPointDetalDatavDto.setPointName(sysDictDatum.getDictLabel());
                                    menuPointDetalDatavDto.setServiceUrl(sysDictDatum.getRemark());
                                    break;
                                }
                            }

                            //查找图标
//                            if (CollectionUtils.isNotEmpty(icons)) {
//                                //首先匹配点位类型
//                                icons.stream().filter(i -> StringUtils.equals(d.getPointType(), i.getType())).forEach(i -> {
//                                    if (CollectionUtils.isNotEmpty(i.getValue())) {
//                                        for (IconDetail icondetail : i.getValue()) {
//                                            if (!StringUtils.equals(icondetail.getPosition(), "首页导航")) {
//                                                menuPointDetalDatavDto.getValue().add(icondetail);
//                                            }
//                                        }
//                                    }
//                                });
//                            }
                            return menuPointDetalDatavDto;
                        }).collect(Collectors.toList());

                dto.setPointData(pointData);
            }

            //查找地图标的相关信息
            if (CollectionUtils.isNotEmpty(e.getChartData())) {
                List<MenuChartDetailDto> chartData = e.getChartData().stream().filter(chart -> Objects.nonNull(chart.getChartId())).collect(Collectors.toList());

                List<Long> chartId = chartData.stream().map(chart -> chart.getChartId()).collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(chartId)){
                    //查找所有图表
                    List<MenuChartDetailDatavDto> charts = this.baseMapper.getMenuChartByIds(chartId);
                    List<MenuChartDetailDatavDto> charList = new ArrayList<>();
                    dto.setChartData(charList);
                    //图表的列表
                    List<SystemconfigChartsListDatavDto> chartLists = this.baseMapper.getMenuChartListByIds(chartId);
                    Map<Long, List<SystemconfigChartsListDatavDto>> cahrtListMaps = new LinkedHashMap<>();
                    if (CollectionUtils.isNotEmpty(chartLists)) {
                        cahrtListMaps = chartLists.stream().collect(Collectors.groupingBy(SystemconfigChartsListDatavDto::getChartId));

                    }
                    //图标的点位
                    List<SystemconfigChartsPointDatavVo> chartPoints = this.baseMapper.getMenuChartPointByIds(chartId);
                    Map<Long, List<SystemconfigChartsPointDatavVo>> chartPointsMaps = new LinkedHashMap<>();
                    if (CollectionUtils.isNotEmpty(chartPoints)) {
                        chartPointsMaps = chartPoints.stream().collect(Collectors.groupingBy(SystemconfigChartsPointDatavVo::getChartId));
                    }

                    //更新为大屏的名称和坐标
                    for (MenuChartDetailDto chartDatum : chartData) {
                        for (MenuChartDetailDatavDto chart : charts) {
                            if (chartDatum.getChartId().equals(chart.getId())) {
                                chart.setName(chartDatum.getName());
                                chart.setPointDatas(chartDatum.getPointDatas());
                                MenuChartDetailDatavDto menuChartDetailDatavDto = new MenuChartDetailDatavDto();
                                BeanUtils.copyProperties(chart,menuChartDetailDatavDto);

                                //设置列表
                                List<SystemconfigChartsListDatavDto> systemconfigChartsListDatavDtos = cahrtListMaps.get(chart.getId());
                                List<SystemconfigChartsListDatavDto> chartList = new ArrayList<>();
                                if(CollectionUtils.isNotEmpty(systemconfigChartsListDatavDtos)){
                                    chartList= systemconfigChartsListDatavDtos.stream().sorted(Comparator.comparing(SystemconfigChartsListDatavDto::getSortNum)).collect(Collectors.toList());
                                }
                                menuChartDetailDatavDto.setListDatas(chartList);

                                //设置首页导航点位
                                List<SystemconfigChartsPointDatavVo> systemconfigChartsPointDatavVos = chartPointsMaps.get(chart.getId());
                                List<SystemconfigChartsPointDatavVo> chartPoint =new ArrayList<>();
                                if(CollectionUtils.isNotEmpty(systemconfigChartsPointDatavVos)){
                                    chartPoint = systemconfigChartsPointDatavVos.stream().sorted(Comparator.comparing(SystemconfigChartsPointDatavVo::getSortNumd)).collect(Collectors.toList());
                                }

                                menuChartDetailDatavDto.setPointNavigationDatas(chartPoint);

                                charList.add(menuChartDetailDatavDto);
                            }
                        }
                    }
                }else {
                    dto.setChartData(new ArrayList<>());
                }
            }
            return dto;
        }).collect(Collectors.toList());

        return collect;
    }

    private List<SystemconfigMenuEntity> queryForAppList() {
        return this.baseMapper.queryForAppList();
    }

    public void updateSimulationStatus(UpdateStatusParam updateStatusParam) {
        SystemconfigMenuEntity entity = this.baseMapper.getById(updateStatusParam.getId());
        entity.setIsSimulation(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    public void updateShowStatus(UpdateStatusParam updateStatusParam) {
        SystemconfigMenuEntity entity = this.baseMapper.getById(updateStatusParam.getId());
        entity.setIsShow(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    public List<SystemconfigChartsPointDatavVo> getIcons(Long id) {
        //查找所有点位图标
        List<Icon> icons = iconService.querMenuIconList();
        List<SystemconfigChartsPointDatavVo> pointDatavVos = Optional.ofNullable(this.baseMapper.getMenuChartPointByIds(Lists.newArrayList(id))).orElse(new ArrayList<>());
        for (SystemconfigChartsPointDatavVo pointDatavVo : pointDatavVos) {
            if (CollectionUtils.isNotEmpty(icons)) {
                //首先匹配点位类型
                icons.stream().filter(i -> StringUtils.equals(pointDatavVo.getPointType(), i.getType())).forEach(i -> {
                    pointDatavVo.setValue(i.getUrl());
                });
            }
        }

        return pointDatavVos;
    }
}
