package com.yjtech.wisdom.tourism.resource.comment.extensionpoint.impl;

import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseWordQryExtPt;
import com.yjtech.wisdom.tourism.resource.comment.service.PraiseWordSummaryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 票务模拟数据扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_PRAISE,
        useCase = PraiseExtensionConstant.USE_CASE_PRAISE_WORD,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplPraiseWordQryExt implements PraiseWordQryExtPt {

    @Autowired
    private PraiseWordSummaryService service;

    @Override
    public Map<String, Object> queryHotWordBYComments() {
        return service.queryHotWordBYComments();
    }
}
