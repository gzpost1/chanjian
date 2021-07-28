package com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 一码游 访问统计 BO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:35
 */
@Data
public class OneTravelVisitStatisticsBO implements Serializable {

     private static final long serialVersionUID = 5054947769241665433L;

     /**
      * 使用总人数
      */
     private BigDecimal userTotal;

     /**
      * 今日活跃用户
      */
     private BigDecimal todayActiveUser;

     /**
      * 昨日活跃用户
      */
     private BigDecimal yesterdayActiveUser;

     /**
      * 总访问次数
      */
     private BigDecimal visitTotal;

}
