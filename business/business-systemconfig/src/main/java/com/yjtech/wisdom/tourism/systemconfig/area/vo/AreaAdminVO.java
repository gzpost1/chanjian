package com.yjtech.wisdom.tourism.systemconfig.area.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.common.annotation.Excel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 行政区域信息表
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
@Data
public class AreaAdminVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码，主键
     */
    private String code;


    /**
     * 区域名称
     */
    private String name;


    /**
     * 行政编码
     */
    @Excel(name = "行政编码")
    private String administration;


    /**
     * 简称
     */
    @Excel(name = "简称")
    private String abbreviation;


    /**
     * 车牌首字母
     */
    @Excel(name = "车牌首字母")
    private String plate;


    /**
     * 省级编码
     */
    private String provinceCode;


    /**
     * 省级名称
     */
    @Excel(name = "省级名称")
    private String provinceName;


    /**
     * 市级编码
     */
    private String cityCode;


    /**
     * 市级名称
     */
    @Excel(name = "市级名称")
    private String cityName;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
