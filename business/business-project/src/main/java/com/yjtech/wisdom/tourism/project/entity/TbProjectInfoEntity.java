package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 项目信息表
 */
@Data
@TableName(value = "tb_project_info")
public class TbProjectInfoEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @JsonSerialize(using = LongTypeJsonSerializer.class)
    private Long id;

    /**
     * 项目名称
     */
    @NotBlank(message = "资源名称不能为空")
    @Length(max = 30, message = "资源名称最长30位")
    @TableField(value = "project_name")
    private String projectName;

    /**
     * 建设要求
     */
    @NotBlank(message = "建设要求不能为空")
    @Length(max = 30, message = "建设要求最长30位")
    @TableField(value = "construction_requirement")
    private String constructionRequirement;

    /**
     * 合作方式
     */
    @NotBlank(message = "合作方式不能为空")
    @Length(max = 30, message = "合作方式最长30位")
    @TableField(value = "cooperation_method")
    private String cooperationMethod;

    /**
     * 项目选址以及规模
     */
    @NotBlank(message = "项目选址以及规模不能为空")
    @Length(max = 99, message = "项目选址以及规模最长99位")
    @TableField(value = "project_site_scale")
    private String projectSiteScale;

    /**
     * 项目规划占地面
     */
    @NotBlank(message = "项目规划占地面不能为空")
    @Length(max = 15, message = "项目规划占地面最长15位")
    @TableField(value = "project_plan_footprint")
    private String projectPlanFootprint;

    /**
     * 项目可建设用地面积
     */
    @NotBlank(message = "项目可建设用地面积不能为空")
    @Length(max = 15, message = "项目可建设用地面积最长15位")
    @TableField(value = "project_build_area")
    private String projectBuildArea;

    /**
     * 建设条件
     */
    @NotBlank(message = "建设条件不能为空")
    @Length(max = 999, message = "建设条件最长999位")
    @TableField(value = "construction_condition")
    private String constructionCondition;

    /**
     * 产业条件
     */
    @NotBlank(message = "产业条件不能为空")
    @Length(max = 999, message = "产业条件最长999位")
    @TableField(value = "industrial_condition")
    private String industrialCondition;

    /**
     * 市场前景预测
     */
    @NotBlank(message = "市场前景预测不能为空")
    @Length(max = 999, message = "市场前景预测最长999位")
    @TableField(value = "market_outlook_forecast")
    private String marketOutlookForecast;

    /**
     * 项目招商内容
     */
    @NotBlank(message = "项目招商内容不能为空")
    @Length(max = 999, message = "项目招商内容最长999位")
    @TableField(value = "project_investment_content")
    private String projectInvestmentContent;

    /**
     * 总投资额
     */
    @NotBlank(message = "总投资额不能为空")
    @TableField(value = "investment_total")
    private String investmentTotal;

    /**
     * 引资金额
     */
    @NotBlank(message = "引资金额不能为空")
    @TableField(value = "funding_amount")
    private String fundingAmount;

    /**
     * 自有资金
     */
    @NotBlank(message = "自有资金不能为空")
    @TableField(value = "private_capital")
    private String privateCapital;

    /**
     * 投资回收期
     */
    @NotBlank(message = "投资回收期不能为空")
    @TableField(value = "payback_period")
    private String paybackPeriod;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空")
    @Length(max = 30, message = "企业名称最长30位")
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 企业地址
     */
    @NotBlank(message = "企业地址不能为空")
    @Length(max = 99, message = "企业地址最长99位")
    @TableField(value = "company_address")
    private String companyAddress;

    /**
     * 联系人（项目业主单位）
     */
    @NotBlank(message = "联系人不能为空")
    @Length(max = 6, message = "联系人最长6位")
    @TableField(value = "project_concat")
    private String projectConcat;

    /**
     * 联系电话（项目业主单位）
     */
    @NotBlank(message = "联系电话不能为空")
    @Length(max = 13, message = "联系电话最长13位")
    @TableField(value = "project_phone")
    private String projectPhone;

    /**
     * 邮编
     */
    @NotBlank(message = "邮编不能为空")
    @Length(max = 6, message = "邮编最长6位")
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 联系座机（项目业主单位）
     */
    @NotBlank(message = "联系座机不能为空")
    @Length(max = 13, message = "联系座机最长13位")
    @TableField(value = "contact_landline")
    private String contactLandline;

    /**
     * 联系邮箱
     */
    @Length(max = 30, message = "联系邮箱最长30位")
    @TableField(value = "contact_email")
    private String contactEmail;

    /**
     * 联系传真
     */
    @Length(max = 13, message = "联系传真最长13位")
    @TableField(value = "contact_fax")
    private String contactFax;

    /**
     * 联系QQ
     */
    @Length(max = 18, message = "联系QQ最长18位")
    @TableField(value = "concat_qq")
    private String concatQq;

    /**
     * 服务单位名称
     */
    @NotBlank(message = "服务单位名称不能为空")
    @Length(max = 30, message = "服务单位名称最长30位")
    @TableField(value = "service_unit_name")
    private String serviceUnitName;

    /**
     * 联系人（项目服务单位）
     */
    @NotBlank(message = "联系人（项目服务单位）不能为空")
    @Length(max = 6, message = "联系人（项目服务单位）最长6位")
    @TableField(value = "project_service_concat")
    private String projectServiceConcat;

    /**
     * 联系电话（项目服务单位）
     */
    @NotBlank(message = "联系电话（项目服务单位）不能为空")
    @Length(max = 11, message = "联系电话（项目服务单位）最长11位")
    @TableField(value = "project_service_phone")
    private String projectServicePhone;

    /**
     * 联系座机（项目服务单位）
     */
    @NotBlank(message = "联系座机（项目服务单位）不能为空")
    @Length(max = 13, message = "联系座机（项目服务单位）最长13位")
    @TableField(value = "project_service_landline")
    private String projectServiceLandline;

    /**
     * 优惠政策及扶持条件描述
     */
    @NotBlank(message = "优惠政策及扶持条件描述不能为空")
    @Length(max = 999, message = "优惠政策及扶持条件描述最长13位")
    @TableField(value = "support_desc")
    private String supportDesc;

    /**
     * 状态 0待审核 1审核中 2已发布 3不予发布 4下架
     */
    @TableField(value = "`status`")
    private Byte status;

    /**
     * 企业id
     */
    @TableField(value = "company_id")
    private String companyId;

    /**
     * 资源
     */
    @TableField(exist = false)
    private List<TbProjectResourceEntity> resource;

    /**
     * 浏览次数
     */
    private Integer viewNum = 0;
}