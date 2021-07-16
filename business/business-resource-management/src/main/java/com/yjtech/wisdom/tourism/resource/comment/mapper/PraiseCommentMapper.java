package com.yjtech.wisdom.tourism.resource.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentDto;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentPageQueryDto;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseCommentEntity;
import org.apache.ibatis.annotations.Param;

public interface PraiseCommentMapper extends BaseMapper<PraiseCommentEntity> {
    IPage<PraiseCommentDto> queryForPage(Page page, @Param("params") PraiseCommentPageQueryDto query);
}