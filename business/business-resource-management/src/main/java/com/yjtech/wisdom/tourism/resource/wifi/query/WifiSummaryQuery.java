package com.yjtech.wisdom.tourism.resource.wifi.query;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * @author zc
 * @create 2021-07-03 14:26
 */
@Data
public class WifiSummaryQuery extends TimeBaseQuery {

    /**1、今日连接数；2、连接时长*/
    private Integer wifiType;
}
