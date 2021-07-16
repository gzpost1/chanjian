package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BroadcastPlayCreateDto {

    /**
     * 名称
     */
    @Length(min = 1,max = 30,message = "广播名称长度必须在30字以内")
    private String name;

    /**
     * 终端ids
     */
    @NotNull(message = "终端不能为空")
    private String broadcastIds;

    /**
     * 话筒id
     */
    private String microphoneId;

    /**
     * 音乐ids
     */
    private String musicIds;

    /**
     * 文本
     */
    @Length(min = 1,max = 300,message = "广播内容长度必须在1和300之间")
    private String text;

    /**
     * 文本播放次数
     */
    @Min(value = 1,message = "文本播放次数不能小于1")
    private String repeatTime;

    /**
     * 状态, 0-未启用, 1-启用
     */
    @EnumValue(values = {"0", "1"})
    private Byte status;

    /**
     * 类型, 0-实时采播, 1-文件广播，2-文本播放
     */
    @EnumValue(values = {"0", "1","2"})
    @NotNull(message = "type不能为null")
    private Byte type;

    /**
     * 音量
     */
    @NotNull(message = "音量不能为空")
    @Min(1)
    @Max(100)
    private Integer volume;

}