package com.yjtech.wisdom.tourism.integration.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 酒店信息
 *
 * @author MJ~
 * @since 2021-05-24
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbYjwlHotelInfoParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 数据来源(0:携程 1:美团)
     */
    private Byte dataSource;

    /**
     * 主来源酒店ID
     */
    private Long hotelId;

    /**
     * 美团酒店ID
     */
    private Long meituanHotelId;

    /**
     * 携程酒店ID
     */
    private Long ctripHotelId;

    /**
     * 云景酒店ID
     */
    private Long yjwlHotelId;

    /**
     * 关联标志(0待关联 1:已关联 2解除关联）
     */
    private Byte relateStatus;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 酒店类型
     */
    private String hotelType;

    /**
     * 酒店类型名称
     */
    private String hotelTypeName;

    /**
     * 酒店星级
     */
    private String hotelStar;

    /**
     * 酒店星级名称
     */
    private String hotelStarName;

    /**
     * 酒店评分
     */
    private Double hotelScore;

    /**
     * 酒店品牌
     */
    private Long brandId;

    /**
     * 酒店品牌名称
     */
    private String brandName;

    /**
     * 酒店集团
     */
    private Long hotelGroupId;

    /**
     * 酒店集团名称
     */
    private String hotelGroupName;

    /**
     * 商圈
     */
    private String businessName;

    /**
     * 主题标签
     */
    private String themeTag;

    /**
     * 主题标签名称
     */
    private String themeTagName;

    /**
     * 酒店图片URL
     */
    private String hotelPicUrl;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 开业日期
     */
    private String praciceDate;

    /**
     * 装修时间
     */
    private String fitmentDate;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 客房数量
     */
    private Integer roomQuantity;

    /**
     * 图文详情
     */
    private String content;

    /**
     * 是否可预订(0:否,1:是)
     */
    private Byte bookable;

    /**
     * 酒店营业状态：0 营业中 1 已关门 2 筹建中 3 暂停营业
     */
    private Byte closeStatus;

    /**
     * 酒店营业状态
     */
    private String closeStatusName;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 创建时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createUser;

    /**
     * 修改人ID
     */
    private Long updateUser;

    /**
     * 删除状态（0：未删除，1：已删除）
     */
    @TableLogic
    @Builder.Default
    private Byte deleted = 0;


}
