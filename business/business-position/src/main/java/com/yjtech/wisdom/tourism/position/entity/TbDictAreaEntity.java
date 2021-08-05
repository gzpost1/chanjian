package com.yjtech.wisdom.tourism.position.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.common.validator.BeanValidationGroup;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 区域信息表
 *
 * @author MJ~
 * @since 2020-10-27
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_dict_area")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TbDictAreaEntity extends Model<TbDictAreaEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 区域编码，主键
     */
    @TableId(value = "code", type = IdType.ID_WORKER)
    @NotNull(message = "id不能为空",groups = BeanValidationGroup.Update.class)
    @NotNull(message = "区域编码，主键不能为空")
    private String code;

    /**
     * 区域名称
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
     * 地址级别：\n0:省 1:市 2:县
     */
    private Integer level;

    /**
     * 地址
     */
    private String address;

    /**
     * 地址-带逗号的
     */
    private String addressComma;

    private BigDecimal longitude;

    private BigDecimal latitude;


    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String PROVINCE_CODE = "province_code";

    public static final String PROVINCE_NAME = "province_name";

    public static final String CITY_CODE = "city_code";

    public static final String CITY_NAME = "city_name";

    public static final String COUNTY_CODE = "county_code";

    public static final String COUNTY_NAME = "county_name";

    public static final String TOWN_CODE = "town_code";

    public static final String TOWN_NAME = "town_name";

    public static final String VILLAGE_CODE = "village_code";

    public static final String VILLAGE_NAME = "village_name";

    public static final String TYPE = "type";

    public static final String PARENT_CODE = "parent_code";

    public static final String LEVEL = "level";

    public static final String ADDRESS = "address";

    public static final String ADDRESS_COMMA = "address_comma";

    public static final String LONGITUDE = "longitude";

    public static final String LATITUDE = "latitude";

    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
