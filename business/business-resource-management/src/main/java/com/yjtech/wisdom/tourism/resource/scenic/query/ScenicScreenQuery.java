package com.yjtech.wisdom.tourism.resource.scenic.query;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ScenicScreenQuery extends TimeBaseQuery {

    @NotNull(message = "景区id不能为空")
    private Long scenicId;
}
