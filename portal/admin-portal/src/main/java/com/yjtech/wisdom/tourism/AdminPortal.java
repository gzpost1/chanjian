package com.yjtech.wisdom.tourism;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 * @author liuhong
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AdminPortal {
    public static void main(String[] args) {
        SpringApplication.run(AdminPortal.class, args);
    }
}
