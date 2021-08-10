package com.yjtech.wisdom.tourism.systemconfig.architecture.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.utils.TreeUtil;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.MenuTreeNode;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.SystemconfigArchitectureCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.SystemconfigArchitectureDto;
import com.yjtech.wisdom.tourism.systemconfig.architecture.dto.SystemconfigArchitecturePageQuery;
import com.yjtech.wisdom.tourism.systemconfig.architecture.entity.TbSystemconfigArchitectureEntity;
import com.yjtech.wisdom.tourism.systemconfig.architecture.mapper.TbSystemconfigArchitectureMapper;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDatavDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigChartsListDatavDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.service.SystemconfigMenuService;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDatavlDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TbSystemconfigArchitectureService extends ServiceImpl<TbSystemconfigArchitectureMapper, TbSystemconfigArchitectureEntity> {

    @Autowired
    private IconService iconService;
    @Autowired
    private SysDictTypeService dictTypeService;
    @Autowired
    private SystemconfigMenuService systemconfigMenuService;

    public IPage<SystemconfigArchitectureDto> queryForPage(SystemconfigArchitecturePageQuery query) {
        if(Objects.isNull(query.getMenuId())){
            query.setMenuId(0L);
        }
//        //查询自己现在是第几级
//        TbSystemconfigArchitectureEntity byId = null;
//
//        //如果前端没有传入则默认查询第一级的所有下级
//        if (Objects.nonNull(query.getMenuId())) {
//            byId = this.getById(query.getMenuId());
//        } else {
//            byId = this.getFirstByParent(0);
//        }
//        if (Objects.nonNull(byId)) {
//            if (byId.getMenuId().equals(byId.getFirstId())) {
//                query.setQuerySql("and a.first_id = " + byId.getFirstId() + " and a.secon_id is not null");
//            } else if (byId.getMenuId().equals(byId.getSeconId())) {
//                query.setQuerySql("and a.secon_id = " + byId.getSeconId() + " and a.three_id is not null");
//            } else {
//                //如果是最后一级，则他下面没有东西，写死一个假条件
//                query.setQuerySql("and 1 <> 1");
//            }
//        } else {
//            //如果没有第一级，则写死一个假条件
//            query.setQuerySql("and 1 <> 1");
//        }
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    public List<MenuTreeNode> getAreaTree(int i) {
        return this.baseMapper.getAreaTree(i);
    }

    private TbSystemconfigArchitectureEntity getFirstByParent(int parentId) {
        return this.baseMapper.getFirstByParent(parentId);
    }

    public SystemconfigArchitectureDto queryForDetail(Long id) {
        TbSystemconfigArchitectureEntity byId = Optional.ofNullable(this.getById(id)).orElse(new TbSystemconfigArchitectureEntity());
        SystemconfigArchitectureDto systemconfigChartsVo = new SystemconfigArchitectureDto();
        BeanUtils.copyProperties(byId, systemconfigChartsVo);

        //第一次创建平台
        Integer sortNum = getChildNumByParendId(0L);
        if (Objects.isNull(byId.getMenuId()) && sortNum == 0) {
            systemconfigChartsVo.setMenuName(Optional.ofNullable(this.baseMapper.queryNameByPingtai()).orElse("产业监测平台"));
        }
        return systemconfigChartsVo;

    }

    public void create(SystemconfigArchitectureCreateDto createDto) {
        TbSystemconfigArchitectureEntity entity = new TbSystemconfigArchitectureEntity();
        BeanUtils.copyProperties(createDto, entity);
        entity.setMenuId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        entity.setIsShow(Byte.valueOf("1"));
        entity.setIsSimulation(Byte.valueOf("0"));

        //查询自己现在是第几级
        TbSystemconfigArchitectureEntity byId = this.getById(createDto.getParentId());
        if (Objects.isNull(byId)) {
            //现在新增的是第一级
            entity.setFirstId(entity.getMenuId());
        } else {
            if (byId.getMenuId().equals(byId.getFirstId())) {
                //如果父级是第一级
                entity.setFirstId(byId.getFirstId());
                entity.setSeconId(entity.getMenuId());
            } else if (byId.getMenuId().equals(byId.getSeconId())) {
                entity.setFirstId(byId.getFirstId());
                entity.setSeconId(byId.getSeconId());
                entity.setThreeId(entity.getMenuId());
            }
        }

        //查询当前的序号
        Integer sortNum = getChildNumByParendId(createDto.getParentId());
        entity.setSortNum(sortNum + 1);
        this.save(entity);
    }

    public Integer getChildNumByParendId(Long parentId) {
        return Optional.ofNullable(this.baseMapper.queryChildsByParent(parentId)).orElse(0);
    }

    public void updateSimulationStatus(UpdateStatusParam updateStatusParam) {
        TbSystemconfigArchitectureEntity entity = this.getById(updateStatusParam.getId());
        entity.setIsSimulation(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    public void updateShowStatus(UpdateStatusParam updateStatusParam) {
        TbSystemconfigArchitectureEntity entity = this.getById(updateStatusParam.getId());
        entity.setIsShow(updateStatusParam.getStatus());
        this.updateById(entity);
    }

    public BaseVO queryMaxAndMinByParendId(Long parentId) {
        return this.baseMapper.queryMaxAndMinByParendId(parentId);
    }

    public void updateSortNum(UpdateStatusParam updateStatusParam) {
        this.baseMapper.updateSortNum(updateStatusParam);
    }

    public List<MenuTreeNode> getDatavMenu() {
        //查找所有菜单

        List<MenuTreeNode> treeNodeList = this.getAreaTree(0);

        if (CollectionUtils.isEmpty(treeNodeList)) {
            return new ArrayList<>();
        }
        //查找所有点位图标
        List<Icon> icons = iconService.querMenuIconList();

        List<SystemconfigMenuEntity> pages = systemconfigMenuService.queryMenusByIds(null);

        treeNodeList = treeNodeList.stream()
                .filter(e -> {
                    boolean flag = StringUtils.equals("1", e.getIsShow() + "") && !StringUtils.equals(e.getParentId(),"0");
                    return flag;
                })
                .map(e -> {
                    SystemconfigMenuDatavlDto dto = new SystemconfigMenuDatavlDto();
                    SystemconfigMenuEntity nowPage = null;
                    if (CollectionUtils.isNotEmpty(pages)) {
                        for (SystemconfigMenuEntity page : pages) {
                            if (page.getId().equals(e.getPageId())) {
                                BeanUtils.copyProperties(page, dto);
                                nowPage = page;
                                break;
                            }
                        }
                    }

                    //查找地图标的相关信息
                    if (Objects.nonNull(nowPage) && CollectionUtils.isNotEmpty(nowPage.getChartData())) {

                        List<Long> chartId = nowPage.getChartData().stream().filter(chart -> Objects.nonNull(chart.getChartId())).map(chart -> chart.getChartId()).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(chartId)) {
                            //查找所有图表
                            List<MenuChartDetailDatavDto> charts = this.systemconfigMenuService.getMenuChartByIds(chartId);
                            dto.setChartData(charts);
                            //图表的列表
                            List<SystemconfigChartsListDatavDto> chartLists = this.systemconfigMenuService.getMenuChartListByIds(chartId);
                            Map<Long, List<SystemconfigChartsListDatavDto>> cahrtListMaps = new LinkedHashMap<>();
                            if (CollectionUtils.isNotEmpty(chartLists)) {
                                cahrtListMaps = chartLists.stream().collect(Collectors.groupingBy(SystemconfigChartsListDatavDto::getChartId));

                            }

                            //更新为大屏的名称和坐标
                            for (MenuChartDetailDatavDto chart : charts) {
                                for (MenuChartDetailDto chartDatum : nowPage.getChartData()) {
                                    if(chart.getId().equals(chartDatum.getChartId())){
                                        chart.setPointDatas(chartDatum.getPointDatas());
                                        chart.setName(chartDatum.getName());
                                    }
                                }
                                //设置列表
                                List<SystemconfigChartsListDatavDto> systemconfigChartsListDatavDtos = cahrtListMaps.get(chart.getId());
                                List<SystemconfigChartsListDatavDto> chartList = new ArrayList<>();
                                if (CollectionUtils.isNotEmpty(systemconfigChartsListDatavDtos)) {
                                    chartList = systemconfigChartsListDatavDtos.stream().sorted(Comparator.comparing(SystemconfigChartsListDatavDto::getSortNum)).collect(Collectors.toList());
                                }
                                chart.setListDatas(chartList);

                                //设置图表跳转路径
                                if (CollectionUtils.isNotEmpty(pages)) {
                                    for (SystemconfigMenuEntity page : pages) {
                                        if (StringUtils.equals("1", chart.getIsRedirect() + "") && StringUtils.equals(chart.getRedirectId(), page.getId() + "")) {
                                            chart.setRedirectPath(page.getRoutePath());
                                            break;
                                        }
                                    }
                                }

                                if(StringUtils.isNotBlank(chart.getPointType()) && CollectionUtils.isNotEmpty(icons)){
                                    for (Icon icon : icons) {
                                        if(StringUtils.equals(icon.getType(),chart.getPointType())){
                                            chart.setPointImgUrl(icon.getUrl());
                                        }
                                    }
                                }
                            }
                        } else {
                            dto.setChartData(new ArrayList<>());
                        }
                    }

                    e.setPageData(dto);
                    return e;
                }).collect(Collectors.toList());

        return TreeUtil.makeTree(treeNodeList);
    }
}


