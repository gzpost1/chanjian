package com.yjtech.wisdom.tourism.command.entity.travelcomplaint;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintCreateVO;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintUpdateVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.common.enums.TravelComplaintStatusEnum;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.MyBaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 旅游投诉(TbTravelComplaint)实体类
 *
 * @author horadirm
 * @since 2021-07-21 09:03:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tb_travel_complaint")
public class TravelComplaintEntity extends MyBaseEntity {

    private static final long serialVersionUID = 9190959961354322730L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
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
    private String contactMobile;

    /**
     * 投诉原因
     */
    private String complaintReason;

    /**
     * 图片
     */
    @TableField(value = "image", typeHandler = JsonTypeHandler.class)
    private List<String> image;

    /**
     * 状态（0-待指派 1-待处理 2-已处理）
     */
    private Byte status;

    /**
     * 配备状态（0-启用 1-禁用）
     */
    private Byte equipStatus;

    /**
     * 处理人id
     */
    private Long acceptUserId;

    /**
     * 处理人
     */
    @JsonIgnore
    @TableField(exist = false)
    private String acceptUser;

    /**
     * 处理单位
     */
    private String acceptOrganization;

    /**
     * 处理时间
     */
    private LocalDate acceptTime;

    /**
     * 投诉时间
     */
    private LocalDate complaintTime;

    /**
     * 处理结果
     */
    private String acceptResult;

    /**
     * 指定处理人id
     */
    @TableField(value = "assign_accept_user_id", typeHandler = JsonTypeHandler.class)
    private List<String> assignAcceptUserId;


    /**
     * 构建新增
     * @param vo
     */
    public void build(TravelComplaintCreateVO vo){
        setId(IdWorker.getInstance().nextId());

        setComplaintType(vo.getComplaintType());
        setComplaintObject(vo.getComplaintObject());
        setObjectId(vo.getObjectId());
        setLocation(vo.getLocation());
        setLongitude(vo.getLongitude());
        setLatitude(vo.getLatitude());
        setContactUser(vo.getContactUser());
        setContactMobile(vo.getContactMobile());
        setComplaintReason(vo.getComplaintReason());
        setImage(vo.getImage());

        //默认启用
        setEquipStatus(EntityConstants.ENABLED);
        //默认待指派
        setStatus(TravelComplaintStatusEnum.TRAVEL_COMPLAINT_STATUS_NO_ASSIGN.getValue());

        //投诉时间
        setComplaintTime(vo.getComplaintTime());
    }

    /**
     * 构建状态
     * @param vo
     */
    public void build(StatusParam vo){
        //更新状态
        if(null != vo.getStatus()){
            setStatus(vo.getStatus());
        }
        //更新配备状态
        if(null != vo.getEquipStatus()){
            setEquipStatus(vo.getEquipStatus());
        }
        //更新指定处理人id列表
        if(null != vo.getAssignAcceptUserId() && !vo.getAssignAcceptUserId().isEmpty()){
            setAssignAcceptUserId(vo.getAssignAcceptUserId().stream().map(String::valueOf).collect(Collectors.toList()));
        }
    }

    /**
     * 构建编辑
     * @param vo
     */
    public void build(TravelComplaintUpdateVO vo){
        if(Objects.nonNull(vo.getComplaintType())){
            setComplaintType(vo.getComplaintType());
        }
        if(StringUtils.isNotBlank(vo.getComplaintObject())){
            setComplaintObject(vo.getComplaintObject());
        }
        if(Objects.nonNull(vo.getObjectId())){
            setObjectId(vo.getObjectId());
        }
        if(StringUtils.isNotBlank(vo.getLocation())){
            setLocation(vo.getLocation());
        }
        if(Objects.nonNull(vo.getLongitude())){
            setLongitude(vo.getLongitude());
        }
        if(Objects.nonNull(vo.getLatitude())){
            setLatitude(vo.getLatitude());
        }
        if(StringUtils.isNotBlank(vo.getContactUser())){
            setContactUser(vo.getContactUser());
        }
        if(StringUtils.isNotBlank(vo.getContactMobile())){
            setContactMobile(vo.getContactMobile());
        }
        if(StringUtils.isNotBlank(vo.getComplaintReason())){
            setComplaintReason(vo.getComplaintReason());
        }
        if(Objects.nonNull(vo.getImage()) && !vo.getImage().isEmpty()){
            setImage(vo.getImage());
        }
        if(Objects.nonNull(vo.getComplaintTime())){
            setComplaintTime(vo.getComplaintTime());
        }
    }




}
