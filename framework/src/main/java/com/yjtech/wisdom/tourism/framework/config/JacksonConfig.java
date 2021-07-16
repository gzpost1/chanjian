package com.yjtech.wisdom.tourism.framework.config;

import com.yjtech.wisdom.tourism.common.convert.StringToDateDeserializer;
import com.yjtech.wisdom.tourism.common.serializer.LongTypeJsonSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义Jackson配置
 *
 * @author wuyongchong
 * @since 2020/3/26.
 */
@Configuration
public class JacksonConfig {

    private final ApplicationContext applicationContext;

    public JacksonConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(
            List<Jackson2ObjectMapperBuilderCustomizer> customizers) {

        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();

        mapperBuilder.applicationContext(this.applicationContext);

        Iterator customizersIterator = customizers.iterator();
        while (customizersIterator.hasNext()) {
            Jackson2ObjectMapperBuilderCustomizer customizer = (Jackson2ObjectMapperBuilderCustomizer) customizersIterator
                    .next();
            customizer.customize(mapperBuilder);
        }

        //解决Long 长度丢失问题
        mapperBuilder.serializerByType(Long.class, LongTypeJsonSerializer.instance)
                .serializerByType(Long.TYPE, LongTypeJsonSerializer.instance);
        //解决时间入参转换Date问题
        mapperBuilder.deserializerByType(Date.class, new StringToDateDeserializer());

        return mapperBuilder;
    }

}
