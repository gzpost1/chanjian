package com.yjtech.wisdom.tourism.common.bean.zlmedia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * zlmedia 任务基础信息
 *
 * @date 2021/9/18 15:50
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZlmediaTaskBaseInfo implements Serializable {

    private static final long serialVersionUID = 1218660389628173821L;

    /**
     * 唯一标识
     */
    private String identifier;

    /**
     * 类型
     */
    private String type;

    /**
     * 流地址
     */
    private String streamUrl;


    public ZlmediaTaskBaseInfo(String identifier) {
        this.identifier = identifier;
    }
}
