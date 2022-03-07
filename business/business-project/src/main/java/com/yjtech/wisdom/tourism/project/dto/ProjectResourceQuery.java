package com.yjtech.wisdom.tourism.project.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProjectResourceQuery {
    /**
     * 项目名称id
     */
    @NotBlank(message = "项目名称id不能为空")
    private String projectId;

    /**
     * 资源类型 0vr  1视屏 2ppt
     */
    @NotNull(message = "资源类型不能为空")
    private Byte resourceType;
}
