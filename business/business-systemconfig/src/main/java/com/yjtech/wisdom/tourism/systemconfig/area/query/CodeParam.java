package com.yjtech.wisdom.tourism.systemconfig.area.query;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/10/22.
 */
@Data
public class CodeParam implements Serializable {

    @NotNull(message = "code不能为空")
    private String code;
}
