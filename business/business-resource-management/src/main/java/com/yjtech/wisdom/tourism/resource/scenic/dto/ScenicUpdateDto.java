package com.yjtech.wisdom.tourism.resource.scenic.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ScenicUpdateDto extends ScenicCreateDto{

    /**景区id*/
    @NotNull(message = "景区id不能为空")
    private Long id;
}
