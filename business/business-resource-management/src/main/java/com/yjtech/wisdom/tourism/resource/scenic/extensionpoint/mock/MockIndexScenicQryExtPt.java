package com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.mock;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.common.bean.index.ScenicBuildingDTO;
import com.yjtech.wisdom.tourism.common.bean.index.TodayRealTimeStatisticsDTO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.spring.SpringUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.IndexScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.scenic.SimulationScenicDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 综合总览景区模拟数据扩展点
 *
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.SCENIC,
        useCase = ScenicExtensionConstant.INDEX_SCENIC_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockIndexScenicQryExtPt implements IndexScenicQryExtPt {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ScenicService scenicService;


    @Override
    public TodayRealTimeStatisticsDTO queryVisitStatistics(TicketSummaryQuery query) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        BigDecimal todayReception = new BigDecimal(0);
        BigDecimal bearCapacity = new BigDecimal(0);
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        List<ScenicEntity> list = queryScenicList(null);
        for(ScenicEntity entity : list){

            //小时客流量 = 小时初始客流量+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
            BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

            //日累计客流量 = 小时客流量*当前时刻数+输入值
            BigDecimal dailylyQuantity = hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend());

            //今日接待人次：汇总各个景区【日累计客流量】
            todayReception = todayReception.add(dailylyQuantity);

            //景区承载度：今日接待人次/各个景区承载量汇总*100%，结果保留2位小数
            bearCapacity = bearCapacity.add(new BigDecimal(entity.getBearCapacity()));
        }
        return new TodayRealTimeStatisticsDTO(todayReception.longValue(), MathUtil.calPercent(todayReception,bearCapacity,3),null);
    }


    @Override
    public ScenicBuildingDTO scenicBuilding(IndexQueryVO vo) {
        SimulationScenicDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.SCENIC);
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int day = now.getDayOfMonth();
        BigDecimal tourist = new BigDecimal(0);
        BigDecimal evaluate = new BigDecimal(0);

        List<ScenicEntity> list = queryScenicList(null);
        for(ScenicEntity entity : list){

            //小时客流量 = 小时初始客流量+随机数
            BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()), "hourly_quantity");
            BigDecimal hourlyQuantity = dto.getInitialHourlyQuantity().add(randomForCache1);

            //日累计客流量 = 小时客流量*当前时刻数+输入值
            BigDecimal dailylyQuantity = hourlyQuantity.multiply(new BigDecimal(hour)).add(dto.getDailyAddend());

           // 接待游客人次：汇总各个景区【日累计客流量】*筛选时段天数。
            tourist = tourist.add(dailylyQuantity.multiply(new BigDecimal(day)));

            //日累计评价量 = 日评价初始数+随机数
            BigDecimal randomForCache2 = getRandomForCache(SimulationConstants.SCENIC, String.valueOf(entity.getId()),"daily_evaluate");
            BigDecimal dailyEvaluate = dto.getInitialDailyEvaluate().add(randomForCache1);

            //收获评价：汇总各个景区【日累计评价量】*筛选时段天数
            evaluate = evaluate.add(dailyEvaluate.multiply(new BigDecimal(day)));
        }

        //评价类型分布-好评 = 好评占比初始值+随机数/5+随机数/100
        BigDecimal good_evaluate = getRandomForCache(SimulationConstants.SCENIC, null,"good_evaluate:");
        BigDecimal satisfaction = dto.getInitialPraiseRate().add(good_evaluate.divide(new BigDecimal(5))).add(good_evaluate.divide(new BigDecimal(100))).setScale(1, BigDecimal.ROUND_HALF_UP);
        return new ScenicBuildingDTO(tourist.longValue(),evaluate.intValue(),satisfaction);
    }

    /**
     * 从缓存中获得随机数  若不存在则更新缓存
     *
     * @param domain 一般是各个业务domain
     * @param day 表明是哪天一个的随机数
     * @return
     */
    public BigDecimal getRandomForCache(String domain, String scenic,String day) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        Integer cache = (Integer) redisCache.getCacheObject(getMyCacheKey(domain,scenic, day));
        //双重校验锁 避免多线程时 缓存不一致
        if (Objects.isNull(cache)) {
            synchronized (this) {
                cache = (Integer) redisCache.getCacheObject(getMyCacheKey(domain,scenic, day));
                if (Objects.isNull(cache)) {
                    //随机数
                    cache = (int) (-20 + Math.random() * (20 - (-20) + 1));
                    redisCache.setCacheObject(getMyCacheKey(domain,scenic, day), cache, getTimeOut().intValue(), TimeUnit.MINUTES);
                }
            }
        }
        return new BigDecimal(String.valueOf(cache));
    }

    /**
     * 获得 随机数的key
     *
     * @return
     */
    public String getMyCacheKey(String domain, String scenic,String day) {
        return Constants.SIMULATION_KEY + domain + ":"+scenic+":" + day;
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

    private List<ScenicEntity> queryScenicList(Long scenicId){
        LambdaQueryWrapper<ScenicEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(scenicId), ScenicEntity::getId, scenicId);
        queryWrapper.eq( ScenicEntity::getStatus, 1);
        List<ScenicEntity> list = scenicService.list(queryWrapper);
        return list;
    }
}
