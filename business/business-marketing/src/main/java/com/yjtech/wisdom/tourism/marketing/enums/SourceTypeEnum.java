package com.yjtech.wisdom.tourism.marketing.enums;

import lombok.Getter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-11-27 14:54
 */
@Getter
public enum SourceTypeEnum {

    WECHAT_ARTICLE("weChat_article"),
    WECHAT_READ("weChat_read"),
    MICRO_BLOG_ARTICLE("microBlog_article"),
    MICRO_BLOG_READ("microBlog_read"),
    TRAVEL_GUIDE_ARTICLE("travelGuide_article"),
    TRAVEL_GUIDE_READ("travelGuide_read"),
    EVALUATE_SCENIC_COMMENT("evaluate_scenic_comment"),
    EVALUATE_SCENIC_RATE("evaluate_scenic_rate"),
    EVALUATE_HOTEL_COMMENT("evaluate_hotel_comment"),
    EVALUATE_HOTEL_RATE("evaluate_hotel_rate"),
    BAIDU_SEARCH("baidu_search");

    private String value;

    private SourceTypeEnum(String value) {
        this.value = value;
    }

}
