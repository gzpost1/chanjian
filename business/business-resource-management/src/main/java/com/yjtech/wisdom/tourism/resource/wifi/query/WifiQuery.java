package com.yjtech.wisdom.tourism.resource.wifi.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author zc
 * @since 2021-02-22
 */
@Getter
@Setter
public class WifiQuery extends PageQuery {
    /**
    * 名称
    */
    private String name;
}
