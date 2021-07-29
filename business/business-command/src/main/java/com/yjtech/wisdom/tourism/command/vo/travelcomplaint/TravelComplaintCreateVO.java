package com.yjtech.wisdom.tourism.command.vo.travelcomplaint;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 旅游投诉 创建VO
 *
 * @Author horadirm
 * @Date 2021/7/28 17:19
 */
@Data
public class TravelComplaintCreateVO implements Serializable {

    private static final long serialVersionUID = 5766579553051762821L;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
    @NotNull(message = "投诉类型不能为空")
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
    @NotBlank(message = "投诉对象id不能为空")
    private String complaintReason;

    /**
     * 图片
     */
    private List<String> image;

}
