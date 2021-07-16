package com.yjtech.wisdom.tourism.event.query;

import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class EventCommonQuery {
    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;
}
