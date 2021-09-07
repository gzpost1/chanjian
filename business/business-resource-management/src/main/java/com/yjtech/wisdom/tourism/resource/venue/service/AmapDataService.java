package com.yjtech.wisdom.tourism.resource.venue.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yjtech.wisdom.tourism.resource.venue.dto.AmapPoiResponse;
import com.yjtech.wisdom.tourism.resource.venue.dto.AmapResponse;
import com.yjtech.wisdom.tourism.resource.venue.dto.Geocode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author xulei
 * @create 2020-01-20 14:21
 */
@Slf4j
@Service
public class AmapDataService {

    @Autowired
    private AmapConfig amapConfig;

    @Autowired
    private RestTemplate restTemplate;

    private final String RET_OK = "1";


    public List<Geocode> getGeocode(String address) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", amapConfig.getKey());
        map.put("address", address);
        AmapResponse amapResponse = getAmapDataUrl("geocode/geo", map);
        return amapResponse.getGeocodes();
    }

    /**
     * 获取经纬度所在的地区编码
     * @param longitude
     * @param latitude
     * @return
     */
    public String getAdCode(String longitude, String latitude) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", amapConfig.getKey());
        try {
            map.put("location", URLEncoder.encode(longitude + "," + latitude, "UTF-8" ));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("location", longitude + "," + latitude);
        AmapResponse amapResponse = getAmapDataUrl("geocode/regeo", map);
        if(amapResponse == null || !RET_OK.equals(amapResponse.getStatus())) {
            return null;
        }
        if(amapResponse.getRegeocode() == null) {
            return null;
        } else {
            if(amapResponse.getRegeocode().getAddressComponent() == null) {
                return null;
            }
            String adCode =  amapResponse.getRegeocode().getAddressComponent().getAdcode();
            if(adCode.length() > 4) {
                adCode = adCode.substring(0, 4);
            }
            return adCode;
        }
    }

    /**
     * 获取经纬度所在的地区编码-前四位
     * @param longitude
     * @param latitude
     * @return
     */
    public String getCityCodePre(BigDecimal longitude, BigDecimal latitude) {
        String longitudeStr = longitude.setScale(6, RoundingMode.FLOOR).toString();
        String latitudeStr = latitude.setScale(6, RoundingMode.FLOOR).toString();
        String cityCode = getAdCode(longitudeStr, latitudeStr);
        if(cityCode == null || cityCode.length() < 4) {
            return null;
        }
        return cityCode.substring(0, 4);
    }

    /**
     * 获取经纬度所在的地区编码-完整的
     * @param longitude
     * @param latitude
     * @return
     */
    public String getCityCodeTotal(BigDecimal longitude, BigDecimal latitude) {
        String cityCode = getCityCodePre(longitude, latitude);
        if(cityCode == null) {
            return null;
        }
        return cityCode + "00000000";
    }

    /**
     * 获取经纬度所在的地区(区县)编码-完整的
     * @param longitude
     * @param latitude
     * @return
     */
    public String getCountyCodeTotal(BigDecimal longitude, BigDecimal latitude) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", amapConfig.getKey());
        try {
            map.put("location", URLEncoder.encode(longitude + "," + latitude, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("location", longitude + "," + latitude);
        AmapResponse amapResponse = getAmapDataUrl("geocode/regeo", map);
        if (amapResponse == null || !RET_OK.equals(amapResponse.getStatus())) {
            return null;
        }
        if (amapResponse.getRegeocode() == null) {
            return null;
        } else {
            if (amapResponse.getRegeocode().getAddressComponent() == null) {
                return null;
            }
            String adCode = amapResponse.getRegeocode().getAddressComponent().getAdcode();
            if (adCode == null) {
                return null;
            }
            return adCode + "000000";
        }
    }

   /* *//**
     * 开多个线程分开获取各个市的数据
     *
     * @param consumer
     *//*
    public void getCityDate(Consumer<AmapConfig.City> consumer) {
        ExecutorService es = Executors.newFixedThreadPool(9);
        //当各个线程执行完 各自的任务  才能重试
        final CountDownLatch latchOne = new CountDownLatch(9);
        List<AmapConfig.City> citys = amapConfig.getCitys();
        citys.forEach(city -> {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        consumer.accept(city);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }finally {
                        latchOne.countDown();
                    }
                }
            });
        });
        try {
            latchOne.await();
        } catch (InterruptedException e) {

        }
        es.shutdown();
    }*/

    /**
     * 获取各个分页数据  最终汇总
     *
     * @param map
     * @param exceptionPoilist
     * @param list
     * @param function
     * @param <T>
     */
    public <T> void queryPage(Map<String, Object> map, CopyOnWriteArrayList<Map<String, Object>> exceptionPoilist,
                              List<T> list, Function<AmapPoiResponse, T> function) {
        Integer page = 1;//初始页码
        do {
            try {
                AmapResponse amapResponse = getAmapData(map);
                if (nonNull(amapResponse) && "1".equals(amapResponse.getStatus()) && amapResponse.getPois().size() > 0) {
                    int count = Integer.valueOf(amapResponse.getCount());
                    int offset = amapConfig.getOffset();
                    //获得总页数
                    page = (count - 1) / offset + 1;
                    List<AmapPoiResponse> pois = amapResponse.getPois();
                    List<T> collect = Optional.ofNullable(pois).orElse(Lists.newArrayList()).stream().map(poi -> {
                        return function.apply(poi);
                    }).collect(Collectors.toList());
                    list.addAll(collect);

                }
            } catch (Exception e) {
                //有时候 会报连接超时  所以这里将查询map存入缓存  而不是结果
                HashMap<String, Object> HashMap = new HashMap();
                HashMap.putAll(map);
                exceptionPoilist.add(HashMap);
                log.error("获取数据时发生异常", e);
            } finally {
                int currentPage = (int) map.get("page");
                map.put("page", ++currentPage);
            }
        } while ((Integer) map.get("page") <= page);
    }

    /**
     * 获得高德数据
     *
     * @param map 查询参数
     * @return
     */
    public AmapResponse getAmapData(Map<String, Object> map) {
        String join = Joiner.on("&").withKeyValueSeparator("=").join(map);
        JSONObject o = restTemplate.getForObject(amapConfig.getUrl() + "?" + join, JSONObject.class);
        log.info("result: " + JSON.toJSONString(o));
        AmapResponse amapResponse = JSON.parseObject(o.toString(), new TypeReference<AmapResponse>() {
        });
        return amapResponse;
    }

    /**
     * 获得高德数据
     *
     * @param map 查询参数
     * @return
     */
    public AmapResponse getAmapDataUrl(String url, Map<String, Object> map) {
        String join = Joiner.on("&").withKeyValueSeparator("=").join(map);
        log.info(amapConfig.getBaseUrl() + "/" + url + "?" + join);
        JSONObject o = restTemplate.getForObject(amapConfig.getBaseUrl() + "/" + url + "?" + join, JSONObject.class);
        log.info("result: " + JSON.toJSONString(o));
        AmapResponse amapResponse = JSON.parseObject(o.toString(), new TypeReference<AmapResponse>() {
        });
        return amapResponse;
    }

    /**
     * 保存异常时的key
     *
     * @param keyPrefix key前缀
     * @return
     */
    public String getExceptionMapKey(String keyPrefix) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = date.format(fmt);
        return keyPrefix + dateStr;
    }

    public Map<String, Object> getMap(String filter, String city) {
        Map<String, Object> map = new HashMap();
        map.put("key", amapConfig.getKey());
        map.put("types", filter);
        map.put("city", city);
        map.put("offset", amapConfig.getOffset());
        map.put("page", amapConfig.getPage());
        map.put("citylimit", amapConfig.getCitylimit());
        map.put("extensions", amapConfig.getExtensions());
        return map;
    }

    /**
     * 重试
     *
     * @param exceptionPoilist
     */
    public List<AmapPoiResponse> retryPoiException(CopyOnWriteArrayList<Map<String, Object>> exceptionPoilist) {

        if (isNull(exceptionPoilist) || exceptionPoilist.size() <= 0) {
            return new ArrayList<>();
        }
        List<AmapPoiResponse> retryList = new ArrayList<>();
        exceptionPoilist.forEach(map -> {

            log.info("开始重试", map);
            try {
                AmapResponse amapResponse = getAmapData(map);
                if (nonNull(amapResponse) && "1".equals(amapResponse.getStatus()) && amapResponse.getPois().size() > 0) {
                    List<AmapPoiResponse> poiResponses =
                            Optional.ofNullable(amapResponse.getPois()).orElse(Lists.newArrayList());
                    retryList.addAll(poiResponses);

                }
            } catch (Exception e) {
                log.error("重试时发生异常", e.getMessage());
            }

        });
        return retryList;
    }

    /**
     * 将 060102|060700  换成单类型  返回第一个满足条件的
     *
     * @param code
     * @return
     */
    public String reservedOnlyType(String code, Set<String> list) {
        String[] types = code.split("\\|");
        for (String type : types) {
            if (list.contains(type)) {
                return type;
            }

        }
        return  null ;
    }
    /**
     * 将 060102|060700 跟配置的code求交集
     *
     * @param code
     * @return
     */
    public Boolean intersection(String code, Set<String> set){
        String[] types = code.split("\\|");
        List<String> typeList = Arrays.asList(types);
        Set<String> setType = new HashSet<>(typeList);

        //交集
        Set<String> intersection = Sets.intersection(setType, set);
        if (intersection.size() >0){
            return  true;
        }
        return false;
    }
}
