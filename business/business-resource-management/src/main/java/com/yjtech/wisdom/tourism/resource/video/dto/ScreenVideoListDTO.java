package com.yjtech.wisdom.tourism.resource.video.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 大屏 综合总览 监控列表 DTO
 *
 * @date 2021/8/19 15:31
 * @author horadirm
 */
@Data
public class ScreenVideoListDTO implements Serializable {

    private static final long serialVersionUID = 1583685768010938168L;

    /**
     * 监控id
     */
    private Long videoId;

    /**
     * 景区id
     */
    private Long scenicId;

    /**
     * 监控名称
     */
    private Long videoName;

    /**
     * 监控流地址
     */
    private String url;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 景区应急联系人
     */
    private String emergencyContact;

    /**
     * 景区应急联系电话
     */
    private String emergencyContactPhone;


}
