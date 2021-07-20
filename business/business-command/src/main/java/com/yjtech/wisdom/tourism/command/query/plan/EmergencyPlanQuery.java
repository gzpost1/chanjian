package com.yjtech.wisdom.tourism.command.query.plan;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Getter
@Setter
public class EmergencyPlanQuery extends PageQuery {
    /**
    * 事件名称
    */
    private String name;

    /**
    * 类型
    */
    private Long type;


}
