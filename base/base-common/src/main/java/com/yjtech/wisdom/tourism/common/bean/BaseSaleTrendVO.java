package com.yjtech.wisdom.tourism.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xulei
 * @create 2021-07-03 14:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseSaleTrendVO implements Serializable {

    private static final long serialVersionUID = 2484715694762877039L;

    /**
     * 日期
     */
    private String time;

}
