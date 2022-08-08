package com.yjtech.wisdom.tourism.portal.controller.weather;

import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.EncryptUtil;
import com.yjtech.wisdom.tourism.weather.service.WeatherService;
import com.yjtech.wisdom.tourism.weather.vo.WeatherInfoVO;
import com.yjtech.wisdom.tourism.weather.web.WeatherQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * 天气
 *
 * @author xulei
 * @create 2020-01-15 11:02
 */

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


    /**
     * 根据经纬度获得天气数据
     */
    @PostMapping("info")
    @IgnoreAuth
    public JsonResult<WeatherInfoVO> queryWeatherByAreaCode(@RequestBody @Valid WeatherQuery query) throws Exception {
        StringBuffer location = new StringBuffer();
        location.append(query.getLongitude()).append(",").append(query.getLatitude());
        Map<String, String> map = Maps.newHashMap();
        map.put("location", location.toString());
        String code = EncryptUtil.makeMD5(query.getLatitude() + query.getLongitude());
        return JsonResult.success(weatherService.queryWeatherInfo(map, code));
    }
}
