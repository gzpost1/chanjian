package com.yjtech.wisdom.tourism.systemconfig.menu.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    /**
     * 地图大小 1大 2小
     */
    private Byte mapsizeType;

    /**
     *是否显示日期筛选 0否 1是
     */
    private Byte isShowdate;

    /**
     *是否显示返回按钮 0否 1是
     */
    private Byte isShowReturn;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}