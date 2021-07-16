package com.yjtech.wisdom.tourism.event.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wuyongchong
 * @since 2021-02-22
 */
@Getter
@Setter
public class EmergencyEventCreateDto implements Serializable {
    /**
     * 事件名称
     */
    @NotBlank(message = "事件名称不能为空")
    @Length(max = 30, message = "事件名称长度必须小于30位")
    private String name;

    /**
     * 事件类型（数据字典）
     */
    @NotBlank(message = "事件类型不能为空")
    @EnumValue(values = {"1", "2", "3", "4","5"})
    private String eventType;

    /**
     * 事件来源（数据字典）
     */
    @NotBlank(message = "事件来源不能为空")
    private String eventSource;


    /**
     * 事件日期
     */
    @NotNull(message = "事件日期不能为空")
    private LocalDate eventDate;

    /**
     * 地址
     */
    @Length(max = 100, message = "地址长度必须小于100位")
    @NotBlank(message = "地址不能为空")
    private String address;

    /**
     * 纬度
     */
    @NotNull(message = "维度不能为空")
    private BigDecimal latitude;

    /**
     * 经度
     */
    @NotNull(message = "经度不能为空")
    private BigDecimal longitude;

    /**
     * 事件过程
     */
    @NotBlank(message = "事件过程不能为空")
    @Length(max = 500, message = "地址长度必须小于500位")
    private String description;

    /**
     * 图片路径
     */
    @Size(max = 10)
    private List<String> imagePath;

    /**
     * 视频流地址
     */
    @Length(max = 500, message = "地址长度必须小于500位")
    private String videoPath;

    /**
     * 视频状态 0：直播中 1: 直播中断 2:直播结束
     */
    private String videoStatus;
}
