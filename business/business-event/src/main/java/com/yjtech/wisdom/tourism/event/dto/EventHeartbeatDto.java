package com.yjtech.wisdom.tourism.event.dto;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xulei
 * @create 2021-04-07 15:02
 */
@Data
public class EventHeartbeatDto {

    /**
     * id
     */
    @NotNull
    private Long id;



    /**
     * 直播状态  0：直播中 1: 直播中断 2:直播结束
     */
    @NotBlank
    @EnumValue(values = {"0","1", "2"})
    private String videoStatus;
}
