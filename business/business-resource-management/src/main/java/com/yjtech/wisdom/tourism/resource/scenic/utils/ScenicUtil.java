package com.yjtech.wisdom.tourism.resource.scenic.utils;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.scenic.entity.dto.OpenTimeDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScenicUtil {

    //开放日期-开始时间 和 结束日期-结束时间 比较
    // 08-11 11:15  和  09-02 12:00
    public static void dateCompare(OpenTimeDto dto) {
        if (StringUtils.isNotBlank(dto.getOpenEndDate()) || StringUtils.isNotBlank(dto.getOpenStartDate())
                || StringUtils.isNotBlank(dto.getOpenStartTime()) || StringUtils.isNotBlank(dto.getOpenEndTime())) {
            if (StringUtils.isNotBlank(dto.getOpenEndDate()) && StringUtils.isNotBlank(dto.getOpenStartDate())
                    && StringUtils.isNotBlank(dto.getOpenStartTime()) && StringUtils.isNotBlank(dto.getOpenEndTime())) {
                //都不为空 时间拼接年份
                String beginDate = LocalDate.now().getYear() + "-" + dto.getOpenStartDate() + " " + dto.getOpenStartTime() + ":00";
                String endDate = LocalDate.now().getYear() + "-" + dto.getOpenEndDate() + " " + dto.getOpenEndTime() + ":00";
                LocalDateTime beginDateTime, endDateTime;
                try {
                    beginDateTime = DateTimeUtil.stringToLocalDateTime(beginDate);
                    endDateTime = DateTimeUtil.stringToLocalDateTime(endDate);
                } catch (Exception e) {
                    throw new CustomException("时间格式错误");
                }
                if (beginDateTime.isAfter(endDateTime) || beginDateTime.isEqual(endDateTime)) {
                    throw new CustomException("开始时间必须小于结束时间");
                }
            } else {
                //存在有空的和没有空的
                throw new CustomException("开放时间段只能都为空或者都不为空");
            }
        }
    }
}
