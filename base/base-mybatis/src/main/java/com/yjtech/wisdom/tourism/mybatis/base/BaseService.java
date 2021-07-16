package com.yjtech.wisdom.tourism.mybatis.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;

import java.util.List;

/**
 *
 * @author Mujun
 * @since 2020-09-27
 */
public interface BaseService<T> extends IService {

      List<T> list(T params);

      Page<T> page(PageQuery param) ;
}
