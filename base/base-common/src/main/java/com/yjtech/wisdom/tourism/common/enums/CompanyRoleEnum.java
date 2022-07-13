package com.yjtech.wisdom.tourism.common.enums;

import lombok.Getter;

/**
 * 企业角色
 *
 * @date 2022/7/12 19:25
 * @author horadirm
 */
public enum CompanyRoleEnum {

    /**
     * 投资方
     */
    COMPANY_ROLE_INVESTOR("投资方", "1"),
    /**
     * 业态方
     */
    COMPANY_ROLE_BUSINESS("业态方", "2"),
    /**
     * 运营方
     */
    COMPANY_ROLE_OPERATOR("运营方", "3"),
    /**
     * 项目方
     */
    COMPANY_ROLE_PROJECT("项目方", "4"),

    ;

    @Getter
    private String describe;
    @Getter
    private String type;

    CompanyRoleEnum(String describe, String type) {
        this.describe = describe;
        this.type = type;
    }

}
