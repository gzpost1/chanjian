package com.yjtech.wisdom.tourism.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.DistrictBigDataConstants;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.constant.DistrictExtensionConstant;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.dto.VisitorTempDto;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.integration.service.DistrictBigDataService;
import com.yjtech.wisdom.tourism.service.point.DistrictExtPt;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 游客结构-调用区县大数据
 *
 * @author renguangqian
 * @date 2021/7/22 14:55
 */
@Extension(bizId = ExtensionConstant.DISTRICT,
        useCase = DistrictExtensionConstant.DISTRICT,
        scenario = ExtensionConstant.SCENARIO_IMPL)
@Slf4j
public class DistrictTourImplService implements DistrictExtPt {

    private static final long LIMIT_VALUE = 5L;

    @Autowired
    private DistrictBigDataService districtBigDataService;

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${tourist.configAreaCodeKey}")
    private String configAreaCodeKey;

    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    @Override
    public DataOverviewDto queryDataOverview (DataOverviewVo vo) {

        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        vo.setAdcode(areaCode);

        if (StringUtils.isEmpty(vo.getBeginDate())) {
            vo.setBeginDate(DateTimeUtil.localDateTimeToString(vo.getBeginTime(), "yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(vo.getEndDate())) {
            vo.setEndDate(DateTimeUtil.localDateTimeToString(vo.getEndTime(), "yyyy-MM-dd"));
        }
        // 10.到访全部游客
        vo.setStatisticsType(DecisionSupportConstants.PROVINCE_ALL_TYPE);
        long allTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(districtBigDataService.requestDistrict(DistrictPathEnum.DATA_OVERVIEW.getPath(), vo, "到访全部游客"), "data")));

        // 11.到访省内游客
        vo.setStatisticsType(DecisionSupportConstants.PROVINCE_INSIDE_TYPE);
        long provinceInsideTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(districtBigDataService.requestDistrict(DistrictPathEnum.DATA_OVERVIEW.getPath(), vo, "到访省内游客"), "data")));

        // 12.到访省外游客
        vo.setStatisticsType(DecisionSupportConstants.PROVINCE_OUTSIDE_TYPE);
        long provinceOutsideTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(districtBigDataService.requestDistrict(DistrictPathEnum.DATA_OVERVIEW.getPath(), vo, "到访省外游客"), "data")));

        return DataOverviewDto.builder()
                .allTouristNum(allTouristNum)
                .provinceOutsideTouristNum(provinceOutsideTouristNum)
                .provinceInsideTouristNum(provinceInsideTouristNum)
                .build();
    }

    /**
     * 游客来源_分页查询
     *
     * 游客来源省份TOP5 31
     * 游客省内来源地市TOP5 32
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<VisitorDto> queryPageVisitor(VisitorVo vo) {
        if (StringUtils.isEmpty(vo.getBeginDate())) {
            vo.setBeginDate(DateTimeUtil.localDateTimeToString(vo.getBeginTime(), "yyyy-MM-dd"));
        }
        if (StringUtils.isEmpty(vo.getEndDate())) {
            vo.setEndDate(DateTimeUtil.localDateTimeToString(vo.getEndTime(), "yyyy-MM-dd"));
        }

        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        vo.setAdcode(areaCode);
        long pageNo = vo.getPageNo();
        long pageSize = vo.getPageSize();
        Long limit = pageNo * pageSize;
        if (limit < LIMIT_VALUE) {
            limit = LIMIT_VALUE;
        }
        vo.setLimit(limit.intValue());
        VisitorTempDto visitorTempDto = JSONObject.parseObject(JSONObject.toJSONString(vo), VisitorTempDto.class);
        visitorTempDto.setType(vo.getStatisticsType());
        String result = districtBigDataService.requestDistrict(DistrictPathEnum.TOURISTS_SOURCE.getPath(), visitorTempDto, DistrictBigDataConstants.TOUR_SOURCE);
        List<VisitorDto> visitorDtoList = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, DistrictBigDataConstants.DATA)), VisitorDto.class);
        return page(pageNo, pageSize, visitorDtoList);
    }

    /**
     * 本年客流趋势
     *
     * @return
     */
    @Override
    public List<MonthPassengerFlowDto> queryYearPassengerFlow (PassengerFlowVo vo) {

        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        vo.setAdcode(areaCode);
        // 今年数据
        vo.setBeginDate(DateTimeUtil.transformDateTime(vo.getBeginTime().plusMonths(-1), DistrictBigDataConstants.YYYY_MM_DD));
        vo.setEndDate(DateTimeUtil.transformDateTime(vo.getEndTime(), DistrictBigDataConstants.YYYY_MM_DD));

        String result = districtBigDataService.requestDistrict(DistrictPathEnum.PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.PASSENGER_FLOW.getDesc());

        // 今年数据
        List<MonthPassengerFlowDto> currentYear = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, DistrictBigDataConstants.DATA)), MonthPassengerFlowDto.class);


        // 修改去年
        vo.setBeginDate(DateTimeUtil.transformDateTime(vo.getBeginTime().plusMonths(-13), DistrictBigDataConstants.YYYY_MM_DD));
        vo.setEndDate(DateTimeUtil.transformDateTime(vo.getEndTime().plusMonths(-12), DistrictBigDataConstants.YYYY_MM_DD));

        // 去年数据
        String lastYearResult = districtBigDataService.requestDistrict(DistrictPathEnum.PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.PASSENGER_FLOW.getDesc());
        List<MonthPassengerFlowDto> lastYear = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(lastYearResult, DistrictBigDataConstants.DATA)), MonthPassengerFlowDto.class);

        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();

        // 计算今年的个月总数
        HashMap<String, Integer> currentYearMap = Maps.newLinkedHashMap();
        // 计算今年的个月总数
        HashMap<String, Integer> lastYearMap = Maps.newLinkedHashMap();
        for (MonthPassengerFlowDto v : currentYear) {
            countYearNumber(v, currentYearMap);
        }
        for (MonthPassengerFlowDto v : lastYear) {
            countYearNumber(v, lastYearMap);
        }
        currentYear.clear();
        lastYear.clear();
        // 设置回list
        currentYearMap.forEach((k, v) -> currentYear.add(MonthPassengerFlowDto.builder().number(v).date(k).build()));
        lastYearMap.forEach((k, v) -> lastYear.add(MonthPassengerFlowDto.builder().number(v).date(k).build()));

        // 是否获取数据标识 用于截取本年1月及以后的数据
        boolean isStop = true;
        // 获取当前年份
        String currentYearStr = DateTimeUtil.getCurrentYearStr() + DistrictBigDataConstants.FIRST_DAY_STR;

        // 当前年月
        String currentYearAndMonth = DateTimeUtil.getCurrentYearStr() + DistrictBigDataConstants.DEFAULT_STR + DateTimeUtil.getCurrentMonthStr();

        for (int i = 0; i < currentYear.size(); i++) {
            // 判断是本年一月的数据
            MonthPassengerFlowDto yearPassengerFlowDto = currentYear.get(i);
            String date = yearPassengerFlowDto.getDate();
            if (currentYearStr.equals(date)) {
                isStop = false;
            }
            else if (isStop) {
                continue;
            }

            Integer currentNumber = currentYear.get(i).getNumber();
            // 如果当天数据为0， 则直接赋值为“-”
            if (0 == currentNumber || currentYearAndMonth.equals(date)) {
                currentYear.get(i).setTbScale(DistrictBigDataConstants.DEFAULT_STR);
                currentYear.get(i).setHbScale(DistrictBigDataConstants.DEFAULT_STR);
            }
            else {
                Integer lastNumber = lastYear.get(i).getNumber();

                // 前一天的数据
                Integer beforeNumber = currentYear.get(i - 1).getNumber();
                String beforeDay = MathUtil.calPercent(new BigDecimal(currentNumber - beforeNumber), new BigDecimal(currentNumber), 1).toString();
                currentYear.get(i).setHbScale(beforeDay);

                String LastYearDay = MathUtil.calPercent(new BigDecimal(currentNumber - lastNumber), new BigDecimal(currentNumber), 1).toString();
                currentYear.get(i).setTbScale(LastYearDay);

                // 设置去年 数量
                currentYear.get(i).setTbNumber(lastYear.get(i).getNumber());
            }
            // 设置去年 日期
            currentYear.get(i).setTbDate(lastYear.get(i).getDate());

            resultList.add(currentYear.get(i));
        }

        return resultList;
    }

    /**
     * 本月客流趋势
     *
     * @return
     */
    @Override
    public List<MonthPassengerFlowDto> queryMonthPassengerFlow (MonthPassengerFlowVo vo) {
        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        vo.setAdcode(areaCode);
        vo.setBeginDate(DateTimeUtil.transformDateTime(vo.getBeginTime().plusDays(-1), DistrictBigDataConstants.YYYY_MM_DD));
        vo.setEndDate(DateTimeUtil.transformDateTime(vo.getEndTime(), DistrictBigDataConstants.YYYY_MM_DD));

        String result = districtBigDataService.requestDistrict(DistrictPathEnum.PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.PASSENGER_FLOW.getDesc());

        // 当月数据
        List<MonthPassengerFlowDto> currentMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, DistrictBigDataConstants.DATA)), MonthPassengerFlowDto.class);


        // 修改上月日期
        vo.setBeginDate(DateTimeUtil.transformDateTime(vo.getBeginTime().plusMonths(-1).plusDays(-1), DistrictBigDataConstants.YYYY_MM_DD));
        vo.setEndDate(DateTimeUtil.transformDateTime(vo.getEndTime().plusMonths(-1), DistrictBigDataConstants.YYYY_MM_DD));

        // 上月数据
        String lastYearResult = districtBigDataService.requestDistrict(DistrictPathEnum.PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.PASSENGER_FLOW.getDesc());
        List<MonthPassengerFlowDto> lastMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(lastYearResult, DistrictBigDataConstants.DATA)), MonthPassengerFlowDto.class);

        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();

        // 当前月份
        String currentMonthStr = DateTimeUtil.getCurrentMonthStr();

        // 是否获取数据标识 用于截取当前月1号之后的数据
        boolean isStop = true;

        for (int i = 0; i < currentMonth.size(); i++) {
            String date = currentMonth.get(i).getDate();

            // 判断是否是 1 日
            if (currentMonthStr.equals(date.substring(5, 7)) && DistrictBigDataConstants.FIRST_DAY.equals(date.substring(date.length() - 2))) {
                isStop = false;
            }else if (isStop) {
                continue;
            }

            Integer currentNumber = currentMonth.get(i).getNumber();
            // 如果当天数据为0， 则直接赋值为“-”
            if (0 == currentNumber) {
                currentMonth.get(i).setTbScale(DistrictBigDataConstants.DEFAULT_STR);
                currentMonth.get(i).setHbScale(DistrictBigDataConstants.DEFAULT_STR);
            }
            else {
             Integer lastNumber = lastMonth.get(i).getNumber();

            // 前一天的数据
            Integer beforeNumber = currentMonth.get(i - 1).getNumber();
            String beforeDay = MathUtil.calPercent(new BigDecimal(currentNumber - beforeNumber), new BigDecimal(currentNumber), 1).toString();
            currentMonth.get(i).setHbScale(beforeDay);

            String LastYearDay = MathUtil.calPercent(new BigDecimal(currentNumber - lastNumber), new BigDecimal(currentNumber), 1).toString();
            currentMonth.get(i).setTbScale(LastYearDay);

            // 设置去年 数量
            currentMonth.get(i).setTbNumber(lastMonth.get(i).getNumber());
            }
            // 设置去年 日期
            currentMonth.get(i).setTbDate(lastMonth.get(i).getDate());

            resultList.add(currentMonth.get(i));
        }
        return resultList;
    }


    /**
     * 计算各月总数
     *
     * @param currentYear
     * @param currentYearMap
     */
    private void countYearNumber(MonthPassengerFlowDto currentYear, Map <String, Integer> currentYearMap) {
        String dateStr = currentYear.getDate().substring(0, 7);
        Integer number = currentYear.getNumber();

        if (currentYearMap.containsKey(dateStr)) {
            int old = Integer.parseInt(currentYearMap.get(dateStr).toString());
            currentYearMap.put(dateStr, old + number);
        }else {
            currentYearMap.put(dateStr, number);
        }

    }

    /**
     * 集合进行自主分页
     *
     * @param pageNo
     * @param pageSize
     * @param list
     * @param <E>
     * @return
     */
    public static <E> IPage<E> page (int pageNo, int pageSize, List<E> list) {
        List<E> newList = Lists.newArrayList();

        int begin = pageNo * pageSize - pageSize;
        int end = pageNo * pageSize;
        int pages = 0;
        int size = 0;

        if (CollectionUtils.isEmpty(list)) {
            end = 0;
        }
        else {
            size = list.size();
        }
        if (end > size) {
            end = size;
        }
        for (int i = begin; i < end; i++) {
            newList.add(list.get(i));
        }

        // 计算页面大小
        if (!CollectionUtils.isEmpty(list)) {
            BigDecimal bcs = new BigDecimal(list.size());
            BigDecimal cs = new BigDecimal(pageSize);
            BigDecimal divideResult = cs.divide(bcs, 0, BigDecimal.ROUND_UP);
            pages = divideResult.intValue();
        }

        // 设置page值
        Page<E> page  = new Page<>();
        page.setPages(pages);
        page.setCurrent(pageNo);
        page.setTotal(size);
        page.setSize(pageSize);
        page.setRecords(newList);
        return page;
    }


    /**
     * 集合进行自主分页
     *
     * @param pageNo
     * @param pageSize
     * @param list
     * @param <E>
     * @return
     */
    public static <E> IPage<E> page (Long pageNo, Long pageSize, List<E> list) {
        return page(pageNo.intValue(), pageSize.intValue(), list);
    }

}
