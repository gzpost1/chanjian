package com.yjtech.wisdom.tourism.resource.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseWordSummaryEntity;

import java.util.List;

public interface PraiseWordSummaryMapper extends BaseMapper<PraiseWordSummaryEntity> {
    List<PraiseWordSummaryEntity> queryHotWordBYComments();

}