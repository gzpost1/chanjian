package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 高发应急事件类型
 *
 * @author renguangqian
 * @date 2021/8/10 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class HighIncidenceEventTypeDto implements Serializable {

    private static final long serialVersionUID = -1839513379546751982L;

    /**
     * 事件类型名称
     */
    private String name;

    /**
     * 事件数量
     */
    private String value;

    /**
     * 比例
     */
    private String rate;

    /**
     * 同比
     */
    private String tb;

    /**
     * 环比
     */
    private String hb;

    /**
     * 索引下标
     */
    private Integer index;
}
