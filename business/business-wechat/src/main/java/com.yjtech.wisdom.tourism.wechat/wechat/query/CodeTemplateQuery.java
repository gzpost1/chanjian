package com.yjtech.wisdom.tourism.wechat.wechat.query;

import com.yjtech.wisdom.tourism.common.validator.EnumValue;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * Created by wuyongchong on 2020/3/23.
 */
@Data
public class CodeTemplateQuery extends PageQuery {

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 状态值 0:禁用 1:启用
     */
    @EnumValue(values = {"0", "1"}, message = "状态值0或者1")
    private Byte status;

}
