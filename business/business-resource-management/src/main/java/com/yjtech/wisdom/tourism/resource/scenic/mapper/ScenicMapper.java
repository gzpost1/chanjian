package com.yjtech.wisdom.tourism.resource.scenic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import org.apache.ibatis.annotations.Param;

public interface ScenicMapper extends BaseMapper<ScenicEntity> {

    /**
     * 查询景区承载量统计
     * @return
     */
    Long queryScenicBearCapacity();

    /**
     * 根据id查询名称
     * @param id
     * @return
     */
    String queryNameById(@Param("id") Long id);
}
