package com.yjtech.wisdom.tourism.message.sms.service;

import com.chinaunicom.yunjingtech.sms.bean.SmsQuery;
import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信服务
 * @author xulei
 * @create 2021-03-05 10:42
 */
@Service
@Slf4j
public class SmsUseService {

    @Value("${sms.sdk.tourTemplateId}")
    private String tourTemplateId;

    @Value("${sms.sdk.emergencyTemplateId}")
    private String emergencyTemplateId;

    @Autowired
    private SmsService smsService;

    /**
     * 发送短信
     *
     * @param telephone
     * @param eventType
     * @param body 发送的内容-与配置模板匹配
     */
    public  void smsSend(String telephone, Integer eventType, List<String> body){
        String templateId = 0 == eventType ? tourTemplateId : emergencyTemplateId;
        SmsQuery smsQuery = new SmsQuery();
        try {
            smsQuery.setTemplateId(Integer.parseInt(templateId));
            smsQuery.setPhones(Lists.newArrayList(telephone));
            smsQuery.setParams(body);
            smsService.sendSms(smsQuery);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "发送短信失败");
        }
    }
}
