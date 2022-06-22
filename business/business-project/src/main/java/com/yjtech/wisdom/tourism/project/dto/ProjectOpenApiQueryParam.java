package com.yjtech.wisdom.tourism.project.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 项目管理 开放接口
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOpenApiQueryParam {

    /**
     * 发布最新优先（0：降序 1：升序）
     */
    @Builder.Default
    private Integer orderType = 0;

    /**
     * 项目特色标签
     */
    private String label;

    /**
     * 项目所属地区
     */
    private String area;

    /**
     * 搜索功能
     */
    private  String keyword;

    /**
     * 主键
     */
    private Long id;
}
