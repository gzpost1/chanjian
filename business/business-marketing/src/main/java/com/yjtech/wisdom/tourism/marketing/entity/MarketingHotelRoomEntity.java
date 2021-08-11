package com.yjtech.wisdom.tourism.marketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaHotelRoomPO;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * 酒店房型
 *
 * @Author horadirm
 * @Date 2021/8/10 11:40
 */
@Data
@TableName(value = "tb_marketing_hotel_room")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MarketingHotelRoomEntity extends BaseEntity {

    private static final long serialVersionUID = -8037208439585138556L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 第三方主键id
     */
    @TableField(value = "tp_id")
    private String tpId;

    /**
     * 酒店id
     */
    @TableField(value = "hotel_id")
    private String hotelId;

    /**
     * 房型名称
     */
    @TableField(value = "room_name")
    private String roomName;

    /**
     * 房型
     */
    @TableField(value = "room_type")
    private String roomType;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 价格日期
     */
    @TableField(value = "price_time")
    private LocalDate priceTime;

    /**
     * 床型
     */
    @TableField(value = "bed_type")
    private String bedType;

    /**
     * 面积
     */
    @TableField(value = "area")
    private String area;

    /**
     * 构建
     * @param po
     */
    public void build(ZcOtaHotelRoomPO po){
        setId(IdWorker.getId());
        setCreateTime(new Date());
        setDeleted((byte)0);

        setTpId(po.getId());
        setHotelId(po.getHotelId());
        setRoomName(po.getRoomName());
        setRoomType(po.getRoomType());
        setPrice(po.getPrice());
        setPriceTime(po.getPriceTime());
        setBedType(po.getBedType());
        setArea(po.getArea());
    }


}
