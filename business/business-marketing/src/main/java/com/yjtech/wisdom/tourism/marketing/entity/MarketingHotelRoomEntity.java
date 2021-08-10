package com.yjtech.wisdom.tourism.marketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaHotelRoomPO;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 房型
     */
    @TableField(value = "home_type")
    private String homeType;

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
     * 数据创建时间
     */
    @TableField(value = "data_create_time")
    private Date dataCreateTime;

    /**
     * 数据更新时间
     */
    @TableField(value = "data_update_time")
    private Date dataUpdateTime;

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
        setHomeType(po.getHomeType());
        setBedType(po.getBedType());
        setArea(po.getArea());
        setDataCreateTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, po.getCreateAt()));
        setDataUpdateTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, po.getUpdateAt()));
    }


}
