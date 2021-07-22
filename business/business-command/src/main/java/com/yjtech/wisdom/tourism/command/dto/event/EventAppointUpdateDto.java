package com.yjtech.wisdom.tourism.command.dto.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
/**
*
* @author wuyongchong
* @since 2021-07-21
*/
@Getter
@Setter
public class EventAppointUpdateDto extends EventAppointCreateDto{
    /**
    * id
    */
    @NotNull
    private Long id;


}
