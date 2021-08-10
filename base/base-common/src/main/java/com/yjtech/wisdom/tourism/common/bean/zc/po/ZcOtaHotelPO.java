package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 中测酒店信息 PO
 *
 * @Author horadirm
 * @Date 2020/11/23 15:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcOtaHotelPO extends ZcOtaBasePlacePO {

    private static final long serialVersionUID = -8235823497151019101L;

    /**
     * 酒店类型名称
     */
    private String typeName;

}
