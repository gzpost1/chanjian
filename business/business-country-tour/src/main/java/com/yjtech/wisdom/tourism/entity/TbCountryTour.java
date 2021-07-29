package com.yjtech.wisdom.tourism.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListObjectJsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 乡村游
 *
 * @TableName tb_country_tour
 */
@TableName(value = "tb_country_tour")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TbCountryTour implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 名称
     */
    @NotNull(message = "名称不能为空")
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    private Byte type;

    /**
     * 所在位置
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 开放开始月份
     */
    private Integer openStartMonth;

    /**
     * 开放结束月份
     */
    private Integer openEndMonth;

    /**
     * 开放开始时间
     */
    private Date openStartTime;

    /**
     * 开放结束时间
     */
    private Date openEndTime;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 封面图片
     */
    private String cover;

    /**
     * 其他图片
     */
    @TableField(value = "images", typeHandler = ListObjectJsonTypeHandler.class)
    private List<String> images;

    /**
     * 简介
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

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
     * 已删除
     */
    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}