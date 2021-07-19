package com.yjtech.wisdom.tourism.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 *  基础
 * @author xulei
 * @description:
 * @date 2021/7/416:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseValueVO {
    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private List<?> value;

}
