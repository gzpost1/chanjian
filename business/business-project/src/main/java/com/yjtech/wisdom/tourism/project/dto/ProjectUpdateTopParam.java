package com.yjtech.wisdom.tourism.project.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateTopParam {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "置顶状态不能为空")
    private Byte isTop;
}
