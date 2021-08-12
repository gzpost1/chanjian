package com.yjtech.wisdom.tourism.common.constant;

/**
 * 通用常量信息
 *
 * @author liuhong
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 参数管理 cache key
     */
    public static final String SIMULATION_KEY = "simulation_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 批量执行更新时的大小
     */
    public static final int BATCH_SIZE = 500;

    /**
     * 小时
     */
    public static final Byte HOUR = 4;

    /**
     * 状态
     * 1-启用
     */
    public static final Integer STATUS_NEGATIVE = 1;

    /**
     * 状态
     * 0-禁用
     */
    public static final Integer STATUS_POSITIVE = 0;


    /**
     * 数字常量
     */
    public static class NumberConstants{
        /**
         * 数字0
         */
        public static final Integer NUMBER_ZERO = 0;
        /**
         * 数字1
         */
        public static final Integer NUMBER_ONE = 1;
        /**
         * 数字2
         */
        public static final Integer NUMBER_TWO = 2;
        /**
         * 数字3
         */
        public static final Integer NUMBER_THREE = 3;
        /**
         * 数字4
         */
        public static final Integer NUMBER_FOUR = 4;
        /**
         * 数字6
         */
        public static final Integer NUMBER_SIX = 6;
        /**
         * 数字8
         */
        public static final Integer NUMBER_EIGHT = 8;
        /**
         * 数字9
         */
        public static final Integer NUMBER_NINE = 9;
        /**
         * 数字10
         */
        public static final Integer NUMBER_TEN = 10;
        /**
         * 数字11
         */
        public static final Integer NUMBER_ELEVEN = 11;
        /**
         * 数字12
         */
        public static final Integer NUMBER_TWELVE = 12;
        /**
         * 数字100
         */
        public static final Integer NUMBER_HUNDRED = 100;
    }

}
