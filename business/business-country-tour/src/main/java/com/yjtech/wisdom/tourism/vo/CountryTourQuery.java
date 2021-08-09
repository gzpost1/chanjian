package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * @author songjun
 * @since 2021/7/20 12:00
 */
@Data
public class CountryTourQuery extends PageQuery {
    /**
     * 乡村游名称
     */
    private String name;
    /**
     * 类型 包括：乡村田园、森林休闲、草原休闲、渔猎渔家。可通过字段管理配置。
     */
    private Integer type;
    /**
     * 启停用状态
     */
    private Byte status;
}
