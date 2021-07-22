package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * @author songjun
 * @since 2021/7/20 12:00
 */
@Data
public class CountryTourQuery extends PageQuery {
    private String name;
    private Integer type;
    private Byte status;
}
