package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中测酒店房型 PO
 *
 * @Author horadirm
 * @Date 2020/11/26 9:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcOtaHotelRoomPO implements Serializable {

    private static final long serialVersionUID = 6829528545837266485L;

    /**
     * 第三方主键id
     */
    private String id;

    /**
     * 酒店id
     */
    private String hotelId;

    /**
     * 房型
     */
    private String homeType;

    /**
     * 床型
     */
    private String bedType;

    /**
     * 面积
     */
    private String area;

    /**
     * 创建时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String createAt;

    /**
     * 更新时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String updateAt;

}
