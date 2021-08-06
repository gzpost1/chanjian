package com.yjtech.wisdom.tourism.hotel.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.validator.BeanValidationGroup;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.mybatis.typehandler.StringArrayTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author MJ~
 * @since 2020-08-05
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="tb_hotel_info",autoResultMap = true)
@AllArgsConstructor
@NoArgsConstructor
public class TbHotelInfoEntity extends Model<TbHotelInfoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    private Long id;

    /**
     * 酒店名称
     */
    @NotNull(message = "名称不能为空")
    @Length(max = 50,message = "名称最长为50个字符")
    private String name;

    @TableField(exist = false)
    private float avgRate;
    /**
     * 酒店等级
     */
    private String lev;

    /**
     * 酒店类别
     */
    @NotNull(message = "类别不能为空")
    private String type;

    /**
     * 酒店地址
     */
    @NotNull(message = "地址不能为空")
    private String address;

    /**
     * 应急联系人
     */
    @NotNull(message = "应急联系人不能为空")
    private String contact;

    /**
     * 应急联系人电话
     */
    @NotNull(message = "应急联系人电话不能为空")
    private String mobile;

    /**
     * 酒店电话
     */
    @NotNull(message = "酒店电话不能为空")
    private String phone;

    /**
     * 酒店图片地址
     */

    @TableField(typeHandler = StringArrayTypeHandler.class)
    @NotNull(message = "酒店图片不能为空")
    private String[] img;

    /**
     * 封面图片地址
     */
    private String coverImg;

    /**
     * 酒店简介
     */
    private String description;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 区域编码
     */
    private String areaCode;
    /**
     * 房间数量
     */
    @NotNull(message = "客房数不能为空")
    private Integer roomNum;
    /**
     * 床位数量
     */
    @NotNull(message = "床位数不能为空")
    private Integer bedNum;

    @TableField(exist = false)
    private  List<TbHotelHouseTypeEntity> houseTypeList;

    /**
     * 创建时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除, 0:否, 1:是
     */
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    @Builder.Default
    private Byte deleted=EntityConstants.NOT_DELETED;

    /**
     * 状态
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 酒店Ids
     */
    @TableField(exist = false)
    private List<Long> hotelIds;

    /**
     * 入住数
     */
    @TableField(exist = false)
    private Integer yesterdayCheckInCount;

    /**
     * 申报时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    private Date declareTime;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LEV = "lev";

    public static final String TYPE_ID = "type_id";

    public static final String ADDRESS = "address";

    public static final String CONTACT = "contact";

    public static final String MOBILE = "mobile";

    public static final String PHONE = "phone";

    public static final String IMG = "img";

    public static final String COVER_IMG = "cover_img";

    public static final String DESCRIPTION = "description";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String CREATE_TIME = "create_time";

    public static final String declare_time = "declare_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER = "create_user";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
