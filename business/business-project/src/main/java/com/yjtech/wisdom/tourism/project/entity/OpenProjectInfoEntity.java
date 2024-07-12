package com.yjtech.wisdom.tourism.project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
import com.yjtech.wisdom.tourism.project.vo.ResourcesVo;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_project_info")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OpenProjectInfoEntity extends Model<OpenProjectInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码，主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
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
    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

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
    @TableField(fill = FieldFill.INSERT)
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


    @TableField(exist = false)
    private List<String> labelNames;

    @TableField(exist = false)
    private List<ResourcesVo> resourcesVos;

    public static final String TABLE_NAME = "tb_project_info";


    public static final String ID = "id";

    public static final String PROJECT_NAME = "project_name";

    public static final String CONSTRUCTION_REQUIREMENT = "construction_requirement";

    public static final String COOPERATION_METHOD = "cooperation_method";

    public static final String PROJECT_SITE_SCALE = "project_site_scale";

    public static final String PROJECT_PLAN_FOOTPRINT = "project_plan_footprint";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER = "update_user";

    public static final String DELETED = "deleted";

    public static final String PROJECT_BUILD_AREA = "project_build_area";

    public static final String CONSTRUCTION_CONDITION = "construction_condition";

    public static final String INDUSTRIAL_CONDITION = "industrial_condition";

    public static final String MARKET_OUTLOOK_FORECAST = "market_outlook_forecast";

    public static final String PROJECT_INVESTMENT_CONTENT = "project_investment_content";

    public static final String INVESTMENT_TOTAL = "investment_total";

    public static final String FUNDING_AMOUNT = "funding_amount";

    public static final String PRIVATE_CAPITAL = "private_capital";

    public static final String PAYBACK_PERIOD = "payback_period";

    public static final String COMPANY_NAME = "company_name";

    public static final String COMPANY_ADDRESS = "company_address";

    public static final String PROJECT_CONCAT = "project_concat";

    public static final String PROJECT_PHONE = "project_phone";

    public static final String POST_CODE = "post_code";

    public static final String CONTACT_LANDLINE = "contact_landline";

    public static final String CONTACT_EMAIL = "contact_email";

    public static final String CONTACT_FAX = "contact_fax";

    public static final String CONCAT_QQ = "concat_qq";

    public static final String SERVICE_UNIT_NAME = "service_unit_name";

    public static final String PROJECT_SERVICE_CONCAT = "project_service_concat";

    public static final String PROJECT_SERVICE_PHONE = "project_service_phone";

    public static final String PROJECT_SERVICE_LANDLINE = "project_service_landline";

    public static final String SUPPORT_DESC = "support_desc";

    public static final String STATUS = "status";

    public static final String COMPANY_ID = "company_id";

    public static final String VIEW_NUM = "view_num";

    public static final String AREA_CODE = "area_code";

    public static final String AREA_NAME = "area_name";

    public static final String ADDRESS = "address";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String IS_TOP = "is_top";

    public static final String PROJECT_MAP = "project_map";

    public static final String VIEW_NUM_FLAG = "view_num_flag";

    public static final String COLLECT_NUM_FLAG = "collect_num_flag";

    public static final String SORT_NUM = "sort_num";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
