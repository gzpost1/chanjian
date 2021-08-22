package com.yjtech.wisdom.tourism.resource.scenic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.EncryptUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.entity.dto.ScenicTrendDto;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicScreenVo;
import com.yjtech.wisdom.tourism.resource.scenic.mapper.ScenicMapper;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.ticket.service.TicketCheckService;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.weather.service.WeatherService;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import com.yjtech.wisdom.tourism.weather.web.WeatherQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public IPage<ScenicEntity> queryForPage(ScenicScreenQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName()).orderByDesc(ScenicEntity::getCreateTime);
        return page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper);
    }

    /**
     * 景区分布——分页查询
     */
    public IPage queryScreenForPage(ScenicScreenQuery query) {
        LambdaQueryWrapper wrapper = getCommonWrapper(query.getName()).orderByDesc(ScenicEntity::getLevel);
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
    public List<BaseVO> queryLevelDistribution() {
        LambdaQueryWrapper<ScenicEntity> wrapper = getCommonWrapper(null);
        List<ScenicEntity> list = list(wrapper);
        List<BaseVO> vos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            List<SysDictData> dictCache = DictUtils.getDictCache("scenic_level");
            Map<String, SysDictData> sysDictMap = dictCache.stream().collect(Collectors.toMap(SysDictData::getDictValue, e -> e));
            Map<String, List<ScenicEntity>> map = list.stream().collect(Collectors.groupingBy(ScenicEntity::getLevel));
            for (Map.Entry<String, List<ScenicEntity>> next : map.entrySet()) {
                SysDictData dictData = sysDictMap.get(next.getKey());
                vos.add(BaseVO.builder()
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
                        .eq(ScenicEntity::getStatus, EntityConstants.ENABLED));
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
     * 评论列表
     */
    public IPage<MarketingEvaluateListDTO> queryCommentForPage(ScenicScreenQuery query) {
        return evaluateService.queryForPage(queryToEvaluateQueryVO(query));
    }

    /**
     * 游客接待量
     */
    public List<BaseVO> queryTouristReception(ScenicScreenQuery query) {
        List<BaseVO> vos = new ArrayList<>();
        //今日检票数
        ScenicScreenQuery screenQuery = new ScenicScreenQuery();
        screenQuery.setScenicId(query.getScenicId());
        screenQuery.setBeginTime(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
        screenQuery.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        Integer todayCheckNum = ticketCheckService.queryCheckNumByTime(screenQuery);
        //累计检票数
        Integer totalCheckNum = ticketCheckService.queryCheckNumByTime(query);
        //承载度
        List<ScenicEntity> scenicEntity = list(new LambdaQueryWrapper<ScenicEntity>().eq(!isNull(query.getScenicId()), ScenicEntity::getId, query.getScenicId()));
        int bearCapacityTotal = scenicEntity.stream().mapToInt(ScenicEntity::getBearCapacity).sum();
        double BearingRate = bearCapacityTotal == 0 ? 0D : MathUtil.calPercent(new BigDecimal(String.valueOf(todayCheckNum))
                , new BigDecimal(String.valueOf(bearCapacityTotal)), 3).doubleValue();

        vos.add(BaseVO.builder().name("todayCheckNum").value(String.valueOf(todayCheckNum)).build());
        vos.add(BaseVO.builder().name("totalCheckNum").value(String.valueOf(totalCheckNum)).build());
        vos.add(BaseVO.builder().name("bearingRate").value(String.valueOf(BearingRate)).build());
        return vos;
    }

    /**
     * 客流趋势
     */
    public List<MonthPassengerFlowDto> queryPassengerFlowTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        if (query.getType() == 1 || query.getType() == 4) {
            return resultList;
        }
        ScenicTrendDto trendDto = new ScenicTrendDto();
        //设置当前时间、同比时间、环比时间
        evaluation(trendDto, query.getType().intValue());
        ScenicScreenQuery copyQuery = BeanMapper.copyBean(query, ScenicScreenQuery.class);
        //查询当前时间检票数据.
        copyQuery.setBeginTime(trendDto.getCurBeginDate());
        copyQuery.setEndTime(trendDto.getCurEndDate());
        Map<String, Integer> curMap = queryCheckTrendByTime(query);
        //查询同比时间检票数据.
        copyQuery.setBeginTime(trendDto.getTbBeginDate());
        copyQuery.setEndTime(trendDto.getTbEndDate());
        Map<String, Integer> tbMap = queryCheckTrendByTime(copyQuery);
        //查询同比时间检票数据.
        copyQuery.setBeginTime(trendDto.getHbBeginDate());
        copyQuery.setEndTime(trendDto.getHbEndDate());
        Map<String, Integer> hbMap = queryCheckTrendByTime(copyQuery);
        int curNum, tbNum, hbNum = 0;
        for (String date : trendDto.getAbscissa()) {
            //设置当前时间数
            curNum = !isNull(curMap.get(date)) ? curMap.get(date) : 0;
            //设置同比时间数和占比
            String tbDate = dateToDateFormat(date, "year");
            tbNum = !isNull(tbMap.get(tbDate)) ? tbMap.get(tbDate) : 0;
            String tbRate = tbNum == 0 ? "-" : String.valueOf(MathUtil.calPercent(new BigDecimal(curNum - tbNum), new BigDecimal(tbNum), 3).doubleValue());
            //设置环比时间数和占比
            if (query.getType().intValue() == 2) {
                String hbDate = dateToDateFormat(date, "month");
                hbNum = !isNull(hbMap.get(hbDate)) ? hbMap.get(hbDate) : 0;
                //月份设置时间为yyyy-mm
                date = date.substring(0, 7);
            } else if (query.getType().intValue() == 3) {
                String hbDate = dateToDateFormat(date, "day");
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
        return resultList;
    }


    /**
     * 景区分布——查询景区评价类型分布
     */
    public List<BasePercentVO> queryEvaluateTypeDistribution(ScenicScreenQuery query) {
        return evaluateService.queryScenicEvaluateTypeDistribution(queryToEvaluateQueryVO(query));
    }

    /**
     * 景区分布——游客关注及美誉度
     */
    public MarketingEvaluateStatisticsDTO queryScenicEvaluateStatistics(ScenicScreenQuery query) {
        return evaluateService.queryScenicEvaluateStatistics(queryToEvaluateQueryVO(query));
    }

    /**
     * 景区大数据——客流排行
     */
    public IPage<ScenicBaseVo> queryPassengerFlowTop5(ScenicScreenQuery query) {
        return ticketCheckService.queryPassengerFlowTop5(query);
    }

    /**
     * 景区大数据——评价排行
     */
    public IPage<BaseVO> queryEvaluateTop5(ScenicScreenQuery query) {
        return evaluateService.queryEvaluateTop5(queryToEvaluateQueryVO(query));
    }

    /**
     * 景区大数据——满意度排行
     */
    public IPage<ScenicBaseVo> querySatisfactionTop5(ScenicScreenQuery query) {
        IPage<EvaluateSatisfactionRankDTO> page = evaluateService.querySatisfactionTop5(queryToEvaluateQueryVO(query));
        return page.convert(item -> BeanMapper.copyBean(item, ScenicBaseVo.class));
    }

    /**
     * 景区大数据——热度趋势
     */
    public List<MonthPassengerFlowDto> queryHeatTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        ScenicTrendDto trendDto = new ScenicTrendDto();
        //设置当前时间、同比时间、环比时间
        evaluation(trendDto, query.getType().intValue());
        EvaluateQueryVO queryVO = queryToEvaluateQueryVO(query);
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
            String tbDate = dateToDateFormat(date, "year");
            tbNum = StringUtils.isNotBlank(tbMap.get(tbDate)) ? Integer.parseInt(tbMap.get(tbDate)) : 0;
            String hbDate = dateToDateFormat(date, "month");
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
        return resultList;
    }

    /**
     * 景区大数据——满意度趋势
     */
    public List<MonthPassengerFlowDto> querySatisfactionTrend(ScenicScreenQuery query) {
        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();
        ScenicTrendDto trendDto = new ScenicTrendDto();
        //设置当前时间、同比时间、环比时间
        evaluation(trendDto, query.getType().intValue());
        EvaluateQueryVO queryVO = queryToEvaluateQueryVO(query);
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
            String tbDate = dateToDateFormat(date, "year");
            tbNum = StringUtils.isNotBlank(tbMap.get(tbDate)) ? Integer.parseInt(tbMap.get(tbDate)) : 0;
            String hbDate = dateToDateFormat(date, "month");
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
        return resultList;
    }

    public List<BaseVO> queryScenicHotRank(ScenicScreenQuery query) {
        return evaluateService.queryScenicHotRank(queryToEvaluateQueryVO(query));
    }

    /**
     * 查询景区承载量统计
     * @return
     */
    @Transactional(readOnly = true)
    public Long queryScenicBearCapacity() {
        return baseMapper.queryScenicBearCapacity();
    }

    /**
     * 根据id查询名称
     * @return
     */
    @Transactional(readOnly = true)
    public String queryNameById(Long id) {
        if(null == id){
            return null;
        }
        return baseMapper.queryNameById(id);
    }

    /**
     * 查询当前时间检票数据.
     */
    private Map<String, Integer> queryCheckTrendByTime(ScenicScreenQuery query) {
        List<SaleTrendVO> tbSaleTrendVOS = ticketCheckService.queryCheckTrendByTime(query);
        return tbSaleTrendVOS.stream().collect(Collectors.toMap(SaleTrendVO::getTime, SaleTrendVO::getVisitQuantity));
    }

    /**
     * 查询热词数据
     */
    private Map<String, String> queryHeat(EvaluateQueryVO query) {
        List<BaseVO> curBaseVOS = evaluateService.queryHeatTrend(query);
        return curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
    }

    /**
     * 查询满意度数据
     */
    private Map<String, String> querySatisfaction(EvaluateQueryVO query) {
        List<BaseVO> curBaseVOS = evaluateService.querySatisfactionTrend(query);
        return curBaseVOS.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));
    }

    private void evaluation(ScenicTrendDto dto, Integer type) {
        List<String> abscissa = Lists.newArrayList();
        int monthNum = LocalDate.now().getMonthValue();
        int dayNum = LocalDate.now().getDayOfMonth();
        if (type == 2) {
            dto.setCurBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()));
            dto.setCurEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()));
            dto.setTbBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusYears(1));
            dto.setTbEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusYears(1));
            dto.setHbBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear()).minusMonths(1));
            dto.setHbEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()).minusMonths(1));
            for (int i = 1; i <= monthNum; i++) {
                String monthAndDay = (i < 10 ? "-0" : "-") + i + "-01";
                abscissa.add(LocalDate.now().getYear() + monthAndDay);
            }
            dto.setAbscissa(abscissa);
        } else if (type == 3) {
            dto.setCurBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()));
            dto.setCurEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()));
            dto.setTbBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1));
            dto.setTbEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()).minusMonths(1));
            dto.setHbBeginDate(LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth()).minusDays(1));
            dto.setHbEndDate(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth()).minusDays(1));
            for (int i = 1; i <= dayNum; i++) {
                String month = (monthNum < 10 ? "-0" : "-") + monthNum;
                String day = (i < 10 ? "-0" : "-") + i;
                abscissa.add(LocalDate.now().getYear() + month + day);
            }
            dto.setAbscissa(abscissa);
        }
    }

    /**
     * @Param: date日期、dateType类型、num减数、pattern格式
     * @return:
     */
    private String dateToDateFormat(String date, String dateType) {
        String dateStr = "";
        if (StringUtils.isNotBlank(date)) {
            switch (dateType) {
                case "day":
                    dateStr = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusDays(1), "yyyy-MM-dd");
                    break;
                case "month":
                    dateStr = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusMonths(1), "yyyy-MM-dd");
                    break;
                case "year":
                    dateStr = DateTimeUtil.localDateToString(DateTimeUtil.stringToLocalDate(date).minusYears(1), "yyyy-MM-dd");
                    break;
            }
        }
        return dateStr;
    }

    //ScenicScreenQuery转EvaluateQueryVO
    private EvaluateQueryVO queryToEvaluateQueryVO(ScenicScreenQuery query) {
        EvaluateQueryVO queryVO = new EvaluateQueryVO();
        queryVO.setBeginTime(query.getBeginTime());
        queryVO.setEndTime(query.getEndTime());
        queryVO.setPlaceId(isNull(query.getScenicId()) ? null : String.valueOf(query.getScenicId()));
        queryVO.setPageNo(query.getPageNo());
        queryVO.setPageSize(query.getPageSize());
        queryVO.setEvaluateType(query.getEvaluateType());
        queryVO.setDataType(query.getDataType());
        queryVO.setStatus(EntityConstants.ENABLED);
        queryVO.setEquipStatus(EntityConstants.ENABLED);
        return queryVO;
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
    private LambdaQueryWrapper<ScenicEntity> getCommonWrapper(String name) {
        return new LambdaQueryWrapper<ScenicEntity>()
                .like(StringUtils.isNotBlank(name), ScenicEntity::getName, name)
                .eq(ScenicEntity::getStatus, EntityConstants.ENABLED);
    }
}
