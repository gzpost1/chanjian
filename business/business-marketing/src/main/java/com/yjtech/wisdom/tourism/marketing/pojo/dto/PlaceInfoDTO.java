package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 场所信息 DTO
 *
 * @Date 2020/11/27 15:46
 * @Author horadirm
 */
@Data
public class PlaceInfoDTO implements Serializable {

    private static final long serialVersionUID = 273563744350197232L;

    /**
     * 第三方主键id
     */
    private String tpId;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 区域编码
     */
    private String areaCode;

}
