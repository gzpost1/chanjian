package com.yjtech.wisdom.tourism.resource.comment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentDayForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.CommentMonthForCommentDate;
import com.yjtech.wisdom.tourism.resource.comment.dto.screen.ScreenCommentTotalDto;
import com.yjtech.wisdom.tourism.resource.comment.entity.PraiseTypeSummaryEntity;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseExtensionConstant;
import com.yjtech.wisdom.tourism.resource.comment.extensionpoint.PraiseQryExtPt;
import com.yjtech.wisdom.tourism.resource.comment.mapper.PraiseTypeSummaryMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PraiseTypeSummaryService extends ServiceImpl<PraiseTypeSummaryMapper, PraiseTypeSummaryEntity>  {

    /**
     * 总评数
     *
     * @return
     */
    public ScreenCommentTotalDto queryForAll() {
        ScreenCommentTotalDto screenCommentTotalDto = new ScreenCommentTotalDto();

        PraiseTypeSummaryEntity entity = Optional.ofNullable(this.baseMapper.queryForAll()).orElse(new PraiseTypeSummaryEntity());

        //设置总数
        screenCommentTotalDto.setCommentTotal(entity.getCommentTotal());

        //设置好中差比例名称
        List<BasePercentVO> valuesList = new ArrayList<>();
        screenCommentTotalDto.setValuesList(valuesList);

        BasePercentVO excellentPercentVo = new BasePercentVO();
        excellentPercentVo.setName("好评");
        excellentPercentVo.setValue(String.valueOf(entity.getCommentExcellent() + ""));
        excellentPercentVo.setRate(screenCommentTotalDto.getCommentTotal() == 0L ? 0D :
                MathUtil.divide(BigDecimal.valueOf(Double.valueOf(excellentPercentVo.getValue())), BigDecimal.valueOf(screenCommentTotalDto.getCommentTotal()), 4).doubleValue());
        valuesList.add(excellentPercentVo);

        BasePercentVO poorPercentVo = new BasePercentVO();
        poorPercentVo.setName("差评");
        poorPercentVo.setValue(String.valueOf(entity.getCommentPoor() + ""));
        poorPercentVo.setRate(screenCommentTotalDto.getCommentTotal() == 0L ? 0D :
                MathUtil.divide(BigDecimal.valueOf(Double.valueOf(poorPercentVo.getValue())), BigDecimal.valueOf(screenCommentTotalDto.getCommentTotal()), 4).doubleValue());
        valuesList.add(poorPercentVo);

        BasePercentVO mediumPercentVo = new BasePercentVO();
        mediumPercentVo.setName("中评");
        mediumPercentVo.setValue(String.valueOf(entity.getCommentMedium() + ""));
        mediumPercentVo.setRate(screenCommentTotalDto.getCommentTotal() == 0L ? 0D :
                MathUtil.divide(BigDecimal.valueOf(Double.valueOf(mediumPercentVo.getValue())), BigDecimal.valueOf(screenCommentTotalDto.getCommentTotal()), 4).doubleValue());
        valuesList.add(mediumPercentVo);

        return screenCommentTotalDto;
    }

    /**
     * 评论热度趋势/评论满意度趋势
     *
     * @return
     */
    public CommentDayForCommentDate queryDayForCommentDate() {
        CommentDayForCommentDate commentDayForCommentDate = new CommentDayForCommentDate();
        //评论热度趋势
        List<BaseVO> totalList = new ArrayList<>();
        commentDayForCommentDate.setTotalList(totalList);
        //评论满意度趋势
        List<BasePercentVO> excellentList = new ArrayList<>();
        commentDayForCommentDate.setExcellentList(excellentList);

        //近12月的评论数，好中差总数
        List<PraiseTypeSummaryEntity> entitys = this.baseMapper.queryMonthForCommentDate();

        for (PraiseTypeSummaryEntity entity : entitys) {
            //总数
            totalList.add(new BaseVO(
                    DateTimeUtil.dateToString(entity.getCommentTime(), "yyyy-MM"),
                    String.valueOf(entity.getCommentTotal() + "")));

            //满意度
            BasePercentVO basePercentVO = new BasePercentVO();
            double rate = entity.getCommentTotal() == 0 ? 0D :
                    MathUtil.divide(BigDecimal.valueOf(entity.getCommentExcellent()), BigDecimal.valueOf(entity.getCommentTotal()), 4).doubleValue();
            basePercentVO.setRate(rate);
            basePercentVO.setName(DateTimeUtil.dateToString(entity.getCommentTime(), "yyyy-MM"));
            basePercentVO.setValue(String.valueOf(entity.getCommentExcellent() + ""));
            excellentList.add(basePercentVO);
        }
        return commentDayForCommentDate;
    }

    public IPage<BaseVO> queryTotalOtaTop(PageQuery query) {
        return baseMapper.queryTotalOtaTop(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    public IPage<BasePercentVO> queryExcellentTotalOtaTop(PageQuery query) {
        return baseMapper.queryExcellentTotalOtaTop(new Page(query.getPageNo(), query.getPageSize()), query);
    }

    /**
     * 近12月好评趋势/近12月差评趋势
     *
     * @return
     */
    public CommentMonthForCommentDate queryMonthForCommentDate() {
        CommentMonthForCommentDate commentMonthForCommentDate = new CommentMonthForCommentDate();
        //近12月好评趋势
        List<BaseVO> excellentList = new ArrayList<>();
        commentMonthForCommentDate.setExcellentList(excellentList);
        //近12月差评趋势
        List<BaseVO> poorList = new ArrayList<>();
        commentMonthForCommentDate.setPoorList(poorList);

        //近12月的评论数，好中差总数
        List<PraiseTypeSummaryEntity> entitys = this.baseMapper.queryMonthForCommentDate();
        for (PraiseTypeSummaryEntity entity : entitys) {
            excellentList.add(new BaseVO(
                    DateTimeUtil.dateToString(entity.getCommentTime(), "yyyy-MM"),
                    String.valueOf(entity.getCommentExcellent() + "")
            ));

            poorList.add(new BaseVO(
                    DateTimeUtil.dateToString(entity.getCommentTime(), "yyyy-MM"),
                    String.valueOf(entity.getCommentPoor() + "")
            ));
        }

        return commentMonthForCommentDate;
    }



}
