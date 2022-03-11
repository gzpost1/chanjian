package com.yjtech.wisdom.tourism.portal.controller.bigscreen;

import com.chinaunicom.yunjingtech.sms.bean.SmsQuery;
import com.chinaunicom.yunjingtech.sms.config.SmsProperties;
import com.chinaunicom.yunjingtech.sms.exception.SmsException;
import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.constant.PhoneCodeEnum;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.dto.sms.PhoneCodeParam;
import com.yjtech.wisdom.tourism.dto.sms.SmsSendVo;
import com.yjtech.wisdom.tourism.infrastructure.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 短信接口
 */
@RestController
@RequestMapping("/screen/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @Autowired
    private AppConfig appProperties;

    @Autowired
    SmsProperties smsProperties;


    /**
     * 获取App登录短信验证码
     *
     * @param phoneCodeParam
     * @param request
     * @return
     */
    @PostMapping("/sendScreenLoginPhoneCode")
    @IgnoreAuth
    public JsonResult<SmsSendVo> sendScreenLoginPhoneCode(@RequestBody @Valid PhoneCodeParam phoneCodeParam,
                                               HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        String phone = phoneCodeParam.getPhone();
        String funcName = appProperties.getVersion() + "_" + PhoneCodeEnum.SYS_APP_LOGIN.getType();
        SmsQuery smsQuery = new SmsQuery();
        try {
            String phoneCode = smsService.makePhoneValidator(ip, phone, funcName);

            smsQuery.setTemplateId(PhoneCodeEnum.SYS_APP_LOGIN.getTemplateId());
            smsQuery.setPhones(Lists.newArrayList(phone));
            smsQuery.setParams(Lists.newArrayList(phoneCode, ((int) (smsProperties.getPhoneCodeExpire() / 60)) + ""));

            smsService.sendSms(smsQuery);

        } catch (SmsException e) {
            return JsonResult.error(e.getCode(), e.getMessage());
        } catch (Exception e) {
            return JsonResult.error( "发送手机验证码失败");
        }
        SmsSendVo smsSendVo = new SmsSendVo();
        smsSendVo.setExpireTime(smsProperties.getPhoneCodeExpire());
        return JsonResult.success(smsSendVo);
    }

}
