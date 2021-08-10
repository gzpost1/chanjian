package com.yjtech.wisdom.tourism.common.utils;

/** Created by wuyongchong on 2019/8/19. */
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Stream;

public class DateTimeUtil {

  public static final String _default_date_format = "yyyy-MM-dd";
  public static final String date_format_yyyyMMdd = "yyyyMMdd";
  public static final String _default_datetime_format = "yyyy-MM-dd HH:mm:ss";

  public DateTimeUtil() {}

  public static String dateToString(Date date) {
    return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
  }

  public static String dateToString(Date date, String format) {
    return DateFormatUtils.format(date, format);
  }

  public static Date stringToDate(String dateStr) {
    return stringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
  }

  public static Date stringToDate(String dateStr, String format) {
    if (!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(format)) {
      try {
        return new SimpleDateFormat(format).parse(dateStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static LocalDate getMonthStartDay(LocalDate date) {
    return Objects.isNull(date) ? null : date.withDayOfMonth(1);
  }

  public static LocalDate getCurMonthStartDay() {
    return getMonthStartDay(LocalDate.now());
  }

  public static LocalDate getDayBeforeDate(LocalDate date, int n) {
    return Objects.isNull(date) ? null : date.minusDays((long) n);
  }

  public static LocalDate getLatestSevenDay(LocalDate date) {
    return getDayBeforeDate(date, 6);
  }

  public static LocalDate getLatestSevenDayOfToday() {
    return getLatestSevenDay(LocalDate.now());
  }

  public static LocalDate stringToLocalDate(String dateStr, String format) {
    return !StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(format)
        ? LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format))
        : null;
  }

  public static LocalDate stringToLocalDate(String dateStr) {
    return stringToLocalDate(dateStr, "yyyy-MM-dd");
  }

  public static LocalDateTime stringToLocalDateTime(String dateStr, String format) {
    return !StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(format)
        ? LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(format))
        : null;
  }

  public static LocalDateTime stringToLocalDateTime(String dateStr) {
    return stringToLocalDateTime(dateStr, "yyyy-MM-dd HH:mm:ss");
  }

  public static Long localDateToLong(LocalDate localDate) {
    return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static Date localDateToDate(LocalDate localDate) {
    return new Date(localDateToLong(localDate));
  }

  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return new Date(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
  }

  public static LocalDate dateToLocalDate(Date date) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault())
        .toLocalDate();
  }

  public static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
  }

  public static String localDateTimeToString(LocalDateTime localDateTime, String pattern) {
    return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String localDateToString(LocalDate localDate, String pattern) {
    return localDate.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String strLocalDateToString(String date, String pattern, String fomatPattern) {
    LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    return localDateToString(localDate, fomatPattern);
  }

  public static LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
    long timestamp = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    LocalDateTime localDateTime =
        Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    return localDateTime;
  }

  public static String localDateToStrLocalDateTime(LocalDate localDate, String pattern) {
    long timestamp = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    LocalDateTime localDateTime =
        Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    return localDateTimeToString(localDateTime, pattern);
  }

  public static LocalDate secondsLongToLocalDate(Long seconds) {
    return dateToLocalDate(new Date(seconds * 1000));
  }

  /**
   * 获取两个日期间隔的所有日期
   *
   * @return
   */
  public static List<LocalDate> getBetweenDate(LocalDate beginDate, LocalDate endDate) {
    List<LocalDate> list = new LinkedList<>();

    long distance = ChronoUnit.DAYS.between(beginDate, endDate);
    System.out.println(distance);
    if (distance < 1) {
      list.add(beginDate);
    }
    Stream.iterate(
            beginDate,
            d -> {
              return d.plusDays(1);
            })
        .limit(distance + 1)
        .forEach(
            f -> {
              list.add(f);
            });
    return list;
  }

  public static int monthDiff(LocalDate dt1, LocalDate dt2) {
    // 获取第一个时间点的月份
    int month1 = dt1.getMonthValue();
    // 获取第一个时间点的年份
    int year1 = dt1.getYear();
    // 获取第一个时间点的月份
    int month2 = dt2.getMonthValue();
    // 获取第一个时间点的年份
    int year2 = dt2.getYear();
    // 返回两个时间点的月数差
    return (year2 - year1) * 12 + (month2 - month1);
  }
  /** 获取最近12个月，经常用于统计图表的X轴 */
  public static String[] getLast12Months(String date) {
    String[] months = new String[12];
    LocalDate today = LocalDate.parse(date);
    for (int i = 12; i > 0; i--) {
      LocalDate localDate = today.minusMonths(i);
      String ss = localDate.toString().substring(0, 7);
      months[12 - i] = ss;
    }
    return months;
  }

  public static List<String> get24Hours() {
    LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    LinkedList<String> hoursList = Lists.newLinkedList();
    for (int i = 0; i < 24; i++) {
      LocalDateTime hours = time.plusHours(i);
      hoursList.add(hours.format(DateTimeFormatter.ofPattern(_default_datetime_format)));
    }
    return hoursList;
  }
  public static List<String> get31Days() {
    LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
    LinkedList<String> daysList = Lists.newLinkedList();
    for (int i = 0; i < 31; i++) {
      LocalDateTime days = time.plusDays(i);
      String strDays = days.toLocalDate().toString().substring(0,10);
      daysList.add(strDays);
    }
    return daysList;
  }
  public static List<String> get12Months() {
    LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear());
    LinkedList<String> monthList = Lists.newLinkedList();
    for (int i = 0; i < 12; i++) {
      LocalDateTime days = time.plusMonths(i);
      String strMonths = days.toLocalDate().toString().substring(0,7);
      monthList.add(strMonths);
    }

    return monthList;
  }


  public static LocalDateTime strISO8601ToLocalDateTime(String iso8601) {
    ZonedDateTime zdt = ZonedDateTime.parse(iso8601);
    LocalDateTime ldt = zdt.toLocalDateTime();
    return ldt;
  }

  /**
   * 将Long类型的时间戳转换成String 类型的时间格式，时间格式为：yyyy-MM-dd HH:mm:ss
   */
  public static String convertTimeToString(Long time){
    Assert.notNull(time, "time is null");
    DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time),ZoneId.systemDefault()));
  }

  /**
   * 将时间格式为：yyyy-MM-dd HH:mm:ss 转换为 Long类型的时间戳格式，
   */
  public static Long convertTimeToTimestamp(String time){
    Assert.notNull(time, "time is null");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = simpleDateFormat.parse(time);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date.getTime();
  }

  /**
   * 获取当前时间的 上月日期
   *
   * @return
   */
  public static String getLastMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MONTH, -1);
    Date date3 = calendar.getTime();
    SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format3.format(date3);
  }

  /**
   * 获取当前时间的 上年日期
   *
   * @return
   */
  public static String getLastYearDate(String dateStr) {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date parse = format.parse(dateStr);
      calendar.setTime(parse);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    calendar.add(Calendar.YEAR, -1);
    Date newDate = calendar.getTime();
    return format.format(newDate);
  }

  /**
   * 获取当前时间的 前一天日期
   *
   * @return
   */
  public static String getBeforeDayDate(String dateStr) {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date parse = format.parse(dateStr);
      calendar.setTime(parse);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    calendar.add(Calendar.DATE, -1);
    Date newDate = calendar.getTime();
    return format.format(newDate);
  }


  /**
   * 获取当前时间的 前一天日期
   *
   * @return
   */
  public static String getLastDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.HOUR_OF_DAY, -24);
    Date date3 = calendar.getTime();
    SimpleDateFormat format3= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return format3.format(date3);
  }

  /**
   * 获取当前年份
   *
   * @return
   */
  public static String getCurrentYearStr() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    return String.valueOf(year);
  }

  /**
   * 获取当前月份
   *
   * @return
   */
  public static String getCurrentMonthStr() {
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH) + 1;
    String monthStr = String.valueOf(month);
    if (month < 10) {
      monthStr = "0" + month;
    }
    return monthStr;
  }

  /**
   * 获取上月
   *
   * @return
   */
  public static String getLastMonthStr() {
    Calendar calendar = Calendar.getInstance();
    int month = calendar.get(Calendar.MONTH);
    String monthStr = String.valueOf(month);
    if (month < 10) {
      monthStr = "0" + month;
    }
    return monthStr;
  }


  /**
   * 获取当前年 的上月  yyyy-MM
   *
   * @return
   */
  public static String getCurrentLastMonthStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取上一年 的上月  yyyy-MM
   *
   * @return
   */
  public static String getLastYearLastMonthStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.YEAR, -1);
    calendar.add(Calendar.MONTH, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当前年 的上月  的最后一天
   *
   * @return
   */
  public static String getCurrentLastMonthLastDayStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.DATE, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当前年 的上月  的第一天
   *
   * @return
   */
  public static String getCurrentLastMonthFirstDayStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -1);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当前年 的本月  的第一天
   *
   * @return
   */
  public static String getCurrentMonthFirstDayStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当前年 的本月  的最后一天
   *
   * @return
   */
  public static String getCurrentMonthLastDayStr() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DATE, 1);
    calendar.roll(Calendar.DATE, -1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当 年月日
   *
   * @return
   */
  public static String getCurrentDate() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当前时间
   *
   * @return
   */
  public static String getCurrentTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat(_default_datetime_format);
    return sdf.format(calendar.getTime());
  }

  /**
   * 获取当 年月
   *
   * @return
   */
  public static String getCurrentYearAndMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
    return sdf.format(calendar.getTime());
  }

  /**
   * LocalDateTime时间转换
   *
   * @param localDateTime
   * @param format
   * @return
   */
  public static String transformDateTime(LocalDateTime localDateTime, String format) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    return localDateTime.format(formatter);
  }

  /**
   * "yyyy-MM-dd HH:mm:ss" 转 LocalDateTime
   *
   * @return
   */
  public static LocalDateTime getLocalDateTime(String timeStr) {
    DateTimeFormatter df = DateTimeFormatter.ofPattern(_default_datetime_format);
    LocalDateTime ldt = LocalDateTime.parse(timeStr,df);
    return ldt;
  }

}
