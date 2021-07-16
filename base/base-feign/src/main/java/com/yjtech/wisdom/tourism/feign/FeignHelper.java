package com.yjtech.wisdom.tourism.feign;

import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

/**
 * @author liuhong
 * @date 2021-04-22 14:27
 */
public final class FeignHelper {
    /**
     * 获取feign builder, 默认使用okhttp, 并内置json encoder\decoder及log配置
     */
    public static <T> Feign.Builder builder() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL);
    }
}
