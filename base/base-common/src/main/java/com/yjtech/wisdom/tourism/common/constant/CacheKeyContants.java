package com.yjtech.wisdom.tourism.common.constant;

/**
 * Created by wuyongchong on 2019/10/29.
 */
public class CacheKeyContants {
    //景区天气
    public final static String WEATHER_CACHE_KEY_PREFIX = "industryMonitory:weather:";

    //小程序状态
    public final static String WECHATAPP_STATUS_CACHE_KEY_PREFIX = "smartScenic:wechatapp_status:";

    /**
     * 旅游投诉-配置指派人员 cacheKey
     */
    public static final String KEY_ASSIGN_TRAVEL_COMPLAINT = "assign:travelComplaint:";

    /**
     * 旅游投诉-指派处理人员 cacheKey
     */
    public static final String KEY_DEAL_TRAVEL_COMPLAINT = "deal:travelComplaint:";

    /** ********** 中测 第三方信息缓存 key ********** */
    /**
     * 中测 token
     */
    public static final String ZC_TOKEN_KEY = "zc:token:";


}

