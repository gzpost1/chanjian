package com.yjtech.wisdom.tourism.resource.alarm.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
*
* @author xulei
* @since 2021-07-06
*/
@Getter
@Setter
public class AlarmPostUpdateDto extends AlarmPostCreateDto {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;




}
