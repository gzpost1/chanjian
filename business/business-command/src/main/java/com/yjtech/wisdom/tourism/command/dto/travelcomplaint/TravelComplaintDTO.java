package com.yjtech.wisdom.tourism.command.dto.travelcomplaint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 旅游投诉 DTO
 *
 * @Author horadirm
 * @Date 2021/7/21 16:40
 */
@Data
public class TravelComplaintDTO implements Serializable {

    private static final long serialVersionUID = 2797671117732663308L;

    /**
     * id
     */
    private Long id;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
    private Byte complaintType;

    /**
     * 投诉类型描述
     */
    private String complaintTypeDesc;

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

    /**
     * 状态（0-待指派 1-待处理 2-已处理）
     */
    private Byte status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 配备状态（0-启用 1-禁用）
     */
    private Byte equipStatus;

    /**
     * 配备状态描述
     */
    private String equipStatusDesc;

    /**
     * 处理人id
     */
    private Long acceptUserId;

    /**
     * 处理人
     */
    private String acceptUser;

    /**
     * 处理单位
     */
    private String acceptOrganization;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate acceptTime;

    /**
     * 处理结果
     */
    private String acceptResult;

    /**
     * 指定处理人id
     */
    private List<String> assignAcceptUserId;

    /**
     * 指定处理人
     */
    private List<String> assignAcceptUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;


    /**
     * 构建状态描述
     */
    public void buildDesc(){
        setComplaintTypeDesc(TravelComplaintTypeEnum.getDescByValue(getComplaintType()));
        setStatusDesc(TravelComplaintStatusEnum.getDescByValue(getStatus()));
        setEquipStatusDesc(Constants.STATUS_NEGATIVE.equals(getEquipStatus().intValue()) ? "启用" : "禁用");
    }

}
