package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.ExtensionPointI;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xulei
 * @create 2021-07-14 14:52
 */
public interface ScenicQryExtPt extends ExtensionPointI {

    /**
     * 游客接待量
     */
    List<BaseVO> queryTouristReception(ScenicScreenQuery query);

    /**
     * 景区分布——游客关注及美誉度
     */
     MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(ScenicScreenQuery query);

    /**
     * 景区分布——查询景区评价类型分布
     */
     List<BasePercentVO> queryEvaluateTypeDistribution(ScenicScreenQuery query);

    /**
     * 景区分布——客流趋势(只需要传type)
     */
     List<BaseValueVO> queryPassengerFlow( ScenicScreenQuery query);

    /**
     * 景区分布——热度趋势
     *
     */
     List<BaseValueVO> queryHeatTrend(ScenicScreenQuery query);

    /**
     * 景区分布——满意度趋势
     */
    List<BaseValueVO> querySatisfactionTrend(ScenicScreenQuery query);

    /**
     * 评论热词
     * @param query
     * @return
     */
    public List<BaseVO> queryScenicHotRank(ScenicScreenQuery query);
}
