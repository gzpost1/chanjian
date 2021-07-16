package com.yjtech.wisdom.tourism.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 *
 *  基础
 * @author zc
 * @description:
 * @date 2021/7/416:28
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO {
    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

}
