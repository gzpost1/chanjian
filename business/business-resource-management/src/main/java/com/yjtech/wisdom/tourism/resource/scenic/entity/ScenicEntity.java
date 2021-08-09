package com.yjtech.wisdom.tourism.resource.scenic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 景区
 *
 * @author zc
 * @since 2021-07-06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@TableName("tb_scenic")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScenicEntity extends BaseEntity {

    /**id*/
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**景区名称*/
    private String name;

    /**景区等级*/
    private String level;

    /**地址*/
    private String address;

    /**纬度*/
    private String latitude;

    /**经度*/
    private String longitude;

    /**今日入园数*/
    private Integer enterNum;

    /**地图缩放比例*/
    private Integer mapZoomRate;

    /** 开放日期-开始日期*/
    private String openStartDate;

    /**开放日期-结束日期*/
    private String openEndDate;

    /**开放日期-开始时间*/
    private String openStartTime;

    /**开放日期-结束时间*/
    private String openEndTime;

    /**景区承载量*/
    private Integer bearCapacity;

    /**舒适度预警比例*/
    private BigDecimal comfortWarnRate;

    /**联系电话*/
    private String phone;

    /**应急联系人*/
    private String emergencyContact;

    /**应急联系人电话*/
    private String emergencyContactPhone;

    /**封面图片Url*/
    private String frontPicUrl;

    /**
     * 其他图片Url
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> otherPicUrl;

    /**简介*/
    private String introduction;

    /**启停用状态*/
    private Byte status;
}
