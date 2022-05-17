package com.yjtech.wisdom.tourism.bigscreen.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注册信息 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2022-03-02
 */
public interface TbRegisterInfoMapper extends BaseMybatisMapper<TbRegisterInfoEntity> {

    @MapKey("id")
    Map<Long, TbRegisterInfoEntity> selectMap();
}
