package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论热词信息
 *
 * @Date 2020/11/24 18:55
 * @Author horadirm
 */
@Data
public class EvaluateHotInfo implements Serializable {

    private static final long serialVersionUID = -5414201705197400066L;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 频率
     */
    private Integer frequency;
}
