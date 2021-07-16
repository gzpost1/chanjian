package com.yjtech.wisdom.tourism.portal.controller.wxopen;

import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatAppService;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.*;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.WxOpenQueryAuthResult;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenKefuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wuyongchong on 2019/9/5.
 */
@RestController
@RequestMapping("/wxopen/notify")
public class WxOpenNotifyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxOpenComponentService wxOpenComponentService;

    @Autowired
    private WxOpenKefuService wxOpenKefuService;

    @Autowired
    private WechatAppService wechatAppService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 授权事件接收URL
     * <pre>
     * 用于接收取消授权通知、授权成功通知、授权更新通知，也用于接收ticket，ticket是验证平台方的重要凭据，
     * 服务方在获取component_access_token时需要提供最新推送的ticket以供验证身份合法性
     * 在第三方平台创建审核通过后，微信服务器会向其“授权事件接收URL”
     * 每隔10分钟定时推送component_verify_ticket
     * 当授权方对第三方平台进行授权、取消授权、更新授权后，微信服务器会向第三方平台方的授权事件接收URL（创建第三方平台时填写）推送相关通知。
     * </pre>
     *
     * @param requestBody requestBody
     * @param timestamp timestamp
     * @param nonce nonce
     * @param signature signature
     * @param encType encType
     * @param msgSignature msgSignature
     * @return success
     */
    @RequestMapping("/event/authorize/accept")
    public String acceptAuthorizeEvent(
            @RequestBody(required = false) String requestBody,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("signature") String signature,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature,
            HttpServletRequest request, HttpServletResponse response) throws WxErrorException {

        logger.info(
                "\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType)
                || !wxOpenComponentService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxOpenXmlMessage wxMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
                wxOpenComponentService.getWxOpenProperties(), timestamp, nonce, msgSignature);

        logger.info("\n消息解密后内容为：\n{} ", wxMessage.toString());

        if (wxMessage == null) {
            throw new NullPointerException("message is empty");
        }
        //接收授权事件
        if (StringUtils.equalsIgnoreCase(wxMessage.getInfoType(), "component_verify_ticket")) {
            wxOpenComponentService.setComponentVerifyTicket(wxMessage.getComponentVerifyTicket());
        }
        //新增、更新授权
        if (StringUtils
                .equalsAnyIgnoreCase(wxMessage.getInfoType(), "authorized", "updateauthorized")) {
            logger.info(wxMessage.getInfoType());
        }
        //取消授权
        if (StringUtils
                .equalsAnyIgnoreCase(wxMessage.getInfoType(), "unauthorized")) {
            if (StringUtils.isNotBlank(wxMessage.getAuthorizerAppid())) {
                wechatAppService.unauthorize(wxMessage.getAuthorizerAppid());
            }
        }
        return "success";
    }

    /**
     * 公众号消息与事件接收URL
     *
     * @param requestBody requestBody
     * @param appId appId
     * @param signature signature
     * @param timestamp timestamp
     * @param nonce nonce
     * @param openid openid
     * @param encType encType
     * @param msgSignature msgSignature
     */
    @RequestMapping("/{appId}/callback/accept")
    public String acceptCallback(@RequestBody(required = false) String requestBody,
                                 @PathVariable("appId") String appId,
                                 @RequestParam("signature") String signature,
                                 @RequestParam("timestamp") String timestamp,
                                 @RequestParam("nonce") String nonce,
                                 @RequestParam("openid") String openid,
                                 @RequestParam("encrypt_type") String encType,
                                 @RequestParam("msg_signature") String msgSignature,
                                 HttpServletRequest request, HttpServletResponse response) throws WxErrorException {

        logger.info(
                "\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!StringUtils.equalsIgnoreCase("aes", encType) || !wxOpenComponentService
                .checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        // aes加密的消息
        WxMaMessage wxMessage = WxOpenXmlMessage
                .fromEncryptedMaXml(requestBody, wxOpenComponentService.getWxOpenProperties(),
                        timestamp,
                        nonce, msgSignature);

        logger.info("\n消息解密后内容为：\n{} ", wxMessage.toString());

        String out = "";

        if (StringUtils.equalsAny(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {
            // 全网发布测试用例
            out = networkPublishCheck(response, wxMessage);
        } else {
            //根据不同消息类型做不同的处理回应
            if (StringUtils.equals(wxMessage.getMsgType(), "text")) {
                String authorizerAccessToken = wxOpenComponentService
                        .getAuthorizerAccessToken(appId, false);
                WxMaKefuMessage kefuMessage = WxMaKefuMessage.newTextBuilder()
                        .content(wxMessage.getContent()).toUser(wxMessage.getFromUser()).build();
                wxOpenKefuService.sendKefuMessage(kefuMessage, authorizerAccessToken);
            } else if (StringUtils.equals(wxMessage.getMsgType(), "event")) {
                //审核通过时通知
                if (StringUtils.equals(wxMessage.getEvent(), "weapp_audit_success")) {
                    wechatAppService.auditSuccess(appId);
                }
                //审核不通过时通知
                if (StringUtils.equals(wxMessage.getEvent(), "weapp_audit_fail")) {
                    wechatAppService.auditFail(appId, wxMessage.getReason());
                }
                //审核延后
                if (StringUtils.equals(wxMessage.getEvent(), "weapp_audit_delay")) {
                    wechatAppService.auditDelay(appId, wxMessage.getReason());
                }
                //进入客服会话
                if (StringUtils.equals(wxMessage.getEvent(), "user_enter_tempsession")) {
                    String authorizerAccessToken = wxOpenComponentService
                            .getAuthorizerAccessToken(appId, false);
                    WxMaKefuMessage kefuMessage = WxMaKefuMessage.newTextBuilder()
                            .content("欢迎访问").toUser(wxMessage.getFromUser()).build();
                    wxOpenKefuService.sendKefuMessage(kefuMessage, authorizerAccessToken);
                }
            }
        }
        return out;
    }

    private String networkPublishCheck(HttpServletResponse response, WxMaMessage wxMessage) {
        logger.info("全网发布测试用例....");
        String out = "";
        if (StringUtils.equals(wxMessage.getMsgType(), "text")) {
            if (StringUtils.equals(wxMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                logger.info("测试普通文本消息");
                WxMpXmlOutTextMessage outTextMessage = WxMpXmlOutMessage.TEXT()
                        .content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .build();
                out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outTextMessage,
                        wxOpenComponentService.getWxOpenProperties());

            } else if (StringUtils.startsWith(wxMessage.getContent(), "QUERY_AUTH_CODE")) {
                logger.info("测试Api文本消息");
                String authorizationCode = wxMessage.getContent().replace("QUERY_AUTH_CODE:", "");
                taskExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            WxOpenQueryAuthResult authResult = wxOpenComponentService
                                    .getQueryAuth(authorizationCode);
                            WxMaKefuMessage kefuMessage = WxMaKefuMessage.newTextBuilder()
                                    .content(authorizationCode + "_from_api")
                                    .toUser(wxMessage.getFromUser()).build();
                            wxOpenKefuService.sendKefuMessage(kefuMessage,
                                    authResult.getAuthorizationInfo().getAuthorizerAccessToken());
                        } catch (WxErrorException wxe) {
                            logger.error(wxe.getMessage(), wxe.getCause());
                        }
                    }
                });
            }
        } else if (StringUtils.equals(wxMessage.getMsgType(), "event")) {
            logger.info("测试发送事件消息");
            WxMpXmlOutTextMessage outTextMessage = WxMpXmlOutMessage.TEXT()
                    .content(wxMessage.getEvent() + "from_callback")
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
            out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outTextMessage,
                    wxOpenComponentService.getWxOpenProperties());
        }
        return out;
    }

}
