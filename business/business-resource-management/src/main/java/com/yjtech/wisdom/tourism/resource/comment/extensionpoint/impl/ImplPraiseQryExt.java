package com.yjtech.wisdom.tourism.resource.comment.extensionpoint.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentDayForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentMonthForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.ScreenCommentTotalDto;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseQryExtPt;
import com.yjtech.wisdom.tourism.resource.comment.service.PraiseTypeSummaryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 票务模拟数据扩展点
 * @author liuhong
 * @date 2021-07-13 11:12
 */
@Extension(bizId = ExtensionConstant.BIZ_PRAISE,
        useCase = PraiseExtensionConstant.USE_CASE_PRAISE_TYPE,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplPraiseQryExt implements PraiseQryExtPt
{

    @Autowired
    private PraiseTypeSummaryService service;

    @Override
    public ScreenCommentTotalDto queryForAll() {
        return service.queryForAll();
    }

    @Override
    public CommentDayForCommentDate queryDayForCommentDate() {
        return service.queryDayForCommentDate();
    }

    @Override
    public IPage<BaseVO> queryTotalOtaTop(PageQuery query) {
        return service.queryTotalOtaTop(query);
    }

    @Override
    public IPage<BasePercentVO> queryExcellentTotalOtaTop(PageQuery query) {
        return service.queryExcellentTotalOtaTop(query);
    }

    @Override
    public CommentMonthForCommentDate queryMonthForCommentDate() {
        return service.queryMonthForCommentDate();
    }
}
