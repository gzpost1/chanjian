package com.yjtech.wisdom.tourism.resource.lecture.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 展演讲座管理
 *
 * @author renguangqian
 * @date 2021/7/21 11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName(value = "tb_jz_lecture_manger", autoResultMap = true)
public class LectureEntity implements Serializable {

    private static final long serialVersionUID = 3847077007129548072L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 场展演讲座名称
     */
    private String lectureName;

    /**
     * 场展演讲类型_通过字典管理配置
     */
    private String lectureType;

    /**
     * 场展演讲类型_通过字典管理配置
     */
    private String lectureValue;

    /**
     * 关联场馆ID
     */
    private Long venueId;

    /**
     * 关联场馆名称
     */
    private String venueName;

    /**
     * 开放日期-开始日期
     */
    private String holdStartDate;

    /**
     * 开放日期-结束日期
     */
    private String holdEndDate;

    /**
     * 联系电话
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> phone;

    /**
     * 举办地点
     */
    private String holdAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 封面图片Url
     */
    private String frontPicUrl;

    /**
     * 其他图片Url
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> otherPicUrl;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;


    /**
     * 启停用状态
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 讲座类型数量
     */
    @TableField(exist = false)
    private Integer lectureTypeNumber;
}
