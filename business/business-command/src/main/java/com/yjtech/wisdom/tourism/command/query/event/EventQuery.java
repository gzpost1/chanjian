package com.yjtech.wisdom.tourism.command.query.event;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@Getter
@Setter
public class EventQuery extends PageQuery {
    /**
    * 事件名称
    */
    private String name;

    /**
    * 事件类型（数据字典）
    */
    private String eventType;

    /**
     * 事件状态（数据字典）
     */
    private String eventStatus;

    private Date beginTime;

    private Date endTime;

    /**
     * 指派状态  1未指派 2 已指派
     */
    private String appointStatus;


}
