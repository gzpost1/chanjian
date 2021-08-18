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
     * 评价主体类型(0-民宿，1-景点，2-酒店，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    private Byte dataType;

    /**
     * 评价主体类型描述
     */
    private String dataTypeDesc;

    /**
     * 评价内容
     */
    private String evaluateContent;

    /**
     * 评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date evaluateTime;

    /**
     * 评价类型(0-好评，1-中评，2-差评)
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

    /**
     * 评论用户
     */
    private String evaluateUser;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 配备状态（0-禁用 1-启用）
     */
    private Byte equipStatus;

    /**
     * 配备状态描述
     */
    private String equipStatusDesc;


}
