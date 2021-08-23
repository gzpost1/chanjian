package com.yjtech.wisdom.tourism.marketing.extensionpoint.mock;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.hotel.SimulationHotelDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

/**
 * 酒店民宿评价 模拟数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 13:39
 */
@Extension(bizId = ExtensionConstant.HOTEL,
        useCase = HotelExtensionConstant.HOTEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockHotelQryExtPt implements HotelQryExtPt {

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询评价统计-酒店民宿详情、酒店民宿大数据
     * SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatistics(EvaluateQueryVO vo) {
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);

        return new MarketingEvaluateStatisticsDTO(
                simulationHotelDTO.getDayOfTotalCount() * (int) vo.getBeginTime().until(vo.getEndTime(), ChronoUnit.DAYS),
                simulationHotelDTO.getGoodRatePercent(),
                simulationHotelDTO.getRate());
    }

    /**
     * 查询评价统计-综合总览
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatisticsIndex(EvaluateQueryVO vo) {
        return null;
    }

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateQueryVO vo) {
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);

        BasePercentVO good = new BasePercentVO();
        good.setName("好评");
        good.setRate(simulationHotelDTO.getGoodRatePercent().doubleValue());

        BasePercentVO medium = new BasePercentVO();
        medium.setName("中评");
        medium.setRate(simulationHotelDTO.getMediumRatePercent().doubleValue());

        BasePercentVO bad = new BasePercentVO();
        bad.setName("差评");
        medium.setRate(simulationHotelDTO.getBadRatePercent().doubleValue());

        return Arrays.asList(good, medium, bad);
    }

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryEvaluateHotRank(EvaluateQueryVO vo) {
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);

        //获取热词排行
        List<BaseVO> hotTagRank = simulationHotelDTO.getHotTagRank();

        if(null != hotTagRank && !hotTagRank.isEmpty()){
            for(BaseVO hotTag : hotTagRank){
                //随机值
                int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
                //计算单日词频
                Integer workFrequency = Integer.valueOf(hotTag.getValue()) + randomInt/10;
                //配置实际返回模拟数据
                hotTag.setValue(String.valueOf(workFrequency * (int) vo.getBeginTime().until(vo.getEndTime(), ChronoUnit.DAYS)));
            }
        }

        return hotTagRank;
    }

    /**
     * 查询房型价格统计
     *
     * @param vo
     * @return
     */
    @Override
    public RoomTypePriceScreenDTO queryRoomPriceStatistics(RoomScreenQueryVO vo) {
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);

        return new RoomTypePriceScreenDTO(simulationHotelDTO.getHighestPrice(), simulationHotelDTO.getLowestPrice(), simulationHotelDTO.getAveragePrice());
    }

    /**
     * 查询房型价格趋势
     *
     * @param vo
     * @return
     */
    @Override
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(RoomScreenQueryVO vo) {
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);
        return null;
    }

    /**
     * 查询酒店评价排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<BaseVO> queryEvaluateRank(EvaluateQueryVO vo) {
        return null;
    }

    /**
     * 查询酒店满意度排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<EvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(EvaluateQueryVO vo) {
        return null;
    }

    /**
     * 查询景区评价排行
     *
     * @param query
     * @return
     */
    @Override
    public IPage<BaseVO> queryEvaluateTop5(EvaluateQueryVO query) {
        return null;
    }

    /**
     * 查询景区满意度排行
     *
     * @param query
     * @return
     */
    @Override
    public IPage<EvaluateSatisfactionRankDTO> querySatisfactionTop5(EvaluateQueryVO query) {
        return null;
    }

    /**
     * 查询评价量趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateAnalysis(EvaluateQueryVO vo) {
        return null;
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysis(EvaluateQueryVO vo) {
        return null;
    }
}
