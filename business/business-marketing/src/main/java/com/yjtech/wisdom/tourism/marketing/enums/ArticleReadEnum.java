package com.yjtech.wisdom.tourism.marketing.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-11-26 16:56
 */
@Getter
public enum ArticleReadEnum {

    ARTICLE("article"),
    READ("read");

    private String value;

    private ArticleReadEnum(String value) {
        this.value = value;
    }

}
