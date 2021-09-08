package com.yjtech.wisdom.tourism.resource.lecture.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 场展演讲类型的值_通过字典管理配置
     */
    private String lectureValue;

    /**
     * 数据字典对应的值
     */
    private String dictName;

    /**
     * 关联场馆ID
     */
    private String venueId;

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
     * 其他图片Url
     */
    private List<String> otherPicUrl;

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

    /**
     * 图标地址
     */
    @TableField(exist = false)
    private String iconUrl;
}
