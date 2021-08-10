package com.yjtech.wisdom.tourism.common.bean.zc.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 中测酒店房型参数
 *
 * @Date 2020/11/25 21:33
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcHotelRoomParam extends ZcBasePage{

    private static final long serialVersionUID = -6988452547279396593L;

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

}
