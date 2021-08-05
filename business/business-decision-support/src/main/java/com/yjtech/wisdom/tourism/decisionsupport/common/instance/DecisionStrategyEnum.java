package com.yjtech.wisdom.tourism.decisionsupport.common.instance;


import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.SpringBeanUtils;
import com.yjtech.wisdom.tourism.decisionsupport.common.impl.Demo;
import lombok.extern.slf4j.Slf4j;

/**
 * 策略枚举-单例实现
 *
 * @author renguangqian
 * @date 2021/8/5 10:50
 */
@Slf4j
public enum DecisionStrategyEnum {

    /**
     * 测试
     */
    doSome(Demo.class),

    ;

    private Class<?> clazz;

    DecisionStrategyEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取Spring bean实例
     *
     * @return
     */
    public Object getInstance(){
        Object bean;
        try {
            bean = SpringBeanUtils.getBean(this.clazz);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(this.clazz.getName() + "不存在实例，需要使用注解@Component交给Spring IOC容器管理");
        }
        return bean;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }}
