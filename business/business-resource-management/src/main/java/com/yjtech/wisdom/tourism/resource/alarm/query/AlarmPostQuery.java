package com.yjtech.wisdom.tourism.resource.alarm.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wuyongchong
 * @since 2021-07-06
 */
@Getter
@Setter
public class AlarmPostQuery extends PageQuery {
    /**
    * 设备名称
    */
    private String name;

    /**
    * 状态(0:禁用,1:正常)
    */
    private Byte status;



}
