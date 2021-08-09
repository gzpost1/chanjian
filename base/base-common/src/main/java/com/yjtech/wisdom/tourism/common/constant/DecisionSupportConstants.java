package com.yjtech.wisdom.tourism.common.constant;

/**
 * 决策辅助
 *
 * @author renguangqian
 * @date 2021/7/28 9:40
 */
public class DecisionSupportConstants {

    /**
     * 省外访客类型
     */
    public final static String PROVINCE_OUTSIDE_TYPE = "12";

    /**
     * 大屏_决策预警_上次分析时间的redis 的 Key
     */
    public final static String LAST_ANALYZE_DATE_KEY = "LAST_ANALYZE_DATE_KEY";

    /**
     * 低风险类型
     */
    public final static int LOW_RISK_TYPE = 0;

    /**
     * 中风险类型
     */
    public final static int MEDIUM_RISK_TYPE = 1;

    /**
     * 高风险类型
     */
    public final static int HIGH_RISK_TYPE = 2;

    /**
     * 无风险类型
     */
    public final static int IGNORE_RISK_TYPE = 3;

    /**
     * 低风险类型文本
     */
    public final static String LOW_RISK_TYPE_TEXT = "低风险";

    /**
     * 中风险类型文本
     */
    public final static String MEDIUM_RISK_TYPE_TEXT = "中风险";

    /**
     * 高风险类型文本
     */
    public final static String HIGH_RISK_TYPE_TEXT = "高风险";



    /**
     * 数值 预警配置项 上升类型
     */
    public final static int NUMBER_TYPE_UP = 0;
    /**
     * 数值 预警配置项 下降类型
     */
    public final static int NUMBER_TYPE_DOWN = 1;
    /**
     * 数值 预警配置项 忽略变化
     */
    public final static int NUMBER_TYPE_IGNORE = 2;


    /**
     * 数值 预警配置项
     */
    public final static Integer DECISION_WARN_TYPE_NUMBER = 1;

    /**
     * 文本 预警配置项
     */
    public final static Integer DECISION_WARN_TYPE_TEXT = 0;



    /**
     * 数值类型 上升 标识
     */
    public final static Integer NUMBER_TYPE_UP_SIGN = 1;

    /**
     * 数值类型 下降 标识
     */
    public final static Integer NUMBER_TYPE_DOWN_SIGN = -1;




    /**
     * 综合概况_统计年月
     */
    public final static int ZHGK_TJNY = 1;

    /**
     * 综合概况_平台名称
     */
    public final static int ZHGK_PTMC = 2;

    /**
     * 综合概况_风险预警项数量
     */
    public final static int ZHGK_FXYJXSL = 3;

    /**
     * 综合概况_高风险项数量
     */
    public final static int ZHGK_GFXXSL = 4;

    /**
     * 综合概况_中风险项数量
     */
    public final static int ZHGK_ZFXXSL = 5;

    /**
     * 综合概况_低风险项数量
     */
    public final static int ZHGK_DFXXSL = 6;

    /**
     * 综合概况_高风险项指标项
     */
    public final static int ZHGK_GFXXZBX = 7;

    /**
     * 综合概况_中风险项指标项
     */
    public final static int ZHGK_ZFXXZBX = 8;

    /**
     * 综合概况_低风险项指标项
     */
    public final static int ZHGK_XFXXZBX = 9;

    /**
     * 省外游客_统计年月
     */
    public final static int SWYK_TJNY = 10;

    /**
     * 省外游客_平台简称
     */
    public final static int SWYK_PTJC = 11;

    /**
     * 省外游客_省外游客数量
     */
    public final static int SWYK_SWYKSL = 12;

    /**
     * 省外游客_环比变化（较上月）
     */
    public final static int SWYK_HBBH = 13;

    /**
     * 省外游客_同比变化（较去年同月）
     */
    public final static int SWYK_TBBH = 14;

    /**
     * 省内游客 _统计年月
     */
    public final static int SNYK_TJNY = 15;

    /**
     * 省内游客 _平台简称
     */
    public final static int SNYK_PTJC = 16;

    /**
     * 省内游客 _省内游客数量
     */
    public final static int SNYK_SNYKSL = 17;

    /**
     * 省内游客 _环比变化（较上月）
     */
    public final static int SNYK_HBBH = 18;

    /**
     * 省内游客 _同比变化（较去年同月）
     */
    public final static int SNYK_TBBH = 19;

    /**
     * 整体游客 _统计年月
     */
    public final static int ZTYK_TJNY = 20;

    /**
     * 整体游客 _平台简称
     */
    public final static int ZTYK_PTJC = 21;

    /**
     * 整体游客 _整体游客数量
     */
    public final static int ZTYK_ZTYKSL = 22;

    /**
     * 整体游客 _环比变化（较上月）
     */
    public final static int ZTYK_HBBH = 23;

    /**
     * 整体游客 _同比变化（较去年同月）
     */
    public final static int ZTYK_TBBH = 24;

    /**
     * 整体景区客流 _统计年月
     */
    public final static int ZTJQKL_TJNY = 25;

    /**
     * 整体景区客流 _平台简称
     */
    public final static int ZTJQKL_PTJC = 26;

    /**
     * 整体景区客流 _全部景区接待数量
     */
    public final static int ZTJQKL_QBJQJDSL = 27;

    /**
     * 整体景区客流 _环比变化（较上月）
     */
    public final static int ZTJQKL_HBBH = 28;

    /**
     * 整体景区客流 _同比变化（较去年同月）
     */
    public final static int ZTJQKL_TBBH = 29;

    /**
     * 景区客流排行 _统计年月
     */
    public final static int JQKLPH_TJNY = 30;

    /**
     * 景区客流排行 _游客流失最多景区名称
     */
    public final static int JQKLPH_YKLSZDJQMC = 31;

    /**
     * 景区客流排行 _游客流失最多景区接待量
     */
    public final static int JQKLPH_YKLSZDJQJDL = 32;

    /**
     * 景区客流排行 _环比变化（较上月）
     */
    public final static int JQKLPH_HBBH = 33;

    /**
     * 景区客流排行 _同比变化（较去年同月）
     */
    public final static int JQKLPH_TBBH = 34;

    /**
     * 景区客流排行 _其他游客流失景区名称
     */
    public final static int JQKLPH_QTYKLSJQMC = 35;

    /**
     * 整体景区满意度 _统计年月
     */
    public final static int ZTJQMYD_TJNY = 36;

    /**
     * 整体景区满意度 _整体景区评价数量
     */
    public final static int ZTJQMYD_ZTJQPJSL = 37;

    /**
     * 整体景区满意度 _整体景区好评数量
     */
    public final static int ZTJQMYD_ZTJQHPSL = 38;

    /**
     * 整体景区满意度 _整体景区满意度
     */
    public final static int ZTJQMYD_ZTJQMYD = 39;

    /**
     * 整体景区满意度 _环比变化（较上月）
     */
    public final static int ZTJQMYD_HBBH = 40;

    /**
     * 整体景区满意度 _同比变化（较去年同月）
     */
    public final static int ZTJQMYD_TBBH = 41;

    /**
     * 景区满意度排行 _统计年月
     */
    public final static int JQMYDPH_TJNY = 42;

    /**
     * 景区满意度排行 _满意度下降最多景区名称
     */
    public final static int JQMYDPH_MYDXJZDJQMC = 43;

    /**
     * 景区满意度排行 _满意度下降最多景区评价量
     */
    public final static int JQMYDPH_MYDXJZDJQPJL = 44;

    /**
     * 景区满意度排行 _满意度下降最多景区好评量
     */
    public final static int JQMYDPH_MYDXJZDJQHPL = 45;

    /**
     * 景区满意度排行 _满意度下降最多景区满意度
     */
    public final static int JQMYDPH_MYDXJZDJQMYD = 46;

    /**
     * 景区满意度排行 _环比变化（较上月）
     */
    public final static int JQMYDPH_HBBH = 47;

    /**
     * 景区满意度排行 _同比变化（较去年同月）
     */
    public final static int JQMYDPH_TBBH = 48;

    /**
     * 景区满意度排行 _其他满意度下降景区名称
     */
    public final static int JQMYDPH_QTMYDXJJQMC = 49;

    /**
     * 整体酒店民宿满意度 _统计年月
     */
    public final static int ZTJDMSMYD_TJNY = 50;

    /**
     * 整体酒店民宿满意度 _整体酒店民宿评价数量
     */
    public final static int ZTJDMSMYD_ZTJDMSPJSL = 51;

    /**
     * 整体酒店民宿满意度 _整体酒店民宿好评数量
     */
    public final static int ZTJDMSMYD_ZTJDMSHPSL = 52;

    /**
     * 整体酒店民宿满意度 _整体酒店民宿满意度
     */
    public final static int ZTJDMSMYD_ZTJDMSMYD = 53;

    /**
     * 整体酒店民宿满意度 _环比变化（较上月）
     */
    public final static int ZTJDMSMYD_HBBH = 54;

    /**
     * 整体酒店民宿满意度 _同比变化（较去年同月）
     */
    public final static int ZTJDMSMYD_TBBH = 55;

    /**
     * 酒店民宿满意度排行 _统计年月
     */
    public final static int JDMSMYDPH_TJNY = 56;

    /**
     * 酒店民宿满意度排行 _满意度下降最多酒店民宿名称
     */
    public final static int JDMSMYDPH_MYDXJZDJDMSMC = 57;

    /**
     * 酒店民宿满意度排行 _满意度下降最多酒店民宿评价量
     */
    public final static int JDMSMYDPH_MYDXJZDJDMSPJL = 58;

    /**
     * 酒店民宿满意度排行 _满意度下降最多酒店民宿好评量
     */
    public final static int JDMSMYDPH_MYDXJZDJDMSHPL = 59;

    /**
     * 酒店民宿满意度排行 _满意度下降最多酒店民宿满意度
     */
    public final static int JDMSMYDPH_MYDXJZDJDMSMYD = 60;

    /**
     * 酒店民宿满意度排行 _环比变化（较上月）
     */
    public final static int JDMSMYDPH_HBBH = 61;

    /**
     * 酒店民宿满意度排行 _同比变化（较去年同月）
     */
    public final static int JDMSMYDPH_TBBH = 62;

    /**
     * 酒店民宿满意度排行 _其他满意度下降酒店民宿名称
     */
    public final static int JDMSMYDPH_QTMYDXJJDMSMC = 63;

    /**
     * 投诉量_统计年月
     */
    public final static int TSL_TJNY = 64;

    /**
     * 投诉量_一码游投诉数量
     */
    public final static int TSL_YMYTSL = 65;

    /**
     * 投诉量_环比变化（较上月）
     */
    public final static int TSL_HBBH = 66;

    /**
     * 投诉量_同比变化（较去年同月）
     */
    public final static int TSL_TBBH = 67;

    /**
     * 订单量_统计年月
     */
    public final static int DDL_TJNY = 68;

    /**
     * 订单量_一码游订单数量
     */
    public final static int DDL_YMLDDSL = 69;

    /**
     * 订单量_环比变化（较上月）
     */
    public final static int DDL_HBBH = 70;

    /**
     * 订单量_同比变化（较去年同月）
     */
    public final static int DDL_TBBH = 71;

    /**
     * 交易额_统计年月
     */
    public final static int JYE_TJNY = 72;

    /**
     * 交易额_一码游交易额
     */
    public final static int JYE_YMYJYE = 73;

    /**
     * 交易额_环比变化（较上月）
     */
    public final static int JYE_HBBH = 74;

    /**
     * 交易额_同比变化（较去年同月）
     */
    public final static int JYE_TBBH = 75;

    /**
     * 旅游投诉_统计年月
     */
    public final static int LYTS_TJNY = 76;

    /**
     * 旅游投诉_旅游投诉数量
     */
    public final static int LYTS_YLTSSL = 77;

    /**
     * 旅游投诉_环比变化（较上月）
     */
    public final static int LYTS_HBBH = 78;

    /**
     * 旅游投诉_同比变化（较去年同月）
     */
    public final static int LYTS_TBBH = 79;

    /**
     * 应急事件统计_统计年月
     */
    public final static int YJSJTJ_TJNY = 80;

    /**
     * 应急事件统计_应急事件数量
     */
    public final static int YJSJTJ_YJSJSL = 81;

    /**
     * 应急事件统计_环比变化（较上月）
     */
    public final static int YJSJTJ_HBBH = 82;

    /**
     * 应急事件统计_同比变化（较去年同月）
     */
    public final static int YJSJTJ_TBBH = 83;

    /**
     * 高并发应急事件_统计年月
     */
    public final static int GBFYJSJ_TJNY = 84;

    /**
     * 高并发应急事件_应急事件数量
     */
    public final static int GBFYJSJ_YJSJSL = 85;

    /**
     * 高并发应急事件_高发事件类型
     */
    public final static int GBFYJSJ_GBFSJLX = 86;

    /**
     * 高并发应急事件_高发事件类型环比变化（较上月）
     */
    public final static int GBFYJSJ_GBFSJLXHBBH = 87;

    /**
     * 高并发应急事件_高发事件类型同比变化（较去年同月）
     */
    public final static int GBFYJSJ_GBFSJLXTBBH = 88;

    /**
     * 高并发应急事件_高发事件等级
     */
    public final static int GBFYJSJ_GBFSJDJ = 89;

    /**
     * 高并发应急事件_高发事件等级环比变化（较上月）
     */
    public final static int GBFYJSJ_GBFSJDJHBBH = 90;

    /**
     * 高并发应急事件_高发事件等级同比变化（较去年同月）
     */
    public final static int GBFYJSJ_GBFSJDJTBBH = 91;



}
