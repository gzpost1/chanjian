package com.yjtech.wisdom.tourism.common.utils;

import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.*;
import com.yjtech.wisdom.tourism.common.enums.AnalysisDateTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 构建趋势信息
 *
 * @Date 2021/5/11 16:09
 * @Author horadirm
 */
@Slf4j
public class AnalysisUtils {


    /**
     * 获得趋势
     *
     * @param query
     * @param analysisInfo
     * @param func
     * @param <T>
     * @return
     */
    public static <T extends BaseSaleTrendVO> List<BaseValueVO> MultipleBuildAnalysis(TimeBaseQuery query,
                                                                                          List<T> analysisInfo,
                                                                                          SFunction<T, ? extends Integer>... func) {
        AssertUtil.isFalse(Objects.isNull(query.getBeginTime()) || Objects.isNull(query.getEndTime()), "起始时间或结束时间不能为空");
        AnalysisDateTypeEnum analysisDateTypeEnum = AnalysisDateTypeEnum.getItemByValue(query.getType());

        //初始化时间趋势
        String javaDateFormat = analysisDateTypeEnum.getJavaDateFormat();
        List<LocalDateTime> markList = getRangeToList(query.getBeginTime(), query.getEndTime(),javaDateFormat, analysisDateTypeEnum.getChronoUnit());


        ArrayList<BaseValueVO> baseValueVOS = Lists.newArrayList();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(javaDateFormat);
        for (SFunction<T, ? extends Integer> fun : func) {

            Map<String, Integer> timeMap = Optional.ofNullable(analysisInfo).orElse(Lists.newArrayList())
                    .stream().collect(Collectors.toMap(item -> item.getTime(), item -> fun.apply(item)));
            //值
            List<Integer> value = Lists.newArrayList();
            for (LocalDateTime yearMark : markList) {
                Integer quantity = timeMap.get(yearMark.format(dateTimeFormatter));
                if (Objects.nonNull(quantity)) {
                    value.add(quantity);
                } else {
                    value.add(0);
                }
            }
            baseValueVOS.add(BaseValueVO.builder().name(ColumnUtil.getName(fun)).value(value).build());
        }

        ArrayList<Integer> coordinate = Lists.newArrayList();
        DateTimeFormatter coorDateTimeFormatter = DateTimeFormatter.ofPattern(analysisDateTypeEnum.getCoorJavaDateFormat());
        for (LocalDateTime yearMark : markList) {
            coordinate.add(Integer.parseInt(yearMark.format(coorDateTimeFormatter)));
        }
        baseValueVOS.add(BaseValueVO.builder().name("coordinate").value(coordinate).build());
        return baseValueVOS;
    }



    public static List<LocalDateTime> getRangeToList(LocalDateTime beginTime, LocalDateTime endTime,String javaDateFormat, ChronoUnit chronoUnit) {
        /**
         *  结束时间 + 1 避免边界问题
         *  如
         *   "beginTime": "2020-10-25 23:00:00",
         *   "endTime": "2020-12-23 21:00:00",
         *   "type": 3
         */
        List<LocalDateTime> timeList = new ArrayList<>();
        while (endTime.isAfter(beginTime)) {
            timeList.add(beginTime);
            beginTime = beginTime.plus(1, chronoUnit);
        }

        /**
         *  避免边界问题
         *  如
         *   "beginTime": "2020-10-25 23:00:00",
         *   "endTime": "2020-12-23 21:00:00",
         *   "type": 3
         */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(javaDateFormat);
        if(beginTime.format(dateTimeFormatter).equals(endTime.format(dateTimeFormatter))){
            timeList.add(endTime);
        }
        return timeList;
    }
}
