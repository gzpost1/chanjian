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
 * @since 2021-07-03
 */
@Data
@TableName("tb_ticket_hour_summary")
public class TicketHourSummaryEntity implements Serializable {

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
     * 应入园人数
     */
    private Integer willVisitQuantity;


    /**
     * 实际入园人数
     */
    private Integer visitQuantity;


    /**
     * 应入园团队游客人数
     */
    private Integer willVisitGroupQuantity;


    /**
     * 实际入园团队游客数
     */
    private Integer groupQuantity;


    /**
     * 应入园散客数
     */
    private Integer willVisitIndividualQuantity;


    /**
     * 实际入园散客数
     */
    private Integer individualQuantity;


    /**
     * 出园人数
     */
    private Integer leaveQuantity;


    /**
     * 总售票数
     */
    private Integer saleQuantity;



}
