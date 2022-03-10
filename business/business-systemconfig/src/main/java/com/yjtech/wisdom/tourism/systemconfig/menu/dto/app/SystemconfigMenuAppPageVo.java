package com.yjtech.wisdom.tourism.systemconfig.menu.dto.app;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.MenuChartDetailDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 李波
 * @description:
 * @date 2021/11/3 14:56
 */
@Data
public class SystemconfigMenuAppPageVo {
    /**
     * id
     */
    private Long id;

    /**
     * 大屏显示名称
     */
    private String name;

    /**
     * menu_type 大屏菜单类型
     */
    private String menuType;

    /**
     * 大屏菜单类型名称
     */
    private String menuName;

    /**
     * 大屏缩略图
     */
    private String imgUrl;

    /**
     * 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;

    /**
     * 是否显示日期筛选 0否 1是
     */
    private Byte isShowdate;

    /**
     * 路由地址
     */
    private String routePath;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * chart_data 图表数据
     */
    @TableField(value = "chart_data", typeHandler = ListObjectJsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<MenuChartDetailDto> chartData;
}
