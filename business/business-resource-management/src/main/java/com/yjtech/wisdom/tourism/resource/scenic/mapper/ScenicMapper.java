package com.yjtech.wisdom.tourism.resource.scenic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;

public interface ScenicMapper extends BaseMapper<ScenicEntity> {

    /**
     * 查询景区承载量统计
     * @return
     */
    Long queryScenicBearCapacity();
}
