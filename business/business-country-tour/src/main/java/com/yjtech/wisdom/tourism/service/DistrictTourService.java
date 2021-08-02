package com.yjtech.wisdom.tourism.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.DistrictBigDataConstants;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.dto.YearPassengerFlowDto;
import com.yjtech.wisdom.tourism.integration.service.DistrictBigDataService;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;
import com.yjtech.wisdom.tourism.vo.YearPassengerFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 游客结构-调用区县大数据
 *
 * @author renguangqian
 * @date 2021/7/22 14:55
 */
@Service
@Slf4j
public class DistrictTourService {

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
    public DataOverviewDto queryDataOverview (DataOverviewVo vo) {

        // 10.到访全部游客
        vo.setStatisticsType("10");
        long allTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(districtBigDataService.requestDistrict(DistrictPathEnum.DATA_OVERVIEW.getPath(), vo, "到访全部游客"), "data")));

        // 11.到访省内游客
        vo.setStatisticsType("11");
        long provinceInsideTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(districtBigDataService.requestDistrict(DistrictPathEnum.DATA_OVERVIEW.getPath(), vo, "到访省内游客"), "data")));

        // 12.到访省外游客
        vo.setStatisticsType("12");
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
    public IPage<VisitorDto> queryPageVisitor(VisitorVo vo) {
        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        vo.setAdcode(areaCode);
        long pageNo = vo.getPageNo();
        long pageSize = vo.getPageSize();
        Long limit = pageNo * pageSize;
        if (limit < LIMIT_VALUE) {
            limit = LIMIT_VALUE;
        }
        vo.setLimit(limit.intValue());
        String result = districtBigDataService.requestDistrict(DistrictPathEnum.TOURISTS_SOURCE.getPath(), vo, "省级游客来源");
        List<VisitorDto> visitorDtoList = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), VisitorDto.class);
        return page(pageNo, pageSize, visitorDtoList);
    }

    /**
     * 本年客流趋势
     *
     * @return
     */
    public List<YearPassengerFlowDto> queryYearPassengerFlow () {
        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        YearPassengerFlowVo vo = YearPassengerFlowVo.builder()
                .adcode(areaCode)
                .statisticsType(DistrictBigDataConstants.YEAR_PASSENGER_FLOW_VISIT_IN)
                .build();
        List<YearPassengerFlowDto> result = JSONObject.parseArray(
                String.valueOf(
                        JsonUtils.getValueByKey(
                                districtBigDataService.requestDistrict(DistrictPathEnum.YEAR_PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.YEAR_PASSENGER_FLOW.getDesc()),
                                "data"
                        )
                ),
                YearPassengerFlowDto.class);

        // 获取当前年份
        String currentYearStr = DateTimeUtil.getCurrentYearStr();

        // 是否获取数据标识 用于截取本年1月及以后的数据
        boolean isStop = true;

        List<YearPassengerFlowDto> resultList = Lists.newArrayList();

        for (int i = 0; i < result.size(); i++) {
            // 判断是本年一月的数据
            YearPassengerFlowDto yearPassengerFlowDto = result.get(i);
            if (currentYearStr.equals(yearPassengerFlowDto.getCurYear())
                    && "01".equals(yearPassengerFlowDto.getCurDate().substring(yearPassengerFlowDto.getCurDate().length() - 2))) {
                isStop = false;
            }
            else if (isStop) {
                continue;
            }

            // todo 计算比例相关逻辑
            // 当月人数
            Integer curNumber = yearPassengerFlowDto.getCurNumber();
            //去年当月人数
            Integer tbNumber = yearPassengerFlowDto.getTbNumber();
            // 上月人数
            Integer hbNumber = yearPassengerFlowDto.getHbNumber();

            if (0 == curNumber) {
                yearPassengerFlowDto.setTbScale("-");
                yearPassengerFlowDto.setHbScale("-");
            }
            else {
               // 今年和去年的比例-同比
                String tbScale = MathUtil.calPercent(new BigDecimal(curNumber - tbNumber), new BigDecimal(curNumber), 2).toString();

                // 当月和上月的比列-环比
                String hbScale = MathUtil.calPercent(new BigDecimal(curNumber - hbNumber), new BigDecimal(curNumber), 2).toString();

                yearPassengerFlowDto.setTbScale(tbScale);
                yearPassengerFlowDto.setHbScale(hbScale);
            }

            resultList.add(yearPassengerFlowDto);
        }

        return resultList;
    }

    /**
     * 本月客流趋势
     *
     * @return
     */
    public List<MonthPassengerFlowDto> queryMonthPassengerFlow () {
        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);
        MonthPassengerFlowVo vo = new MonthPassengerFlowVo();
        vo.setAdcode(areaCode);
        vo.setBeginDate(DateTimeUtil.getCurrentLastMonthLastDayStr());
        vo.setEndDate(DateTimeUtil.getCurrentDate());
        vo.setStatisticsType(DistrictBigDataConstants.MONTH_PASSENGER_FLOW_VISIT_IN);

        String result = districtBigDataService.requestDistrict(DistrictPathEnum.MONTH_PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.MONTH_PASSENGER_FLOW.getDesc());
        // 当月数据
        List<MonthPassengerFlowDto> currentMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), MonthPassengerFlowDto.class);

        // 修改上月日期
        vo.setBeginDate(DateTimeUtil.getLastYearDate(vo.getBeginDate()));
        vo.setEndDate(DateTimeUtil.getLastYearDate(vo.getEndDate()));

        // 上月数据
        String lastYearResult = districtBigDataService.requestDistrict(DistrictPathEnum.MONTH_PASSENGER_FLOW.getPath(), vo, DistrictPathEnum.MONTH_PASSENGER_FLOW.getDesc());
        List<MonthPassengerFlowDto> lastMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(lastYearResult, "data")), MonthPassengerFlowDto.class);

        // 结果数据
        List<MonthPassengerFlowDto> resultList = Lists.newArrayList();

        // 当前月份
        String currentMonthStr = DateTimeUtil.getCurrentMonthStr();

        // 是否获取数据标识 用于截取当前月1号之后的数据
        boolean isStop = true;

        for (int i = 0; i < currentMonth.size(); i++) {
            String date = currentMonth.get(i).getDate();

            // 判断是否是 1 日
            if (currentMonthStr.equals(date.substring(5, 7)) && "01".equals(date.substring(date.length() - 2))) {
                isStop = false;
            }else if (isStop) {
                continue;
            }

            // todo 计算比例相关逻辑
            Integer currentNumber = currentMonth.get(i).getNumber();
            // 如果当天数据为0， 则直接赋值为“-”
            if (0 == currentNumber) {
                currentMonth.get(i).setTbScale("-");
                currentMonth.get(i).setHbScale("-");
            }
            else {
             Integer lastNumber = lastMonth.get(i).getNumber();

            // 前一天的数据
            Integer beforeNumber = currentMonth.get(i - 1).getNumber();
            String beforeDay = MathUtil.calPercent(new BigDecimal(currentNumber - beforeNumber), new BigDecimal(currentNumber), 2).toString();
            currentMonth.get(i).setHbScale(beforeDay);

            String LastYearDay = MathUtil.calPercent(new BigDecimal(currentNumber - lastNumber), new BigDecimal(currentNumber), 2).toString();
            currentMonth.get(i).setTbScale(LastYearDay);
            }
            // 设置去年 数量、日期
            currentMonth.get(i).setTbDate(lastMonth.get(i).getDate());
            currentMonth.get(i).setTbNumber(lastMonth.get(i).getNumber());

            resultList.add(currentMonth.get(i));
        }
        return resultList;
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

        if (end > list.size()) {
            end = list.size();
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
        page.setTotal(list.size());
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
