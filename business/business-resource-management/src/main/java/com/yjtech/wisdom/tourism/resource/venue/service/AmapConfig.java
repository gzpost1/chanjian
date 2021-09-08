package com.yjtech.wisdom.tourism.resource.venue.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xulei
 * @date 2020/01/09/1214:44
 * @description:
 */
@Component
@Data
@ConfigurationProperties(prefix = "amap")
@EnableConfigurationProperties
@PropertySource(value = "classpath:amap.yml",encoding = "utf-8",factory = CommPropertyResourceFactory.class)
public class AmapConfig {

    private String provinceCode;

    private List<String> city;

    private Map<String,String> shopping;

    private String key;

    private int offset;

    private int page;

    private String extensions;

    private Boolean citylimit;

    private String url;

    private String baseUrl;

    private Map<String,String> venue;

}
