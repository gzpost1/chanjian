package com.yjtech.wisdom.tourism.command.dto.travelcomplaint;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 旅游投诉 列表DTO
 *
 * @Author horadirm
 * @Date 2021/7/21 16:40
 */
@Data
public class TravelComplaintListDTO implements Serializable {

    private static final long serialVersionUID = -2151157946685493952L;

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
     * 投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate complaintTime;

}
