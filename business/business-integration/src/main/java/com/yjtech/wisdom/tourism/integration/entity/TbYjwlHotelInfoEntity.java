package com.yjtech.wisdom.tourism.integration.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.validator.BeanValidationGroup;
/**
 * 酒店信息
 *
 * @author MJ~
 * @since 2021-05-24
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_yjwl_hotel_info")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbYjwlHotelInfoEntity extends Model<TbYjwlHotelInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    private Long id;

    /**
     * 数据来源(0:携程 1:美团)
     */
    @NotNull(message = "数据来源(0:携程 1:美团)不能为空")
    private Byte dataSource;

    /**
     * 主来源酒店ID
     */
    @NotNull(message = "主来源酒店ID不能为空")
    private Long hotelId;

    /**
     * 美团酒店ID
     */
    @NotNull(message = "美团酒店ID不能为空")
    private Long meituanHotelId;

    /**
     * 携程酒店ID
     */
    @NotNull(message = "携程酒店ID不能为空")
    private Long ctripHotelId;

    /**
     * 云景酒店ID
     */
    @NotNull(message = "云景酒店ID不能为空")
    private Long yjwlHotelId;

    /**
     * 关联标志(0待关联 1:已关联 2解除关联）
     */
    @NotNull(message = "关联标志(0待关联 1:已关联 2解除关联）不能为空")
    private Byte relateStatus;

    /**
     * 酒店名称
     */
    @NotNull(message = "酒店名称不能为空")
    private String hotelName;

    /**
     * 酒店类型
     */
    private String hotelType;

    /**
     * 酒店类型名称
     */
    private String hotelTypeName;

    /**
     * 酒店星级
     */
    private String hotelStar;

    /**
     * 酒店星级名称
     */
    private String hotelStarName;

    /**
     * 酒店评分
     */
    private Double hotelScore;

    /**
     * 酒店品牌
     */
    private Long brandId;

    /**
     * 酒店品牌名称
     */
    private String brandName;

    /**
     * 酒店集团
     */
    private Long hotelGroupId;

    /**
     * 酒店集团名称
     */
    private String hotelGroupName;

    /**
     * 商圈
     */
    private String businessName;

    /**
     * 主题标签
     */
    private String themeTag;

    /**
     * 主题标签名称
     */
    private String themeTagName;

    /**
     * 酒店图片URL
     */
    private String hotelPicUrl;

    /**
     * 区域编码
     */
    @NotNull(message = "区域编码不能为空")
    private String areaCode;

    /**
     * 区域名称
     */
    @NotNull(message = "区域名称不能为空")
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 开业日期
     */
    private String praciceDate;

    /**
     * 装修时间
     */
    private String fitmentDate;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 客房数量
     */
    private Integer roomQuantity;

    /**
     * 图文详情
     */
    private String content;

    /**
     * 是否可预订(0:否,1:是)
     */
    @NotNull(message = "是否可预订(0:否,1:是)不能为空")
    private Byte bookable;

    /**
     * 酒店营业状态：0 营业中 1 已关门 2 筹建中 3 暂停营业
     */
    @NotNull(message = "酒店营业状态：0 营业中 1 已关门 2 筹建中 3 暂停营业不能为空")
    private Byte closeStatus;

    /**
     * 酒店营业状态
     */
    private String closeStatusName;

    /**
     * 状态(0:禁用,1:正常)
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "创建时间不能为空")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 删除状态（0：未删除，1：已删除）
     */
    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;


    public static final String ID = "id";

    public static final String DATA_SOURCE = "data_source";

    public static final String HOTEL_ID = "hotel_id";

    public static final String MEITUAN_HOTEL_ID = "meituan_hotel_id";

    public static final String CTRIP_HOTEL_ID = "ctrip_hotel_id";

    public static final String YJWL_HOTEL_ID = "yjwl_hotel_id";

    public static final String RELATE_STATUS = "relate_status";

    public static final String HOTEL_NAME = "hotel_name";

    public static final String HOTEL_TYPE = "hotel_type";

    public static final String HOTEL_TYPE_NAME = "hotel_type_name";

    public static final String HOTEL_STAR = "hotel_star";

    public static final String HOTEL_STAR_NAME = "hotel_star_name";

    public static final String HOTEL_SCORE = "hotel_score";

    public static final String BRAND_ID = "brand_id";

    public static final String BRAND_NAME = "brand_name";

    public static final String HOTEL_GROUP_ID = "hotel_group_id";

    public static final String HOTEL_GROUP_NAME = "hotel_group_name";

    public static final String BUSINESS_NAME = "business_name";

    public static final String THEME_TAG = "theme_tag";

    public static final String THEME_TAG_NAME = "theme_tag_name";

    public static final String HOTEL_PIC_URL = "hotel_pic_url";

    public static final String AREA_CODE = "area_code";

    public static final String AREA_NAME = "area_name";

    public static final String ADDRESS = "address";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String PRACICE_DATE = "pracice_date";

    public static final String FITMENT_DATE = "fitment_date";

    public static final String TELEPHONE = "telephone";

    public static final String ROOM_QUANTITY = "room_quantity";

    public static final String CONTENT = "content";

    public static final String BOOKABLE = "bookable";

    public static final String CLOSE_STATUS = "close_status";

    public static final String CLOSE_STATUS_NAME = "close_status_name";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String DELETED = "deleted";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
