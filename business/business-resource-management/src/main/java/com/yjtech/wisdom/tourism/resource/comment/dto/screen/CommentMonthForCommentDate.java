package com.yjtech.wisdom.tourism.resource.comment.dto.screen;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 李波
 * @description: 评论热度趋势/评论满意度趋势
 * @date 2021/7/1215:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMonthForCommentDate {
    /**
     * 近12月好评趋势
     */
    private List<BaseVO> excellentList;

    /**
     * 近12月差评趋势
     */
    private List<BaseVO> poorList;
}
