package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import com.yjtech.wisdom.tourism.systemconfig.menu.vo.SystemconfigMenuDatavlDto;
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
     * chart_type 图标类型
     */
    private String chartType;

    /**
     * 图表名称
     */
    private String name;

    /**
     * service_url 后端服务url
     */
    private String serviceUrl;

    /**
     * commponent_type 组件类型
     */
    private String commponentType;
    /**
     * h5_commponent_type 组件类型
     */
    private String h5CommponentType;

    /**
     * 点位坐标[0,1,2]
     */
    private List<Integer> pointDatas;

    /**
     * 是否有页面跳转 0-否,1-是
     */
    private Byte isRedirect;

    /**
     * 跳转页面id
     */
    private String redirectId;
    /**
     * h5跳转页面id
     */
    private String h5RedirectId;

    /**
     * 跳转页面路径
     */
    private String redirectPath;

    /**
     *  跳转页面数据
     */
    private SystemconfigMenuDatavlDto redirectMenuData;

    /**
     * 指标项
     */
    private String indexItem;

    /**
     * menu_type 点位类型
     */
    private String pointType;

    /**
     *  点位图片
     */
    private String pointImgUrl;

    /**
     * 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;


    /**
     * 表格类型，展示那些字段
     */
    private List<SystemconfigChartsListDatavDto> listDatas = new ArrayList<>();
}
