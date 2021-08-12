package com.yjtech.wisdom.tourism.hotel.dto;

import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author MJ~
 * @since 2020-08-05
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TbHotelInfoEntityParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 酒店名称
     */
    private String name;

    /**
     * 酒店等级
     */
    private String lev;

    /**
     * 酒店类别
     */
    private String type;

    /**
     * 酒店地址
     */
    private String address;

    /**
     * 应急联系人
     */
    private String contact;

    /**
     * 应急联系人电话
     */
    private String mobile;

    /**
     * 景区电话
     */
    private String phone;

    /**
     * 酒店简介
     */
    private String description;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 区域编码
     */
    private String areaCode;
    /**
     * 房间数量
     */
    private Integer roomNum;
    /**
     * 床位数量
     */
    private Integer bedNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 酒店Ids
     */
    private List<Long> hotelIds;

    @Builder.Default
    private Byte deleted = EntityConstants.NOT_DELETED;

    /**
     * 状态
     */
    private Byte status;


}
