package com.yjtech.wisdom.tourism.systemconfig.menu.vo;

import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDatavDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuPointDetalDatavDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * name 大屏显示名称
     */
    private String name;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuType;

    /**
     * sort_num 展示序号
     */
    private Integer sortNum;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    private Byte hasBackdrop;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * point_data 地图点位展示数据
     */
    private List<MenuPointDetalDatavDto> pointData;

    /**
     * chart_data 图表数据
     */
    private List<MenuChartDetailDatavDto> chartData;

}