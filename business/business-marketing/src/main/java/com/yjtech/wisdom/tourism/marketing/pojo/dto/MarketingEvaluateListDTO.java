package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 评价 分页列表 DTO
 *
 * @Author horadirm
 * @Date 2021/8/10 19:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingEvaluateListDTO implements Serializable {

    private static final long serialVersionUID = 3746811606907091391L;

    /**
     * id
     */
    private Long id;

    /**
     * 场所id
     */
    private String placeId;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 评价内容
     */
    private String evaluateContent;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date evaluateTime;

    /**
     * 评价类型(0-差评，1-中评，2-好评)
     */
    private Integer evaluateType;

    /**
     * 评价类型描述
     */
    private String evaluateTypeDesc;

    /**
     * 评分
     */
    private BigDecimal rate;

    /**
     * 评论数据来源平台名称
     */
    private String sourcePlatform;

    /**
     * 评论数据来源平台id
     */
    private Integer sourcePlatformId;


}
