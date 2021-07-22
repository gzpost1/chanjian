package com.yjtech.wisdom.tourism.command.dto.event;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
public class EventCreateDto implements Serializable {
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
    @Pattern(regexp = "^[\\-\\+]?((0|([1-8]\\d?))(\\.\\d{1,10})?|90(\\.0{1,10})?)$" ,message = "纬度格式不符合要求")
    @NotNull(message = "维度不能为空")
    private String latitude;

    /**
     * 经度
     */
    @Pattern(regexp = "^[\\-\\+]?(0(\\.\\d{1,10})?|([1-9](\\d)?)(\\.\\d{1,10})?|1[0-7]\\d{1}(\\.\\d{1,10})?|180\\.0{1,10})$" ,message = "经度格式不符合要求")
    @NotNull(message = "经度不能为空")
    private String longitude;

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

}
