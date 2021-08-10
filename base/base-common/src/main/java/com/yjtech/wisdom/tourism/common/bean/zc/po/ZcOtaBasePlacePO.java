package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 中测 基础场所 PO
 *
 * @Author horadirm
 * @Date 2020/11/24 10:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcOtaBasePlacePO implements Serializable {

    private static final long serialVersionUID = -2484855979098287032L;

    /**
     * 第三方主键id
     */
    private String id;

    /**
     * 场所名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 等级
     *
     * 一.景区：0无 12345分别表示几A级景区
     * 二.酒店：酒店星级
     */
    private Byte level;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 创建时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String createAt;

    /**
     * 更新时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String updateAt;

    /**
     * 区域编码
     */
    private String areaCode;


}
