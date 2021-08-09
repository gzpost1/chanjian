package com.yjtech.wisdom.tourism.resource.broadcast.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;


/**
 * @author zc
 */
@Data
public class BroadcastMediaLibQuery extends PageQuery {

    /**名称*/
    private String name;
}
