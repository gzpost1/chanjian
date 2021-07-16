package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by wuyongchong on 2019/10/22.
 */
@Data
public class IdsParam implements Serializable {
    @NotNull
    private List<Long> ids;
}
