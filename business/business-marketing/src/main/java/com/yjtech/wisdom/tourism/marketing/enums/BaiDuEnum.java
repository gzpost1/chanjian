package com.yjtech.wisdom.tourism.marketing.enums;

import lombok.Getter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-11-26 20:17
 */
@Getter
public enum BaiDuEnum {

    SEARCH("search");

    private String value;

    private BaiDuEnum(String value) {
        this.value = value;
    }
}
