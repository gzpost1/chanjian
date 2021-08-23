package com.yjtech.wisdom.tourism.service.mock;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.DistrictBigDataConstants;
import com.yjtech.wisdom.tourism.common.constant.MockDataConstant;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.common.utils.RandomUtils;
import com.yjtech.wisdom.tourism.constant.DistrictExtensionConstant;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.DistrictMockDataDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.service.point.DistrictExtPt;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.districttour.DistrictMockRuleDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.districttour.OriginDistributedProvinceInsideDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.districttour.OriginDistributedProvinceOutsideDTO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import com.yjtech.wisdom.tourism.systemconfig.simulation.service.SimulationConfigService;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 游客结构-调用区县大数据-模拟数据
 *
 * @author renguangqian
 * @date 2021/8/11 19:24
 */
@Extension(bizId = ExtensionConstant.DISTRICT,
        useCase = DistrictExtensionConstant.DISTRICT,
        scenario = ExtensionConstant.SCENARIO_MOCK)
@Component
public class DistrictTourMockService implements SimulationFactory<DistrictMockRuleDTO>, DistrictExtPt, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PlatformService platformService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SimulationConfigService service;

    @Value("${tourist.configAreaCodeKey}")
    private String configAreaCodeKey;

    private static OriginDistributedProvinceOutsideDTO province;

    private static List<OriginDistributedProvinceInsideDTO> city;

    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    @Override
    public DataOverviewDto queryDataOverview(DataOverviewVo vo) {
        String beginDate = vo.getBeginDate();
        String endDate = vo.getEndDate();

        List<LocalDate> betweenDate = DateTimeUtil.getBetweenDate(DateTimeUtil.stringToLocalDate(beginDate), DateTimeUtil.stringToLocalDate(endDate));
        DistrictMockRuleDTO mockRule = getMockRule();

        // 游客总数
        long allTouristNum = 0L;
        // 省外游客
        long provinceOutsideTouristNum;
        // 省内游客
        long provinceInsideTouristNum = 0L;

        for (LocalDate localDate : betweenDate) {
            int dayOfMonth = localDate.getDayOfMonth();
            if (!ObjectUtils.isEmpty(mockRule)) {
                DistrictMockDataDto mockData = createMockData(dayOfMonth, mockRule);
                allTouristNum += mockData.getDayNumber();
            }
        }

        // 计算省内人数
        if (!ObjectUtils.isEmpty(province)) {
            String scale = province.getValue();
            provinceInsideTouristNum = DistrictTourMockService.multiply(scale, allTouristNum);
        }

        // 省外人数
        provinceOutsideTouristNum = allTouristNum - provinceInsideTouristNum;

        return DataOverviewDto.builder()
                .allTouristNum(allTouristNum)
                .provinceOutsideTouristNum(provinceOutsideTouristNum)
                .provinceInsideTouristNum(provinceInsideTouristNum)
                .build();
    }

    /**
     * 游客来源_分页查询
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<VisitorDto> queryPageVisitor(VisitorVo vo) {

        String endDate = vo.getEndDate();
        String beginDate = vo.getBeginDate();

        List<LocalDate> betweenDate = DateTimeUtil.getBetweenDate(DateTimeUtil.stringToLocalDate(beginDate), DateTimeUtil.stringToLocalDate(endDate));
        DistrictMockRuleDTO mockRule = getMockRule();

        // 游客总数
        long allTouristNum = 0L;

        List<VisitorDto> record = Lists.newArrayList();

        for (LocalDate localDate : betweenDate) {
            int dayOfMonth = localDate.getDayOfMonth();
            if (!ObjectUtils.isEmpty(mockRule)) {
                DistrictMockDataDto mockData = createMockData(dayOfMonth, mockRule);
                allTouristNum += mockData.getDayNumber();
            }
        }

        List<OriginDistributedProvinceOutsideDTO> provinceOutsideDistributed = mockRule.getProvinceOutsideDistributed();
        // 全国
        if (DistrictBigDataConstants.TOUR_SOURCE_COUNTRY.equals(vo.getType())) {
            for (OriginDistributedProvinceOutsideDTO provinceOutsideDto : provinceOutsideDistributed) {
                setList(vo, allTouristNum, record, provinceOutsideDto.getValue(), provinceOutsideDto.getName());
            }
        }
        // 全省
        else if(DistrictBigDataConstants.TOUR_SOURCE_PROVINCE.equals(vo.getType())) {
            for (OriginDistributedProvinceInsideDTO provinceInsideDto : city) {
                setList(vo, allTouristNum, record, provinceInsideDto.getValue(), provinceInsideDto.getName());
            }
        }

        // 排序
        Collections.sort(record);

        // 如果指定返回条数，则进行截取
        if (!ObjectUtils.isEmpty(vo.getLimit()) && vo.getLimit() < record.size()) {
            record = record.subList(0, vo.getLimit());
        }

        Page<VisitorDto> page = new Page<>();
        page.setSize(vo.getLimit());
        page.setRecords(record);
        page.setTotal(record.size());
        page.setCurrent(1L);
        page.setPages(1L);
        return page;
    }

    /**
     * 本年客流趋势
     *
     * @param vo
     * @return
     */
    @Override
    public List<MonthPassengerFlowDto> queryYearPassengerFlow(PassengerFlowVo vo) {
        String scale = "1";

        // 省外游客
        if (DistrictBigDataConstants.PROVINCE_OUTSIDE.equals(vo.getStatisticsType())) {
            scale = String.valueOf(DistrictBigDataConstants.ONE_HUNDRED - Double.parseDouble(province.getValue()));
        }
        // 省内游客
        else if (DistrictBigDataConstants.PROVINCE_INSIDI.equals(vo.getStatisticsType())) {
            scale = province.getValue();
        }

        // 获取当前月份
        int month = Integer.parseInt(DateTimeUtil.getCurrentMonthStr());
        DistrictMockRuleDTO mockRule = getMockRule();

        // 当月 全部客流
        int[] totalNumberMonth = new int[month + 1];

        List<MonthPassengerFlowDto> currentYearData = Lists.newArrayList();

        // 制造数据
        for (int i = 0; i < month + 1; i++) {
            if (!ObjectUtils.isEmpty(mockRule)) {
                DistrictMockDataDto mockData = createMockData(DateTimeUtil.getDayNumber(DateTimeUtil.getCurrentYearStr(), i + 1), mockRule);
                // 当前月
                if (month == i) {
                    mockData = createMockData(DateTimeUtil.getCurrentDay(), mockRule);
                }

                // 当前月的生成规则
                if (month == i) {
                    // 当月 全部客流
                    totalNumberMonth[i] = new BigDecimal(mockData.getMonthNumber()).multiply(new BigDecimal(scale)).intValue();
                }
                // 其他月 全部客流
                else {
                    totalNumberMonth[i] = new BigDecimal(mockData.getMonthNumber() * (DistrictBigDataConstants.ONE_HUNDRED + getRandom(null)))
                            .divide(new BigDecimal(DistrictBigDataConstants.ONE_HUNDRED), 0)
                            .intValue();
                }
            }
        }

        // 当前年份
        String currentYearStr = DateTimeUtil.getCurrentYearStr();
        // 去年年份
        String lastYearStr = DateTimeUtil.getLastYearStr();

        // 组装数据
        for (int i = 1; i < totalNumberMonth.length; i++) {
            String monthStr = "-" + i;
            // 月份小于10 拼 0
            if (i < 10) {
                monthStr = "-0" + i;
            }

            String tb = "-";
            String hb = "-";
            int total = totalNumberMonth[i];
            if (!DistrictBigDataConstants.ZERO.equals(total)) {
                // 环比
                hb = MathUtil.calPercent(new BigDecimal(total - totalNumberMonth[i - 1]), new BigDecimal(total), 2).toString();
            }

            currentYearData.add(MonthPassengerFlowDto.builder()
                    .date(currentYearStr + monthStr)
                    .tbDate(lastYearStr + monthStr)
                    .number(total)
                    .tbNumber(0)
                    .tbScale(tb)
                    .hbScale(hb)
                    .build());
        }

        return currentYearData;
    }

    /**
     * 本月客流趋势
     *
     * @param vo
     * @return
     */
    @Override
    public List<MonthPassengerFlowDto> queryMonthPassengerFlow(MonthPassengerFlowVo vo) {
        String scale = "1";

        // 省内游客
        if (DistrictBigDataConstants.PROVINCE_INSIDI.equals(vo.getStatisticsType())) {
            scale = province.getValue();
        }
        // 省外游客
        else if (DistrictBigDataConstants.PROVINCE_OUTSIDE.equals(vo.getStatisticsType())) {
            scale = String.valueOf(DistrictBigDataConstants.ONE_HUNDRED - Double.parseDouble(province.getValue()));
        }

        // 当前日期号数
        int currentDayNumber = DateTimeUtil.getCurrentDayNumber();
        DistrictMockRuleDTO mockRule = getMockRule();

        // 今年本月至今 全部客流
        int[] totalNumberDay = new int[currentDayNumber + 1];

        // 当前年月
        String currentYearAndMonth = DateTimeUtil.getCurrentYearAndMonth();
        // 去年同月
        String lastYearAndMonthStr = DateTimeUtil.getLastYearAndMonthStr();
        // 月累计客流量
        int totalMonth = 0;

        // 结果容器
        List<MonthPassengerFlowDto> result = Lists.newArrayList();

        // 制造数据
        for (int i = currentDayNumber; i >= 0; i--) {
            if (!ObjectUtils.isEmpty(mockRule)) {
                DistrictMockDataDto mockData = createMockData(i, mockRule);
                // 当日
                if (i == currentDayNumber) {
                    // 月总客流量
                    totalMonth = mockData.getMonthNumber();
                    // 当日 全部客流
                    totalNumberDay[i] = new BigDecimal(mockData.getDayNumber()).multiply(new BigDecimal(scale)).intValue();
                }
                // 其他日 全部客流
                else {
                    totalNumberDay[i] = new BigDecimal(totalMonth - mockData.getDayNumber())
                            .divide(new BigDecimal(currentDayNumber - 1), 0)
                            .multiply(new BigDecimal(DistrictBigDataConstants.ONE_HUNDRED + getRandom(null)))
                            .divide(new BigDecimal(DistrictBigDataConstants.ONE_HUNDRED), 0)
                            .intValue();
                }
            }
        }

        // 组装数据
        for (int i = 1; i < totalNumberDay.length; i++) {
            String tb = "-";
            String hb = "-";

            String dayStr = "-" + i;
            if (i < 10) {
                dayStr = "-0" + i;
            }

            int currentNumber = totalNumberDay[i];

            if (!DistrictBigDataConstants.ZERO.equals(currentNumber)) {
                // 环比
                hb = MathUtil.calPercent(new BigDecimal(currentNumber - totalNumberDay[i - 1]), new BigDecimal(currentNumber), 2).toString();
            }

            result.add(MonthPassengerFlowDto.builder()
                    .number(currentNumber)
                    .date(currentYearAndMonth + dayStr)
                    .tbNumber(0)
                    .tbDate(lastYearAndMonthStr + dayStr)
                    .hbScale(hb)
                    .tbScale(tb)
                    .build()
            );
        }

        return result;
    }


    /**
     * 乘法计算
     *
     * @param scale
     * @param total
     * @return
     */
    private static long multiply(String scale, long total) {
        String realScale = DistrictTourMockService.getRealScaleDecimal(scale);
        return new BigDecimal(total).multiply(new BigDecimal(realScale)).longValue();
    }

    /**
     * 除法-除以100
     *
     * @param scale
     * @return
     */
    private static String getRealScaleDecimal(String scale) {
        return new BigDecimal(scale).divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 根据模拟规则  创建模拟数据
     *
     * @param currentDay
     * @param districtMockRuleDto
     * @return
     */
    private DistrictMockDataDto createMockData(int currentDay, DistrictMockRuleDTO districtMockRuleDto) {

        // 日游客量数据
        int dayNumber = getRandom(districtMockRuleDto) + districtMockRuleDto.getDayPassengerFlowValue();

        // 月累计客流 = 日客流 * 当前日期号数 + 固定值
        int monthNumber = dayNumber * currentDay + districtMockRuleDto.getMonthPassengerFlowValue();

        return DistrictMockDataDto.builder().dayNumber(dayNumber).monthNumber(monthNumber).build();

    }

    /**
     * 获取随机数 - 先查缓存再返回
     *
     * @return
     */
    private int getRandom(DistrictMockRuleDTO districtMockRuleDto) {
        String currentDate = DateTimeUtil.getCurrentDate();
        String key = DistrictBigDataConstants.MOCK_TOUR_SIGN + currentDate;
        // 查询当日缓存随机数是否存在
        Object randomObj = redisTemplate.opsForValue().get(key);
        if (ObjectUtils.isEmpty(randomObj)) {
            int randomNumber = getRandomNumber(districtMockRuleDto);
            redisTemplate.opsForValue().set(key, randomNumber, 1, TimeUnit.DAYS);
            randomObj = randomNumber;
        }
        return Integer.parseInt(randomObj.toString());
    }

    /**
     * 刷新随机数
     */
    public void refreshRandom() {
        DistrictMockRuleDTO mockRule = getMockRule();
        String currentDate = DateTimeUtil.getCurrentDate();
        int randomNumber = getRandomNumber(mockRule);
        String key = DistrictBigDataConstants.MOCK_TOUR_SIGN + currentDate;
        redisTemplate.opsForValue().set(key, randomNumber, 1, TimeUnit.DAYS);
    }

    /**
     * 获取随机数 -- 具体生成
     *
     * @param districtMockRuleDto
     * @return
     */
    private int getRandomNumber(DistrictMockRuleDTO districtMockRuleDto) {
        int randomStart = -20;
        int randomEnd = 20;
        if (!ObjectUtils.isEmpty(districtMockRuleDto)) {
            randomStart = districtMockRuleDto.getRandomStart();
            randomEnd = districtMockRuleDto.getRandomEnd();
        }
        return RandomUtils.getRandomNumber(randomStart, randomEnd);
    }

    /**
     * 根据模拟规则  创建模拟数据
     *
     * @return
     */
    public DistrictMockRuleDTO getMockRule() {
        SimulationQueryDto simulationQueryDto = new SimulationQueryDto();
        simulationQueryDto.setDomainId(MockDataConstant.DISTRICT_TOUR_MOCK_DOMAIN_ID);
        String configValue = String.valueOf(service.queryForDetail(simulationQueryDto));
        if (StringUtils.isEmpty(configValue) || DecisionSupportConstants.NULL.equals(configValue)) {
            return null;
        }
        return JSONObject.parseObject(configValue, DistrictMockRuleDTO.class);
    }

    /**
     * 游客来源-设置返回数据
     *
     * @param vo
     * @param allTouristNum
     * @param record
     * @param scale
     * @param areaName
     */
    private void setList(VisitorVo vo, long allTouristNum, List<VisitorDto> record, String scale, String areaName) {
        // 存在配置比例 则进行计算
        if (!ObjectUtils.isEmpty(scale)) {
            String realScaleDecimal = DistrictTourMockService.getRealScaleDecimal(scale);
            Long provinceNumber = DistrictTourMockService.multiply(scale, allTouristNum);
            record.add(VisitorDto.builder()
                    .startDate(vo.getBeginDate())
                    .endDate(vo.getEndDate())
                    .name(areaName)
                    .percent(Float.parseFloat(realScaleDecimal))
                    .number(provinceNumber.intValue())
                    .build()
            );
        }
    }

    /**
     * 容器初始化完毕后执行初始化数据
     *
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        DistrictMockRuleDTO mockRule = getMockRule();
        if (ObjectUtils.isEmpty(mockRule)) {
            return;
        }
        city = mockRule.getProvinceInsideDistributed();
        List<OriginDistributedProvinceOutsideDTO> provinceOutsideDistributed = mockRule.getProvinceOutsideDistributed();
        String provinceName = com.yjtech.wisdom.tourism.common.utils.StringUtils.substringBefore(platformService.getPlatform().getAreaName(), "/");
        for (OriginDistributedProvinceOutsideDTO provinceOutsideDto : provinceOutsideDistributed) {
            if (provinceName.equals(provinceOutsideDto.getName())) {
                province = provinceOutsideDto;
            }
        }
    }

    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, DistrictMockRuleDTO.class);
    }

    @Override
    public String toJSONBytes(DistrictMockRuleDTO obj) {
        return JSONObject.toJSONString(obj);
    }

    @Override
    public void generateMockRedisData(DistrictMockRuleDTO obj) {
        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.TOURIST), obj);
    }

}
