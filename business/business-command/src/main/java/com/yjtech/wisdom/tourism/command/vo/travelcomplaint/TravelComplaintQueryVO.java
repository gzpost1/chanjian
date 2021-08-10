package com.yjtech.wisdom.tourism.command.vo.travelcomplaint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

/**
 * 旅游投诉 查询VO
 *
 * @Author horadirm
 * @Date 2021/7/21 15:18
 */
@Data
public class TravelComplaintQueryVO extends PageQuery {

    private static final long serialVersionUID = 374682261559080288L;

    /**
     * 投诉对象
     */
    private String complaintObject;

    /**
     * 投诉对象id
     */
    private Long objectId;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
    @Range(min = 0, max = 2, message = "投诉类型不合法")
    private Byte complaintType;

    /**
     * 投诉状态（0-待指派 1-待处理 2-已处理）
     */
    @Range(min = 0, max = 2, message = "投诉状态不合法")
    private Byte status;

    /**
     * 配备状态（0-启用 1-禁用）
     */
    @Range(min = 0, max = 1, message = "配备状态不合法")
    private Byte equipStatus;

    /**
     * 处理人id
     */
    private Long acceptUserId;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * 创建人id
     */
    private Long createUser;

}
