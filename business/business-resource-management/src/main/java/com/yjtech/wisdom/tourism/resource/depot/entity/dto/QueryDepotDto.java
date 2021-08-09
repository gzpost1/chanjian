package com.yjtech.wisdom.tourism.resource.depot.entity.dto;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author 陈忠凌
 * @Date 2020-09-15 16:30
 */
@Getter
@Setter
public class QueryDepotDto extends PageQuery {

    /**广播名称*/
    private String name;
    /**启停状态*/
    private Integer status;
}
