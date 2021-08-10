package com.yjtech.wisdom.tourism.marketing.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.AnalysisMonthChartInfo;

import java.util.List;

/**
 * 服务工具类
 *
 * @Date 2020/12/7 10:27
 * @Author horadirm
 */
public class ServiceUtils {


    /**
     * 构建趋势信息
     *
     * ps：构建部分大屏月趋势信息，具有强耦合性
     * @param monthMarkList
     * @param currentAnalysisMonthInfo
     * @param lastAnalysisMonthInfo
     * @param currentData
     * @param lastData
     */
    public static void buildAnalysisInfo(List<String> monthMarkList,
                     List<AnalysisMonthChartInfo> currentAnalysisMonthInfo, List<AnalysisMonthChartInfo> lastAnalysisMonthInfo,
                     List<AnalysisMonthChartInfo> currentData, List<AnalysisMonthChartInfo> lastData){
        //校验月趋势信息，为无结果的查询设置默认值
        for(String monthMark : monthMarkList){
            //去年月趋势匹配标识
            boolean lastMathFlag = false;
            //当年月趋势匹配标识
            boolean currentMathFlag = false;
            //当年月趋势信息缓存
            AnalysisMonthChartInfo currentMonthTemp = new AnalysisMonthChartInfo();
            //去年月趋势信息缓存
            AnalysisMonthChartInfo lastMonthTemp = new AnalysisMonthChartInfo();
            //处理今年趋势信息
            for(AnalysisMonthChartInfo currentMonth : currentAnalysisMonthInfo){
                //匹配成功
                if(monthMark.equals(currentMonth.getXTime())){
                    currentData.add(currentMonth);
                    //设置临时当年月趋势信息，用于同步去年趋势信息同比、环比
                    currentMonthTemp = currentMonth;
                    //匹配标识操作
                    currentMathFlag = true;
                    break;
                }
            }
            //处理去年趋势信息
            for(AnalysisMonthChartInfo lastMonth : lastAnalysisMonthInfo){
                String lastMonthTime = lastMonth.getXTime();
                //匹配成功
                if(StringUtils.isNotBlank(lastMonthTime) && monthMark.substring(5, 7).equals(lastMonthTime.substring(5, 7))){
                    lastMonthTemp = lastMonth;
                    lastMathFlag = true;
                    break;
                }
            }

            //当年月趋势未匹配，则设置默认
            if(!currentMathFlag){
                currentData.add(new AnalysisMonthChartInfo(monthMark));
            }

            //去年月趋势未匹配，则设置默认
            if(!lastMathFlag){
                lastMonthTemp = new AnalysisMonthChartInfo(DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.offset(DateUtils.dateTime(DateUtils.YYYY_MM, monthMark), DateField.YEAR, -1)));
            }
            //设置同比、环比
            lastMonthTemp.setSame(currentMathFlag ? currentMonthTemp.getSame() : null);
            lastMonthTemp.setSequential(currentMathFlag ? currentMonthTemp.getSequential() : null);

            lastData.add(lastMonthTemp);
        }
    }

}
