package com.yjtech.wisdom.tourism.systemconfig.menu.vo;

import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDatavDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuPointDetalDatavDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统配置-大屏菜单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigMenuDatavlDto {

    /**
     * id
     */
    private Long id;


    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    private Byte hasBackdrop;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * 地图大小 1大 2小
     */
    private Byte mapsizeType;

    /**
     *是否显示日期筛选 0否 1是
     */
    private Byte isShowdate;

    /**
     *是否显示返回按钮 0否 1是
     */
    private Byte isShowReturn;

    /**
     * chart_data 图表数据
     */
    private List<MenuChartDetailDatavDto> chartData;
}