package com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 一码游 市级访问统计 BO
 *
 * @Author horadirm
 * @Date 2021/5/24 19:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OneTravelAreaVisitStatisticsBO implements Serializable {

     private static final long serialVersionUID = 1988684783677042296L;

     /**
      * 区域编码
      */
     private String code;

     /**
      * 区域名称
      */
     private String name;

     /**
      * 访问次数
      */
     private Long value;

     /**
      * 经度
      */
     private BigDecimal longitude;

     /**
      * 纬度
      */
     private BigDecimal latitude;
}
