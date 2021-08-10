package com.yjtech.wisdom.tourism.marketing.pojo.dto.evaluate;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论排名信息
 *
 * @Date 2020/11/24 20:02
 * @Author horadirm
 */
@Data
public class EvaluateRankingInfo implements Serializable {

    private static final long serialVersionUID = -3920896810995454776L;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 评论数量
     */
    private Integer count;


}
