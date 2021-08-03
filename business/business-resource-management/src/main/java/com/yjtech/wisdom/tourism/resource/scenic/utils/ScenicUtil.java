package com.yjtech.wisdom.tourism.resource.scenic.utils;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScenicUtil {

    //开放日期-开始时间 和 结束日期-结束时间 比较
    // 08-11 11:15  和  09-02 12:00
    public static void dateCompare(ScenicEntity entity){
        if (StringUtils.isNotBlank(entity.getOpenEndDate()) || StringUtils.isNotBlank(entity.getOpenStartDate())
                || StringUtils.isNotBlank(entity.getOpenStartTime()) || StringUtils.isNotBlank(entity.getOpenEndTime())) {
            if(StringUtils.isNotBlank(entity.getOpenEndDate()) && StringUtils.isNotBlank(entity.getOpenStartDate())
                    && StringUtils.isNotBlank(entity.getOpenStartTime()) && StringUtils.isNotBlank(entity.getOpenEndTime())){
                //都不为空 时间拼接年份
                String beginDate = LocalDate.now().getYear() + "-" + entity.getOpenStartDate() + " " +entity.getOpenStartTime() + ":00";
                String endDate = LocalDate.now().getYear() + "-" + entity.getOpenEndDate() + " " + entity.getOpenEndTime() + ":00";
                LocalDateTime beginDateTime,endDateTime;
                try {
                    beginDateTime = DateTimeUtil.stringToLocalDateTime(beginDate);
                    endDateTime = DateTimeUtil.stringToLocalDateTime(endDate);
                } catch (Exception e) {
                    throw new CustomException("时间格式错误");
                }
                if (beginDateTime.isAfter(endDateTime) || beginDateTime.isEqual(endDateTime)) {
                    throw new CustomException("开始时间必须小于结束时间");
                }
            }else {
                //存在有空的和没有空的
                throw new CustomException("开放时间段只能都为空或者都不为空");
            }
        }
    }
}
