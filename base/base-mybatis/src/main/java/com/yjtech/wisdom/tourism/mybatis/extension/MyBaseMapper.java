package com.yjtech.wisdom.tourism.mybatis.extension;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuyongchong
 * @date 2020/1/2
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 以下定义的 4个 method 其中 3 个是内置的选装件
     */
    int insertBatchSomeColumn(List<T> entityList);

    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);

    int deleteByIdWithFill(T entity);

    /**
     * 批量插入, 根据唯一键是否唯一选择插入, 若唯一键重复则忽略后插入的数据
     */
    int insertBatchIgnoreInto(List<T> entityList);

    /**
     * 批量插入, 根据唯一键是否唯一选择插入, 若唯一键重复则覆盖之前的数据
     */
    int replaceBatchInto(List<T> entityList);

}
