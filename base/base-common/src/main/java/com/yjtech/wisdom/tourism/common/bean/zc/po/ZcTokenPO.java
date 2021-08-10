package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 中测token PO
 *
 * @Date 2020/11/20 17:54
 * @Author horadirm
 */
@Data
public class ZcTokenPO implements Serializable {

    private static final long serialVersionUID = -7039344830640066527L;

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * appKey
     */
    private String appKey;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * 有效时间
     */
    private Long expireTime;

}
