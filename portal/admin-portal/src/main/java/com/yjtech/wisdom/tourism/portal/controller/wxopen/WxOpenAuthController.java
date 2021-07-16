package com.yjtech.wisdom.tourism.portal.controller.wxopen;

import com.yjtech.wisdom.tourism.common.constant.WechatAuthorizeStatus;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatApp;
import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatAppService;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizationInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.auth.WxOpenAuthorizerInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenAuthorizerInfoResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenQueryAuthResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by wuyongchong on 2019/9/6.
 */
@Controller
@RequestMapping("/wxopen/auth")
public class WxOpenAuthController {

    @Autowired
    private WxOpenComponentService wxOpenComponentService;

    @Autowired
    private WechatAppService wechatAppService;

    /**
     * 一键授权,授权注册页面扫码授权,显示二维码
     */
    @RequestMapping(value = "/goto_auth_url", method = RequestMethod.GET)
    public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response)
            throws WxErrorException, IOException {

        response.addHeader("Referer",
                wxOpenComponentService.getWxOpenProperties().getAuthRedirectURI()
                        .replace("/wxopen/auth/callback", ""));

        String auth_url = wxOpenComponentService
                .getMobilePreAuthUrl(
                        wxOpenComponentService.getWxOpenProperties().getAuthRedirectURI());

        response.sendRedirect(auth_url);
    }

    /**
     * 授权成功回调链接
     * <pre>
     * 可以直接重定向自己前台的页面
     * 也可判断权限是否全部勾选完成
     * 授权完成后把授权小程序的信息保存好
     * </pre>
     */
    @GetMapping("/callback")
    public String callback(@RequestParam("auth_code") String authorizationCode,
            @RequestParam(value = "expires_in", required = false) Integer expiresIn,
            HttpServletResponse response) throws WxErrorException {

        WxOpenQueryAuthResult queryAuthResult = wxOpenComponentService
                .getQueryAuth(authorizationCode);
        //保存授权信息
        if (null != queryAuthResult && null != queryAuthResult.getAuthorizationInfo()) {
            WechatApp entity = wechatAppService
                    .getByAppId(queryAuthResult.getAuthorizationInfo().getAuthorizerAppid());
            if (null != entity) {
                WxOpenAuthorizationInfo authorizationInfo = queryAuthResult.getAuthorizationInfo();
                entity.setAuthorizerAppid(authorizationInfo.getAuthorizerAppid());
                entity.setAuthorizerAccessToken(authorizationInfo.getAuthorizerAccessToken());
                entity.setAuthorizerExpires(authorizationInfo.getExpiresIn());
                entity.setAuthorizerRefreshToken(authorizationInfo.getAuthorizerRefreshToken());
                entity.setFuncInfo(JsonUtil.writeValueAsString(authorizationInfo.getFuncInfo()));
                entity.setAuthorizeTime(new Date());
                entity.setAuthorizeStatus(Byte.valueOf(WechatAuthorizeStatus.AUTHORIZED));

                WxOpenAuthorizerInfoResult authorizerInfoResult = wxOpenComponentService
                        .getAuthorizerInfo(authorizationInfo.getAuthorizerAppid());

                if (null != authorizerInfoResult && null != authorizerInfoResult
                        .getAuthorizerInfo()) {

                    WxOpenAuthorizerInfo authorizerInfo = authorizerInfoResult.getAuthorizerInfo();

                    entity.setNickName(authorizerInfo.getNickName());
                    entity.setHeadImg(authorizerInfo.getHeadImg());
                    entity
                            .setServiceTypeInfo(
                                    Byte.valueOf(
                                            String.valueOf(authorizerInfo.getServiceTypeInfo())));
                    entity
                            .setVerifyTypeInfo(Byte.valueOf(
                                    String.valueOf(authorizerInfo.getVerifyTypeInfo())));

                    entity.setUserName(authorizerInfo.getUserName());
                    entity.setPrincipalName(authorizerInfo.getPrincipalName());
                    entity.setAlias(authorizerInfo.getAlias());
                    entity.setBusinessInfo(
                            JsonUtil.writeValueAsString(authorizerInfo.getBusinessInfo()));
                    entity.setQrcodeUrl(authorizerInfo.getQrcodeUrl());
                    entity.setSignature(authorizerInfo.getSignature());
                    entity
                            .setMiniProgramInfo(JsonUtil.writeValueAsString(
                                    authorizerInfo.getMiniProgramInfo()));
                }
                wechatAppService.updateById(entity);
            }
        }
        return "auth_success";
    }

}
