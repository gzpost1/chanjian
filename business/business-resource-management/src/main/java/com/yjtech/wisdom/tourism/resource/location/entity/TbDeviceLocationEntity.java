package com.yjtech.wisdom.tourism.resource.location.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
import com.yjtech.wisdom.tourism.common.utils.spring.SpringUtils;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 定位设备
 *
 * @author MJ~
 * @since 2021-07-07
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_device_location")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbDeviceLocationEntity extends Model<TbDeviceLocationEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    private Long id;

    private String deviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所在位置
     */
    private String localtion;

    /**
     * 设备类型
     */
    private String type;

    /**
     * 经度
     */
    private BigDecimal longtitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    @TableField(exist = false)
    private String iconUrl;

    public String getIconUrl() {
        IconService iconService = SpringUtils.getBean("iconService");
        return iconService.queryIconUrl(IconSpotEnum.VIDEO, String.valueOf(equipStatus));
    }

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 状态
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 删除标志
     */
    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

    /**
     * 设备状态
     */
    private Byte equipStatus;

    /**
     * 排序
     */
    private Integer sort;


    public static final String ID = "id";

    public static final String DEVICE_ID = "device_id";

    public static final String NAME = "name";

    public static final String LOCALTION = "localtion";

    public static final String TYPE = "type";

    public static final String LONGTITUDE = "longtitude";

    public static final String LATITUDE = "latitude";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String STATUS = "status";

    public static final String DELETED = "deleted";

    public static final String EQUIP_STATUS = "equip_status";

    public static final String SORT = "sort";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
