package com.yjtech.wisdom.tourism.systemconfig.area.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author xulei
 * @create 2021-07-02 16:50
 */
@Data
public class AbbreviationQuery {

    @NotBlank(message = "编码不能为空")
    private String areaCode;
}
