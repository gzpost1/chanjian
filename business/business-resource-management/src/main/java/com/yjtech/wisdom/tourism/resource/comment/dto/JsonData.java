package com.yjtech.wisdom.tourism.resource.comment.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentDayForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentMonthForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.ScreenCommentTotalDto;
import lombok.Data;

import java.util.Map;
import java.util.List;

/**
 * @author 李波
 * @description:
 * @date 2021/7/1420:56
 */
@Data
public class JsonData {
    /**
     * 总评数
     */
    private ScreenCommentTotalDto one;

    /**
     * 评论热度趋势/评论满意度趋势
     */
    private CommentDayForCommentDate two;

    /**
     * 近12月好评趋势/近12月差评趋势
     */
    private CommentMonthForCommentDate three;

    /**
     * 热度分布TOP5/满意度分布TOP5
     */
    private Map<String, Object> four;

    /**
     * 行业评价分布/评论热词词频
     */
    private Map<String,List<BaseVO>> five;
}
