package com.yjtech.wisdom.tourism.event.vo;

import lombok.Data;

/**
 * @author xulei
 * @create 2021-04-07 15:02
 */
@Data
public class EventVideoStatusVO {

    /**
     * id
     */
    private Long id;

    /**
     * 直播状态  0：直播中 1: 直播中断 2:直播结束
     */
    private String videoStatus;
    /**
     * 直播状态  0：直播中 1: 直播中断 2:直播结束
     */
    private String videoStatusName;

}
