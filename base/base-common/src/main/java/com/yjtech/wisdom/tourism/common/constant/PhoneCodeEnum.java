package com.yjtech.wisdom.tourism.common.constant;

public enum PhoneCodeEnum implements EnumInterface {
    SYS_UPDATE_PASSWORD("sys_update_password", 524892),
    SYS_APP_LOGIN("sys_app_login", 524888),
    SYS_LOTTERY_ACTIVITY("sys_lottery_activity", 778154),
    HAX_GET_LIST("sys_hax_list_get", 1190953);

    private String type;
    private Integer templateId;

    private PhoneCodeEnum(String type, Integer templateId) {
        this.type = type;
        this.templateId = templateId;
    }

    public String getType() {
        return type;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    @Override
    public Object getValue() {
        return this.type;
    }

}
