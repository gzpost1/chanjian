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
 * 文本、文件、实时播放对应表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_broadcast_play")
public class BroadcastPlayEntity extends MyBaseEntity {

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

    /**
     * 终端ids
     */
    private String broadcastIds;

    /**
     * 话筒id
     */
    private Long microphoneId;

    /**
     * 音乐ids
     */
    private String musicIds;

    /**
     * 文本
     */
    private String text;

    /**
     * 文本播放次数
     */
    private Integer repeatTime;

    /**
     * 状态, 0-未启用, 1-启用
     */
    private Byte status;

    /**
     * 类型, 0-实时采播, 1-文件广播，2-文本播放
     */
    private Byte type;

    /**
     * 正在执行的任务id
     */
    private String taskId;

    /**
     * 音量
     */
    private Integer volume;

}