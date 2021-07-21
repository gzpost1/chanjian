package com.yjtech.wisdom.tourism.resource.broadcast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 广播信息对应表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_broadcast")
public class BroadcastEntity extends MyBaseEntity {

    /**
     * 主键
     */
    @TableId(value = "broadcast_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long broadcastId;

    /**
     * 名称
     */
    private String name;

    /**
     * 终端类型 0：音响，1：话筒，2：(采集终端、转换终端只作展示不做操作)
     */
    private Byte broadcastType;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 连接字符串
     */
    private String connectString;

    /**
     * 地址
     */
    private String place;

    /**
     * 关联第三方标识,id或name之类的
     */
    private Integer thirdId;

    /**
     * 关联第三方标识的字段类型, 0-字符串, 1-整型
     */
    private Byte thirdIdType;

    /**
     * 编号
     */
    private String number;

    /**
     * 0-离线, 1-在线, 2-播放中状态
     */
    private Byte equipStatus;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 正在播放的内容
     */
    @TableField(exist = false)
    private String content;

    /**
     * 所属分组
     */
    @TableField(exist = false)
    private List<BroadcastGroupEntity> groups;

    /**
     * 音量
     */
    @TableField(exist = false)
    private Integer volume = 1;

}
