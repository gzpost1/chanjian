package com.yjtech.wisdom.tourism.resource.depot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (tb_depot_trend_summary)实体类
 *
 * @author zc
 * @since 2021-07-05 10:08:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_depot_trend_summary")
public class DepotTrendSummaryEntity {

    /**
     * summaryId
     */
    @TableId(value = "summary_id",type= IdType.ID_WORKER)
    private Long summaryId;
    /**
     * 时间名称
     */
    private String timeName;
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
     * 进入数量
     */
    private Integer enterQuantity;
    /**
     * 进入数量
     */
    private Integer exitQuantity;
}
