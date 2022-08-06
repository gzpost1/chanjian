package com.yjtech.wisdom.tourism.common.bean.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 项目数据统计 查询VO
 *
 * @date 2022/8/5 16:51
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDataStatisticsQueryVO implements Serializable {
    private static final long serialVersionUID = -6481254937938520228L;

    /**
     * 项目id
     */
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    /**
     * 企业id
     */
    @JsonIgnore
    private String companyId;

    /**
     * 数据来源类型 1企业 2项目
     */
    @JsonIgnore
    private Byte favouriteSource;

    /**
     * 收藏类型 1收藏 2点赞
     */
    @JsonIgnore
    private Byte favouriteType;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
