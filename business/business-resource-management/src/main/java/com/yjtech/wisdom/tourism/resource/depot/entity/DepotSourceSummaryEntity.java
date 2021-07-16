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
 * (tb_depot_source_summary)实体类
 *
 * @author zc
 * @since 2021-07-05 10:08:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_depot_source_summary")
public class DepotSourceSummaryEntity {

    /**
     * summaryId
     */
    @TableId(value = "summary_id",type= IdType.ID_WORKER)
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
     * 来源,0:省内,1:省外
     */
    private Byte sourceType;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 数量
     */
    private Integer quantity;
}
