package com.yjtech.wisdom.tourism.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring容器的工具
 *
 * @author renguangqian
 * @date 2021/8/5 11:39
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 获取容器
     *
     * @param applicationContext springIoc容器上下文对象
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        SpringBeanUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean (Class<T> beanClass) {
        return SpringBeanUtils.applicationContext.getBean(beanClass);
    }
}
