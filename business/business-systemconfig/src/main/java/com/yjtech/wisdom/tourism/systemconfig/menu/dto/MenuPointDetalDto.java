package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 李波
 * @description: 菜单数据-点位详细
 * @date 2021/7/416:39
 */
@Data
public class MenuPointDetalDto implements Serializable {
    /**
     * 点位类型
     */
    @NotBlank(message = "点位不能为空")
    private String pointType;

    /**
     * 是否展示 0否 1是
     */
    @NotNull(message = "是否展示不能为空")
    private Byte isShow;
}
