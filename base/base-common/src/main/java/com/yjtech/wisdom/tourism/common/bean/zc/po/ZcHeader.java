package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中测请求头部
 *
 * @Date 2020/11/20 17:26
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcHeader implements Serializable {

    private static final long serialVersionUID = 3091881107543594500L;

    /**
     * accessToken
     */
    private String accessToken;

}
