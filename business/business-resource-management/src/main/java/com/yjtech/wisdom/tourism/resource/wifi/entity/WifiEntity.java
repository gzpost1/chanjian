package com.yjtech.wisdom.tourism.resource.wifi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
    * 资源管理-WiFi设备 
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_wifi")
public class WifiEntity extends BaseEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**当前连接*/
    private Integer currentConnect;

    /**设备最大连接数*/
    private Integer connectTotal;

    /**
     * name 设备名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * device_id 设备编号
     */
    @TableField(value = "device_id")
    private String deviceId;

    /**
     * location 所在位置
     */
    @TableField(value = "`address`")
    private String address;

    /**
     * longitude 经度
     */
    @TableField(value = "longitude")
    private BigDecimal longitude;

    /**
     * latitude 纬度
     */
    @TableField(value = "latitude")
    private BigDecimal latitude;

    /**
     * equip_status 状态(0:离线,1:在线)
     */
    @TableField(value = "equip_status")
    private Byte equipStatus;

    /**
     * enable 是否启用:0否, 1是
     */
    @TableField(value = "`status`")
    private Byte status;

}