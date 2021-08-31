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
public class BasePercentVO extends BaseVO{

    /**
     * 占比
     */
    private Double rate;


    public BasePercentVO(String name, String value, Double rate) {
        super(name, value);
        this.rate = rate;
    }
}
