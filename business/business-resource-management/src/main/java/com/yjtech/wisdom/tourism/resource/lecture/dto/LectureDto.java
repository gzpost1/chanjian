package com.yjtech.wisdom.tourism.resource.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 展演讲座
 *
 * @author renguangqian
 * @date 2021/7/22 9:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LectureDto implements Serializable {

    private static final long serialVersionUID = 4553966510255028153L;

    /**
     * id
     */
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
     * 关联场馆名称
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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 启停用状态
     */
    private Byte status;
}