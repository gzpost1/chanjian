package com.yjtech.wisdom.tourism.resource.scenic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.utils.*;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenVo;
import com.yjtech.wisdom.tourism.resource.scenic.mapper.ScenicMapper;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketCheckService;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.weather.service.WeatherService;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import com.yjtech.wisdom.tourism.weather.web.WeatherQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

@Service
public class ScenicService extends ServiceImpl<ScenicMapper, ScenicEntity> {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private TicketCheckService ticketCheckService;
    @Autowired
    private MarketingEvaluateService evaluateService;

    public IPage<ScenicEntity> queryForPage(ScenicPageQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName(), Constants.STATUS_NEGATIVE.byteValue()).orderByDesc(ScenicEntity::getCreateTime);
        return page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper);
    }

    /**
     * 景区分布——分页查询
     */
    public IPage queryScreenForPage(ScenicPageQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName(), Constants.STATUS_NEGATIVE.byteValue()).orderByDesc(ScenicEntity::getLevel);
        IPage page = page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper)
                .convert(item -> BeanMapper.copyBean(item, ScenicScreenVo.class));
        List<ScenicScreenVo> records = page.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(item -> {
                //天气
                WeatherQuery weatherQuery = new WeatherQuery();
                weatherQuery.setLatitude(item.getLatitude());
                weatherQuery.setLongitude(item.getLongitude());
                item.setWeatherInfoVO(queryWeatherByAreaCode(weatherQuery));
            });
        }
        return page;
    }

    /**
     * 景区分布——景区等级分布
     */
    public List<ScenicBaseVo> queryLevelDistribution() {
        LambdaQueryWrapper<ScenicEntity> wrapper = getCommonWrapper(null, (byte) 1);
        List<ScenicEntity> list = list(wrapper);
        ArrayList<ScenicBaseVo> vos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<SysDictData> dictCache = DictUtils.getDictCache("scenic_level");
            Map<String, SysDictData> sysDictMap = dictCache.stream().collect(Collectors.toMap(SysDictData::getDictValue, e -> e));
            Map<String, List<ScenicEntity>> map = list.stream().collect(Collectors.groupingBy(ScenicEntity::getLevel));
            for (Map.Entry<String, List<ScenicEntity>> next : map.entrySet()) {
                SysDictData dictData = sysDictMap.get(next.getKey());
                vos.add(ScenicBaseVo.builder()
                        .name(isNull(dictData) ? "" : dictData.getDictLabel())
                        .value(String.valueOf(MathUtil.calPercent(new BigDecimal(next.getValue().size()), BigDecimal.valueOf(list.size()), 3).doubleValue()))
                        .build());
            }
        }
        return vos;
    }

    /**
     * 景区介绍
     */
    public ScenicScreenVo queryIntroduce(ScenicScreenQuery query) {
        ScenicEntity one = getOne(
                new LambdaQueryWrapper<ScenicEntity>()
                        .eq(ScenicEntity::getId, query.getScenicId())
                        .eq(ScenicEntity::getStatus, 1));
        if (!isNull(one)) {
            ScenicScreenVo scenicScreenVo = BeanMapper.copyBean(one, ScenicScreenVo.class);
            //天气
            WeatherQuery weatherQuery = new WeatherQuery();
            weatherQuery.setLatitude(scenicScreenVo.getLatitude());
            weatherQuery.setLongitude(scenicScreenVo.getLongitude());
            scenicScreenVo.setWeatherInfoVO(queryWeatherByAreaCode(weatherQuery));
            return scenicScreenVo;
        }
        return null;
    }

    /**
     * 游客接待量
     */
    public List<ScenicBaseVo> queryTouristReception(ScenicScreenQuery query) {
        List<ScenicBaseVo> vos = new ArrayList<>();
        //今日检票数
        Integer todayCheckNum = ticketCheckService.queryCheckNumByTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
                , LocalDateTime.of(LocalDate.now(), LocalTime.MAX), query.getScenicId());
        //累计检票数
        Integer totalCheckNum = ticketCheckService.queryCheckNumByTime(query.getBeginTime(), query.getEndTime(), query.getScenicId());
        //承载度
        ScenicEntity scenicEntity = getOne(new LambdaQueryWrapper<ScenicEntity>().eq(ScenicEntity::getId, query.getScenicId()));
        double BearingRate = scenicEntity.getBearCapacity() == 0 ? 0D : MathUtil.calPercent(new BigDecimal(String.valueOf(todayCheckNum))
                , new BigDecimal(String.valueOf(scenicEntity.getBearCapacity())), 3).doubleValue();

        vos.add(ScenicBaseVo.builder().name("todayCheckNum").value(String.valueOf(todayCheckNum)).build());
        vos.add(ScenicBaseVo.builder().name("totalCheckNum").value(String.valueOf(totalCheckNum)).build());
        vos.add(ScenicBaseVo.builder().name("bearingRate").value(String.valueOf(BearingRate)).build());
        return vos;
    }

    /**
     * 客流趋势
     */
    public List<MonthPassengerFlowDto> queryPassengerFlowTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        //当前时间
        LocalDateTime curBeginDate = null;
        LocalDateTime curEndDate = null;
        //同比时间
        LocalDateTime tbBeginDate = null;
        LocalDateTime tbEndDate = null;
        //环比时间
        LocalDateTime hbBeginDate = null;
        LocalDateTime hbEndDate = null;
        //横坐标长度(截至当前时刻的时间)
        List<String> abscissa = Lists.newArrayList();
        if (query.getType().intValue() == 1) {
            curBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear());
            curEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear());
            tbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusYears(1);
            tbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusYears(1);
            hbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusMonths(1);
            hbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusMonths(1);
            int timeNum = LocalDate.now().getMonthValue();
            for (int i = 1; i <= timeNum; i++) {
                String monthAndDay = (LocalDate.now().getMonthValue() < 10 ? "-0" : "-") + i + "-01";
                abscissa.add(LocalDate.now().getYear() + monthAndDay);
            }

        } else if (query.getType().intValue() == 2) {
            curBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
            curEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth());
            tbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1);
            tbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()).minusMonths(1);
            hbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).minusDays(1);
            hbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()).minusDays(1);
            int timeNum = LocalDate.now().getDayOfMonth();
            for (int i = 1; i <= timeNum; i++) {
                String month = (LocalDate.now().getMonthValue() < 10 ? "-0" : "-") + i;
                String day = (i < 10 ? "-0" : "-") + i;
                abscissa.add(LocalDate.now().getYear() + month + day);
            }
        }
        //查询当前时间检票数据.
        query.setBeginTime(curBeginDate);
        query.setEndTime(curEndDate);
        List<SaleTrendVO> curSaleTrendVOS = ticketCheckService.queryCheckTrendByTime(query);
        Map<String, Integer> curMap = curSaleTrendVOS.stream().collect(Collectors.toMap(SaleTrendVO::getTime, SaleTrendVO::getVisitQuantity));
        //查询同比时间检票数据.
        query.setBeginTime(tbBeginDate);
        query.setEndTime(tbEndDate);
        List<SaleTrendVO> tbSaleTrendVOS = ticketCheckService.queryCheckTrendByTime(query);
        Map<String, Integer> tbMap = tbSaleTrendVOS.stream().collect(Collectors.toMap(SaleTrendVO::getTime, SaleTrendVO::getVisitQuantity));
        //查询同比时间检票数据.
        query.setBeginTime(hbBeginDate);
        query.setEndTime(hbEndDate);
        List<SaleTrendVO> hbSaleTrendVOS = ticketCheckService.queryCheckTrendByTime(query);
        Map<String, Integer> hbMap = hbSaleTrendVOS.stream().collect(Collectors.toMap(SaleTrendVO::getTime, SaleTrendVO::getVisitQuantity));
        int curNum, tbNum, hbNum = 0;
        for (String date : abscissa) {
            if (query.getType().intValue() == 1) {
                String hbDate = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusMonths(1), "yyyy-MM-dd");
                hbNum = !isNull(hbMap.get(hbDate)) ? hbMap.get(hbDate) : 0;
            } else if (query.getType().intValue() == 2) {
                String hbDate = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusDays(1), "yyyy-MM-dd");
                hbNum = !isNull(hbMap.get(hbDate)) ? hbMap.get(hbDate) : 0;
            }
            curNum = !isNull(curMap.get(date)) ? curMap.get(date) : 0;
            tbNum = !isNull(tbMap.get(date)) ? tbMap.get(date) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
            resultList.add(MonthPassengerFlowDto.builder().date(String.valueOf(date)).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.forEach(item -> item.setTime(item.getDate()));
        }
        return resultList;
    }

    /**
     * 景区分布——查询景区评价类型分布
     */
    public List<BasePercentVO> queryEvaluateTypeDistribution(ScenicScreenQuery query) {
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setBeginTime(query.getBeginTime());
        queryVO.setEndTime((query.getEndTime()));
        queryVO.setEquipStatus((byte) 1);
        return evaluateService.queryScenicEvaluateTypeDistribution(queryVO);
    }

    /**
     * 景区分布——游客关注及美誉度
     */
    public MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(ScenicScreenQuery query) {
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setBeginTime(query.getBeginTime());
        queryVO.setEndTime((query.getEndTime()));
        queryVO.setEquipStatus(Constants.STATUS_NEGATIVE.byteValue());
        return evaluateService.queryScenicEvaluateStatistics(queryVO);
    }

    /**
     * 景区大数据——客流排行
     */
    public IPage<ScenicBaseVo> queryPassengerFlowTop5(ScenicPageQuery query) {
        return ticketCheckService.queryPassengerFlowTop5(query);
    }

    /**
     * 景区大数据——评价排行
     */
    public IPage<BaseVO> queryEvaluateTop5(ScenicPageQuery query) {
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setPageNo(query.getPageNo());
        queryVO.setPageSize(query.getPageSize());
        queryVO.setEquipStatus(Constants.STATUS_NEGATIVE.byteValue());
        return evaluateService.queryEvaluateTop5(queryVO);
    }

    /**
     * 景区大数据——满意度排行
     */
    public IPage<BaseVO> querySatisfactionTop5(ScenicPageQuery query) {
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setPageNo(query.getPageNo());
        queryVO.setPageSize(query.getPageSize());
        queryVO.setEquipStatus(Constants.STATUS_NEGATIVE.byteValue());
        return evaluateService.querySatisfactionTop5(queryVO);
    }

    /**
     * 景区大数据——热度趋势
     */
    public List<MonthPassengerFlowDto> queryHeatTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        //当前时间
        LocalDateTime curBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime curEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear());
        //同比时间
        LocalDateTime tbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusYears(1);
        LocalDateTime tbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusYears(1);
        //环比时间
        LocalDateTime hbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusMonths(1);
        LocalDateTime hbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusMonths(1);
        //横坐标长度(截至当前时刻的时间)
        List<String> abscissa = Lists.newArrayList();
        int timeNum = LocalDate.now().getMonthValue();
        for (int i = 1; i <= timeNum; i++) {
            String monthAndDay = (LocalDate.now().getMonthValue() < 10 ? "-0" : "-") + i + "-01";
            abscissa.add(LocalDate.now().getYear() + monthAndDay);
        }
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setPlaceId(String.valueOf(query.getScenicId()));
        queryVO.setBeginTime(curBeginDate);
        queryVO.setEndTime(curEndDate);
        queryVO.setEquipStatus(Constants.STATUS_NEGATIVE.byteValue());
        List<BaseVO> curBaseVOS = evaluateService.queryHeatTrend(queryVO);
        Map<String, String> curMap = curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        queryVO.setBeginTime(tbBeginDate);
        queryVO.setEndTime(tbEndDate);
        List<BaseVO> tbBaseVOS = evaluateService.queryHeatTrend(queryVO);
        Map<String, String> tbMap = tbBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        queryVO.setBeginTime(hbBeginDate);
        queryVO.setEndTime(hbEndDate);
        List<BaseVO> hbBaseVOS = evaluateService.queryHeatTrend(queryVO);
        Map<String, String> hbMap = hbBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        int curNum, tbNum, hbNum;
        for (String date : abscissa) {
            curNum = StringUtils.isNotBlank(curMap.get(date)) ? Integer.parseInt(curMap.get(date)) : 0;
            tbNum = StringUtils.isNotBlank(tbMap.get(date)) ? Integer.parseInt(tbMap.get(date)) : 0;
            String hbDate = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusMonths(1), "yyyy-MM-dd");
            hbNum = StringUtils.isNotBlank(hbMap.get(hbDate)) ? Integer.parseInt(hbMap.get(hbDate)) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
            resultList.add(MonthPassengerFlowDto.builder().date(String.valueOf(date)).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.forEach(item -> item.setTime(item.getDate()));
        }
        return resultList;
    }

    /**
     * 景区大数据——满意度趋势
     */
    public List<MonthPassengerFlowDto> querySatisfactionTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        //当前时间
        LocalDateTime curBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime curEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear());
        //同比时间
        LocalDateTime tbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusYears(1);
        LocalDateTime tbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusYears(1);
        //环比时间
        LocalDateTime hbBeginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusMonths(1);
        LocalDateTime hbEndDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusMonths(1);
        //横坐标长度(截至当前时刻的时间)
        List<String> abscissa = Lists.newArrayList();
        int timeNum = LocalDate.now().getMonthValue();
        for (int i = 1; i <= timeNum; i++) {
            String monthAndDay = (LocalDate.now().getMonthValue() < 10 ? "-0" : "-") + i + "-01";
            abscissa.add(LocalDate.now().getYear() + monthAndDay);
        }
        EvaluateScreenQueryVO queryVO = new EvaluateScreenQueryVO();
        queryVO.setPlaceId(String.valueOf(query.getScenicId()));
        queryVO.setBeginTime(curBeginDate);
        queryVO.setEndTime(curEndDate);
        queryVO.setEquipStatus(Constants.STATUS_NEGATIVE.byteValue());
        List<BaseVO> curBaseVOS = evaluateService.querySatisfactionTrend(queryVO);
        Map<String, String> curMap = curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        queryVO.setBeginTime(tbBeginDate);
        queryVO.setEndTime(tbEndDate);
        List<BaseVO> tbBaseVOS = evaluateService.querySatisfactionTrend(queryVO);
        Map<String, String> tbMap = tbBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        queryVO.setBeginTime(hbBeginDate);
        queryVO.setEndTime(hbEndDate);
        List<BaseVO> hbBaseVOS = evaluateService.querySatisfactionTrend(queryVO);
        Map<String, String> hbMap = hbBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
        int curNum, tbNum, hbNum;
        for (String date : abscissa) {
            curNum = StringUtils.isNotBlank(curMap.get(date)) ? Integer.parseInt(curMap.get(date)) : 0;
            tbNum = StringUtils.isNotBlank(tbMap.get(date)) ? Integer.parseInt(tbMap.get(date)) : 0;
            String hbDate = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusMonths(1), "yyyy-MM-dd");
            hbNum = StringUtils.isNotBlank(hbMap.get(hbDate)) ? Integer.parseInt(hbMap.get(hbDate)) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            String hbRate = hbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - hbNum), new BigDecimal(hbNum), 3).doubleValue());
            resultList.add(MonthPassengerFlowDto.builder().date(String.valueOf(date)).number(curNum).tbNumber(tbNum).tbScale(tbRate).hbScale(hbRate).build());
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.forEach(item -> item.setTime(item.getDate()));
        }
        return resultList;
    }

    //获取天气
    private WeatherInfoVO queryWeatherByAreaCode(WeatherQuery query) {
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("location", query.getLongitude() + "," + query.getLatitude());
            String code = EncryptUtil.makeMD5(query.getLatitude() + query.getLongitude());
            return weatherService.queryWeatherInfo(map, code);
        } catch (Exception e) {
            log.error("景区获取天气信息失败......", e);
            e.printStackTrace();
        }
        return null;
    }

    //公共分页lambda
    private LambdaQueryWrapper<ScenicEntity> getCommonWrapper(String name, Byte status) {
        return new LambdaQueryWrapper<ScenicEntity>()
                .like(StringUtils.isNotBlank(name), ScenicEntity::getName, name)
                .eq(!isNull(status), ScenicEntity::getStatus, status);
    }
}
