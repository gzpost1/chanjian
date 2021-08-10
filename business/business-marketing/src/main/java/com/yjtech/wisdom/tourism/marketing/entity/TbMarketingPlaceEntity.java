package com.yjtech.wisdom.tourism.marketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaBasePlacePO;
import com.yjtech.wisdom.tourism.common.enums.DataSourceTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

/**
 * 营销推广 场所信息
 *
 * @Author horadirm
 * @Date 2020/11/20 15:06
 */
@Data
@TableName(value = "tb_marketing_place")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbMarketingPlaceEntity extends AreaBaseEntity {

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
     * 场所名称
     */
    @TableField(value = "place_name")
    private String placeName;

    /**
     * 场所地址
     */
    @TableField(value = "place_address")
    private String placeAddress;

    /**
     * 场所等级
     * <p>
     * 一.景区：0无 12345分别表示几A级景区
     * 二.酒店：酒店星级
     */
    @TableField(value = "place_level")
    private Byte placeLevel;

    /**
     * 场所类型(0-酒店，1-民宿，2-景点，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    @TableField(value = "place_type")
    private Integer placeType;

    /**
     * 场所类型名称
     */
    @TableField(value = "place_type_name")
    private String placeTypeName;

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
     * 经度
     */
    @TableField(value = "longitude")
    private String longitude;

    /**
     * 纬度
     */
    @TableField(value = "latitude")
    private String latitude;

    /**
     * 构建场所信息
     *
     * @param tPo
     * @param placeTypeName
     * @param dataSourceTypeEnum
     * @param <T>
     */
    public <T extends ZcOtaBasePlacePO> void build(T tPo, String placeTypeName, DataSourceTypeEnum dataSourceTypeEnum) {
        setId(IdWorker.getId());
        setTpId(tPo.getId());
        setPlaceAddress(tPo.getAddress());
        setPlaceName(tPo.getName());
        setPlaceLevel(tPo.getLevel());
        setPlaceType(Objects.isNull(dataSourceTypeEnum) ? null : dataSourceTypeEnum.getValue());
        setPlaceTypeName(StringUtils.isBlank(placeTypeName) ? dataSourceTypeEnum.getDescribe() : placeTypeName);
        setDataCreateTime(StringUtils.isNotBlank(tPo.getCreateAt()) ? DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, tPo.getCreateAt()) : null);
        setDataUpdateTime(StringUtils.isNotBlank(tPo.getUpdateAt()) ? DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, tPo.getUpdateAt()) : null);

//        setAreaCode(StringUtils.isBlank(tPo.getAreaCode()) ? Constants.BusinessConstants.AREA_HEISHUI_CODE : tPo.getAreaCode());
    }


}
