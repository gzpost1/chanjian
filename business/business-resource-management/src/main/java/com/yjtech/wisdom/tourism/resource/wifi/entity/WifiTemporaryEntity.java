package com.yjtech.wisdom.tourism.resource.wifi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_wifi_temporary")
public class WifiTemporaryEntity {
    /**
     *  id
     */
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 景点id scenic_spot_id
     */
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long scenicSpotId;

    /**
     *  key
     */
    @TableField("`key`")
    private String key;

    /**
     *  value
     */
    @TableField("`value`")
    private Integer value;

    /**
     * 1:在线来宾总数，2：历史在线峰值 ，3:注册来宾总数，type（必填）
     */
    private String type;
}