package com.yjtech.wisdom.tourism.common.service;

import com.alibaba.fastjson.JSONObject;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcHotelRoomParam;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaPlaceParam;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcTokenParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.*;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.zc.ZCConfigKey;
import com.yjtech.wisdom.tourism.common.utils.zc.ZcRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 中测信息同步 服务实现
 *
 * @Author horadirm
 * @Date 2020/11/20 10:25
 */
@Service
public class ZcInfoSyncService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        if(redisTemplate.hasKey(CacheKeyContants.ZC_TOKEN_KEY)){
            //获取token
            ZcTokenPO zcTokenPO = JSONObject.parseObject(redisTemplate.opsForValue().get(CacheKeyContants.ZC_TOKEN_KEY).toString(), ZcTokenPO.class);
            return zcTokenPO.getAccessToken();
        }
        //向第三方刷新token
        ZcTokenPO zcTokenPO = refreshToken();

        return zcTokenPO.getAccessToken();
    }

    /**
     * 刷新token
     * @return
     */
    public ZcTokenPO refreshToken(){
        //构建业务参数
        ZcTokenParam params = new ZcTokenParam(
                redisTemplate.opsForValue().get(Constants.SYS_CONFIG_KEY + ZCConfigKey.APPID).toString(),
                redisTemplate.opsForValue().get(Constants.SYS_CONFIG_KEY + ZCConfigKey.APPSECRET).toString());

        ZcTokenPO tokenInfo = ZcRequestUtils.getToForm(jointRequestUrl(ZCConfigKey.PRE_URL, ZCConfigKey.TOKEN), null, params, ZcTokenPO.class, true);

        //缓存token
        redisTemplate.opsForValue().getAndSet(CacheKeyContants.ZC_TOKEN_KEY, JSONObject.toJSONString(tokenInfo));
        redisTemplate.expire(CacheKeyContants.ZC_TOKEN_KEY, tokenInfo.getExpireTime(), TimeUnit.SECONDS);

        return tokenInfo;
    }

    /**
     * 获取评价分页列表信息
     * @return
     */
    public List<ZcOtaEvaluatePO> getEvaluateList(ZcOtaEvaluateParam params){
        //构建头部信息
        ZcHeader header = new ZcHeader(getToken());

        List<ZcOtaEvaluatePO> evaluatePO = ZcRequestUtils.getListToForm(jointRequestUrl(ZCConfigKey.PRE_URL, ZCConfigKey.SPOT_EVALUATE), header, params, ZcOtaEvaluatePO.class, true);

        return evaluatePO;
    }

    /**
     * 获取酒店分页列表
     * @return
     */
    public List<ZcOtaHotelPO> getHotelList(ZcOtaPlaceParam params){
        //构建头部信息
        ZcHeader header = new ZcHeader(getToken());

        List<ZcOtaHotelPO> hotelList = ZcRequestUtils.getListToForm(jointRequestUrl(ZCConfigKey.PRE_URL, ZCConfigKey.HOTEL_PAGE), header, params, ZcOtaHotelPO.class, true);

        return hotelList;
    }

    /**
     * 获取酒店房型分页列表
     * @return
     */
    public List<ZcOtaHotelRoomPO> getHotelRoomList(ZcHotelRoomParam params){
        //构建头部信息
        ZcHeader header = new ZcHeader(getToken());

        List<ZcOtaHotelRoomPO> hotelList = ZcRequestUtils.getListToForm(jointRequestUrl(ZCConfigKey.PRE_URL, ZCConfigKey.HOTEL_ROOM_PAGE), header, params, ZcOtaHotelRoomPO.class, true);

        return hotelList;
    }

    /**
     * 获取景区分页列表
     * @return
     */
    public List<ZcOtaScenicAreaPO> getScenicAreaList(ZcOtaPlaceParam params){
        //构建头部信息
        ZcHeader header = new ZcHeader(getToken());

        List<ZcOtaScenicAreaPO> scenicAreaList = ZcRequestUtils.getListToForm(jointRequestUrl(ZCConfigKey.PRE_URL, ZCConfigKey.SPOT_PAGE), header, params, ZcOtaScenicAreaPO.class, true);

        return scenicAreaList;
    }


    /**
     * 获取并拼接请求url
     *
     * @param baseRedisKey 基础url缓存key
     * @param interfaceRedisKey 接口url缓存key
     * @return
     */
    public String jointRequestUrl(String baseRedisKey, String interfaceRedisKey){
        //获取基础请求url
        Object baseUrl = redisTemplate.opsForValue().get(Constants.SYS_CONFIG_KEY + baseRedisKey);

        //获取接口请求url
        Object interfaceUrl = redisTemplate.opsForValue().get(Constants.SYS_CONFIG_KEY + interfaceRedisKey);

        return String.format("%s%s", Objects.isNull(baseUrl) ? "" : baseUrl.toString(), Objects.isNull(interfaceUrl) ? "" : interfaceUrl);
    }

}
