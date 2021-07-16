package com.yjtech.wisdom.tourism.systemconfig.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置-大屏菜单配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigMenuPageVo {
    /**
     * id
     */
    private Long id;

    /**
     * sort_num 展示序号
     */
    private Integer sortNum;

    /**
     * name 大屏显示名称
     */
    private String name;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuType;

    /**
     * temp_id 模板名称
     */
    private String tempName;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    private Byte isShow;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;
}