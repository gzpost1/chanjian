package com.yjtech.wisdom.tourism.common.utils;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/10/22.
 */
@Data
public class IdParam implements Serializable {
    @NotNull
    private Long id;
}
