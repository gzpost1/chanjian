package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.mock;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseDto;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.common.utils.spring.SpringUtils;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingEvaluateEntity;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.entity.dto.ScenicTrendDto;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.systemconfig.menu.dto.SystemconfigChartsListDatavDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.scenic.SimulationScenicDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

/**
 * 景区模拟数据扩展点
 *
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.SCENIC,
        useCase = ScenicExtensionConstant.SCENIC_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockScenicQryExtPt implements ScenicQryExtPt {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ScenicService scenicService;

    private Cache<String, Integer> cache = CacheBuilder.newBuilder()
            .maximumSize(10000) // 设置缓存的最大容量
            .expireAfterWrite(12 * 60, TimeUnit.MINUTES)
            .concurrencyLevel(10) // 设置并发级别为10
            .build();

    @Override
    public List<BaseVO> queryTouristReception(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        BigDecimal timeInterval = getTimeInterval(query);
        List<ScenicEntity> list = queryScenicList(query.getScenicId());

        BigDecimal dailylyQuantity = new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        Integer bearCapacity = 0;
        for (ScenicEntity entity : list) {
            //小时客流量 = 小时初始客流量+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
            BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

            //日累计客流量 = 小时客流量*当前时刻数+输入值
            //今日接待人次=日累计客流量
            dailylyQuantity = dailylyQuantity.add(hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend()));

            //累计游客人次=日累计客流量*筛选时段天数
            total = total.add(dailylyQuantity.multiply(timeInterval));

            //承载度=今日接待人次/单个景区承载量*100%，结果保留2位小数
            bearCapacity = bearCapacity + entity.getBearCapacity();


        }
        double BearingRate = Objects.isNull(bearCapacity) || bearCapacity == 0 ? 0D : MathUtil.calPercent(new BigDecimal(String.valueOf(dailylyQuantity)),
                new BigDecimal(String.valueOf(bearCapacity)), 3).doubleValue();

        List<BaseVO> vos = new ArrayList<>();
        vos.add(BaseVO.builder().name("todayCheckNum").value(String.valueOf(dailylyQuantity)).build());
        vos.add(BaseVO.builder().name("totalCheckNum").value(String.valueOf(total)).build());
        vos.add(BaseVO.builder().name("bearingRate").value(String.valueOf(BearingRate)).build());
        return vos;
    }

    @Override
    public MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);

        BigDecimal timeInterval = getTimeInterval(query);
        List<ScenicEntity> list = queryScenicList(query.getScenicId());

        BigDecimal evaluate = new BigDecimal(0);
        BigDecimal score = new BigDecimal(0);
        for (ScenicEntity entity : list) {

            //日累计评价量 = 日评价初始数+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "daily_evaluate");
            BigDecimal dailyEvaluate = dto.getInitialDailyEvaluate().add(randomForCache1);
            //收获评价=日累计评价量*筛选时段天数。
            evaluate = evaluate.add(dailyEvaluate.multiply(timeInterval));

            //评分 + 评分初始数+随机数/10
            BigDecimal randomForCache3 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(query.getScenicId()), "score:");
            score = score.add(dto.getInitialScore().add(randomForCache3.divide(new BigDecimal(10))));
        }

        BigDecimal satisfaction = new BigDecimal(0);
        if (Objects.nonNull(query.getScenicId())) {
            //评价类型分布-好评 = 好评占比初始值+随机数/5+随机数/100
            //满意度=评价类型分布-好评。
            DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM");
            BigDecimal good_evaluate = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(query.getScenicId()), "good_evaluate:");
            satisfaction = dto.getInitialPraiseRate().add(good_evaluate.divide(new BigDecimal(5))).add(good_evaluate.divide(new BigDecimal(100)));

        } else {
            //通过统计筛选时段各个景区评价数量*好评占比，结果保留整数，汇总各个景区好评数量，满意度=好评数量/收获评价数量*100%，结果保留2位小数。
            BigDecimal goodEvaluate = evaluate.multiply(dto.getInitialPraiseRate()).divide(new BigDecimal(100), 0);
            satisfaction = MathUtil.calPercent(goodEvaluate, evaluate, 3);


            //评分：汇总所有景区评分/有评分值的景区数量，结果保留2位小数
            if (CollectionUtils.isNotEmpty(list)) {
                score = score.divide(new BigDecimal(list.size()), 3);
            }
        }

        MarketingEvaluateStatisticsDTO marketingEvaluateStatisticsDTO = new MarketingEvaluateStatisticsDTO();
        marketingEvaluateStatisticsDTO.setEvaluateTotal(evaluate.intValue());
        marketingEvaluateStatisticsDTO.setSatisfaction(satisfaction);
        marketingEvaluateStatisticsDTO.setRate(score);
        return marketingEvaluateStatisticsDTO;
    }

    @Override
    public List<BasePercentVO> queryEvaluateTypeDistribution(ScenicScreenQuery query) {
        ArrayList<BasePercentVO> result = Lists.newArrayList();
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);

        List<ScenicEntity> list = queryScenicList(query.getScenicId());
        BigDecimal satisfactionTotal = new BigDecimal(0);
        BigDecimal normalTotal = new BigDecimal(0);
        BigDecimal badTotal = new BigDecimal(0);
        for (ScenicEntity entity : list) {

            //评价类型分布-好评	好评占比初始值+随机数/5+随机数/100
            // 好评占比=评价类型分布-好评
            BigDecimal good_evaluate = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "good_evaluate");
            BigDecimal satisfaction = dto.getInitialPraiseRate().add(good_evaluate.divide(new BigDecimal(5))).add(good_evaluate.divide(new BigDecimal(100)));
            satisfactionTotal = satisfactionTotal.add(satisfaction);

            //评价类型分布-中评 =（100-好评占比）/2+随机数
            // 中评占比=评价类型分布-中评
            BigDecimal normal_evaluate = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "normal_evaluate");
            BigDecimal normal = (new BigDecimal(100).subtract(satisfaction)).divide(new BigDecimal(2)).add(normal_evaluate);
            normalTotal = normalTotal.add(normal);

            //评价类型分布-差评	100-好评占比-差评占比
            // 差评占比=评价类型分布-差评
            BigDecimal bad = new BigDecimal(100).subtract(satisfaction).subtract(normal);
            badTotal = badTotal.add(bad);
        }
        BigDecimal size = new BigDecimal(list.size());

        result.add(BasePercentVO.builder().name("好评").rate(size.compareTo(new BigDecimal(0)) == 0 ? 0d : satisfactionTotal.divide(size, 3).doubleValue()).build());

        result.add(BasePercentVO.builder().name("中评").rate(size.compareTo(new BigDecimal(0)) == 0 ? 0d : normalTotal.divide(size, 3).doubleValue()).build());

        result.add(BasePercentVO.builder().name("差评").rate(size.compareTo(new BigDecimal(0)) == 0 ? 0d : badTotal.divide(size, 3).doubleValue()).build());
        return result;
    }

    /**
     * 客流趋势
     * 节约时间  将正确数据拿来修改
     *
     * @param query
     * @return
     */
    @Override
    public List<BaseValueVO> queryPassengerFlow(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        if (query.getType() != 1 && query.getType() != 4) {
            ScenicTrendDto trendDto = new ScenicTrendDto();
            //设置当前时间、同比时间、环比时间
            scenicService.evaluation(trendDto, query.getType().intValue());
            ScenicScreenQuery copyQuery = BeanMapper.copyBean(query, ScenicScreenQuery.class);
            //查询当前时间检票数据.
            copyQuery.setBeginTime(trendDto.getCurBeginDate());
            copyQuery.setEndTime(trendDto.getCurEndDate());
            Map<String, Integer> curMap = queryCheckTrend(copyQuery);
            //查询同比时间检票数据.
            copyQuery.setBeginTime(trendDto.getTbBeginDate());
            copyQuery.setEndTime(trendDto.getTbEndDate());
            Map<String, Integer> tbMap = queryCheckTrend(copyQuery);
            //查询同比时间检票数据.
            copyQuery.setBeginTime(trendDto.getHbBeginDate());
            copyQuery.setEndTime(trendDto.getHbEndDate());
            Map<String, Integer> hbMap = queryCheckTrend(copyQuery);
            int curNum, tbNum, hbNum = 0;
            for (String date : trendDto.getAbscissa()) {
                //设置当前时间数
                curNum = !isNull(curMap.get(date)) ? curMap.get(date) : 0;
                //设置同比时间数和占比
                String tbDate = scenicService.dateToDateFormat(date, "year");
                tbNum = !isNull(tbMap.get(tbDate)) ? tbMap.get(tbDate) : 0;
                String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
                //设置环比时间数和占比
                if (query.getType().intValue() == 2) {
                    String hbDate = scenicService.dateToDateFormat(date, "month");
                    hbNum = !isNull(hbMap.get(hbDate)) ? hbMap.get(hbDate) : 0;
                    //月份设置时间为yyyy-mm
                    date = date.substring(0, 7);
                } else if (query.getType().intValue() == 3) {
                    String hbDate = scenicService.dateToDateFormat(date, "day");
                    hbNum = !isNull(hbMap.get(hbDate)) ? hbMap.get(hbDate) : 0;
                }
                String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
                resultList.add(MonthPassengerFlowDto.builder().date(date).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
            }
            if (CollectionUtils.isNotEmpty(resultList)) {
                resultList.forEach(item -> item.setTime(item.getDate()));
            }
            //设置query时间，方便MultipleBuildAnalysis返回
            query.setBeginTime(trendDto.getCurBeginDate());
            query.setEndTime(LocalDateTime.now());
        }
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                resultList,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseValueVO> queryHeatTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        ScenicTrendDto trendDto = new ScenicTrendDto();
        //设置当前时间、同比时间、环比时间
        scenicService.evaluation(trendDto, query.getType().intValue());
        EvaluateQueryVO queryVO = scenicService.queryToEvaluateQueryVO(query);
        //查询当前时间热词数据.
        queryVO.setBeginTime(trendDto.getCurBeginDate());
        queryVO.setEndTime(trendDto.getCurEndDate());
        Map<String, String> curMap = queryHeat(queryVO);
        //查询同比时间热词数据.
        queryVO.setBeginTime(trendDto.getTbBeginDate());
        queryVO.setEndTime(trendDto.getTbEndDate());
        Map<String, String> tbMap = queryHeat(queryVO);
        //查询环比时间热词数据.
        queryVO.setBeginTime(trendDto.getHbBeginDate());
        queryVO.setEndTime(trendDto.getHbEndDate());
        Map<String, String> hbMap = queryHeat(queryVO);
        int curNum, tbNum, hbNum;
        for (String date : trendDto.getAbscissa()) {
            curNum = StringUtils.isNotBlank(curMap.get(date)) ? Integer.parseInt(curMap.get(date)) : 0;
            String tbDate = scenicService.dateToDateFormat(date, "year");
            tbNum = StringUtils.isNotBlank(tbMap.get(tbDate)) ? Integer.parseInt(tbMap.get(tbDate)) : 0;
            String hbDate = scenicService.dateToDateFormat(date, "month");
            hbNum = StringUtils.isNotBlank(hbMap.get(hbDate)) ? Integer.parseInt(hbMap.get(hbDate)) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
            resultList.add(MonthPassengerFlowDto.builder().date(date.substring(0, 7)).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.forEach(item -> item.setTime(item.getDate()));
        }
        //设置时间，方便MultipleBuildAnalysis返回
        query.setBeginTime(trendDto.getCurBeginDate());
        query.setEndTime(LocalDateTime.now());
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                resultList,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseValueVO> querySatisfactionTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        ScenicTrendDto trendDto = new ScenicTrendDto();
        //设置当前时间、同比时间、环比时间
        scenicService.evaluation(trendDto, query.getType().intValue());
        EvaluateQueryVO queryVO = scenicService.queryToEvaluateQueryVO(query);
        //查询当前时间满意度数据.
        queryVO.setBeginTime(trendDto.getCurBeginDate());
        queryVO.setEndTime(trendDto.getCurEndDate());
        Map<String, String> curMap = querySatisfaction(queryVO);
        //查询同比时间满意度数据.
        queryVO.setBeginTime(trendDto.getTbBeginDate());
        queryVO.setEndTime(trendDto.getTbEndDate());
        Map<String, String> tbMap = querySatisfaction(queryVO);
        //查询环比时间满意度数据.
        queryVO.setBeginTime(trendDto.getHbBeginDate());
        queryVO.setEndTime(trendDto.getHbEndDate());
        Map<String, String> hbMap = querySatisfaction(queryVO);
        int curNum, tbNum, hbNum;
        for (String date : trendDto.getAbscissa()) {
            curNum = StringUtils.isNotBlank(curMap.get(date)) ? Integer.parseInt(curMap.get(date)) : 0;
            String tbDate = scenicService.dateToDateFormat(date, "year");
            tbNum = StringUtils.isNotBlank(tbMap.get(tbDate)) ? Integer.parseInt(tbMap.get(tbDate)) : 0;
            String hbDate = scenicService.dateToDateFormat(date, "month");
            hbNum = StringUtils.isNotBlank(hbMap.get(hbDate)) ? Integer.parseInt(hbMap.get(hbDate)) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
            resultList.add(MonthPassengerFlowDto.builder().date(date.substring(0, 7)).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.forEach(item -> item.setTime(item.getDate()));
        }
        //设置时间，方便MultipleBuildAnalysis返回
        query.setBeginTime(trendDto.getCurBeginDate());
        query.setEndTime(LocalDateTime.now());
        return AnalysisUtils.MultipleBuildAnalysis(
                query,
                resultList,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    @Override
    public List<BaseVO> queryScenicHotRank(ScenicScreenQuery query) {
        List<BaseVO> result = Lists.newArrayList();
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        List<BaseDto> hotWords = dto.getHotWords();
        BigDecimal timeInterval = getTimeInterval(query);

        List<ScenicEntity> list = queryScenicList(query.getScenicId());
        for (BaseDto baseDto : hotWords) {
            BigDecimal q = new BigDecimal(0);
            for (ScenicEntity entity : list) {
                BigDecimal hot_rank = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hot_rank");
                //单日词频=单日初始词频+随机数/10
                BigDecimal dailyHotRank = new BigDecimal(String.valueOf(baseDto.getValue())).add(hot_rank.divide(new BigDecimal(10)));
                //每个热词词频=单日词频*筛选时段天数
                q = q.add(timeInterval.multiply(dailyHotRank));
            }
            result.add(BaseVO.builder().name(baseDto.getName()).value(String.valueOf(q.intValue())).build());
        }
        return result;
    }

    @Override
    public IPage<ScenicBaseVo> queryPassengerFlowTop5(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        BigDecimal timeInterval = getTimeInterval(query);
        List<ScenicEntity> list = queryScenicList(query.getScenicId());
        List<ScenicBaseVo> result = Lists.newArrayList();
        for (ScenicEntity entity : list) {
            //小时客流量 = 小时初始客流量+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
            BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

            //日累计客流量 = 小时客流量*当前时刻数+输入值
            //今日接待人次=日累计客流量
            BigDecimal dailylyQuantity = hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend());

            //累计游客人次=日累计客流量*筛选时段天数
            BigDecimal total = dailylyQuantity.multiply(timeInterval);

            result.add(ScenicBaseVo.builder().id(entity.getId()).name(entity.getName()).value(String.valueOf(total)).build());
        }
        result = result.stream().sorted(Comparator.comparing(ScenicBaseVo::getValue).reversed()).collect(Collectors.toList());
        List<List<ScenicBaseVo>> partition = Lists.partition(result, query.getPageSize().intValue());
        Page<ScenicBaseVo> page = new Page<>();
        page.setRecords(partition.get(query.getPageNo().intValue() -1));
        page.setTotal(result.size());
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageNo());
        page.setPages(partition.size());
        return page;
    }

    @Override
    public IPage<BaseVO> queryEvaluateTop5(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);

        BigDecimal timeInterval = getTimeInterval(query);
        List<ScenicEntity> list = queryScenicList(query.getScenicId());
        List<BaseVO> result = Lists.newArrayList();
        BigDecimal evaluate = new BigDecimal(0);
        for (ScenicEntity entity : list) {

            //日累计评价量 = 日评价初始数+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "daily_evaluate");
            BigDecimal dailyEvaluate = dto.getInitialDailyEvaluate().add(randomForCache1);
            //收获评价=日累计评价量*筛选时段天数。
            evaluate = evaluate.add(dailyEvaluate.multiply(timeInterval));
            result.add(BaseVO.builder().name(entity.getName()).value(String.valueOf(evaluate)).build());
        }
        result = result.stream().sorted(Comparator.comparing(BaseVO::getValue).reversed()).collect(Collectors.toList());
        List<List<BaseVO>> partition = Lists.partition(result, query.getPageSize().intValue());
        Page<BaseVO> page = new Page<>();
        page.setRecords(partition.get(query.getPageNo().intValue() -1));
        page.setTotal(result.size());
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageNo());
        page.setPages(partition.size());
        return page;
    }

    @Override
    public IPage<ScenicBaseVo> querySatisfactionTop5(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);

        List<ScenicEntity> list = queryScenicList(query.getScenicId());

        List<ScenicBaseVo> result = Lists.newArrayList();
        for (ScenicEntity entity : list) {
            //评价类型分布-好评 = 好评占比初始值+随机数/5+随机数/100
            //满意度=评价类型分布-好评。
            BigDecimal good_evaluate = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "good_evaluate:");
            BigDecimal satisfaction = dto.getInitialPraiseRate().add(good_evaluate.divide(new BigDecimal(5))).add(good_evaluate.divide(new BigDecimal(100)));
            result.add(ScenicBaseVo.builder().id(entity.getId()).name(entity.getName()).value(String.valueOf(satisfaction)).build());
        }
        result = result.stream().sorted(Comparator.comparing(ScenicBaseVo::getValue).reversed()).collect(Collectors.toList());
        List<List<ScenicBaseVo>> partition = Lists.partition(result, query.getPageSize().intValue());
        Page<ScenicBaseVo> page = new Page<>();
        page.setRecords(partition.get(query.getPageNo().intValue() -1));
        page.setTotal(result.size());
        page.setSize(query.getPageSize());
        page.setCurrent(query.getPageNo());
        page.setPages(partition.size());
        return page;
    }

    /**
     * 从缓存中获得随机数  若不存在则更新缓存
     * <p>
     * 由于此处有多次循环  缓存使用本地缓存
     *
     * @param domain 一般是各个业务domain
     * @param day    表明是哪天一个的随机数
     * @return
     */
    public BigDecimal getRandomForCache(String domain, String scenic, String day) {
        Integer value = cache.getIfPresent(getMyCacheKey(domain, scenic, day));

        //双重校验锁 避免多线程时 缓存不一致
        if (Objects.isNull(value)) {
            synchronized (this) {
                value = cache.getIfPresent(getMyCacheKey(domain, scenic, day));
                if (Objects.isNull(value)) {
                    //随机数
                    value = (int) (-20 + Math.random() * (20 - (-20) + 1));
                    cache.put(getMyCacheKey(domain, scenic, day), value);
                }
            }
        }
        return new BigDecimal(String.valueOf(value));
    }

    /**
     * 获得 随机数的key
     *
     * @return
     */
    public String getMyCacheKey(String domain, String scenic, String day) {
        return Constants.SIMULATION_KEY + domain + ":" + scenic + ":" + day;
    }

    /**
     * 获得距离凌晨的过期时间
     *
     * @return
     */
    public Long getTimeOut() {
        //第二天凌晨1点
        LocalDateTime endTime = LocalDate.now().atStartOfDay().plusDays(1).plusHours(SimulationConstants.HOUR);
        return Duration.between(LocalDateTime.now(), endTime).toMinutes();
    }

    public BigDecimal getTimeInterval(ScenicScreenQuery query) {
        //如果不传入时间  默认查询时间为往前推30天
        if (Objects.isNull(query.getBeginTime()) || Objects.isNull(query.getEndTime())) {
            return new BigDecimal("30");
        }
        long until = query.getBeginTime().until(query.getEndTime(), ChronoUnit.DAYS);
        return new BigDecimal(String.valueOf(until));

    }

    private Map<String, Integer> queryCheckTrend(ScenicScreenQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        LocalDateTime now = LocalDateTime.now();
        int dayOfMonth = now.getDayOfMonth();
        int monthValue = now.getMonthValue();
        int hour = now.getHour();
        LocalDateTime beginTime = query.getBeginTime();
        LocalDateTime endTime = query.getEndTime();
        List<SaleTrendVO> tbSaleTrendVOS = Lists.newArrayList();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<ScenicEntity> list = queryScenicList(query.getScenicId());

        if (query.getType() == 2) {
            while (beginTime.isBefore(endTime)) {
                BigDecimal visitQuantity = new BigDecimal(0);
                for (ScenicEntity entity : list) {

                    //小时客流量 = 小时初始客流量+随机数
                    BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
                    BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

                    //日累计客流量 = 小时客流量*当前时刻数+输入值
                    BigDecimal dailylyQuantity = hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend());

                    //月累计客流量 = 日累计客流量*当前日期号数+输入值
                    BigDecimal monQuantity = dailylyQuantity.multiply(new BigDecimal(dayOfMonth)).add(dto.getMonthlyAddend());

                    //○ 本月=月客流分布-本月
                    //○ 其他月份=月客流分布-除本月外其他月份
                    if (Objects.equals(beginTime.getMonthValue(), monthValue)) {
                        visitQuantity = visitQuantity.add(monQuantity);
                    } else {
                        //月客流分布-除本月外其他月份 = 月累计客流量*（100+随机数）/100
                        BigDecimal randomForCache2 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "mon_visit_quantity:" + beginTime.format(dateTimeFormatter1));
                        BigDecimal otherMon = monQuantity.multiply(new BigDecimal(100).add(randomForCache2)).divide(new BigDecimal(100));
                        visitQuantity = visitQuantity.add(otherMon);
                    }
                }
                SaleTrendVO saleTrendVO = new SaleTrendVO();
                saleTrendVO.setTime(beginTime.format(dateTimeFormatter) + "-01");
                saleTrendVO.setVisitQuantity(visitQuantity.intValue());
                tbSaleTrendVOS.add(saleTrendVO);
                beginTime = beginTime.plusMonths(1);
            }
        } else if (query.getType() == 3) {
            while (beginTime.isBefore(endTime)) {
                BigDecimal visitQuantity = new BigDecimal(0);
                for (ScenicEntity entity : list) {

                    //小时客流量 = 小时初始客流量+随机数
                    BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
                    BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

                    //日累计客流量 = 小时客流量*当前时刻数+输入值
                    BigDecimal dailylyQuantity = hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend());

                    //月累计客流量 = 日累计客流量*当前日期号数+输入值
                    BigDecimal monQuantity = dailylyQuantity.multiply(new BigDecimal(dayOfMonth)).add(dto.getMonthlyAddend());

                    //○ 今日=本月客流趋势-今日
                    //○ 其他日期=本月客流趋势-其他日期
                    if (Objects.equals(beginTime.getDayOfMonth(), dayOfMonth)) {
                        //本月客流趋势-今日 = 日累计客流量
                        visitQuantity = visitQuantity.add(dailylyQuantity);
                    } else {
                        //本月客流趋势-其他日期 =（月累计客流量-日累计客流量）/（系统当前日期号数-1）*（100+随机数）/100
                        BigDecimal randomForCache2 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "mon_visit_quantity:" + beginTime.getMonthValue() + "-" + beginTime.getDayOfMonth());
                        BigDecimal otherDay = new BigDecimal(0);
                        if (dayOfMonth != 1) {
                            otherDay = (monQuantity.subtract(dailylyQuantity)).divide(new BigDecimal(dayOfMonth - 1)).multiply(new BigDecimal(100).add(randomForCache2)).divide(new BigDecimal(100));
                        }
                        visitQuantity = visitQuantity.add(otherDay);
                    }
                }
                SaleTrendVO saleTrendVO = new SaleTrendVO();
                saleTrendVO.setTime(beginTime.format(dateTimeFormatter1));
                saleTrendVO.setVisitQuantity(visitQuantity.intValue());
                tbSaleTrendVOS.add(saleTrendVO);
                beginTime = beginTime.plusDays(1);
            }
        }


        return tbSaleTrendVOS.stream().collect(Collectors.toMap(SaleTrendVO::getTime, SaleTrendVO::getVisitQuantity));
    }

    private Map<String, String> queryHeat(EvaluateQueryVO query) {
        LocalDateTime now = LocalDateTime.now();
        int monthValue = now.getMonthValue();
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        //日累计评价量 = 日评价初始数+随机数
        BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(query.getPlaceId()), "daily_evaluate");
        BigDecimal dailyEvaluate = dto.getInitialDailyEvaluate().add(randomForCache1);

        //月累计评价量 = 日累计评价量*当前日期号数+输入值
        BigDecimal monEvaluate = dailyEvaluate.multiply(new BigDecimal(now.getDayOfMonth())).add(dto.getMonthlyEvaluateAddend());


        ArrayList<BaseVO> curBaseVOS = Lists.newArrayList();
        LocalDateTime beginTime = query.getBeginTime();
        LocalDateTime endTime = query.getEndTime();
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM");

        List<ScenicEntity> list = queryScenicList(org.apache.commons.lang3.StringUtils.isBlank(query.getPlaceId()) ? null : Long.valueOf(query.getPlaceId()));

        while (beginTime.isBefore(endTime)) {
            BigDecimal quantity = new BigDecimal(0);
            for (ScenicEntity entity : list) {
                if (Objects.equals(beginTime.getMonthValue(), monthValue)) {
                    //本月=月评价分布-本月
                    //月评价分布-本月 = 月累计评价量
                    quantity = quantity.add(monEvaluate);
                } else {
                    //其他月=月评价分布-其他月
                    //月评价分布-其他月 = 月累计评价量*（100+随机数）/100
                    BigDecimal randomForCache2 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "mon_evaluate:" + beginTime.format(dateTimeFormatter1));
                    BigDecimal otherMon = monEvaluate.multiply(new BigDecimal(100).add(randomForCache2)).divide(new BigDecimal(100));
                    quantity = quantity.add(otherMon);
                }
            }
            BaseVO vo = new BaseVO();
            vo.setValue(String.valueOf(quantity.intValue()));
            vo.setName(beginTime.format(dateTimeFormatter1) + "-01");
            curBaseVOS.add(vo);
            beginTime = beginTime.plusMonths(1);
        }
        return curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
    }

    private Map<String, String> querySatisfaction(EvaluateQueryVO query) {
        // 收获评价=日累计评价量*筛选时段天数（没有输入时间 默认30天）
        BigDecimal timeInterval = new BigDecimal(30);
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);


        ArrayList<BaseVO> curBaseVOS = Lists.newArrayList();
        LocalDateTime beginTime = query.getBeginTime();
        LocalDateTime endTime = query.getEndTime();
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM");

        List<ScenicEntity> list = queryScenicList(org.apache.commons.lang3.StringUtils.isBlank(query.getPlaceId()) ? null : Long.valueOf(query.getPlaceId()));

        while (beginTime.isBefore(endTime)) {
            BigDecimal satisfaction = new BigDecimal(0);
            BigDecimal evaluate = new BigDecimal(0);

            //评价类型分布-好评	好评占比初始值+随机数/5+随机数/100
            // 好评占比=评价类型分布-好评
            BigDecimal good_evaluate = getRandomForCache(SimulationConstants.SCENIC, query.getPlaceId(), "good_evaluate:" + beginTime.format(dateTimeFormatter1));
            satisfaction = dto.getInitialPraiseRate().add(good_evaluate.divide(new BigDecimal(5))).add(good_evaluate.divide(new BigDecimal(100)));

            if (Objects.isNull(query.getPlaceId())) {
                for (ScenicEntity entity : list) {
                    //日累计评价量 = 日评价初始数+随机数
                    BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "daily_evaluate");
                    BigDecimal dailyEvaluate = dto.getInitialDailyEvaluate().add(randomForCache1);
                    //收获评价=日累计评价量*筛选时段天数。
                    evaluate = evaluate.add(dailyEvaluate.multiply(timeInterval));
                }
                //● 各月满意度：汇总各个景区该月的评价数量得到评价总数，通过各个景区评价数量*好评占比得到好评数量，汇总好评数量/评价总数得到该月满意度。
                BigDecimal goodEvaluate = evaluate.multiply(satisfaction).divide(new BigDecimal(100));
                satisfaction = MathUtil.calPercent(goodEvaluate, evaluate, 3);
            }
            BaseVO vo = new BaseVO();
            vo.setValue(String.valueOf(satisfaction.intValue()));
            vo.setName(beginTime.format(dateTimeFormatter1) + "-01");
            curBaseVOS.add(vo);
            beginTime = beginTime.plusMonths(1);
        }
        return curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
    }

    private List<ScenicEntity> queryScenicList(Long scenicId) {
        LambdaQueryWrapper<ScenicEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(scenicId), ScenicEntity::getId, scenicId);
        queryWrapper.eq(ScenicEntity::getStatus, 1);
        List<ScenicEntity> list = scenicService.list(queryWrapper);
        return list;
    }
}
