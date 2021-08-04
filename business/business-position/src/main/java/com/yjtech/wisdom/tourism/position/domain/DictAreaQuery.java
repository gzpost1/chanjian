package com.yjtech.wisdom.tourism.position.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DictAreaQuery {
    /**
     * 经度 在查附近时不能为空
     */
    private BigDecimal longitude;

    /**
     * 纬度 在查附近时不能为空
     */
    private BigDecimal latitude;

    /**
     * 地区编码 获取地区树必传
     */
    private String code;

    /**
     * 往下获取的层级数量 获取地区树必传
     */
    private Integer level;

    /**
     * 名称
     */
    private String name;


    /**
     * 省级编码
     */
    private String provinceCode;

    /**
     * 省级名称
     */
    private String provinceName;

    /**
     * 市级编码
     */
    private String cityCode;

    /**
     * 市级名称
     */
    private String cityName;

    /**
     * 区/县编码
     */
    private String countyCode;

    /**
     * 区/县名称
     */
    private String countyName;

    /**
     * 乡/镇编码
     */
    private String townCode;

    /**
     * 乡/镇名称
     */
    private String townName;

    /**
     * 村编码
     */
    private String villageCode;

    /**
     * 村名称
     */
    private String villageName;

    /**
     * 城乡分类代码
     */
    private String type;

    /**
     * 上级/父级区域编码
     */
    private String parentCode;

    /**
     * 区域过滤
     */
    private List<Byte> levelFilterList;
}
