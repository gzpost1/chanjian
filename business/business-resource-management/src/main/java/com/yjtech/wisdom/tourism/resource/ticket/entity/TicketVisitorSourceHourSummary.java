package com.yjtech.wisdom.tourism.resource.ticket.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-04
 */
@Data
@TableName("tb_ticket_visitor_source_hour_summary")
public class TicketVisitorSourceHourSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("summary_id")
    private Long summaryId;


    /**
     * 记录时间
     */
    private Date recordTime;


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
     * 小时
     */
    private Integer hour;


    /**
     * 来源,0:本地,1:省内外地,2:省外
     */
    private Byte sourceType;


    /**
     * 来源省
     */
    private String province;


    /**
     * 来源市
     */
    private String city;


    /**
     * 数量
     */
    private Integer quantity;



}
