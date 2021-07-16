package com.yjtech.wisdom.tourism.systemconfig.area.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author wuyongchong
 * @since 2021-07-02
 */
@Getter
@Setter
public class AreaAdminQuery extends PageQuery {

    /**
     * 行政区划
     */
    private String administration;

    /**
    * 省级名称
    */
    private String provinceName;

    /**
    * 市级名称
    */
    private String cityName;



}
