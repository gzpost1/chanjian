package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.impl;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 事务模拟数据扩展点
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.SCENIC,
        useCase = ScenicExtensionConstant.SCENIC_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplScenicQryExtPt implements ScenicQryExtPt {

    @Autowired
    private ScenicService scenicService;

    @Override
    public List<BaseVO> queryTouristReception(ScenicScreenQuery query) {
        return scenicService.queryTouristReception(query);
    }

    @Override
    public MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(ScenicScreenQuery query) {
        return scenicService.queryScenicEvaluateStatistics(query);
    }

    @Override
    public List<BasePercentVO> queryEvaluateTypeDistribution(ScenicScreenQuery query) {
        return scenicService.queryEvaluateTypeDistribution(query);
    }

    @Override
    public List<BaseValueVO> queryPassengerFlow(ScenicScreenQuery query) {
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                scenicService.queryPassengerFlowTrend(query),
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseValueVO> queryHeatTrend(ScenicScreenQuery query) {
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                scenicService.queryHeatTrend(query),
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseValueVO> querySatisfactionTrend(ScenicScreenQuery query) {
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                scenicService.querySatisfactionTrend(query),
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseVO> queryScenicHotRank(ScenicScreenQuery query) {
        return scenicService.queryScenicHotRank(query);
    }
}