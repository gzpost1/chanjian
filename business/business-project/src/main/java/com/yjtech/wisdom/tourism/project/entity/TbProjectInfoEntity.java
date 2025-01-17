package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yjtech.wisdom.tourism.common.annotation.ExcelRead;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 项目信息表
 */
@Data
@TableName(value = "tb_project_info")
public class TbProjectInfoEntity {

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
    @ExcelRead(rowNum = 4, cellNum = 2)
    @TableField(value = "project_name")
    private String projectName;

    /**
     * 建设要求
     */
    @NotBlank(message = "建设要求不能为空")
    @Length(max = 30, message = "建设要求最长30位")
    @ExcelRead(rowNum = 5, cellNum = 2)
    @TableField(value = "construction_requirement")
    private String constructionRequirement;

    /**
     * 合作方式
     */
    @NotBlank(message = "合作方式不能为空")
    @Length(max = 30, message = "合作方式最长30位")
    @ExcelRead(rowNum = 6, cellNum = 2)
    @TableField(value = "cooperation_method")
    private String cooperationMethod;

    /**
     * 项目选址以及规模
     */
//    @NotBlank(message = "项目选址以及规模不能为空")
    @Length(max = 99, message = "项目选址以及规模最长99位")
//    @ExcelRead(rowNum = 8, cellNum = 2)
    @TableField(value = "project_site_scale")
    private String projectSiteScale;

    /**
     * 项目所属区县编码
     */
    @NotBlank(message = "项目选址所属区县不能为空")
    @TableField(value = "area_code")
    @ExcelRead(rowNum = 8, cellNum = 2)
    private String areaCode;

    /**
     * 项目所属区县信息
     */
    @TableField(value = "area_name")
    private String areaName;

    /**
     * 项目选址
     */
    @NotBlank(message = "项目具体地址不能为空")
    @TableField(value = "address")
    @ExcelRead(rowNum = 9, cellNum = 2)
    private String address;

    /**
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 建设条件
     */
    @ExcelRead(rowNum = 10, cellNum = 2)
    @NotBlank(message = "建设条件不能为空")
    @Length(max = 999, message = "建设条件最长999位")
    @TableField(value = "construction_condition")
    private String constructionCondition;

    /**
     * 产业条件
     */
    @ExcelRead(rowNum = 11, cellNum = 2)
    @NotBlank(message = "产业条件不能为空")
    @Length(max = 999, message = "产业条件最长999位")
    @TableField(value = "industrial_condition")
    private String industrialCondition;

    /**
     * 市场前景预测
     */
    @ExcelRead(rowNum = 12, cellNum = 2)
    @NotBlank(message = "市场前景预测不能为空")
    @Length(max = 999, message = "市场前景预测最长999位")
    @TableField(value = "market_outlook_forecast")
    private String marketOutlookForecast;

    /**
     * 项目规划占地面
     */
    @NotBlank(message = "项目规划占地面不能为空")
    @Length(max = 15, message = "项目规划占地面最长15位")
    @ExcelRead(rowNum = 13, cellNum = 2)
    @TableField(value = "project_plan_footprint")
    private String projectPlanFootprint;

    /**
     * 项目可建设用地面积
     */
    @ExcelRead(rowNum = 14, cellNum = 2)
    @NotBlank(message = "项目可建设用地面积不能为空")
    @Length(max = 15, message = "项目可建设用地面积最长15位")
    @TableField(value = "project_build_area")
    private String projectBuildArea;

    /**
     * 3d地图
     */
    @ExcelRead(rowNum = 15, cellNum = 2)
    @Length(max = 200, message = "3d地图最长200位")
    @URL(message = "3d地图必须是一个合法的url地址")
    @TableField(value = "project_map")
    private String projectMap;

    /**
     * 项目招商内容
     */
    @ExcelRead(rowNum = 17, cellNum = 2)
    @NotBlank(message = "项目招商内容不能为空")
    @Length(max = 999, message = "项目招商内容最长999位")
    @TableField(value = "project_investment_content")
    private String projectInvestmentContent;

    /**
     * 总投资额
     */
    @ExcelRead(rowNum = 18, cellNum = 2)
    @NotBlank(message = "总投资额不能为空")
    @TableField(value = "investment_total")
    private String investmentTotal;

    /**
     * 引资金额
     */
    @ExcelRead(rowNum = 19, cellNum = 2)
    @NotBlank(message = "引资金额不能为空")
    @TableField(value = "funding_amount")
    private String fundingAmount;

    /**
     * 自有资金
     */
    @ExcelRead(rowNum = 20, cellNum = 2)
    @NotBlank(message = "自有资金不能为空")
    @TableField(value = "private_capital")
    private String privateCapital;

    /**
     * 投资回收期
     */
    @ExcelRead(rowNum = 21, cellNum = 2)
    @NotBlank(message = "投资回收期不能为空")
    @TableField(value = "payback_period")
    private String paybackPeriod;

    /**
     * 企业名称
     */
//    @ExcelRead(rowNum = 22, cellNum = 2)
//    @NotBlank(message = "企业名称不能为空")
    @Length(max = 30, message = "企业名称最长30位")
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 企业地址
     */
//    @ExcelRead(rowNum = 23, cellNum = 2)
//    @NotBlank(message = "企业地址不能为空")
    @Length(max = 99, message = "企业地址最长99位")
    @TableField(value = "company_address")
    private String companyAddress;

    /**
     * 邮编
     */
//    @ExcelRead(rowNum = 24, cellNum = 2)
    @Length(max = 6, message = "邮编最长6位")
    @TableField(value = "post_code")
    private String postCode;

    /**
     * 联系人（项目业主单位）
     */
//    @ExcelRead(rowNum = 26, cellNum = 2)
//    @NotBlank(message = "联系人不能为空")
    @Length(max = 6, message = "联系人最长6位")
    @TableField(value = "project_concat")
    private String projectConcat;

    /**
     * 联系电话（项目业主单位）
     */
//    @ExcelRead(rowNum = 27, cellNum = 2)
//    @NotBlank(message = "联系电话不能为空")
    @Length(max = 13, message = "联系电话最长13位")
    @TableField(value = "project_phone")
    private String projectPhone;

    /**
     * 联系座机（项目业主单位）
     */
//    @ExcelRead(rowNum = 28, cellNum = 2)
    @Length(max = 13, message = "联系座机最长13位")
    @TableField(value = "contact_landline")
    private String contactLandline;

    /**
     * 联系传真
     */
//    @ExcelRead(rowNum = 29, cellNum = 2)
    @Length(max = 13, message = "联系传真最长13位")
    @TableField(value = "contact_fax")
    private String contactFax;

    /**
     * 联系QQ
     */
//    @ExcelRead(rowNum = 30, cellNum = 2)
    @TableField(value = "concat_qq")
    private String concatQq;

    /**
     * 联系邮箱
     */
//    @ExcelRead(rowNum = 31, cellNum = 2)
    @Length(max = 30, message = "联系邮箱最长30位")
    @Email(message = "联系邮箱格式不正确")
    @TableField(value = "contact_email")
    private String contactEmail;

    /**
     * 服务单位名称
     */
    @ExcelRead(rowNum = 23, cellNum = 2)
    @Length(max = 30, message = "服务单位名称最长30位")
    @TableField(value = "service_unit_name")
    private String serviceUnitName;

    /**
     * 联系人（项目服务单位）
     */
    @ExcelRead(rowNum = 24, cellNum = 2)
    @Length(max = 6, message = "联系人（项目服务单位）最长6位")
    @TableField(value = "project_service_concat")
    private String projectServiceConcat;

    /**
     * 联系座机（项目服务单位）
     */
    @ExcelRead(rowNum = 25, cellNum = 2)
    @TableField(value = "project_service_landline")
    private String projectServiceLandline;

    /**
     * 联系电话（项目服务单位）
     */
    @ExcelRead(rowNum = 26, cellNum = 2)
    @TableField(value = "project_service_phone")
    private String projectServicePhone;

    /**
     * 优惠政策及扶持条件描述
     */
    @ExcelRead(rowNum = 28, cellNum = 1)
    @Length(max = 999, message = "优惠政策及扶持条件描述最长999位")
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

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @JsonIgnore
    private Byte deleted;

    /**
     * 已选中标签id列表
     */
    @TableField(exist = false)
    private List<Long> pitchOnLabelIdList;

    /**
     * 是否置顶 0:否, 1:是（关闭否）
     */
    private Byte isTop;

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
    /**
     * 审核状态
     */
    @TableField(exist = false)
    private Integer auditStatus;
    /**
     * 审核时间
     */
    @TableField(exist = false)
    private Date auditTime;
    /**
     * 展示序号
     */
    @TableField(exist = false)
    private List<Long> nextAuditUserIds;

    public String getStatusStr() {
        if (auditStatus == null) {
            return "待审核";
        }
        if (status == 4) {
            return "已下架";
        }
        switch (auditStatus) {
            case 0:
                return "审核中";
            case 1:
                return "已发布";
            case 2:
                return "驳回";
        }
        return "状态错误";
    }
}