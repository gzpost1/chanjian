package com.yjtech.wisdom.tourism.systemconfig.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.dto.area.AreaTreeNode;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.MenuTreeNode;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    public List<BaseVO> queryPageList() {
        return this.baseMapper.queryPageList();
    }

    public Integer findMenuIsExistJiagou(Long id) {
        return this.baseMapper.findMenuIsExistJiagou(id);
    }

    public List<SystemconfigMenuEntity> queryMenusByIds(List<Long> pageIds) {
        return this.baseMapper.queryMenusByIds(pageIds);
    }

    public List<MenuChartDetailDatavDto> getMenuChartByIds(List<Long> chartId) {
        return this.baseMapper.getMenuChartByIds(chartId);
    }

    public List<SystemconfigChartsListDatavDto> getMenuChartListByIds(List<Long> chartId) {
        return this.baseMapper.getMenuChartListByIds(chartId);
    }
}
