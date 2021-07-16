package com.yjtech.wisdom.tourism.systemconfig.area.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
/**
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
@Getter
@Setter
public class AreaAdminDto implements Serializable {

    /**
    * 行政编码
    */
    @Pattern(regexp = "^(\\d{6})(,\\d{6})*$" ,message = "行政编码不符合要求")
    @Length(max = 100,message = "行政编码长度必须小于100位")
    private String administration;

    /**
    * 简称
    */
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1}$" ,message = "简称不符合要求")
    @Length(max = 6,message = "简称长度必须小于6位")
    private String abbreviation;

    /**
    * 车牌首字母
    */
    @Pattern(regexp = "^([A-Z]{1}|[A-Z]-[A-Z]{1})(,[A-Z]{1}|,[A-Z]-[A-Z]{1})*$" ,message = "车牌首字母不符合要求")
    @Length(max = 20,message = "车牌首字母长度必须小于20位")
    private String plate;

    /**
    * 省级编码
    */
    @NotBlank(message = "省级编码不能为空")
    @Length(max = 12,message = "省级编码长度必须小于12位")
    private String provinceCode;

    /**
     * 市级编码
     */
    @Length(max = 12,message = "市级编码长度必须小于12位")
    private String cityCode;



}
