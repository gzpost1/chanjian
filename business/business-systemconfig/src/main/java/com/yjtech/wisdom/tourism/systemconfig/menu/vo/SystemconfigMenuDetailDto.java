package com.yjtech.wisdom.tourism.systemconfig.menu.vo;

import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuPointDetalDto;
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
public class SystemconfigMenuDetailDto {

    /**
     * id
     */
    private Long id;

    /**
     * name 大屏显示名称
     */
    private String name;

    /**
     * temp_id 模板id
     */
    private Long tempId;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuType;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    private Byte hasBackdrop;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * point_data 点位展示数据
     */
    private List<MenuPointDetalDto> pointData;

    /**
     * chart_data 图表数据
     */
    private List<MenuChartDetailDto> chartData;

}