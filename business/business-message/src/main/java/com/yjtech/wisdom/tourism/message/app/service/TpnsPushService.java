package com.yjtech.wisdom.tourism.message.app.service;

import com.tencent.xinge.XingeAppSimple;
import com.tencent.xinge.push.app.PushAppResponse;
import com.yjtech.wisdom.tourism.common.config.TpnsConfig;
import com.yjtech.wisdom.tourism.message.app.bo.TpnsPushBO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * TPNS 消息推送
 *
 * @Author horadirm
 * @Date 2021/3/22 15:03
 */
@Slf4j
@Service
public class TpnsPushService {

    @Autowired
    private TpnsConfig tpnsConfig;


    /**
     * 推送tokenList Android设备
     * @param pushBO
     */
    public PushAppResponse pushTokenListAndroid(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getAndroidAppId(), tpnsConfig.getAndroidSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushTokenListAndroid(pushBO.getTitle(), pushBO.getContent(), pushBO.getTokenList());

        return handleResult(result);
    }

    /**
     * 推送accountList Android设备
     * @param pushBO
     */
    public PushAppResponse pushAccoutListAndroid(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getAndroidAppId(), tpnsConfig.getAndroidSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushAccountListAndroid(pushBO.getTitle(), pushBO.getContent(), pushBO.getAccountList());

        return handleResult(result);
    }

    /**
     * 全推送 Android设备
     * @param pushBO
     */
    public PushAppResponse pushAllAndroid(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getAndroidAppId(), tpnsConfig.getAndroidSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushAllAndroid(pushBO.getTitle(), pushBO.getContent());

        return handleResult(result);
    }

    /**
     * 推送tokenList IOS设备
     * @param pushBO
     */
    public PushAppResponse pushTokenListIos(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getIosAppId(), tpnsConfig.getIosSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushTokenListIos(pushBO.getTitle(), pushBO.getContent(), pushBO.getTokenList(), pushBO.getEnvironment());

        return handleResult(result);
    }

    /**
     * 推送accountList IOS设备
     * @param pushBO
     */
    public PushAppResponse pushAccountListIos(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getIosAppId(), tpnsConfig.getIosSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushAccountListIos(pushBO.getTitle(), pushBO.getContent(), pushBO.getTokenList(), pushBO.getEnvironment());

        return handleResult(result);
    }

    /**
     * 全推送 IOS设备
     * @param pushBO
     */
    public PushAppResponse pushAllIos(TpnsPushBO pushBO){
        XingeAppSimple xingeAppSimple = new XingeAppSimple(tpnsConfig.getIosAppId(), tpnsConfig.getIosSecretKey(), tpnsConfig.getDomainUrl());
        JSONObject result = xingeAppSimple.pushAllIos(pushBO.getTitle(), pushBO.getContent(), pushBO.getEnvironment());

        return handleResult(result);
    }

    /**
     * 处理推送结果
     * @param result
     * @return
     */
    private PushAppResponse handleResult(JSONObject result){
        if(Objects.isNull(result)){
            log.info("******************** 推送结果为空 ********************");
            return null;
        }
        log.info("******************** 推送结果：{} ********************", result);

        return com.alibaba.fastjson.JSONObject.parseObject(result.toString(), PushAppResponse.class);
    }


}
