package com.yjtech.wisdom.tourism;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 * @author liuhong
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@ComponentScan(basePackages = {"com.yjtech.wisdom.tourism.portal.controller.flyway"})
public class AdminPortal {
    public static void main(String[] args) {
        SpringApplication.run(AdminPortal.class, args);
    }
}
