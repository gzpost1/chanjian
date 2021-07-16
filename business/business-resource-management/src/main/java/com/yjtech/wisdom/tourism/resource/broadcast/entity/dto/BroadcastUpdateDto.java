package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 *
 * @author zc
 * @since 2020-09-16
 */
@Getter
@Setter
public class BroadcastUpdateDto extends BroadcastCreateDto {
    /**
     * 主键
     */
    @NotNull(message = "id不能为空")
    private Long broadcastId;


}
