package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 系统配置-大屏菜单配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigMenuCreateDto {

    /**
     * name 大屏显示名称
     */
    @NotBlank(message = "菜单显示名称不能为空")
    @Length(max = 10, message = "菜单显示名称最多10位")
    private String name;

    /**
     * temp_id 模板id
     */
    @NotNull(message = "模板id不能为空")
    private Long tempId;

    /**
     * menu_type 大屏菜单类型
     */
    @NotBlank(message = "大屏菜单类型不能为空")
    private String menuType;

    /**
     * img_url 大屏缩略图
     */
    @NotBlank(message = "大屏缩略图不能为空")
    private String imgUrl;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    @NotNull(message = "是否启用背景板不能为空")
    private Byte hasBackdrop;

    /**
     * 路由地址
     */
    @NotBlank(message = "路由地址不能为空")
    @Length(max = 255, message = "路由地址最多255位")
    private String routePath;

    /**
     * point_data 点位展示数据
     */
    @Valid
    private List<MenuPointDetalDto> pointData;

    /**
     * chart_data 图表数据
     */
    @Valid
    private List<MenuChartDetailDto> chartData;

}