package com.yjtech.wisdom.tourism.resource.wifi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_wifi_visitor")
public class WifiVisitorEntity {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 景区id scenic_spot_id
     */
    private String scenicSpotId;

    /**
     * 游客账户 user_account
     */
    private String userAccount;

    /**
     * 游客名称 user_name
     */
    private String userName;

    /**
     * mac地址 mac_address
     */
    private String macAddress;

    /**
     * 认证方式，如微信 auth_type
     */
    private String authType;

    /**
     * 操作系统 os_type
     */
    private String osType;

    /**
     * 手机号码 phone_number
     */
    private String phoneNumber;

    /**
     * 浏览器 browser_type
     */
    private String browserType;

    /**
     * ap 主键 ap_id
     */
    private String apId;

    /**
     * 注册时间 register_time
     */
    private Date registerTime;

    /**
     * 手机厂商 phone_manufacturer
     */
    private String phoneManufacturer;
}