package com.yjtech.wisdom.tourism.marketing.enums;

import lombok.Getter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-11-26 16:56
 */
@Getter
public enum EvaluateEnum {

    SCENIC_COMMENT_NUM("scenic_comment"),
    SCENIC_RATE("scenic_rate"),
    HOTEL_COMMENT_NUM("hotel_comment"),
    HOTEL_RATE("hotel_rate");

    private String value;

    private EvaluateEnum(String value) {
        this.value = value;
    }

}
