package com.yjtech.wisdom.tourism.resource.broadcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
/**
 * 广播分组对应表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_broadcast_group")
public class BroadcastGroupEntity extends MyBaseEntity {
    /**
     * 主键
     */
    @TableId(value = "group_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long groupId;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态, 0-未启用, 1-启用
     */
    private Byte status;

    /**
     * 分组广播信息
     */
    @TableField(exist = false)
    private List<BroadcastEntity> broadcastList;

}