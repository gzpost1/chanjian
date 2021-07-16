package com.yjtech.wisdom.tourism.resource.alarm.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 * @author xulei
 * @since 2021-07-06
 */
@Getter
@Setter
public class AlarmPostCreateDto implements Serializable {

    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空")
    @Length(max = 50,message = "设备编号长度必须小于50位")
    private String code;

    /**
    * 设备名称
    */
    @NotBlank(message = "设备名称不能为空")
    @Length(max = 30,message = "设备名称长度必须小于30位")
    private String name;

    /**
    * 位置
    */
    @Length(max = 100,message = "位置长度必须小于100位")
    private String address;

    /**
    * 经度
    */
    @Length(max = 20,message = "经度长度必须小于20位")
    private String latitude;

    /**
    * 维度
    */
    @Length(max = 20,message = "维度长度必须小于20位")
    private String longitude;

    /**
    * 设备状态(0:停用,1:正常)
    */
    @EnumValue(values = {"0", "1"})
    private Byte equipStatus;

    /**
    * 状态(0:禁用,1:正常)
    */
    @EnumValue(values = {"0", "1"})
    private Byte status;



}
