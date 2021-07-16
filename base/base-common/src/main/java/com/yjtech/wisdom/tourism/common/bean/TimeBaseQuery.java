package com.yjtech.wisdom.tourism.common.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.common.enums.AnalysisDateTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 时间范围基础
 *
 * @Author xulei
 * @Date 2021/5/24 19:50
 */
@Data
public class TimeBaseQuery extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1433486511420789935L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 时间趋势类型（1-年 2-年月 3-年月日  4-年月日时）
     */
    @NotNull(message = "type 不能为空")
    @Range(min = 1, max = 4, message = "时间趋势类型不合法")
    private Byte type;

    /**
     * sql时间格式（%Y 或 %Y-%m 或 %Y-%m-%d）
     */
    @JsonIgnore
    private String sqlDateFormat;

    /**
     * 所属年
     */
    private Integer year;


    /**
     * 所属月
     */
    private Integer month;


    /**
     * 所属日
     */
    private Integer day;

    /**
     * 所属时
     */
    private Integer hour;

    public String getSqlDateFormat() {
        AnalysisDateTypeEnum analysisDateTypeEnum = AnalysisDateTypeEnum.getItemByValue(this.type);
        return analysisDateTypeEnum.getSqlDateFormat();
    }

}
