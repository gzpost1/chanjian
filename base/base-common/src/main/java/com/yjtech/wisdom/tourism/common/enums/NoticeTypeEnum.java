package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 通知类型
 *
 * @date 2022/7/7 15:04
 * @author horadirm
 */
public enum NoticeTypeEnum {

    /**
     * 公告
     */
    NOTICE_TYPE_PUBLIC((byte)0, "公告"),
    /**
     * 项目申报通知
     */
    NOTICE_TYPE_PROGRAM_DECLARE((byte)1, "项目申报通知"),

    ;

    @Getter
    private Byte type;
    @Getter
    private String describe;

    NoticeTypeEnum(Byte type, String describe) {
        this.type = type;
        this.describe = describe;
    }


}
