package com.yjtech.wisdom.tourism.command.extensionpoint.mock;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseDto;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.enums.AnalysisDateTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.spring.SpringUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.event.SimulationEventDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 事务模拟数据扩展点
 *
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.BIZ_EVENT,
        useCase = EventExtensionConstant.EVENT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockEventQryExtPt implements EventQryExtPt {

    @Autowired
    private RedisCache redisCache;

    /**
     * ● 受时间筛选影响。
     * ● 应急事件=日累计事件量*筛选时段天数。
     * ● 处理率=已处理时间占比输入值。
     *
     * @param query
     * @return
     */
    @Override
    public List<BaseVO> queryEventQuantity(EventSumaryQuery query) {
        ArrayList<BaseVO> result = Lists.newArrayList();
        SimulationEventDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.EVENT);
        //日累计事件量 = 初始事件量+随机数/10
        BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.EVENT, "daily_event");
        BigDecimal dayQuantity = dto.getInitialQuantity().add(randomForCache1.divide(new BigDecimal("10")));
        //应急事件=日累计事件量*筛选时段天数。
        BigDecimal timeInterval = getTimeInterval(query);
        BigDecimal total = dayQuantity.multiply(timeInterval);
        result.add(BaseVO.builder().name("total").value(String.valueOf(total.intValue())).build());
        //处理率=已处理事件占比输入值
        result.add(BasePercentVO.builder().name("processed")
                .value(String.valueOf(total.multiply(dto.getHandledRate()).divide(new BigDecimal(100)).intValue()))
                .rate(dto.getHandledRate().doubleValue())
                .build());
        return result;
    }

    @Override
    public List<BaseValueVO> querySaleTrend(EventSumaryQuery query) {
        List<EventTrendVO> trendVOS = Lists.newArrayList();
        LocalDateTime beginTime = query.getBeginTime();
        LocalDateTime endTime = query.getEndTime();
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(AnalysisDateTypeEnum.ANALYSIS_DATE_TYPE_YEAR_MONTH.getJavaDateFormat());
        SimulationEventDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.EVENT);

        //日累计事件量 = 初始事件量+随机数/10
        BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.EVENT, "daily_event");
        BigDecimal dayQuantity = dto.getInitialQuantity().add(randomForCache1.divide(new BigDecimal("10")));

        //月累计事件量 = 日累计事件量*当前日期号数+月累计事件量加数
        BigDecimal monthlyQuantity = dayQuantity.multiply(new BigDecimal(LocalDate.now().getDayOfMonth())).add(dto.getMonthlyAddend());

        //月事件分布-本月 = 月累计事件量
        EventTrendVO eventTrendVO = new EventTrendVO();
        eventTrendVO.setTime(now.format(dateTimeFormatter));
        eventTrendVO.setQuantity(monthlyQuantity.intValue());
        trendVOS.add( eventTrendVO);

        //月事件分布-其他月 = 月累计事件量*（100+随机数）/100
        while (beginTime.isBefore(endTime) && !Objects.equals(now.getMonthValue(),beginTime.getMonthValue())){
            BigDecimal randomForCache = getRandomForCache(SimulationConstants.EVENT, "month_event:" + beginTime.getMonthValue());
            BigDecimal quantity = monthlyQuantity.multiply(randomForCache.add(new BigDecimal("100"))).divide(new BigDecimal("100"));

            EventTrendVO eventTrendVO2 = new EventTrendVO();
            eventTrendVO2.setTime(beginTime.format(dateTimeFormatter));
            eventTrendVO2.setQuantity(quantity.intValue());
            trendVOS.add( eventTrendVO2);

            beginTime = beginTime.plusMonths(1);
        }

        //今年
        LinkedList<Object> thisYear = new LinkedList<>(AnalysisUtils.supplementTime(query, trendVOS, true, EventTrendVO::getQuantity));

        //去年 模拟数据去年数据均显示0
        query.setBeginTime(query.getBeginTime().plusYears(-1));
        query.setEndTime(query.getEndTime().plusYears(-1));
        List<EventTrendVO> lastYeartrendVOS = Lists.newArrayList();
        while (beginTime.isBefore(endTime)){
            EventTrendVO eventTrendVO3 = new EventTrendVO();
            eventTrendVO3.setTime(beginTime.format(dateTimeFormatter));
            eventTrendVO3.setQuantity(0);
            lastYeartrendVOS.add(eventTrendVO3);
            beginTime = beginTime.plusMonths(1);
        }
        List<BaseValueVO> result = AnalysisUtils.MultipleBuildAnalysis(query, lastYeartrendVOS, true, EventTrendVO::getQuantity);


        List<?> lastYear = Lists.newArrayList();
        for (BaseValueVO vo : result){
            if(Objects.equals(vo.getName(),"quantity")){
                lastYear = vo.getValue();
            }
        }

        //如果是1月  则它的上月就是去年12月 这里加上去年12月
        thisYear.addFirst(lastYear.get(lastYear.size() - 1));
        int size = thisYear.size();
        List<HashMap<String, Object>> resultMap = Lists.newArrayList();
        for (int i = 1; i < size; i++) {
            //较上月变化=（当月事件数量-上一月事件数量）/上一月事件数量*100%
            Object mom = "";
            //较去年变化=（当月事件数量-去年同月事件数量）/去年同月事件数量
            Object yoy = "";
            if (Objects.equals(thisYear.get(i - 1), 0)) {
                mom = "-";
            } else {
                BigDecimal thisMon = new BigDecimal(String.valueOf(thisYear.get(i)));
                BigDecimal lastMon = new BigDecimal(String.valueOf(thisYear.get(i - 1)));
                mom = MathUtil.calPercent(thisMon.subtract(lastMon), lastMon, 3);
            }

            if (Objects.equals(lastYear.get(i - 1), 0)) {
                yoy = "-";
            } else {
                BigDecimal thisMon = new BigDecimal(String.valueOf(thisYear.get(i)));
                BigDecimal lastYearMon = new BigDecimal(String.valueOf(lastYear.get(i - 1)));
                yoy = MathUtil.calPercent(thisMon.subtract(lastYearMon), lastYearMon, 3);
            }
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("data", thisYear.get(i));
            map.put("mom", mom);
            map.put("yoy", yoy);
            resultMap.add(map);

        }
        result.add(BaseValueVO.builder().name("thisYear").value(resultMap).build());
        return result;
    }

    @Override
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        List<BaseVO> result = Lists.newArrayList();

        SimulationEventDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.EVENT);

        //日累计事件量 = 初始事件量+随机数/10
        BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.EVENT, "daily_event");
        BigDecimal dayQuantity = dto.getInitialQuantity().add(randomForCache1.divide(new BigDecimal("10")));
        //应急事件=日累计事件量*筛选时段天数。
        BigDecimal timeInterval = getTimeInterval(query);
        BigDecimal total = dayQuantity.multiply(timeInterval);


        for (BaseDto baseDto : dto.getEventType()) {
            //各类型事件量 = 累计事件量*录入的事件类型分布占比
            int value = total.multiply(new BigDecimal(String.valueOf(baseDto.getValue()))).intValue();
            BasePercentVO basePercentVO = new BasePercentVO();
            basePercentVO.setName(baseDto.getName());
            basePercentVO.setValue(String.valueOf(value));
            basePercentVO.setRate(baseDto.getValue());
            result.add(basePercentVO);
        }
        return result;
    }

    @Override
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        List<BaseVO> result = Lists.newArrayList();

        SimulationEventDto dto = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.EVENT);

        //日累计事件量 = 初始事件量+随机数/10
        BigDecimal randomForCache1 = getRandomForCache(SimulationConstants.EVENT, "daily_event");
        BigDecimal dayQuantity = dto.getInitialQuantity().add(randomForCache1.divide(new BigDecimal("10")));
        //应急事件=日累计事件量*筛选时段天数。
        BigDecimal timeInterval = getTimeInterval(query);
        BigDecimal total = dayQuantity.multiply(timeInterval);

        for (BaseDto baseDto : dto.getEventLevel()) {
            //各类型事件量 = 累计事件量*录入的事件类型分布占比
            int value = total.multiply(new BigDecimal(String.valueOf(baseDto.getValue()))).intValue();
            BasePercentVO basePercentVO = new BasePercentVO();
            basePercentVO.setName(baseDto.getName());
            basePercentVO.setValue(String.valueOf(value));
            basePercentVO.setRate(baseDto.getValue());
            result.add(basePercentVO);
        }
        return result;
    }

    /**
     * 从缓存中获得随机数  若不存在则更新缓存
     *
     * @param key 一般是各个业务domain
     * @param day 表明是哪天一个的随机数 1 今天 2 明天  以此类推
     * @return
     */
    public BigDecimal getRandomForCache(String key, String day) {
        RedisCache redisCache = SpringUtils.getBean(RedisCache.class);
        Integer cache = (Integer) redisCache.getCacheObject(getMyCacheKey(key, day));
        //双重校验锁 避免多线程时 缓存不一致
        if (Objects.isNull(cache)) {
            synchronized (this) {
                cache = (Integer) redisCache.getCacheObject(getMyCacheKey(key, day));
                if (Objects.isNull(cache)) {
                    //随机数
                    cache = (int) (-20 + Math.random() * (20 - (-20) + 1));
                    redisCache.setCacheObject(getMyCacheKey(key, day), cache, getTimeOut().intValue(), TimeUnit.MINUTES);
                }
            }
        }
        return new BigDecimal(String.valueOf(cache));
    }

    /**
     * 获得 随机数的key
     *
     * @param configKey
     * @param day
     * @return
     */
    public String getMyCacheKey(String configKey, String day) {
        return Constants.SYS_DICT_KEY + configKey + ":" + day;
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

    public BigDecimal getTimeInterval(EventSumaryQuery query) {
        //如果不传入时间  默认查询时间为往前推30天
        if (Objects.isNull(query.getBeginTime()) || Objects.isNull(query.getEndTime())) {
            return new BigDecimal("30");
        }
        long until = query.getBeginTime().until(query.getEndTime(), ChronoUnit.DAYS);
        return new BigDecimal(String.valueOf(until));

    }
}
