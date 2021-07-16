package com.yjtech.wisdom.tourism.mybatis.base;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-09-25 11:21
 */
public abstract class BaseMybatisServiceImpl<M extends BaseMybatisMapper<T>,T> extends ServiceImpl  implements BaseService<T>{
    @Autowired
    protected M baseMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<T> page(PageQuery param) {
        Page<T> page = new Page<T>(param.getPageNo(), param.getPageSize());
        List<OrderItem> orderItems = new LinkedList<>();
        if(param.getAscs()!=null && param.getAscs().length>0){
            Arrays.asList(param.getAscs()).forEach(bean->{
                orderItems.add(OrderItem.asc(bean));
            });
        }
        if(param.getDescs()!=null&& param.getDescs().length>0){
            Arrays.asList(param.getDescs()).forEach(bean->{
                orderItems.add(OrderItem.desc(bean));
            });
        }
        page.setOrders(orderItems);
        T entity = BeanMapper.copyBean(param, getTClass());
        page.setRecords(baseMapper.list(page, entity));
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> list(T params) {
        return baseMapper.list(null,params);
    }


    public Class<T> getTClass() {
        return (Class<T>)
                ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public T getById(Serializable id) {
        return (T) super.getById(id);
    }
}
