package com.yjtech.wisdom.tourism.resource.turnstile.vo;

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
public class TurnstileCreateDto implements Serializable {
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
     *  启停用(0:停用,1:启用)
     * */
    private Byte status;

    /**
     *  设备状态(0:离线, 1:在线)
     * */
    private Byte equipStatus;



}
