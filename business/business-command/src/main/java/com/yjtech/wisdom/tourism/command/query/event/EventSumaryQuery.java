package com.yjtech.wisdom.tourism.command.query.event;

import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class EventSumaryQuery extends TimeBaseQuery {

    /**
     * 事件状态（数据字典）
     */
    private String eventStatus;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;
}
