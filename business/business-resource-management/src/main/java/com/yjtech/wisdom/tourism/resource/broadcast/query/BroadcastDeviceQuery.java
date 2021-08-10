package com.yjtech.wisdom.tourism.resource.broadcast.query;

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
public class BroadcastDeviceQuery extends PageQuery {
    /**
     * 名称
     */
    private String name;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;
}
