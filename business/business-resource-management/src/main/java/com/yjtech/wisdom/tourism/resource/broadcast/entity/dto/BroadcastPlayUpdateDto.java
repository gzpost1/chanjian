package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BroadcastPlayUpdateDto extends BroadcastPlayCreateDto{

    /**
     * 主键
     */
    @NotNull
    private Long id;
}