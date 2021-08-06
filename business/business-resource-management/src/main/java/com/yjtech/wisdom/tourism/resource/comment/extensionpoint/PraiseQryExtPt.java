package com.yjtech.wisdom.tourism.resource.comment.extensionpoint;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentDayForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentMonthForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.ScreenCommentTotalDto;

/**
 * 定义扩展点
 *
 * @author liuhong
 * @date 2021-07-13 11:08
 */
public interface PraiseQryExtPt extends ExtensionPointI {
    /**
     * 总评数
     *
     * @return
     */
    public ScreenCommentTotalDto queryForAll() ;

    /**
     * 评论热度趋势/评论满意度趋势
     *
     * @return
     */
    public CommentDayForCommentDate queryDayForCommentDate() ;

    public IPage<BaseVO> queryTotalOtaTop(PageQuery query) ;

    public IPage<BasePercentVO> queryExcellentTotalOtaTop(PageQuery query) ;

    /**
     * 近12月好评趋势/近12月差评趋势
     * @return
     */
    public CommentMonthForCommentDate queryMonthForCommentDate();
}
