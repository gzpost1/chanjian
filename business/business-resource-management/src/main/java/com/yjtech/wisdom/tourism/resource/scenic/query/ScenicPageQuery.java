package com.yjtech.wisdom.tourism.resource.scenic.query;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

@Data
public class ScenicPageQuery extends PageQuery {

    /**景区名称*/
    private String name;

    /**起停用状态*/
    private Byte status;
}
