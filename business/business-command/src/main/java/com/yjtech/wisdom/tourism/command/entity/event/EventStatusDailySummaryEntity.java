package com.yjtech.wisdom.tourism.command.entity.event;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 事件-统计
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
@Data
@TableName("tb_event_status_daily_summary")
public class EventStatusDailySummaryEntity implements Serializable {

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
     * 事件状态
     */
    private String status;


    /**
     * 事件数
     */
    private Integer quantity;



}
