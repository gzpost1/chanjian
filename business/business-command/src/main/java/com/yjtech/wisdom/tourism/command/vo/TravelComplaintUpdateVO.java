package com.yjtech.wisdom.tourism.command.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 旅游投诉(TbTravelComplaint)实体类
 *
 * @author horadirm
 * @since 2021-07-21 09:03:46
 */
@Data
public class TravelComplaintUpdateVO implements Serializable {

    private static final long serialVersionUID = -5010890473206696356L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
    @Range(min = 0, max = 2, message = "投诉类型不合法")
    private Byte complaintType;

    /**
     * 投诉对象
     */
    private String complaintObject;

    /**
     * 投诉对象id
     */
    private Long objectId;

    /**
     * 事发地点
     */
    private String location;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 联系人
     */
    private String contactUser;

    /**
     * 联系电话
     */
    private List<String> contactMobile;

    /**
     * 投诉原因
     */
    private String complaintReason;

    /**
     * 图片
     */
    private List<String> image;

}