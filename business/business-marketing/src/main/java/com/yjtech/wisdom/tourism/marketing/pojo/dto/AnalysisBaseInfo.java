package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 趋势基础信息
 *
 * @Date 2020/11/26 9:08
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisBaseInfo<T> implements Serializable {

    private static final long serialVersionUID = -4952966693492049335L;

    /**
     * 日期名称
     */
    private String name;

    /**
     * 趋势信息
     */
    private List<T> data;

}
