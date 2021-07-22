package com.yjtech.wisdom.tourism.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.Proxy;

/**
 * TPNS 配置属性
 *
 * @Author horadirm
 * @Date 2021/3/22 15:07
 */
@Component
@ConfigurationProperties(prefix = "tpns.config")
@Data
public class TpnsConfig {

    /**
     * androidAppId
     */
    private String androidAppId;

    /**
     * androidSecretKey
     */
    private String androidSecretKey;

    /**
     * iosAppId
     */
    private String iosAppId;

    /**
     * iosSecretKey
     */
    private String iosSecretKey;

    /**
     * proxy
     */
    private Proxy proxy;

    /**
     * connectTimeOut
     */
    private Integer connectTimeOut;

    /**
     * readTimeOut
     */
    private Integer readTimeOut;

    /**
     * domainUrl
     */
    private String domainUrl;

}
