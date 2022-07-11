package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 通知模板类型
 *
 * @date 2022/7/7 17:31
 * @author horadirm
 */
public enum NoticeTemplateTypeEnum {

    /**
     * 审核通过
     */
    NOTICE_TEMPLATE_TYPE_AUDIT_SUCCESS((byte)1, (byte)2, "审核通过"),
    /**
     * 审核不通过
     */
    NOTICE_TEMPLATE_TYPE_AUDIT_FAIL((byte)2, (byte)3, "审核不通过"),

    ;


    @Getter
    private Byte type;
    @Getter
    private Byte auditStatus;
    @Getter
    private String describe;

    NoticeTemplateTypeEnum(Byte type, Byte auditStatus, String describe) {
        this.type = type;
        this.auditStatus = auditStatus;
        this.describe = describe;
    }

    /**
     * 根据审核类型获取消息模板类型
     *
     * @param auditStatus
     * @return
     */
    public static Byte getNoticeTemplateTypeByAuditStatus(Byte auditStatus){
        //默认为空
        if(null == auditStatus){
            return null;
        }
        for(NoticeTemplateTypeEnum item : NoticeTemplateTypeEnum.values()){
            if(item.getAuditStatus().equals(auditStatus)){
                return item.getType();
            }
        }
        //默认为空
        return null;
    }
}
