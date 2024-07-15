package com.yjtech.wisdom.tourism.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.Proxy;

@Component
@ConfigurationProperties(prefix = "open.api")
@Data
public class OpenApiConfig {
    private String token;
}
