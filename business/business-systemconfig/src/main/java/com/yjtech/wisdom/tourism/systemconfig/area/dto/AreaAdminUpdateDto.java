package com.yjtech.wisdom.tourism.systemconfig.area.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
/**
*
* @author wuyongchong
* @since 2021-07-02
*/
@Getter
@Setter
public class AreaAdminUpdateDto extends AreaAdminDto {
    /**
    * 区域编码，主键
    */
    @NotBlank(message =  "code主键不能为null")
    @Length(max = 12,message = "区域编码，主键长度必须小于12位")
    private String code;


}
