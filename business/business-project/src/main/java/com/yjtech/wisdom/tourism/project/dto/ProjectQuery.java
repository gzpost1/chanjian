package com.yjtech.wisdom.tourism.project.dto;


import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

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
}
