package com.yjtech.wisdom.tourism.systemconfig.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.*;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigAppMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.entity.SystemconfigMenuEntity;
import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuPageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemconfigAppMenuMapper extends BaseMapper<SystemconfigAppMenuEntity> {

    IPage<SystemconfigMenuPageVo> queryForPage(Page page, @Param("params") SystemconfigMenuPageQueryDto query);

    List<SystemconfigMenuPageVo> queryForList();

    void updateMenuSort(@Param("params") MenuSortDto params);

    SystemconfigMenuEntity getById(Long id);

    List<MenuChartDetailDatavDto> getMenuChartByIds(@Param("chartIds") List<Long> chartId);

    List<SystemconfigChartsListDatavDto> getMenuChartListByIds(@Param("chartIds") List<Long> chartIds);

    List<SystemconfigChartsPointDatavVo> getMenuChartPointByIds(@Param("chartIds") List<Long> chartId);

    List<SystemconfigMenuEntity> queryForAppList();

    List<BaseVO> queryPageList();

    Integer findMenuIsExistJiagou(Long id);

    List<SystemconfigMenuEntity> queryMenusByIds(@Param("ids") List<Long> pageIds);

    List<BaseVO> queryAppRedirectPages();

}