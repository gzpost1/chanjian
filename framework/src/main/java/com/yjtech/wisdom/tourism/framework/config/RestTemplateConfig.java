package com.yjtech.wisdom.tourism.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyongchong on 2019/8/20.
 */
@Configuration
public class RestTemplateConfig {

  @Bean
  public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(30000);//ms
    factory.setConnectTimeout(3000);//ms
    return factory;
  }

  @Bean
  public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
    return restTemplate;
  }
  public class WxMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public WxMappingJackson2HttpMessageConverter(){
      List<MediaType> mediaTypes = new ArrayList<>();
      mediaTypes.add(MediaType.TEXT_PLAIN);
      mediaTypes.add(MediaType.TEXT_HTML);
      setSupportedMediaTypes(mediaTypes);
    }
  }
}
