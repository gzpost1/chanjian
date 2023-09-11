package com.yjtech.wisdom.tourism.project.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProjectQuery extends PageQuery {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 状态 0待审核 1审核中 2已发布 3不予发布 4下架
     */
    private List<String> status;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 区域编码(内部)
     */
    private List<String> areaCodes;

    /**
     * 标签id列表
     */
    private List<Long> labelIdList;
    /**
     * 更新开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateBeginTime;
    /**
     * 更新结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateEndTime;
}
