package com.yjtech.wisdom.tourism.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 查询项目总数及总投资参数
 * description: ProjectNumQueryParam
 * date: 2022/7/11 15:01
 * author: chenzhongge
 */
@Data
public class ProjectNumQueryParam {

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
