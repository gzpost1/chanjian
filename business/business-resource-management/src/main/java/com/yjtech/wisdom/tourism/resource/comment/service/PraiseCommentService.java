package com.yjtech.wisdom.tourism.resource.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentDto;
import com.yjtech.wisdom.tourism.resource.comment.dto.PraiseCommentPageQueryDto;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseCommentEntity;
import com.yjtech.wisdom.tourism.resource.comment.mapper.PraiseCommentMapper;
import org.springframework.stereotype.Service;

@Service
public class PraiseCommentService extends ServiceImpl<PraiseCommentMapper, PraiseCommentEntity> {

    public IPage<PraiseCommentDto> queryForPage(PraiseCommentPageQueryDto query) {
        return baseMapper.queryForPage(new Page(query.getPageNo(), query.getPageSize()), query);
    }
}
