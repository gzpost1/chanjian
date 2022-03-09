package com.yjtech.wisdom.tourism.portal.controller.bigscreen;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinaunicom.yunjingtech.sms.bean.SmsQuery;
import com.chinaunicom.yunjingtech.sms.exception.SmsException;
import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.PhoneCodeEnum;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.dto.sms.PhoneCodeParam;
import com.yjtech.wisdom.tourism.dto.sms.SmsSendVo;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.framework.web.service.TokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.LoginUser;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginBody;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.infrastructure.utils.IpUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.TokenUtils;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 认证模块
 */
@Slf4j
@RestController
@RequestMapping("/screen/auth")
public class BigScreenLoginController {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private SmsService smsService;
    @Autowired
    private TbRegisterInfoService companyInfoService;
    @Autowired
    private ScreenTokenService tokenService;

    /**
     * 大屏用户登录
     * @param loginBody
     * @return
     */
    @PostMapping("login")
    public JsonResult<String> login(@RequestBody @Validated ScreenLoginBody loginBody) {
        //校验手机验证码
        String funcName =
                appConfig.getVersion() + "_" + PhoneCodeEnum.SYS_APP_LOGIN.getType();
        String phoneCode = loginBody.getPhoneCode();
        String phone = loginBody.getPhone();
        String loginPwd = loginBody.getPassword();
        TbRegisterInfoEntity companyInfo = companyInfoService.queryByPhone(phone);
        AssertUtil.isFalse(Objects.isNull(companyInfo), "该公司不存在");
        AssertUtil.isFalse(Objects.equals(companyInfo.getStatus(), EntityConstants.DISABLED), "该公司状态不正常");
        if (Objects.nonNull(phoneCode)) {
            AssertUtil.isTrue(smsService
                            .validPhoneCode(phone, funcName, phoneCode),
                    "短信验证码输入有误");
        }else if(Objects.nonNull(loginPwd)){
            AssertUtil.isFalse(!Objects.equals(EncryptTypeHandler.AES.encrypt(loginPwd),companyInfo.getPwd()),"输入密码不正确");
        }else{
            return JsonResult.error("请输入手机验证码或者登录密码");
        }
        ScreenLoginUser loginUser = new ScreenLoginUser();
        BeanUtils.copyProperties(companyInfo,loginUser);
        return JsonResult.success(tokenService.createToken(loginUser));
    }

    /**
     * 退出登录
     * @param
     * @return
     */
    @PostMapping("loginout")
    public JsonResult loginout() {
        //获取当前用户信息
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Assert.notNull(loginUser, ErrorCode.NO_PERMISSION.getMessage());
        //登出
        tokenService.delLoginUser(loginUser.getToken());
        return JsonResult.success();
    }
    /**
     * 查询登陆用户信息
     * @param
     * @return
     */
    @PostMapping("queryLoginInfo")
    public JsonResult queryLoginInfo() {
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return JsonResult.success(loginUser);
    }


}
