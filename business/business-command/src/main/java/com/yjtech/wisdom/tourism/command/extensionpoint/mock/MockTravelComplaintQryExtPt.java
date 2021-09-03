package com.yjtech.wisdom.tourism.command.extensionpoint.mock;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.command.dto.travelcomplaint.TravelComplaintSimulationDTO;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintQryExtPt;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getTravelComplaintTotalToIndex();
    }

    /**
     * 查询旅游投诉总量
     * @param vo
     * @return
     */
    @Override
    public Integer queryTravelComplaintTotal(TravelComplaintScreenQueryVO vo) {
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getTravelComplaintTotal();
    }

    /**
     * 查询旅游投诉类型分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintTypeDistribution(TravelComplaintScreenQueryVO vo) {
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getComplaintTypeDistribution();
    }

    /**
     * 查询旅游投诉状态分布
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryComplaintStatusDistribution(TravelComplaintScreenQueryVO vo) {
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getComplaintStatusDistribution();
    }

    /**
     * 查询旅游投诉类型Top排行
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryComplaintTopByType(TravelComplaintScreenQueryVO vo) {
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getComplaintTopByType();
    }

    /**
     * 查询旅游投诉趋势、同比、环比
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryComplaintAnalysis(TravelComplaintScreenQueryVO vo) {
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getComplaintAnalysis();
    }

    /**
     * 查询统计
     * @param vo
     * @return
     */
    private Integer queryTotal(TravelComplaintScreenQueryVO vo){
        return calculateAndQuery(vo.getComplaintType(), vo.getBeginTime(), vo.getEndTime()).getQueryTotal();
    }


    /**
     * 计算并查询模拟数据
     * @param complaintType
     * @param beginTime
     * @param endTime
     * @return
     */
    public TravelComplaintSimulationDTO calculateAndQuery(Byte complaintType, LocalDateTime beginTime, LocalDateTime endTime){
        //获取缓存数据
        TravelComplaintSimulationDTO cacheInfo = redisCache.getCacheObject(CacheKeyContants.TRAVEL_COMPLAINT_SIMULATION_PREFIX + beginTime + endTime);
        if (null != cacheInfo) {
            return cacheInfo;
        }

        //获取当前时间
        LocalDateTime now = LocalDateTime.now();

        //获取初始化模板数据
        SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);

        //查询综合总览-旅游投诉总量
        Integer travelComplaintTotalToIndex = null == beginTime || null == endTime ?
                simulationTravelComplaintDTO.getDayOfComplaint().intValue() :
                simulationTravelComplaintDTO.getDayOfComplaint().multiply(new BigDecimal(beginTime.until(endTime, ChronoUnit.DAYS))).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

        //查询旅游投诉总量
        Integer travelComplaintTotal = travelComplaintTotalToIndex;

        //查询旅游投诉类型分布
        List<BasePercentVO> complaintTypeDistribution = simulationTravelComplaintDTO.getComplaintTypeDistribution();

        //查询旅游投诉状态分布
        List<BasePercentVO> complaintStatusDistribution = simulationTravelComplaintDTO.getComplaintStatusDistribution();

        //查询旅游投诉类型Top排行
        List<BaseVO> complaintTopByType;
        if(TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_SCENIC.getValue().equals(complaintType)){
            complaintTopByType =  simulationTravelComplaintDTO.getScenicComplaintRank();
        }else if(TravelComplaintTypeEnum.TRAVEL_COMPLAINT_TYPE_HOTEL.getValue().equals(complaintType)){
            complaintTopByType =  simulationTravelComplaintDTO.getHotelComplaintRank();
        }else {
            complaintTopByType = Collections.emptyList();
        }

        //查询旅游投诉趋势、同比、环比
        List<AnalysisBaseInfo> complaintAnalysis = calculateAnalysis(now.toLocalDate().toString(), simulationTravelComplaintDTO.getMonthOfComplaintTotal());

        //查询统计
        Integer queryTotal = travelComplaintTotalToIndex;

        TravelComplaintSimulationDTO dto = new TravelComplaintSimulationDTO(travelComplaintTotalToIndex, travelComplaintTotal, complaintTypeDistribution,
                complaintStatusDistribution, complaintTopByType, complaintAnalysis, queryTotal);

        //获取缓存数据
        redisCache.setCacheObject(CacheKeyContants.TRAVEL_COMPLAINT_SIMULATION_PREFIX + beginTime + endTime, dto, (int) DateUtils.getCacheExpire(), TimeUnit.MINUTES);

        return dto;
    }


    /**
     * 计算信息趋势，同比、环比
     *
     * @param currentDate
     * @param monthOfTotal
     * @return
     */
    private List<AnalysisBaseInfo> calculateAnalysis(String currentDate, BigDecimal monthOfTotal) {
        //获取当前月份
        String currentMonth = currentDate.substring(0, 7);

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
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, monthOfTotal, lastYearByMonth.getCount(), lastMonthValue);
            }
            //匹配非当前月，则进行计算：月累计投诉量*（100+随机数）/100
            else {
                BigDecimal count = monthOfTotal.multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 1, BigDecimal.ROUND_HALF_UP);
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, count, lastYearByMonth.getCount(), lastMonthValue);
            }

            lastMonthValue = currentYearByMonth.getCount();

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
}
