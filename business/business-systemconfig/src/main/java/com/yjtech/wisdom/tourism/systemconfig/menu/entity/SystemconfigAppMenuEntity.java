package com.yjtech.wisdom.tourism.systemconfig.menu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统配置-h5菜单配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_app_menu", autoResultMap = true)
public class SystemconfigAppMenuEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * name h5显示名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * menu_type 大屏菜单类型
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * 是否显示日期筛选 0否 1是
     */
    @TableField(value = "is_showdate")
    private Byte isShowdate;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    @TableField(value = "is_simulation")
    private Byte isSimulation;

    /**
     * img_url 大屏缩略图
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * chart_data 图表数据
     */
    @TableField(value = "chart_data", typeHandler = ListObjectJsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<MenuChartDetailDto> chartData;
}