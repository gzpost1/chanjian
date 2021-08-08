package com.yjtech.wisdom.tourism.resource.broadcast.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * @author liuhong
 */
@Data
public class BroadcastQuery extends PageQuery {
    private List<Long> broadcastIds;

    private String searchKey;

    public static BroadcastQuery createQuery(int page, int pageSize, String orderBy, List<Long> broadcastIds) {
        BroadcastQuery query = new BroadcastQuery();
        query.setPageNo(Long.valueOf(page));
        query.setPageSize(Long.valueOf(pageSize));
        query.setDescs(new String[]{orderBy});
        query.setBroadcastIds(broadcastIds);
        return query;
    }
}
