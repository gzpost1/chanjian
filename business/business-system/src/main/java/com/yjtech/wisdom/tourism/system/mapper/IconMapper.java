package com.yjtech.wisdom.tourism.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.system.domain.Icon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liuhong
 * @date 2021-07-05 9:11
 */
public interface IconMapper extends MyBaseMapper<Icon> {
    public IPage<Icon> queryForPage(IPage<Icon> page,
                                    @Param("spotQuery") String spotQuery,
                                    @Param("imgQuery") String imgQuery,
                                    @Param("type") String type);

    public Icon queryForDetail(@Param("id") Long id,
                               @Param("spotQuery") String spotQuery,
                               @Param("imgQuery") String imgQuery);

    public Icon queryIconByType(@Param("type") String type);

    public List<Icon> querMenuIconList();

}
