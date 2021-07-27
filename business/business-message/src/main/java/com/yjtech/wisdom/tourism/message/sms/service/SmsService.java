package com.yjtech.wisdom.tourism.message.sms.service;

import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.common.utils.MD5Utils;
import com.yjtech.wisdom.tourism.message.sms.vo.SmsSendReq;
import com.yjtech.wisdom.tourism.message.sms.vo.SmsSendResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 短信服务
 * @author xulei
 * @create 2021-03-05 10:42
 */
@Service
@Slf4j
public class SmsService {

    @Value("${sms.wo.cpcode}")
    private String cpcode;

    @Value("${sms.wo.key}")
    private String key;

    @Value("${sms.wo.tourTemplateId}")
    private String tourTemplateId;

    @Value("${sms.wo.emergencyTemplateId}")
    private String emergencyTemplateId;

    @Value("${sms.wo.url}")
    private String url;

    @Value("${sms.wo.msg}")
    private String msg;

    @Autowired
    private RestTemplate restTemplate;


    public  void smsSend(String telephone, Integer eventType){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>( getParam(telephone, eventType), headers);
        ResponseEntity<SmsSendResp> exchange;
        try{
             exchange = restTemplate.exchange(url, HttpMethod.POST, request, SmsSendResp.class, "");
            log.info("短信发送返回值{}",exchange);
        }catch (Exception e){
            throw new CustomException( "发送短信失败");
        }
        if(!"0".equals(exchange.getBody().getResultcode())){
            throw new  CustomException( "发送短信失败" + exchange.getBody().getResultmsg());
        }
    }

    private SmsSendReq getParam(String telephone, Integer eventType){
        String templateId = 0 == eventType ? tourTemplateId : emergencyTemplateId;
        SmsSendReq smsSendReq = SmsSendReq.builder()
                .cpcode(cpcode)
                .msg(msg)
                .mobiles(telephone)
                .excode("")
                // 事件类型 0:旅游投诉 1:应急事件
                .templetid(templateId)
                .sign( MD5Utils.makeMD5(String.format("%s%s%s%s%s%s", cpcode, msg, telephone, "", templateId, key)))
                .build();
        log.info("短信发送入参{}", JsonUtil.writeValueAsString(smsSendReq));
        return smsSendReq;
    }
}
