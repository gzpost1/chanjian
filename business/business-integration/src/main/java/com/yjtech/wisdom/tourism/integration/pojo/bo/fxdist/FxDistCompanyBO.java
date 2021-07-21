package com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist;

import lombok.Data;

import java.io.Serializable;


/**
 * 珊瑚礁 商户
 *
 * @Date 2021/5/24 19:22
 * @Author horadirm
 */
@Data
public class FxDistCompanyBO implements Serializable {

    private static final long serialVersionUID = 8070740384685744406L;

    /**
     * 企业名
     */
    private String companyName;

    /**
     * 区域
     */
    private String areaName;

    /**
     * 联系人
     */
    private String contact_name;

    /**
     * 联系人电话
     */
    private String contactPhone;

    /**
     *  地址
     */
    private String address;

    /**
     * 简介
     */
    private String introduction;

    /**
     *企业类型
     */
    private String companyType;

}
