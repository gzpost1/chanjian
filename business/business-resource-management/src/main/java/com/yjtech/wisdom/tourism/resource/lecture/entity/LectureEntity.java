package com.yjtech.wisdom.tourism.resource.lecture.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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
@TableName("tb_jz_lecture_manger")
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
     * 关联场馆
     */
    private String venueId;

    /**
     * 开放日期-开始日期
     */
    private String holdStartDate;

    /**
     * 开放日期-结束日期
     */
    private String holdEndDate;

    /**
     * 联系电话，多个用“,”分割
     */
    private String phone;

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
     * 其他图片Url，多张用“,”逗号分割
     */
    private String otherPicUrl;

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
