package com.yjtech.wisdom.tourism.common.utils;

import com.google.common.base.Preconditions;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * @author liuhong
 */
public class CommonPreconditions {
    private static final String timePatternStr = "((([0-1][0-9])|2[0-3]|[1-9])(:)[0-5][0-9](:)[0-5][0-9])";// 不接受中文分割符
    private static final String timeHHPatternStr = "(([0-1][0-9])|2[0-3]|[1-9])";// 不接受中文分割符
    private static final String datePatternStr = "\\d{4}(\\-)\\d{1,2}\\1\\d{1,2}";//ps 没有检查闰年
    private static final String dateYYYYMMPatternStr = "\\d{4}(\\-)\\d{1,2}";
    private static final String dateYYYYPatternStr = "\\d{4}";

    /**
     * 日期格式断言 yyyy-MM
     */
    private static final Predicate<String> dateYYYYMMPredicate =
            s -> {
                Pattern pattern = Pattern.compile(dateYYYYMMPatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * 日期格式断言 yyyy
     */
    private static final Predicate<String> dateYYYYPredicate =
            s -> {
                Pattern pattern = Pattern.compile(dateYYYYPatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * 日期格式断言 yyyy-MM-dd
     */
    private static final Predicate<String> datePredicate =
            s -> {
                Pattern pattern = Pattern.compile(datePatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * 日期时间格式断言 yyyy-MM-dd HH:mm:ss
     */
    private static final Predicate<String> dateTimePredicate =
            s -> {
                Pattern pattern = Pattern.compile(datePatternStr + " " + timePatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * 日期时间格式断言 yyyy-MM-dd HH:
     */
    private static final Predicate<String> dateYYYYMMDDHHPredicate =
            s -> {
                Pattern pattern = Pattern.compile(datePatternStr + " " + timeHHPatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * 时间格式断言 HH:mm:ss
     */
    private static final Predicate<String> timePredicate =
            s -> {
                Pattern pattern = Pattern.compile(timePatternStr);
                return !checkStringEmpty(s) && pattern.matcher(s).matches();
            };

    /**
     * bool表达式为false, 抛出异常
     * @param expression
     * @param errorMsg
     */
    public static void checkArgument(boolean expression, String errorMsg) {
        Preconditions.checkArgument(expression, errorMsg);
    }

    /**
     * 对象为空时, 抛出异常
     * @param t
     * @param errorMsg
     * @param <T>
     */
    public static <T> void checkNotNull(T t, String errorMsg) {
        Preconditions.checkNotNull(t, errorMsg);
    }

    /**
     * 对象为空时, 抛出指定异常
     * @param t
     * @param exSupplier
     * @param <T>
     */
    public static <T> void checkNotNull(T t, Supplier<? extends RuntimeException> exSupplier) {
        Optional.ofNullable(t).orElseThrow(exSupplier);
    }

    /**
     * 判断字符串是否为空串或空
     * @param s
     * @return
     */
    public static boolean checkStringEmpty(String s) {
        return StringUtils.isEmpty(s);
    }

    /**
     * 字符串不为空时, 若其长度不在指定范围内, 抛出异常
     * @param str
     * @param low
     * @param high
     * @param errorMsg
     */
    public static void checkNullableString(String str, int low, int high, String errorMsg) {
        Predicate<String> predicate = getStringPredicate(true, low, high);
        predicateString(predicate, str, errorMsg);
    }

    /**
     * 字符串为空且长度不在指定范围内, 抛出异常
     * @param str
     * @param low
     * @param high
     * @param errorMsg
     */
    public static void checkNonNullString(String str, int low, int high, String errorMsg) {
        Predicate<String> predicate = getStringPredicate(false, low, high);
        predicateString(predicate, str, errorMsg);
    }

    /**
     * 根据predicate规则校验字符串
     * @param predicate
     * @param str
     * @param errorMsg
     */
    public static void predicateString(Predicate<String> predicate, String str, String errorMsg) {
        if (!predicate.test(str)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 判断集合是否为null或empty
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> boolean checkCollectionEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 若元素不在集合中, 抛出异常
     * @param t
     * @param collection
     * @param errorMsg
     * @param <T>
     */
    public static <T> void checkElementInSet(T t, Collection<T> collection, String errorMsg) {
        if (checkCollectionEmpty(collection) || !collection.contains(t)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 校验日期格式是否符合(yyyy-MM-dd)
     * @param date
     * @param errorMsg
     */
    public static void checkDate(String date, String errorMsg) {
        if (!datePredicate.test(date)) {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 校验日期时间格式是否符合(yyyy-MM-dd HH:mm:ss)
     * @param dateTime
     * @param errorMsg
     */
    public static void checkDateTime(String dateTime, String errorMsg) {
        if (!dateTimePredicate.test(dateTime)) {
            throw new RuntimeException(errorMsg);
        }
    }


    /**
     * 校验日期时间格式是否符合(yyyy-MM-dd HH:mm:ss)
     * @param dateTime
     * @param errorMsg
     */
    public static void checkDateYYYYMMDDHH(String dateTime, String errorMsg) {
        if (!dateYYYYMMDDHHPredicate.test(dateTime)) {
            throw new RuntimeException(errorMsg);
        }
    }
    /**
     * 校验日期时间格式是否符合(yyyy-MM)
     * @param dateTime
     * @param errorMsg
     */
    public static void checkDateYYYYMM(String dateTime, String errorMsg) {
        if (!dateYYYYMMPredicate.test(dateTime)) {
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * 校验日期时间格式是否符合(yyyy)
     * @param dateTime
     * @param errorMsg
     */
    public static void checkDateYYYY(String dateTime, String errorMsg) {
        if (!dateYYYYPredicate.test(dateTime)) {
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     *  校验日期格式是否符合(HH:mm:ss)
     * @param time
     * @param errorMsg
     */
    public static void checkTime(String time, String errorMsg) {
        if (!timePredicate.test(time)) {
            throw new RuntimeException(errorMsg);
        }
    }

    private static Predicate<String> getStringPredicate(boolean isEmpty, int low, int high) {
        Predicate<String> lengthPredicate = s -> (s.length() >= low && s.length() < high);
        if (isEmpty) {
            return s -> StringUtils.isEmpty(s) || lengthPredicate.test(s);
        } else {
            return s -> !StringUtils.isEmpty(s) && lengthPredicate.test(s);
        }
    }
}
