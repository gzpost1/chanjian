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
@TableName(value = "tb_wifi_summary")
public class WifiSummaryEntity {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 景点id scenic_spot_id
     */
    private String scenicSpotId;

    /**
     * key
     */
    private String key;

    /**
     * 记录时间
     */
    private Date recordTime;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 1:连接时长，2:链接数量 type
     */
    private String type;

}