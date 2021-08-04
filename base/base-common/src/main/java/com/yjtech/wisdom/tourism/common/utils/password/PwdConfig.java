package com.yjtech.wisdom.tourism.common.utils.password;

/**
 * @author liuhong
 */

public class PwdConfig {
    /**
     * 密码口令检测对应系统等级
     */
    public static String SYSTEM_GRADE = "3";

    /**
     * 是否检测密码口令长度标识
     */
    public static String CHECK_PASSWORD_LENGTH = "enable";
    /**
     * 密码最小长度，默认为8
     */
    public static String MIN_LENGTH = "8";
    /**
     * 密码最大长度，默认为30
     */
    public static String MAX_LENGTH = "30";

    /**
     * 是否包含数字
     */
    public static String CHECK_CONTAIN_DIGIT = "enable";
    /**
     * 是否区分大小写
     */
    public static String CHECK_DISTINGGUISH_CASE = "enable";
    /**
     * 是否包含小写字母
     */
    public static String CHECK_LOWER_CASE = "enable";
    /**
     * 是否包含大写字母
     */
    public static String CHECK_UPPER_CASE = "enable";
    /**
     * 是否包含特殊符号
     */
    public static String CHECK_CONTAIN_SPECIAL_CHAR = "disable";
    /**
     * 特殊符号集合
     */
    public static String DEFAULT_SPECIAL_CHAR="!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static String SPECIAL_CHAR;

    /**
     * 是否检测键盘按键横向连续
     */
    public static String CHECK_HORIZONTAL_KEY_SEQUENTIAL = "disable";
    /**
     * 键盘物理位置横向不允许最小的连续个数
     */
    public static String LIMIT_HORIZONTAL_NUM_KEY = "3";
    /**
     * 是否检测键盘按键斜向连续
     */
    public static String CHECK_SLOPE_KEY_SEQUENTIAL = "disable";
    /**
     * 键盘物理位置斜向不允许最小的连续个数
     */
    public static String LIMIT_SLOPE_NUM_KEY = "3";

    /**
     * 是否检测逻辑位置连续
     */
    public static String CHECK_LOGIC_SEQUENTIAL = "disable";
    /**
     * 密码口令中字符在逻辑位置上不允许最小的连续个数
     */
    public static String LIMIT_LOGIC_NUM_CHAR = "4";

    /**
     * 是否检测连续字符相同
     */
    public static String CHECK_SEQUENTIAL_CHAR_SAME = "disable";
    /**
     * 密码口令中相同字符不允许最小的连续个数
     */
    public static String LIMIT_NUM_SAME_CHAR = "4";

    /**
     * 键盘横向方向规则
     */
    public static String[] KEYBOARD_HORIZONTAL_ARR = {
            "01234567890",
            "qwertyuiop",
            "asdfghjkl",
            "zxcvbnm",
    };
    /**
     * 键盘斜线方向规则
     */
    public static String[] KEYBOARD_SLOPE_ARR = {
            "1qaz",
            "2wsx",
            "3edc",
            "4rfv",
            "5tgb",
            "6yhn",
            "7ujm",
            "8ik,",
            "9ol.",
            "0p;/",
            "=[;.",
            "-pl,",
            "0okm",
            "9ijn",
            "8uhb",
            "7ygv",
            "6tfc",
            "5rdx",
            "4esz"
    };

}
