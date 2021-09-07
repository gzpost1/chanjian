package com.yjtech.wisdom.tourism.resource.venue.service;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author xulei
 * @create 2020-03-18 11:34
 *
 * 通用的资源文件读取工厂类
 */
public class CommPropertyResourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        String resourceName = Optional.ofNullable(name).orElse(resource.getResource().getFilename());
        if (resourceName.endsWith(".yml") || resourceName.endsWith(".yaml")) {
            List<PropertySource<?>> yamlSources = new YamlPropertySourceLoader().load(resourceName, resource.getResource());
            return yamlSources.get(0);
        } else {
            return new DefaultPropertySourceFactory().createPropertySource(name, resource);
        }
    }
}
