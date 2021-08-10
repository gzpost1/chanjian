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
        if (StringUtils.isNotBlank(dto.getOpenEndDate()) && StringUtils.isNotBlank(dto.getOpenStartDate()) && dto.getOpenStartDate().compareTo(dto.getOpenEndDate()) > 0) {
            throw new CustomException("开放月份必须小于或等于结束月份");
        }else if((StringUtils.isBlank(dto.getOpenEndDate()) && StringUtils.isNotBlank(dto.getOpenStartDate()))
                || StringUtils.isNotBlank(dto.getOpenEndDate()) && StringUtils.isBlank(dto.getOpenStartDate())){
            throw new CustomException("开放月份和结束月份只能都为空或者都不为空");
        }
        if(StringUtils.isNotBlank(dto.getOpenStartTime()) && StringUtils.isNotBlank(dto.getOpenEndTime())){
            //都不为空 时间拼接年份
            LocalDateTime beginDateTime, endDateTime;
            String beginDate = LocalDate.now() + " " + dto.getOpenStartTime() + ":00";
            String endDate = LocalDate.now() + " " + dto.getOpenEndTime() + ":00";
            try {
                beginDateTime = DateTimeUtil.stringToLocalDateTime(beginDate);
                endDateTime = DateTimeUtil.stringToLocalDateTime(endDate);
            } catch (Exception e) {
                throw new CustomException("时间格式错误");
            }
            if (beginDateTime.compareTo(endDateTime) != -1) {
                throw new CustomException("开始时间必须小于结束时间");
            }
        } else if ((StringUtils.isBlank(dto.getOpenStartTime()) && StringUtils.isNotBlank(dto.getOpenEndTime()))
                || StringUtils.isNotBlank(dto.getOpenStartTime()) && StringUtils.isBlank(dto.getOpenEndTime())) {
            throw new CustomException("开始时间和结束时间只能都为空或者都不为空");
        }
    }
}
