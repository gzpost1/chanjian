package com.yjtech.wisdom.tourism.resource.turnstile.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@Getter
@Setter
public class TurnstileQuery extends PageQuery {
    /**
    * 监测点名称
    */
    private String name;

    /**
    * 状态(0:禁用,1:启用)
    */
    private String status;



}
