package com.yjtech.wisdom.tourism.system.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 平台信息
 *
 * @author liuhong
 * @date 2021-07-02 15:52
 */
@Data
public class PlatformVO implements Serializable {
    /**
     * 平台名称
     */
    @NotBlank(message = "平台名称不能为空")
    @Length(max = 20, message = "平台名称长度不超过20")
    private String name;

    /**
     * 平台简称
     */
    @NotBlank(message = "平台简称不能为空")
    @Length(max = 10, message = "平台简称长度不超过10")
    private String simpleName;

    /**
     * 英文名称
     */
    @NotBlank(message = "英文名称不能为空")
    @Length(max = 100, message = "英文名称长度不超过100")
    private String englishName;

    /**
     * 区域编码
     */
    @NotBlank(message = "行政区域编码不能为空")
    private String areaCode;

    /**
     * 行政区域
     */
    private String areaName;

    /**
     * 中心位置
     */
    @NotBlank(message = "中心位置不能为空")
    @Length(max = 100, message = "平台简称长度不超过100")
    private String address;

    /**
     * 中心点经度
     */
    @NotBlank(message = "中心点经度不能为空")
    @Pattern(regexp = "^[\\-\\+]?(0?\\d{1,2}\\.\\d{1,6}|1[0-7]?\\d\\.\\d{1,6}|180\\.0{1,6})$", message = "请输入正确的经度")
    private String longitude;

    /**
     * 中心点纬度
     */
    @NotBlank(message = "中心点纬度不能为空")
    @Pattern(regexp = "^[\\-\\+]?([0-8]?\\d\\.\\d{1,6}|90\\.0{1,6})$", message = "请输入正确的纬度")
    private String latitude;

    /**
     * 默认时间筛选类型（1-30天 2-60天 3-90天 4-其他）
     */
    @NotNull(message = "默认时间筛选类型不能为空")
    @Range(min = 1, max = 4, message = "请输入正确的时间筛选类型")
    private Byte timeSelectType;
}
