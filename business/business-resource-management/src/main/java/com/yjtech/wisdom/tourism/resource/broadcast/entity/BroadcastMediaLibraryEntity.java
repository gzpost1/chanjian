package com.yjtech.wisdom.tourism.resource.broadcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广播媒体库对应表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_broadcast_media_library")
public class BroadcastMediaLibraryEntity extends MyBaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**设备id*/
    private String deviceId;
}
