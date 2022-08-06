package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 收藏类型
 *
 * @date 2022/7/7 15:04
 * @author horadirm
 */
public enum FavouriteTypeEnum {

    /**
     * 收藏
     */
    FAVOURITE_TYPE_COLLECT((byte)1, "收藏"),

    /**
     * 点赞
     */
    FAVOURITE_TYPE_LIKE((byte)2, "点赞"),

    ;

    @Getter
    private Byte type;
    @Getter
    private String describe;

    FavouriteTypeEnum(Byte type, String describe) {
        this.type = type;
        this.describe = describe;
    }

}
