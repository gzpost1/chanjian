package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李波
 * @description: 菜单数据-图表数据详细
 * @date 2021/7/416:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuChartDetailDatavDto {
    /**
     * 图表id
     */
    private Long id;

    /**
     * 图表名称
     */
    private String name;

    /**
     * chart_type 图标类型
     */
    private String chartType;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuType;

    /**
     * service_url 后端服务url
     */
    private String serviceUrl;

    /**
     * commponent_type 组件类型
     */
    private String commponentType;

    /**
     * list_type 当图表类型选择列表时显示
     */
    private String listType;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation;

    /**
     * sample_img 示例图
     */
    private String sampleImg;

    /**
     * 点位坐标[0,1,2]
     */
    private List<Integer> pointDatas;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * 表格类型，展示那些字段
     */
    private List<SystemconfigChartsListDatavDto> listDatas = new ArrayList<>();

    /**
     * 点位导航展示那些点位
     */
    private List<SystemconfigChartsPointDatavVo> pointNavigationDatas = new ArrayList<>();
}
