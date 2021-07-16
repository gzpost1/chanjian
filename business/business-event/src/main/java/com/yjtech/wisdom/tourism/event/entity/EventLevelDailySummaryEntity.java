package com.yjtech.wisdom.tourism.event.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 事件-级别统计
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
@Data
@TableName("tb_event_level_daily_summary")
public class EventLevelDailySummaryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("summary_id")
    private Long summaryId;


    /**
     * 记录时间
     */
    private Date recordDate;


    /**
     * 年度, yyyy
     */
    private Integer year;


    /**
     * 月度
     */
    private Integer month;


    /**
     * 日期
     */
    private Integer day;


    /**
     * 事件级别
     */
    private String level;


    /**
     * 数量
     */
    private Integer quantity;



}
