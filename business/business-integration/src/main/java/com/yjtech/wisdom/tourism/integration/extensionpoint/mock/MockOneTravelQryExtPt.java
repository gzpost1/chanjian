package com.yjtech.wisdom.tourism.integration.extensionpoint.mock;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OneTravelSimulationDataBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.OperationDataInfo;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.mybatis.entity.SimulationBaseVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.onetravel.SimulationOneTravelDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一码游相关 模拟数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 13:39
 */
@Extension(bizId = ExtensionConstant.ONE_TRAVEL,
        useCase = OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockOneTravelQryExtPt implements OneTravelQryExtPt {

    /**
     * 年龄分布计算系数
     */
    private static final double USER_AGE_CALCULATE_COEFFICIENT = 14.66;
    /**
     * 年龄分布名称-其他
     */
    private static final String USER_AGE_DISTRIBUTION_NAME = "其他";

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private OneTravelApiService oneTravelApiService;


    /**
     * 查询一码游访问次数-综合总览
     *
     * @param vo
     * @return
     */
    @Override
    public Integer queryOneTravelVisitIndex(IndexQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelVisitIndex();
    }

    /**
     * 查询一码游交易-综合总览
     *
     * @param vo
     * @return
     */
    @Override
    public FxDistOrderStatisticsBO queryOneTravelTradeIndex(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelTradeIndex();
    }

    /**
     * 查询一码游投诉
     *
     * @param vo
     * @return
     */
    @Override
    public Integer queryOneTravelComplaint(OneTravelQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelComplaint();
    }

    /**
     * 查询一码游投诉趋势-一码游投诉
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOneTravelComplaintAnalysis(OneTravelQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelComplaintAnalysis();
    }

    /**
     * 查询一码游受理状态分布-一码游投诉
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOneTravelComplaintDistribution(OneTravelQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelComplaintDistribution();
    }

    /**
     * 查询全国用户分布-一码游统计
     *
     * @return
     */
    @Override
    public List<OneTravelAreaVisitStatisticsBO> queryUserNationDistribution(SimulationBaseVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getUserNationDistribution();
    }

    /**
     * 查询访问数据-一码游统计
     *
     * @return
     */
    @Override
    public OneTravelVisitStatisticsBO queryVisitStatistics(SimulationBaseVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getVisitStatistics();
    }

    /**
     * 查询交易数据-一码游统计
     *
     * @param vo
     * @return
     */
    @Override
    public FxDistOrderStatisticsBO queryOneTravelTradeStatistics(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOneTravelTradeStatistics();
    }

    /**
     * 查询用户年龄分布-一码游统计
     *
     * @return
     */
    @Override
    public List<BasePercentVO> queryUserAgeDistribution(SimulationBaseVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getUserAgeDistribution();
    }

    /**
     * 运营数据-一码游统计
     *
     * @return
     */
    @Override
    public OperationDataInfo queryOperationStatistics(SimulationBaseVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOperationStatistics();
    }

    /**
     * 商品订单分布-一码游统计
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOrderFromProductTypeDistribution(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOrderFromProductTypeDistribution();
    }

    /**
     * 商品交易额分布-一码游统计
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryOrderSumFromProductTypeDistribution(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOrderSumFromProductTypeDistribution();
    }

    /**
     * 本年订单趋势-一码游统计
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOrderAnalysis(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOrderAnalysis();
    }

    /**
     * 本年交易额趋势-一码游统计
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryOrderSumAnalysis(FxDistQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getOrderSumAnalysis();
    }


    /**
     * 计算并查询模拟数据
     *
     * @date 2021/9/1 16:59
     * @author horadirm
     */
    public OneTravelSimulationDataBO calculateAndQuery(LocalDateTime beginTime, LocalDateTime endTime) {
        //获取缓存数据
        OneTravelSimulationDataBO cacheInfo = redisCache.getCacheObject(CacheKeyContants.ONE_TRAVEL_SIMULATION_PREFIX + beginTime + endTime);
        if (null != cacheInfo) {
            return cacheInfo;
        }

        //获取初始化模板数据
        SimulationOneTravelDTO simulationOneTravelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.ONE_TRAVEL);
        //获取模板随机数
        Integer randomNumber = Integer.valueOf(simulationOneTravelDTO.getRandomNumber());
        //获取时段天数
        int untilDay = (int) beginTime.until(endTime, ChronoUnit.DAYS);

        //获取当前时间
        LocalDateTime now = LocalDateTime.now();

        //查询一码游访问次数-综合总览
        Integer oneTravelVisitIndex = simulationOneTravelDTO.getHourOfVisit() * now.getHour();

        //查询一码游交易-综合总览
        FxDistOrderStatisticsBO oneTravelTradeIndex = new FxDistOrderStatisticsBO((long) ((simulationOneTravelDTO.getDayOfOrderCount() + randomNumber) * untilDay),
                new BigDecimal(((simulationOneTravelDTO.getDayOfOrderSum().intValue() + randomNumber) * untilDay)));

        //查询一码游投诉
        Integer oneTravelComplaint = (simulationOneTravelDTO.getDayOfComplaintCount() / 10 + randomNumber) * untilDay;

        //查询一码游投诉趋势-一码游投诉
        List<AnalysisBaseInfo> oneTravelComplaintAnalysis = calculateAnalysis(now.toLocalDate().toString(),
                new BigDecimal((simulationOneTravelDTO.getDayOfComplaintCount() / 10 + randomNumber) * now.getDayOfMonth() + simulationOneTravelDTO.getMonthOfComplaintCountTotal()));

        //查询一码游受理状态分布-一码游投诉
        List<BasePercentVO> oneTravelComplaintDistribution = simulationOneTravelDTO.getComplaintStatusDistribution();

        //查询全国用户分布-一码游统计
        List<OneTravelAreaVisitStatisticsBO> userNationDistribution = Lists.newArrayList();
        List<BasePercentVO> provinceList = simulationOneTravelDTO.getProvinceOutSideSourceDistribution();
        if(null != provinceList && !provinceList.isEmpty()){
            //获取使用总人数
            Integer userTotal = simulationOneTravelDTO.getUserTotal();
            for (BasePercentVO province : provinceList) {
                userNationDistribution.add(new OneTravelAreaVisitStatisticsBO().toBuilder().name(province.getName()).value(new BigDecimal(userTotal * Double.valueOf(province.getValue())).setScale(0, BigDecimal.ROUND_HALF_UP).longValue()).build());
            }
        }

        //查询访问数据-一码游统计
        OneTravelVisitStatisticsBO visitStatistics = new OneTravelVisitStatisticsBO(new BigDecimal(simulationOneTravelDTO.getUserTotal()), new BigDecimal(oneTravelVisitIndex),
                new BigDecimal(simulationOneTravelDTO.getYesterdayVisit()), new BigDecimal(simulationOneTravelDTO.getVisitTotal()));

        //查询交易数据-一码游统计
        FxDistOrderStatisticsBO oneTravelTradeStatistics = oneTravelTradeIndex;

        //查询用户年龄分布-一码游统计
        List<BasePercentVO> userAgeDistribution = Lists.newArrayList();
        //获取用户年龄分布名称
        List<String> userAgeDistributionNameList = oneTravelApiService.queryUserAgeDistributionName();
        if (null == userAgeDistributionNameList || userAgeDistributionNameList.isEmpty()) {
            userAgeDistribution.add(new BasePercentVO(USER_AGE_DISTRIBUTION_NAME, null, 100.0));
        } else {
            //除“其他”之外的分布总计
            double statistics = 0.0;
            //遍历用户年龄名称
            for (String userAgeDistributionName : userAgeDistributionNameList) {
                if (USER_AGE_DISTRIBUTION_NAME.equals(userAgeDistributionName)) {
                    continue;
                }
                //随机值
                int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
                //获取分布占比
                double rate = USER_AGE_CALCULATE_COEFFICIENT + randomInt / 10.0;
                userAgeDistribution.add(new BasePercentVO(userAgeDistributionName, null, rate));
                statistics += rate;
            }
            //设置其他
            userAgeDistribution.add(new BasePercentVO(USER_AGE_DISTRIBUTION_NAME, null, 100.0 - statistics));
        }

        //运营数据-一码游统计
        OperationDataInfo operationStatistics = new OperationDataInfo(simulationOneTravelDTO.getScenic() + randomNumber, simulationOneTravelDTO.getMerchant() + randomNumber, simulationOneTravelDTO.getProductTotal() + randomNumber);

        //商品订单分布-一码游统计
        List<BasePercentVO> orderFromProductTypeDistribution = simulationOneTravelDTO.getProductTypeDistribution();

        //商品交易额分布-一码游统计
        List<BasePercentVO> orderSumFromProductTypeDistribution = simulationOneTravelDTO.getProductTypeDistribution();

        //本年订单趋势-一码游统计
        List<AnalysisBaseInfo> orderAnalysis = calculateAnalysis(now.toLocalDate().toString(),
                new BigDecimal((simulationOneTravelDTO.getDayOfOrderCount() + randomNumber) * now.getDayOfMonth() + simulationOneTravelDTO.getMonthOfOrderCountTotal()));

        //本年交易额趋势-一码游统计
        List<AnalysisBaseInfo> orderSumAnalysis = calculateAnalysis(now.toLocalDate().toString(),
                (simulationOneTravelDTO.getDayOfOrderSum().add(new BigDecimal(randomNumber))).multiply(new BigDecimal(now.getDayOfMonth())).add(simulationOneTravelDTO.getMonthOfOrderSumTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));


        OneTravelSimulationDataBO dto = new OneTravelSimulationDataBO(oneTravelVisitIndex, oneTravelTradeIndex, oneTravelComplaint, oneTravelComplaintAnalysis,
                oneTravelComplaintDistribution, userNationDistribution, visitStatistics, oneTravelTradeStatistics, userAgeDistribution,
                operationStatistics, orderFromProductTypeDistribution, orderSumFromProductTypeDistribution, orderAnalysis, orderSumAnalysis);

        //获取缓存数据
        redisCache.setCacheObject(CacheKeyContants.ONE_TRAVEL_SIMULATION_PREFIX + beginTime + endTime, dto, (int) DateUtils.getCacheExpire(), TimeUnit.MINUTES);

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
        for (String monthMark : monthMarkList) {
            //随机值
            int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
            //设置去年信息
            AnalysisMonthChartInfo lastYearByMonth = new AnalysisMonthChartInfo(DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.offset(DateUtils.dateTime(DateUtils.YYYY_MM, monthMark), DateField.YEAR, -1)));

            //设置今年信息
            AnalysisMonthChartInfo currentYearByMonth;
            /**
             * 匹配当前月
             * 1.月累计投诉量
             * 2.月累计订单量
             * 3.月累计订单金额
             */
            if (currentMonth.equals(monthMark)) {
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, monthOfTotal, lastYearByMonth.getCount(), lastMonthValue);
            }
            /**
             * 匹配非当前月
             * 1.月累计投诉量
             * 2.月累计订单量
             * 3.月累计订单金额
             */
            else {
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, monthOfTotal.multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 1, BigDecimal.ROUND_HALF_UP), lastYearByMonth.getCount(), lastMonthValue);
            }

            //同步同比、环比
            lastYearByMonth.setSame(currentYearByMonth.getSame());
            lastYearByMonth.setSequential(currentYearByMonth.getSequential());

            currentData.add(currentYearByMonth);
            lastData.add(lastYearByMonth);

            lastMonthValue = currentYearByMonth.getCount();
        }

        return Arrays.asList(
                //封装今年月趋势信息
                new AnalysisBaseInfo(currentData.get(0).getTime().substring(0, 4), currentData),
                //封装去年月趋势信息
                new AnalysisBaseInfo(lastData.get(0).getTime().substring(0, 4), lastData)
        );
    }


}
