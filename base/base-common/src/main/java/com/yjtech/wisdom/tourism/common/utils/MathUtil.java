package com.yjtech.wisdom.tourism.common.utils;

import java.math.BigDecimal;

/**
 * @author liuhong
 */
public final class MathUtil {
    private static final int DEF_DIV_SCALE = 4;

    /** 百分比默认1位小数, 那么除数的商默认为3位小数 */
    private static final int DEF_DIV_PERCENT_SCALE = 3;

    private static final BigDecimal BIG_DECIMAL_100 = BigDecimal.valueOf(100);

    /**
     * ( 相对 )精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, Integer scale) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }

        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }

        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * ( 相对 )精确百分比运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal calPercent(BigDecimal v1, BigDecimal v2, Integer scale) {
        return divide(v1, v2, scale).multiply(BIG_DECIMAL_100);
    }

    /**
     * ( 相对 )精确百分比运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入，
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商(BigDecimal)的百分比(乘以100保留一位小数)
     */
    public static BigDecimal calPercent(BigDecimal v1, BigDecimal v2) {
        return calPercent(v1, v2, DEF_DIV_PERCENT_SCALE);
    }

}
