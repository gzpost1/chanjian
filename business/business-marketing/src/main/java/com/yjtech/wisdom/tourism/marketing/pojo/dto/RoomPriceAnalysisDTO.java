package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 房型价格趋势信息
 *
 * @Date 2021/8/11 15:50
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomPriceAnalysisDTO extends BaseSaleTrendVO {

    private static final long serialVersionUID = 6444937329136634613L;

    /**
     * 整体价格
     */
    private BigDecimal entiretyPrice;

    /**
     * 大床房价格
     */
    private BigDecimal bigBedPrice;

    /**
     * 双床房价格
     */
    private BigDecimal doubleBedPrice;

    /**
     * 亲子/家庭房价格
     */
    private BigDecimal familyRoomPrice;

    /**
     * 套房价格
     */
    private BigDecimal suiteRoomPrice;


    public RoomPriceAnalysisDTO(String time, BigDecimal entiretyPrice, BigDecimal bigBedPrice, BigDecimal doubleBedPrice, BigDecimal familyRoomPrice, BigDecimal suiteRoomPrice) {
        super(time);
        this.entiretyPrice = entiretyPrice;
        this.bigBedPrice = bigBedPrice;
        this.doubleBedPrice = doubleBedPrice;
        this.familyRoomPrice = familyRoomPrice;
        this.suiteRoomPrice = suiteRoomPrice;
    }

}
