package com.yjtech.wisdom.tourism.resource.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseTypeSummaryEntity;
import feign.Param;

import java.util.List;

public interface PraiseTypeSummaryMapper extends BaseMapper<PraiseTypeSummaryEntity> {
    PraiseTypeSummaryEntity queryForAll();

    List<PraiseTypeSummaryEntity> queryDayForCommentDate();


    IPage<BaseVO> queryTotalOtaTop(Page page, @Param("page") PageQuery query);

    IPage<BasePercentVO> queryExcellentTotalOtaTop(Page page, @Param("page") PageQuery query);

    List<PraiseTypeSummaryEntity> queryMonthForCommentDate();

}