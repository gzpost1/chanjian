package com.yjtech.wisdom.tourism.hotel.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yjtech.wisdom.tourism.mybatis.typehandler.StringArrayTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 大屏 酒店列表 DTO
 *
 * @Author horadirm
 * @Date 2021/8/9 10:16
 */
@Data
public class HotelScreenDetailDTO implements Serializable {

    private static final long serialVersionUID = -1842239831154965253L;

    /**
     * id
     */
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
     * 酒店等级描述
     */
    private String levDesc;

    /**
     * 酒店类别
     */
    private String type;

    /**
     * 酒店类别描述
     */
    private String typeDesc;

    /**
     * 酒店地址
     */
    private String address;

    /**
     * 酒店电话
     */
    private String phone;

    /**
     * 酒店图片地址
     */
    @TableField(typeHandler = StringArrayTypeHandler.class)
    private String[] img;

    /**
     * 封面图片地址
     */
    private String coverImg;

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
     * 入住价格
     */
    private BigDecimal price;

    /**
     * 酒店点位图标
     */
    private String iconUrl;

}
