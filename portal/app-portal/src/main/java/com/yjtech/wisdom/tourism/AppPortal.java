package com.yjtech.wisdom.tourism;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuhong
 **/
@SpringBootApplication(exclude = { DruidDataSourceAutoConfigure.class })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class AppPortal {
    public static void main(String[] args) {
        SpringApplication.run(AppPortal.class, args);
    }
}
