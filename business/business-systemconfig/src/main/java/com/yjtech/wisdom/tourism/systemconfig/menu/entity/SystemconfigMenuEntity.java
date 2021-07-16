package com.yjtech.wisdom.tourism.systemconfig.menu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuPointDetalDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统配置-大屏菜单配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_systemconfig_menu", autoResultMap = true)
public class SystemconfigMenuEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * name 大屏显示名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * temp_id 模板id
     */
    @TableField(value = "temp_id")
    private Long tempId;

    /**
     * menu_type 大屏菜单类型
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * sort_num 展示序号
     */
    @TableField(value = "sort_num")
    private Integer sortNum;

    /**
     * img_url 大屏缩略图
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    @TableField(value = "is_show")
    private Byte isShow;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    @TableField(value = "is_simulation")
    private Byte isSimulation;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    @TableField(value = "has_backdrop")
    private Byte hasBackdrop;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * point_data 点位展示数据
     */
    @TableField(value = "point_data", typeHandler = ListObjectJsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<MenuPointDetalDto> pointData;

    /**
     * chart_data 图表数据
     */
    @TableField(value = "chart_data", typeHandler = ListObjectJsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<MenuChartDetailDto> chartData;

}