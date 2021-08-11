package com.yjtech.wisdom.tourism.command.dto.plan;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
*
* @author wuyongchong
* @since 2021-07-19
*/
@Getter
@Setter
public class EmergencyPlanTypeUpdateDto extends EmergencyPlanTypeCreateDto {

    /**
    * id
    */
    private Long id;


}
