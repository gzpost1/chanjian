package com.yjtech.wisdom.tourism.command.extensionpoint.mock;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintQryExtPt;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.complaint.SimulationTravelComplaintDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 旅游投诉 模拟数据 扩展
 *
 * @date 2021/8/21 10:21
 * @author horadirm
 */
@Extension(bizId = ExtensionConstant.TRAVEL_COMPLAINT,
        useCase = TravelComplaintExtensionConstant.TRAVEL_COMPLAINT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockTravelComplaintQryExtPt implements TravelComplaintQryExtPt {

    @Autowired
    private RedisCache redisCache;


    /**
     * 查询综合总览-旅游投诉总量
     * @param vo
     * @return
     */
    @Override
    public Integer queryTravelComplaintTotalToIndex(TravelComplaintScreenQueryVO vo) {
        return queryTotal(vo);
    }

    /**
     * 查询旅游投诉总量
     * @param vo
     * @return
     */
    @Override
    public Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo) {
        return queryTotal(vo);
    }

    /**
     * 查询旅游投诉类型分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo) {
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);
        return simulationTravelComplaintDTO.getComplaintTypeDistribution();
    }

    /**
     * 查询旅游投诉状态分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo) {
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);
        return simulationTravelComplaintDTO.getComplaintStatusDistribution();
    }

    /**
     * 查询旅游投诉类型Top排行
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo) {
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);

        //获取景区Top排行
        if(TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_SCENIC.getValue().equals(vo.getComplaintType())){
            return simulationTravelComplaintDTO.getScenicComplaintRank();
        }else if(TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_HOTEL.getValue().equals(vo.getComplaintType())){
            return simulationTravelComplaintDTO.getHotelComplaintRank();
        }

        return Collections.emptyList();
    }

    /**
     * 查询旅游投诉趋势、同比、环比
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo) {
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);

        //获取当前月份
        String currentMonth = DateUtils.parseDateToStr(DateUtils.YYYY_MM, new Date());

        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //构建今年月趋势信息
        List<AnalysisMonthChartInfo> currentData = new ArrayList<>();
        //构建去年月趋势信息
        List<AnalysisMonthChartInfo> lastData = new ArrayList<>();

        //临时标记上月默认值，用于计算环比
        BigDecimal lastMonthValue = BigDecimal.ZERO;

        //校验月趋势信息，为无结果的查询设置默认值
        for(String monthMark : monthMarkList){
            //随机值
            int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
            //设置去年信息
            AnalysisMonthChartInfo lastYearByMonth = new AnalysisMonthChartInfo(DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.offset(DateUtils.dateTime(DateUtils.YYYY_MM, monthMark), DateField.YEAR, -1)));

            //设置今年信息
            AnalysisMonthChartInfo currentYearByMonth;
            //匹配当前月，直接使用：月累计投诉量
            if(currentMonth.equals(monthMark)){
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, simulationTravelComplaintDTO.getMonthOfComplaintTotal(), lastYearByMonth.getCount(), lastMonthValue);
            }
            //匹配非当前月，则进行计算：月累计投诉量*（100+随机数）/100
            else {
                BigDecimal count = simulationTravelComplaintDTO.getMonthOfComplaintTotal().multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 1, BigDecimal.ROUND_HALF_UP);
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, count, lastYearByMonth.getCount(), lastMonthValue);
            }

            //同步同比、环比
            lastYearByMonth.setSame(currentYearByMonth.getSame());
            lastYearByMonth.setSequential(currentYearByMonth.getSequential());

            currentData.add(currentYearByMonth);
            lastData.add(lastYearByMonth);
        }

        return Arrays.asList(
                //封装今年月趋势信息
                new AnalysisBaseInfo(currentData.get(0).getTime().substring(0, 4), currentData),
                //封装去年月趋势信息
                new AnalysisBaseInfo(lastData.get(0).getTime().substring(0, 4), lastData)
        );
    }

    /**
     * 查询统计
     * @param vo
     * @return
     */
    private Integer queryTotal(TravelComplaintScreenQueryVO vo){
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);
        //获取查询时间间隔
        return null == vo.getBeginTime() || null == vo.getEndTime() ?
                simulationTravelComplaintDTO.getDayOfComplaint().intValue() :
                simulationTravelComplaintDTO.getDayOfComplaint().multiply(new BigDecimal(vo.getBeginTime().until(vo.getEndTime(), ChronoUnit.DAYS))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

}
