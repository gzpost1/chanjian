package com.yjtech.wisdom.tourism.marketing.enums;

import lombok.Getter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-11-26 19:55
 */
@Getter
public enum EvaluateTypeEnum {

    SCENIC(2),
    HOTEL(0);

    private int value;

    private EvaluateTypeEnum(int value) {
        this.value = value;
    }
}
