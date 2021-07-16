package com.yjtech.wisdom.tourism.system.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
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
    private String longitude;

    /**
     * 中心点纬度
     */
    @NotBlank(message = "中心点纬度不能为空")
    private String latitude;

    /**
     * 缩放比例
     */
    private String zoomLevel;

    /**
     * 左上角经度
     */
    @NotBlank(message = "左上角经度不能为空")
    private String leftLongitude;

    /**
     * 左上角纬度
     */
    @NotBlank(message = "左上角纬度不能为空")
    private String leftLatitude;

    /**
     * 右下角经度
     */
    @NotBlank(message = "右下角经度不能为空")
    private String rightLongitude;

    /**
     * 右下角纬度
     */
    @NotBlank(message = "右下角纬度不能为空")
    private String rightLatitude;
}
