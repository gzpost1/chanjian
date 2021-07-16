package com.yjtech.wisdom.tourism.weather.service;

import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.utils.HttpRequest;
import com.yjtech.wisdom.tourism.common.utils.HttpResponse;
import com.yjtech.wisdom.tourism.common.utils.HttpSession;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.weather.config.WeatherConfigKey;
import com.yjtech.wisdom.tourism.weather.vo.WeatherDaily;
import com.yjtech.wisdom.tourism.weather.vo.WeatherHourly;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import com.yjtech.wisdom.tourism.weather.vo.WeatherWarning;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuyongchong on 2019/11/18.
 */
@Service
public class WeatherService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysConfigService configService;

    public WeatherInfoVO queryWeatherInfo(Map<String, String> map, Object code) throws Exception {
        map.put("key", configService.selectConfigByKey(WeatherConfigKey.KEY));
        WeatherInfoVO weatherInfo = new WeatherInfoVO();

        String jsonFromCache = redisCache.getCacheObject(CacheKeyContants.WEATHER_CACHE_KEY_PREFIX + code);
        if (StringUtils.isNotBlank(jsonFromCache)) {
            weatherInfo = JsonUtil.readValue(jsonFromCache, WeatherInfoVO.class);
        } else {
//            //天气数据
//            WeatherDaily weatherBody = sendHttpRequest(configService.selectConfigByKey(WeatherConfigKey.URL), map, WeatherDaily.class);
//            if (CollectionUtils.isNotEmpty(weatherBody.getDaily())) {
//                weatherInfo.setToday(weatherBody.getDaily().get(0));
//            }
//
//            //空气质量
//            WeatherAirNow weatherAirNow = sendHttpRequest(configService.selectConfigByKey(WeatherConfigKey.AIR_URL), map, WeatherAirNow.class);
//            weatherInfo.setAir(weatherAirNow.getNow());

            // 24小时天气预报
            WeatherHourly weatherHourly = sendHttpRequest(configService.selectConfigByKey(WeatherConfigKey.HOURLY_URL), map, WeatherHourly.class);
            weatherInfo.setHourly(weatherHourly.getHourly());

            // 一周天气预报
            WeatherDaily weekly = sendHttpRequest(configService.selectConfigByKey(WeatherConfigKey.WEEKLY_URL), map, WeatherDaily.class);
            weatherInfo.setWeekly(weekly.getDaily());

            // 灾害预警
            WeatherWarning warning = sendHttpRequest(configService.selectConfigByKey(WeatherConfigKey.WARNING_URL), map, WeatherWarning.class);
            weatherInfo.setWarnings(warning.getWarning());

            redisCache.setCacheObject(CacheKeyContants.WEATHER_CACHE_KEY_PREFIX + code,
                    JsonUtil.writeValueAsString(weatherInfo), Integer.parseInt(configService.selectConfigByKey(WeatherConfigKey.EXPIRE_MINUTES)),
                    TimeUnit.MINUTES);
        }
        return weatherInfo;
    }

    private <T> T sendHttpRequest(String target, Map<String, String> reqParams, Class<T> respClz) throws Exception {
        HttpSession httpSession = new HttpSession();
        HttpRequest request = new HttpRequest(target);
        request.addParams(reqParams);
        request.addExtHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        HttpResponse response = httpSession.sendGetRequest(request);
        return JsonUtil.readValue(response.getBody(), respClz);
    }
}
