package com.yjtech.wisdom.tourism.resource.broadcast.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zc
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastGroupSaveDto {

    @NotBlank(message = "分组名称不能为空")
    private String name;

    @NotEmpty(message = "终端不能为空")
    private List<Long> broadcastIds;
}
