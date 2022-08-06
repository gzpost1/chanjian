package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 收藏来源类型
 *
 * @date 2022/7/7 15:04
 * @author horadirm
 */
public enum FavouriteSourceEnum {

    /**
     * 企业
     */
    FAVOURITE_SOURCE_COMPANY((byte)1, "企业"),

    /**
     * 项目
     */
    FAVOURITE_SOURCE_PROJECT((byte)2, "项目"),

    ;

    @Getter
    private Byte type;
    @Getter
    private String describe;

    FavouriteSourceEnum(Byte type, String describe) {
        this.type = type;
        this.describe = describe;
    }

}
