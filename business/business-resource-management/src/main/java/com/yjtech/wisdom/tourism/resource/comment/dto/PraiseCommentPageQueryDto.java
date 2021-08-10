package com.yjtech.wisdom.tourism.resource.comment.dto;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * @author 李波
 * @description: 评论分页
 * @date 2021/7/915:14
 */
@Data
public class PraiseCommentPageQueryDto extends TimeBaseQuery {
    /**
     * 评论类型 0好评  1中评  2差评
     */
    private String commentType;
}
