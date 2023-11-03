package com.yjtech.wisdom.tourism.project.vo;

import lombok.Data;

/**
 * @author songjun
 * @since 2023/11/1
 */
@Data
public class ProjectStatisticsVo {
    /**
     * 总数
     */
    private Integer all;
    /**
     * 项目启动数
     */
    private Integer auditRunning;
    /**
     * 项目下架数
     */
    private Integer takeDown;
}
