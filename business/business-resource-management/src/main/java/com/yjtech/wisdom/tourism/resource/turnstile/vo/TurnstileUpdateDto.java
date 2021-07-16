package com.yjtech.wisdom.tourism.resource.turnstile.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
*
* @author wuyongchong
* @since 2020-09-16
*/
@Getter
@Setter
public class TurnstileUpdateDto extends TurnstileCreateDto {
    /**
    * 设备id
    */
    @NotNull(message = "id不能为空")
    private Long id;


}
