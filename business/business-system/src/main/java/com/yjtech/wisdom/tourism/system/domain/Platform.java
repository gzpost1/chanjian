package com.yjtech.wisdom.tourism.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author liuhong
 * @date 2021-07-02 15:17
 */
@Data
@TableName("tb_platform")
public class Platform extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 平台名称
     */
    private String name;

    /**
     * 平台简称
     */
    private String simpleName;

    /**
     * 英文名称
     */
    private String englishName;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 行政区域
     */
    private String areaName;

    /**
     * 中心位置
     */
    private String address;

    /**
     * 中心点经度
     */
    private String longitude;

    /**
     * 中心点纬度
     */
    private String latitude;

    /**
     * 默认时间筛选类型（1-7天 2-30天 3-90天 4-其他）
     */
    private Byte timeSelectType;

    /**
     * 默认开始日期
     */
    private LocalDate defaultBeginTime;

    /**
     * 默认结束日期
     */
    private LocalDate defaultEndTime;

}
