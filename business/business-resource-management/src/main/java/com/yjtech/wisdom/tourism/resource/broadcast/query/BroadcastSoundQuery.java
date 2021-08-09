package com.yjtech.wisdom.tourism.resource.broadcast.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;


/**
 * 音响信息查询
 * @author zc
 */
@Data
public class BroadcastSoundQuery extends PageQuery {

    /**话筒名称*/
    private String name;

    /**分组id*/
    private String groupId;
}
