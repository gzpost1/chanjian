package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 李波
 * @description: 大屏菜单保存
 * @date 2021/7/416:21
 */
@Data
public class MenuSortDto {
    /**
     * 菜单id
     */
    private Long id;

    /**
     * 排序
     */
    @NotNull(message = "展示序号不能为空")
    @Max(value = 999,message = "展示序号最大为999")
    @Min(value = 0,message = "展示序号最小为0")
    private Integer sortNum;
}
