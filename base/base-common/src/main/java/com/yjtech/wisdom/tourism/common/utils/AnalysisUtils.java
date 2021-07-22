package com.yjtech.wisdom.tourism.common.utils;

import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.*;
import com.yjtech.wisdom.tourism.common.enums.AnalysisDateTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 构建趋势信息
 * 折线图及柱状图工具类
 *
 * @Date 2021/5/11 16:09
 * @Author horadirm
 */
@Slf4j
public class AnalysisUtils {


    /**
     *
     *   时间补齐 数据补齐
     *
     * @param query  参数
     * @param analysisInfo 基本数据
     * @param future 是否补齐未来数据
     * @param func 函数 获得数据的函数
     * @param <T>
     * @return
     */
    public static <T extends BaseSaleTrendVO> List<BaseValueVO> MultipleBuildAnalysis(TimeBaseQuery query,
                                                                                      List<T> analysisInfo,
                                                                                      Boolean future,
                                                                                      SFunction<T, ? extends Integer>... func) {
        AssertUtil.isFalse(Objects.isNull(query.getBeginTime()) || Objects.isNull(query.getEndTime()), "起始时间或结束时间不能为空");
        AnalysisDateTypeEnum analysisDateTypeEnum = AnalysisDateTypeEnum.getItemByValue(query.getType());

        //初始化时间趋势
        String javaDateFormat = analysisDateTypeEnum.getJavaDateFormat();
        List<LocalDateTime> markList = getRangeToList(query.getBeginTime(), query.getEndTime(),javaDateFormat, analysisDateTypeEnum.getChronoUnit(),future);


        ArrayList<BaseValueVO> baseValueVOS = Lists.newArrayList();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(javaDateFormat);
        for (SFunction<T, ? extends Integer> fun : func) {

            Map<String, Integer> timeMap = Optional.ofNullable(analysisInfo).orElse(Lists.newArrayList())
                    .stream().collect(Collectors.toMap(item -> item.getTime(), item -> fun.apply(item)));
            //值
            List<Integer> value = Lists.newArrayList();
            for (LocalDateTime yearMark : markList) {
                //前端要求 时间范围内没有补齐0  时间范围外不补任何数
                if(query.getEndTime().isBefore(yearMark)){
                    break;
                }
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


    /**
     * 补齐过去时间  补齐未来时间
     * @param beginTime
     * @param endTime
     * @param javaDateFormat
     * @param chronoUnit
     * @return
     */
    public static List<LocalDateTime> getRangeToList(LocalDateTime beginTime, LocalDateTime endTime,String javaDateFormat, ChronoUnit chronoUnit,Boolean future) {

        /**
         *  月 日 时  需要把后面的日期补全
         *
         *  如 endTime 2021-02-01 00:00:00  type=2
         *  时间需要补齐到12月
         */
        if(future){
            switch (chronoUnit){
                case MONTHS:
                    endTime = endTime.with(TemporalAdjusters.lastDayOfYear());
                    break;
                case DAYS:
                    endTime = endTime.with(TemporalAdjusters.lastDayOfMonth());
                    break;
                case HOURS:
                    endTime = LocalDateTime.of(endTime.toLocalDate(), LocalTime.MAX);
                    break;
                default:
            }
        }


        List<LocalDateTime> timeList = new ArrayList<>();
        while (endTime.isAfter(beginTime)) {
            timeList.add(beginTime);
            beginTime = beginTime.plus(1, chronoUnit);
        }

        /**
         *  避免边界问题
         *  如
         *   "beginTime": "2020-10-25 23:00:00",
         *   "endTime": "2021-09-23 21:00:00",
         *   "type": 1
         */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(javaDateFormat);
        if(beginTime.format(dateTimeFormatter).equals(endTime.format(dateTimeFormatter))){
            timeList.add(endTime);
        }
        return timeList;
    }


}
