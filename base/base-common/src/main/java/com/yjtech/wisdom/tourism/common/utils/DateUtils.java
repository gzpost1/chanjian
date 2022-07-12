package com.yjtech.wisdom.tourism.common.utils;

import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 时间工具类
 *
 * @author liuhong
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去7天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getLastWeek(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取时间范围内的时间日期列表
     *
     * @param begin
     * @param end
     * @param format
     * @param calendarType calendar日期时间
     * @return
     */
    public static List<String> getRangeToList(String begin, String end, String format, int calendarType) {
        //构建日期字符串格式
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dBegin;
        Date dEnd;
        try {
            dBegin = sdf.parse(begin);
            dEnd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "获取时间范围内的时间日期列表失败：格式化日期异常");
        }
        List<String> timeList = new ArrayList<>();
        //手动添加初始时间
        timeList.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        //追踪遍历时间
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(calendarType, 1);
            String dayStr = sdf.format(calBegin.getTime());
            timeList.add(dayStr);
        }
        return timeList;
    }

    /**
     * 获取当前年每月列表
     *
     * @return
     */
    public static List<String> getEveryMonthOfCurrentYear() {
        Date now = new Date();
        return DateUtils.getRangeToList(
                DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.beginOfYear(now)),
                DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.endOfYear(now)),
                DateUtils.YYYY_MM,
                Calendar.MONTH);
    }

    /**
     * 获取当前年每日列表
     *
     * @return
     */
    public static List<String> getEveryDayOfCurrentYear() {
        return DateUtils.getRangeToList(
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtil.beginOfYear(new Date())),
                DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, DateUtil.offsetDay(new Date(), -1)),
                DateUtils.YYYY_MM_DD,
                Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取缓存过期时间
     * 默认每天00:30刷新
     *
     * @date 2021/9/1 18:00
     * @author horadirm
     */
    public static long getCacheExpire() {
        LocalDateTime beginTime = LocalDateTime.now();
        LocalDateTime endTime = beginTime.toLocalDate().atStartOfDay().plusDays(1).plusMinutes(30);
        return Duration.between(beginTime, endTime).toMinutes();
    }

    public static String getTimePeriod(Long during) {
        if (Objects.isNull(during)) {
            return null;
        }
        long hour = during / 3600000;
        long min = (during - hour * 3600000) / 60000;
        long sec = (during - hour * 3600000 - min * 60000) / 1000;
        String duringTime = "";
        if (sec != 0) {
            duringTime = sec + "秒";
        }
        if (min != 0) {
            duringTime = min + "分" + duringTime;
        }
        if (hour != 0) {
            duringTime = hour + "时" + duringTime;
        }
        return duringTime;
    }

}
