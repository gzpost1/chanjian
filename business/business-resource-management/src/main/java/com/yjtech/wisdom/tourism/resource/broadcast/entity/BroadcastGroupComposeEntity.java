package com.yjtech.wisdom.tourism.resource.broadcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 广播分组团体对应表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_broadcast_group_compose")
public class BroadcastGroupComposeEntity {
    /**
     * 主键
     */
    @TableId(value = "compose_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long composeId;

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 广播id
     */
    private Long broadcastId;
}