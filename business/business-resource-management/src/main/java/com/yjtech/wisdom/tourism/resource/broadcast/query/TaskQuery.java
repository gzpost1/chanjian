package com.yjtech.wisdom.tourism.resource.broadcast.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TaskQuery {

    @NotNull(message = "播放id不能为空")
    private Long id;
}