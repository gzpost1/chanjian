package com.yjtech.wisdom.tourism.framework.config;

import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.framework.interceptor.RepeatSubmitInterceptor;
import com.yjtech.wisdom.tourism.framework.interceptor.impl.OpenApiInterceptor;
import com.yjtech.wisdom.tourism.framework.interceptor.impl.ScreenLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 *
 * @author liuhong
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;
    @Autowired
    private ScreenLoginInterceptor screenLoginInterceptor;
    @Autowired
    private OpenApiInterceptor openApiInterceptor;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry
                .addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + AppConfig.getProfile() + "/");

        /** swagger配置 */
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
        registry.addInterceptor(screenLoginInterceptor).addPathPatterns("/screen/**");
        registry.addInterceptor(openApiInterceptor).addPathPatterns("/open-api/**");
//                .excludePathPatterns("/screen/auth/login")
//                .excludePathPatterns("/screen/sms/sendScreenLoginPhoneCode")
//                .excludePathPatterns("/screen/register/investor")
//                .excludePathPatterns("/screen/register/commercial")
//                .excludePathPatterns("/screen/register/operator");
//                .excludePathPatterns("/screen/register/queryForPageByType");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
