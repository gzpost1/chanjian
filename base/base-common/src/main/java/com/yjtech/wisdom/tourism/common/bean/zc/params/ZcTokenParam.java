package com.yjtech.wisdom.tourism.common.bean.zc.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中测token参数
 *
 * @Date 2020/11/20 17:49
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcTokenParam implements Serializable {

    private static final long serialVersionUID = -639606894865631699L;

    /**
     * appKey
     */
    private String appKey;

    /**
     * appSecret
     */
    private String appSecret;

}
