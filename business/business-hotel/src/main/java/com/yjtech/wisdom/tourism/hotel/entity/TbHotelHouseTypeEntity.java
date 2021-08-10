package com.yjtech.wisdom.tourism.hotel.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * <p>
    *
    * </p>
 *
 * @author MJ~
 * @since 2020-08-13
 */
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_hotel_house_type")
public class TbHotelHouseTypeEntity extends Model<TbHotelHouseTypeEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 房型名称
     */
    private String name;

    /**
     * 房型数量
     */
    private Integer num;

    /**
     * 房型价格
     */
    private BigDecimal price;

    /**
     * 价格时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime priceTime;

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
    private Long createUser;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 是否删除
     */
    @TableLogic
    @Builder.Default
    private Byte deleted = EntityConstants.NOT_DELETED;



    private Long hotelId;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String NUM = "num";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String CREATE_USER = "create_user";

    public static final String UPDATE_USER = "update_user";

    public static final String DELETED = "deleted";

    public static final String HOTEL_ID = "hotel_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
