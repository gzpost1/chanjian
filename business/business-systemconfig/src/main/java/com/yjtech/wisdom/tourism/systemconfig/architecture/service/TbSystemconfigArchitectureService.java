package com.yjtech.wisdom.tourism.systemconfig.architecture.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
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
        if (Objects.isNull(query.getMenuId())) {
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
            systemconfigChartsVo.setMenuName(getPintaiName());
        }
        return systemconfigChartsVo;

    }

    public String getPintaiName(){
        return Optional.ofNullable(this.baseMapper.queryNameByPingtai()).orElse("产业监测平台");
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
        TbSystemconfigArchitectureEntity sortEntity = this.baseMapper.getArchitecutueSortNum(updateStatusParam);
        TbSystemconfigArchitectureEntity byId = this.getById(updateStatusParam.getId());
        Integer sortNum = sortEntity.getSortNum();
        Integer sortNum1 = byId.getSortNum();
        sortEntity.setSortNum(sortNum1);
        byId.setSortNum(sortNum);
        this.updateById(byId);
        this.updateById(sortEntity);
    }

    /**
     * 	        第二级（有子节点，子节点有展示）	第二级（有子节点，子节点无展示）	第二级（无节点）	最后一级
     * 展示	       展示	                            展示	                  展示	          展示
     * 不展示	   展示	                            展示	                  不展示	          不展示
     * @return
     */
    public List<MenuTreeNode> getDatavMenu() {
        //查找所有菜单

        List<MenuTreeNode> treeNodeList = this.getAreaTree(0);

        if (CollectionUtils.isEmpty(treeNodeList)) {
            return new ArrayList<>();
        }
        //查找所有点位图标
        List<Icon> icons = iconService.querMenuIconList();

        List<SystemconfigMenuEntity> pages = systemconfigMenuService.queryMenusByIds(null);
        Map<Long, List<SystemconfigMenuDatavlDto>> allMenuPage = null;

        if (CollectionUtils.isNotEmpty(pages)) {
            allMenuPage = pages.stream()
                    .map(e -> processMenuData(icons, pages, e))
                    .collect(Collectors.groupingBy(SystemconfigMenuDatavlDto::getId));
        }

        Map<Long, List<SystemconfigMenuDatavlDto>> finalAllMenuPage = allMenuPage;
        //已经存在的跳转url，用于跳出循坏依赖
        List<String> constantRedirectUrl = new ArrayList<>();

        treeNodeList = treeNodeList.stream()
                .filter(e -> !StringUtils.equals(e.getParentId(),"0"))
                .map(e -> {

                    if (finalAllMenuPage != null && finalAllMenuPage.containsKey(e.getPageId())) {
                        SystemconfigMenuDatavlDto dto = finalAllMenuPage.get(e.getPageId()).get(0);
                        //processRedirectMenu(constantRedirectUrl, dto, finalAllMenuPage);
                        e.setPageData(dto);
                    }

                    return e;
                }).collect(Collectors.toList());
        List<MenuTreeNode> menuTreeNodes = TreeUtil.makeTree(treeNodeList);
        processMenuIsShow(menuTreeNodes);
        return menuTreeNodes;
    }
    public SystemconfigMenuDatavlDto getRedirectPageData(Long id) {
        //查找所有点位图标
        List<Icon> icons = iconService.querMenuIconList();

        List<SystemconfigMenuEntity> pages = systemconfigMenuService.queryMenusByIds(null);
        Map<Long, List<SystemconfigMenuDatavlDto>> allMenuPage = null;

        if (CollectionUtils.isNotEmpty(pages)) {
            allMenuPage = pages.stream()
                    .map(e -> processMenuData(icons, pages, e))
                    .collect(Collectors.groupingBy(SystemconfigMenuDatavlDto::getId));
        }else {
            return null;
        }
        return allMenuPage.get(id).get(0);
    }

    /**
     * 处理节点是否显示
     * @param makeTree
     */
    private void processMenuIsShow(List<MenuTreeNode> makeTree) {
        if(CollectionUtils.isNotEmpty(makeTree)){
            Iterator<MenuTreeNode> iterator = makeTree.iterator();
            while (iterator.hasNext()) {
                MenuTreeNode next = iterator.next();

                if(Objects.isNull(next.getPageId()) && StringUtils.equals("0",next.getIsShow()+"")){
                    iterator.remove();
                }
                if(CollectionUtils.isEmpty(next.getChildren()) && Objects.isNull(next.getPageId())){
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 跳转添加页面
     * @param constantRedirectUrl
     * @param dto
     * @param allMenuPage
     */
    private void processRedirectMenu(List<String> constantRedirectUrl, SystemconfigMenuDatavlDto dto, Map<Long, List<SystemconfigMenuDatavlDto>> allMenuPage) {
        if (CollectionUtils.isNotEmpty(dto.getChartData())) {
            for (MenuChartDetailDatavDto chartDatum : dto.getChartData()) {
                if (Objects.nonNull(chartDatum.getRedirectId()) &&
                        !constantRedirectUrl.contains(StringUtils.join(chartDatum.getId(), "&", chartDatum.getRedirectId())) &&
                        allMenuPage.containsKey(Long.valueOf(chartDatum.getRedirectId()))
                ) {
                    //跳转的菜单
                    SystemconfigMenuDatavlDto redirectMenu = allMenuPage.get(Long.valueOf(chartDatum.getRedirectId())).get(0);
                    chartDatum.setRedirectMenuData(redirectMenu);
                    constantRedirectUrl.add(StringUtils.join(chartDatum.getId(), "&", chartDatum.getRedirectId()));
                    processRedirectMenu(constantRedirectUrl, redirectMenu, allMenuPage);
                }
            }
        }
    }

    /**
     * 处理页面数据
     *
     * @param icons 图标
     * @param pages 所有页面
     */
    private SystemconfigMenuDatavlDto processMenuData(List<Icon> icons, List<SystemconfigMenuEntity> pages, SystemconfigMenuEntity nowPage) {
        SystemconfigMenuDatavlDto dto = new SystemconfigMenuDatavlDto();
        BeanUtils.copyProperties(nowPage, dto);

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
                        if (chart.getId().equals(chartDatum.getChartId())) {
                            chart.setPointDatas(chartDatum.getPointDatas());
                            chart.setName(chartDatum.getName());
                            chart.setIsSimulation(nowPage.getIsSimulation());
                        }
                    }
                    //设置列表
                    List<SystemconfigChartsListDatavDto> systemconfigChartsListDatavDtos = cahrtListMaps.get(chart.getId());
                    List<SystemconfigChartsListDatavDto> chartList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(systemconfigChartsListDatavDtos)) {
                        chartList = systemconfigChartsListDatavDtos.stream().sorted(Comparator.comparing(SystemconfigChartsListDatavDto::getSortNum)).collect(Collectors.toList());
                    }
                    chart.setListDatas(chartList);

                    //设置点位图表
                    if (StringUtils.isNotBlank(chart.getPointType()) && CollectionUtils.isNotEmpty(icons)) {
                        for (Icon icon : icons) {
                            if (StringUtils.equals(icon.getType(), chart.getPointType())) {
                                chart.setPointImgUrl(icon.getUrl());
                            }
                        }
                    }

                    //设置图表跳转路径
                    if (CollectionUtils.isNotEmpty(pages)) {
                        for (SystemconfigMenuEntity page : pages) {
                            if (StringUtils.equals("1", chart.getIsRedirect() + "") && StringUtils.equals(chart.getRedirectId(), page.getId() + "")) {
                                chart.setRedirectPath(page.getRoutePath());
                                break;
                            }
                        }
                    }
                }
            } else {
                dto.setChartData(new ArrayList<>());
            }
        }
        return dto;
    }


}


