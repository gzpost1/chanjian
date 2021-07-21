package com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * tb_scenic_info
 *
 * @author xulei
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmartTravelScenicInfoBO implements Serializable {

    private static final long serialVersionUID = -4293574312334980804L;
    /**
     * 景区id
     */
    private Long scenicId;

    /**
     * 景区名称
     */
    private String scenicName;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 景区等级
     */
    private String scenicLevel;

    /**
     * 景区电话
     */
    private String telephone;

    /**
     * 开放开始时间
     */
    @JsonFormat(pattern = "HH:mm")
    private Date openingTime;

    /**
     * 结束结束时间
     */
    @JsonFormat(pattern = "HH:mm")
    private Date endingTime;

    /**
     * 交通情况
     */
    private String traffic;

    /**
     * 游览须知
     */
    private String touristInformation;

    /**
     * 绑定子商户ID
     */
    private Long subMerchantId;

    /**
     * 绑定子商户名称
     */
    private String subMerchantName;

    /**
     * 关联店铺
     */
    @TableField(typeHandler = JsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<String> relateShops;

    /**
     * 小程序id
     */
    private String appId;

    /**
     * 手绘地图ID
     */
    private Long mapId;

    /**
     * 所属地区编码
     */
    private String areaCode;

    /**
     * 所属地区名称
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;


    /**
     * 首页主图
     */
    private String homeImage;

    /**
     * 动态显示(0:否 1:是)
     */
    private Byte dynamicDisplay;

    /**
     * 封面图片路径
     */
    private String coverPath;

    /**
     * 详情图片路径
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private List<String> detailPath;

    /**
     * 景区详情
     */
    private String introduction;

    /**
     * 所属省编码
     */
    private String provinceCode;

    /**
     * 所属市编码
     */
    private String cityCode;

    /**
     * 所属区县编码
     */
    private String countyCode;

    /**
     * 所属地点区域编码
     */
    private String areaId;

    /**
     * 所属地点景区ID
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 所属地点名称
     */
    private String blongPlace;

    /**
     * 类型(01:景区,02:场馆)
     */
    private String type;

    /**
     * 地点标签
     */
    @TableField(typeHandler = JsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<String> areaTag;

    /**
     * 属性标签
     */
    @TableField(typeHandler = JsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<String> propTag;

    /**
     * 开放月份
     */
    private Integer openStartMonth;

    /**
     * 结束月份
     */
    private Integer openEndMonth;

    /**
     * 景区热度
     */
    private Integer hotRate;

    /**
     * 3d地图url
     */
    private String threeMapUrl;

    /**
     * 是否网红景点 1是 0不是
     */
    private Byte popular;

    /**
     * 简介
     */
    private String description;

    /**
     * 景区轮廓
     */
    private List<SmartTravelScenicBoundaryInfo> boundary;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Byte deleted;

    /**
     * 是否项目专属
     */
    private Byte projectOnly;

    /**
     * 贴图地图
     */
    @TableField(typeHandler = JsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<String> relateMaps;

    /**
     * 关联景区
     */
    @TableField(typeHandler = JsonTypeHandler.class, updateStrategy = FieldStrategy.IGNORED)
    private List<String> relateScenics;
    /**
     * 渠道id
     */
    private Long channelId;
}
