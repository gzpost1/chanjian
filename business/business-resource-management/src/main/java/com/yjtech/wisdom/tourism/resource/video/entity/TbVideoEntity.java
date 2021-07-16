package com.yjtech.wisdom.tourism.resource.video.entity;

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
import java.time.LocalDateTime;
/**
 * 
 *
 * @author MJ~
 * @since 2021-07-05
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_video")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbVideoEntity extends Model<TbVideoEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    private Long id;

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 位置
     */
    private String location;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 视频流地址
     */
    private String url;

    /**
     * 序号
     */
    private Integer sort;

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
     * 创建人id
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
     * 更新人id
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
     * 状态(0:禁用,1:正常)
     */
    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 地址
     */
    private String address;

    /**
     * 状态(0:离线,1:在线)
     */
    private Byte equipStatus;


    public static final String ID = "id";

    public static final String DEVICE_ID = "device_id";

    public static final String NAME = "name";

    public static final String LOCATION = "location";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    public static final String URL = "url";

    public static final String SORT = "sort";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_TIME = "update_time";

    public static final String UPDATE_USER = "update_user";

    public static final String DELETED = "deleted";

    public static final String STATUS = "status";

    public static final String ADDRESS = "address";

    public static final String EQUIP_STATUS = "equip_status";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
