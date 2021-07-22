package com.yjtech.wisdom.tourism.command.vo.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author xulei
 * @create 2021-07-19 15:57
 */
@Data
public class EventListVO {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;


    /**
     * 事件名称
     */
    private String name;


    /**
     * 事件类型（数据字典）
     */
    private String eventType;

    /**
     * 事件类型名称
     */
    private String eventTypeName;


    /**
     * 事件日期
     */
    private LocalDate eventDate;


    /**
     * 地址
     */
    private String address;


    /**
     * 事件状态(数据字典)
     */
    private String eventStatus;

    /**
     * 事件状态名称
     */
    private String eventStatusName;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 指派状态
     */
    private String appointStatus;

    /**
     * 指派状态名称
     */
    private String appointStatusName;

    /**
     * 指定处理人
     */
    @JsonIgnore
    private List<String> appointHandlePersonnel;

    private String iconUrl;

}
