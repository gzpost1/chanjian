package com.yjtech.wisdom.tourism.resource.toilet.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资源管理-WiFi设备
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToiletVo {
    /**
     * id
     */
    private Long id;

    /**
     * name 设备名称
     */
    private String name;

    /**
     * device_id 设备编号
     */
    private String deviceId;

    /**
     * location 所在位置
     */
    private String address;

    /**
     * longitude 经度
     */
    private BigDecimal longitude;

    /**
     * latitude 纬度
     */
    private BigDecimal latitude;

    /**
     * equip_status 状态(0:离线,1:在线)
     */
    private Byte equipStatus;

    /**
     * enable 是否启用:0否, 1是
     */
    private Byte status;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}