package com.yjtech.wisdom.tourism.resource.hydrological.vo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@Getter
@Setter
public class HydrologicalDeviceCreateDto implements Serializable {
    /**
    * 设备序列号
    */
    @Length(max = 128,message = "设备序列号长度必须小于128位")
    @NotBlank(message = "设备编号不能为空")
    private String sn;

    /**
    * 设备名称
    */
    @Length(max = 30,message = "设备名称长度必须小于30位")
    @NotBlank(message = "设备名称不能为空")
    private String name;

    /**
    * 经度
    */
    @Length(max = 20,message = "经度长度必须小于20位")
    private String longitude;

    /**
    * 维度
    */
    @Length(max = 20,message = "维度长度必须小于20位")
    private String latitude;

    /**
    * 地址
    */
    @Length(max = 100,message = "地址长度必须小于100位")
    private String address;

    /**
    * 预警水位
    */
    @NotNull(message = "预警水位不能为空")
    private BigDecimal warningWaterLevel;

    /**
     * 报警水位
     */
    @NotNull(message = "报警水位不能为空")
    private BigDecimal alarmWaterLevel;

    /**
     * 报警类型, 来自字典表
     */
    @NotNull(message = "报警类型不能为空")
    private String alarmMode;

    /**
     *  启停用(0:停用,1:启用)
     * */
    private Byte status;

    /**
     *  设备状态(0:离线, 1:在线, 2:预警, 3:报警)
     * */
    private Byte equipStatus;



}
