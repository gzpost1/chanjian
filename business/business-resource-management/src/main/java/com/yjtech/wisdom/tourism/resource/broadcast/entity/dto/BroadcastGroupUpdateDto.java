package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * @author zc
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastGroupUpdateDto extends BroadcastGroupSaveDto{

    @NotNull(message = "分组id不能为空")
    private Long groupId;
}
