package com.yjtech.wisdom.tourism.system.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.system.domain.UserArea;

/**
 *
 * @author songjun
 * @since 2023/9/7
 */
public interface UserAreaMapper extends BaseMapper<UserArea> {
    int deleteByUserId(@Param("userId")Long userId);

    int insertList(@Param("list")List<UserArea> list);

}