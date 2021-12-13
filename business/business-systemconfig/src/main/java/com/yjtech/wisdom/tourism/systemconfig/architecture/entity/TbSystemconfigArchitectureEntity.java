package com.yjtech.wisdom.tourism.systemconfig.architecture.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

/**
 * 系统配置-大屏菜单架构
 */
@Data
@TableName(value = "tb_systemconfig_architecture")
public class TbSystemconfigArchitectureEntity extends BaseEntity {
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.ID_WORKER)
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 第一级菜单id
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 第一级菜单id
     */
    @TableField(value = "first_id")
    private Long firstId;

    /**
     * 第二级菜单id
     */
    @TableField(value = "secon_id")
    private Long seconId;

    /**
     * 第三级菜单id
     */
    @TableField(value = "three_id")
    private Long threeId;

    /**
     * 第四级菜单id
     */
    @TableField(value = "four_id")
    private Long fourId;

    /**
     * 显示顺序
     */
    @TableField(value = "page_id", updateStrategy = FieldStrategy.IGNORED)
    private Long pageId;

    /**
     * 是否展示 0否 1是
     */
    @TableField(value = "is_show")
    private Byte isShow;

    /**
     * 是否启用模拟数据(0:否,1:是)
     */
    @TableField(value = "is_simulation")
    private Byte isSimulation;

    /**
     * 序号
     */
    @TableField(value = "sort_num")
    private Integer sortNum;

    /**
     * 类型 0.大屏 1.H5
     */
    @TableField(value = "type")
    private Integer type;


}