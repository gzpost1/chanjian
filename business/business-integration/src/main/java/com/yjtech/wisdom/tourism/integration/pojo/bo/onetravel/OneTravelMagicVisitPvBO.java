package com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 一码游 补充数据 BO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:39
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneTravelMagicVisitPvBO implements Serializable {

    private static final long serialVersionUID = -6951805719091454038L;

    /**
     * id
     */
    private Long id;

    /**
     * 访问人数，每小时一次
     */
    private Long visitPersons;

    /**
     * 访问次数，每小时一次
     */
    private Long visitTimes;

    /**
     * 每小时增加的虚假数据
     */
    private Long magicData;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 日
     */
    private String day;

    /**
     * 时
     */
    private String hour;

}
