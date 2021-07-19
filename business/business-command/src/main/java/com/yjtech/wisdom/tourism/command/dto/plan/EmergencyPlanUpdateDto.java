package com.yjtech.wisdom.tourism.command.dto.plan;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
*
* @author wuyongchong
* @since 2021-07-19
*/
@Getter
@Setter
public class EmergencyPlanUpdateDto extends EmergencyPlanCreateDto {
    /**
    * id
    */
    @NotNull(message = "id不能为空")
    private Long id;


}
