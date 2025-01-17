package com.yjtech.wisdom.tourism.bigscreen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListJsonTypeHandler;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListLongJsonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 注册信息
 *
 * @author MJ~
 * @since 2022-03-02
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_register_info")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbRegisterInfoEntity extends Model<TbRegisterInfoEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空", groups = BeanValidationGroup.Update.class)
    private Long id;

    /**
     * 公司名称
     */
    //@Length(max = 30)
    //@NotNull(message = "企业名称不能为空")
    private String companyName;

    /**
     * 企业类型
     */
    //@NotNull(message = "企业类型不能为空")
    private String companyType;
    /**
     * 企业类型名称
     */
    @TableField(exist = false)
    private String companyTypeName;

    /**
     * 公司英文名
     */
    private String companyNameEn;

    /**
     * 联系人手机号
     */
    //@NotNull(message = "手机号不能为空")
    //@Length(max = 13, message = "请输入正确手机号")
    private String phone;

    /**
     * 密码
     */
    //@NotNull(message = "密码不能为空")
    private String pwd;

    /**
     * 地址 - 删除字段
     */
    //@NotNull(message = "地址不能为空")
    private String address;

    /**
     * 联系邮箱 - 删除字段
     */
    private String email;

    /**
     * 经度
     */
    private BigDecimal longtitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 注册资本
     */
    //@NotNull(message = "注册资本不能为空")
    private BigDecimal registeredCapital;

    /**
     * 注册年份
     */
    //@NotNull(message = "注册年份不能为空")
    //@Length(max = 4, message = "请填写正确年")
    private Integer registeredYear;

    /**
     * 主营业务
     */
    //@NotNull(message = "注册年份不能为空")
    private String mainBusiness;

    /**
     * 经营范围
     */
    //@NotNull(message = "经营范围不能为空")
    private String businessScope;

    /**
     * 资质
     */
    //@NotNull(message = "资质资格不能为空")
    private String qualificationImgs;

    /**
     * 业态方向
     */
    //@NotNull(message = "业态方向不能为空", groups = {RegisterValidationGroup.commercial.class})
    private String commercialDirection;

    /**
     * 运营方向
     */
    //@NotNull(message = "运营方向不能为空", groups = {RegisterValidationGroup.operator.class})
    private String operationDirection;
    /**
     * 投资方向
     */
    //@NotNull(message = "投资方向不能为空", groups = {RegisterValidationGroup.investor.class})
    private String investmentDirection;


    /** ================》新增 Start 《================= */
    /**
     * 投资方标签
     */
    @TableField(typeHandler = ListJsonTypeHandler.class)
    private List<String> investmentLabel;
    /**
     * 运营方标签
     */
    @TableField(typeHandler = ListJsonTypeHandler.class)
    private List<String> operationLabel;
    /**
     * 业态方标签
     */
    @TableField(typeHandler = ListJsonTypeHandler.class)
    private List<String> commercialLabel;
    /**
     * 项目方标签
     */
    @TableField(typeHandler = ListJsonTypeHandler.class)
    private List<String> projectLabel;

    /**
     * 曾参与的投资项目
     */
    private String investmentRemark;
    /**
     * 曾参与的业态项目
     */
    private String commercialRemark;
    /**
     * 曾参与的运营项目
     */
    private String operationRemark;

    /**
     * 企业信息是否完善 0-未完善 1-已完善
     */
    private Integer companyInfoFinishSign;

    /**
     * 微信注册用户id,管理后台不传
     */
    private Long weChatUserId;

    /** ================》新增 End 《================= */


    /**
     * 审核状态 0.审核中 1.通过 2.驳回
     */
    private Integer auditStatus;

    /**
     * 是否是加入黑名单
     */
    private Boolean blacklist;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

    /**
     * 所在地区域编码
     */
    private String areaCode;

    /**
     * 区域名称
     */
    @TableField(exist = false)
    private String areaName;


    public static final String ID = "id";

    public static final String COMPANY_NAME = "company_name";

    public static final String COMPANY_TYPE = "company_type";

    public static final String COMPANY_NAME_EN = "company_name_en";

    public static final String PHONE = "phone";

    public static final String PWD = "pwd";

    public static final String ADDRESS = "address";

    public static final String EMAIL = "email";

    public static final String LONGTITUDE = "longtitude";

    public static final String LATITUDE = "latitude";

    public static final String LABEL = "label";

    public static final String REGISTERED_CAPITAL = "registered_capital";

    public static final String REGISTERED_YEAR = "registered_year";

    public static final String MAIN_BUSINESS = "main_business";

    public static final String BUSINESS_SCOPE = "business_scope";

    public static final String INVESTMENT_DIRECTION = "investment_direction";

    public static final String INVESTMENT_PROJECT = "investment_project";

    public static final String QUALIFICATION_IMGS = "qualification_imgs";

    public static final String COMMERCIAL_DIRECTION = "commercial_direction";

    public static final String OPERATION_DIRECTION = "operation_direction";

    public static final String TYPE = "type";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String STATUS = "status";

    public static final String DELETED = "deleted";

    public static final String AUDIT_TIME = "audit_time";
    public static final String AREA_CODE = "area_code";

    /**
     * 注册验证码
     */
    @TableField(exist = false)
    private String phoneCode;
    /**
     * 省
     */
    @TableField(exist = false)
    private String provinceName;
    /**
     * 市
     */
    @TableField(exist = false)
    private String cityName;
    /**
     * 县或区
     */
    @TableField(exist = false)
    private String countyName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    /**
     * 联系人 - 删除字段
     */
     private String contact;

    /**
     * 模糊所在地区域编码
     */
    @TableField(exist = false)
    private String likeAreaCode;

    /** 数据统计权限（0正常 1停用） */
    private String dataPermissions;



    /**
     * 废弃（由于加入多选，无法继续使用）：标签
     */
    //@NotNull(message = "标签不能为空")
    private String label;
    /**
     * 废弃（由于加入多选，无法继续使用）:曾参与投资的项目
     */
    //@NotNull(message = "曾参与的项目不能为空")
    private String investmentProject;

    /**
     * 废弃（由于加入多选，无法继续使用）:1.投资方 2.业态方 3.运营方 4.项目方
     */
    //@NotNull(message = "企业类型不能为空")
    @TableField(typeHandler = ListJsonTypeHandler.class)
    private List<String> type;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<String> roleName;

    /**
     * 类型标识
     */
    @TableField(exist = false)
    private String typeSign;

    @TableField(exist = false)
    private LocalDateTime createBeginTime;
    @TableField(exist = false)
    private LocalDateTime createEndTime;

    public void setInvestmentLabel(List<String> investmentLabel) {
        if (CollectionUtils.isEmpty(investmentLabel)) {
            investmentLabel = null;
        }
        this.investmentLabel = investmentLabel;
    }

    public void setOperationLabel(List<String> operationLabel) {
        if (CollectionUtils.isEmpty(operationLabel)) {
            operationLabel = null;
        }
        this.operationLabel = operationLabel;
    }

    public void setCommercialLabel(List<String> commercialLabel) {
        if (CollectionUtils.isEmpty(commercialLabel)) {
            commercialLabel = null;
        }
        this.commercialLabel = commercialLabel;
    }

    public void setProjectLabel(List<String> projectLabel) {
        this.projectLabel = projectLabel;
    }
}
