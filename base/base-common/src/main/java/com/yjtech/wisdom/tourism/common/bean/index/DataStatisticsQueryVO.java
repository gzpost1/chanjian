package com.yjtech.wisdom.tourism.common.bean.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 数据统计 查询VO
 *
 * @date 2022/8/5 16:51
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataStatisticsQueryVO implements Serializable {
    private static final long serialVersionUID = -7962555183638510534L;

    /**
     * 开始日期
     */
    @NotNull(message = "开始日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    /**
     * 是否是加入黑名单
     */
    @JsonIgnore
    private Byte blacklist;


    public DataStatisticsQueryVO(Byte blacklist) {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        this.beginDate = now.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
        this.endDate = now.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
        this.blacklist = blacklist;
    }

}
