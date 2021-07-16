package com.yjtech.wisdom.tourism.system.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.system.domain.Platform;
import org.apache.ibatis.annotations.Delete;

/**
 * @author liuhong
 * @date 2021-07-02 15:22
 */
public interface PlatformMapper extends MyBaseMapper<Platform> {
    /**
     * 清空表数据
     */
    @Delete("truncate tb_platform")
    public void truncateTable();
}
