package com.yjtech.wisdom.tourism.project.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

;
/**
 * 项目信息表
 *
 * @author MJ~
 * @since 2024-07-12
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OpenProjectInfoParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码，主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 建设要求
     */
    private String constructionRequirement;

    /**
     * 合作方式
     */
    private String cooperationMethod;

    /**
     * 项目选址以及规模
     */
    private String projectSiteScale;

    /**
     * 项目规划占地面
     */
    private String projectPlanFootprint;

    /**
     * 创建时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    private Long createUser;

    /**
     * 更新时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableLogic
    @Builder.Default
    private Byte deleted = 0;

    private List<String> labelNames;

    /**
     * 项目可建设用地面积
     */
    private String projectBuildArea;

    /**
     * 建设条件
     */
    private String constructionCondition;

    /**
     * 产业条件
     */
    private String industrialCondition;

    /**
     * 市场前景预测
     */
    private String marketOutlookForecast;

    /**
     * 项目招商内容
     */
    private String projectInvestmentContent;

    /**
     * 总投资额
     */
    private String investmentTotal;

    /**
     * 引资金额
     */
    private String fundingAmount;

    /**
     * 自有资金
     */
    private String privateCapital;

    /**
     * 投资回收期
     */
    private String paybackPeriod;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业地址
     */
    private String companyAddress;

    /**
     * 联系人（项目业主单位）
     */
    private String projectConcat;

    /**
     * 联系电话（项目业主单位）
     */
    private String projectPhone;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 联系座机（项目业主单位）
     */
    private String contactLandline;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 联系传真
     */
    private String contactFax;

    /**
     * 联系QQ
     */
    private String concatQq;

    /**
     * 服务单位名称
     */
    private String serviceUnitName;

    /**
     * 联系人（项目服务单位）
     */
    private String projectServiceConcat;

    /**
     * 联系电话（项目服务单位）
     */
    private String projectServicePhone;

    /**
     * 联系座机（项目服务单位）
     */
    private String projectServiceLandline;

    /**
     * 优惠政策及扶持条件描述
     */
    private String supportDesc;

    /**
     * 状态 0待审核 1审核中 2已发布 3不予发布 4下架
     */
    private Byte status;

    /**
     * 企业id
     */
    private String companyId;

    /**
     * 浏览数
     */
    private Integer viewNum;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 区域信息
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 是否置顶 0:否, 1:是（默认否）
     */
    private Byte isTop;

    /**
     * 3d地图
     */
    private String projectMap;

    /**
     * 浏览次数展示开关 0否 1是
     */
    private Byte viewNumFlag;

    /**
     * 收藏次数展示开关 0否 1是
     */
    private Byte collectNumFlag;

    /**
     * 展示序号
     */
    private Integer sortNum;


}
