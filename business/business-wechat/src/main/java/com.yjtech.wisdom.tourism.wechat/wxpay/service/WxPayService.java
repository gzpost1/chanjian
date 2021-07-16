package com.yjtech.wisdom.tourism.wechat.wxpay.service;

import com.yjtech.wisdom.tourism.wechat.wxpay.WxPayConstants.SignType;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.order.WxPayOrderResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.request.WxPayOrderCloseRequest;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.request.WxPayOrderQueryRequest;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.request.WxPayRefundQueryRequest;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.request.WxPayRefundRequest;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.WxPayOrderCloseResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.WxPayOrderQueryResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.WxPayRefundQueryResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.WxPayRefundResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.yjtech.wisdom.tourism.wechat.wxpay.config.WxPayConfig;
import com.yjtech.wisdom.tourism.wechat.wxpay.exception.WxPayException;
import com.yjtech.wisdom.tourism.wechat.wxpay.util.SignUtils;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.SSLContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyongchong on 2019/9/16.
 */
public class WxPayService {

  final Logger log = LoggerFactory.getLogger(this.getClass());

  private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";

  private WxPayConfig config;

  public WxPayConfig getConfig() {
    return this.config;
  }

  public void setConfig(WxPayConfig config) {
    this.config = config;
  }

  public String getPayBaseUrl() {
    if (this.getConfig().isUseSandboxEnv()) {
      return PAY_BASE_URL + "/sandboxnew";
    }
    return PAY_BASE_URL;
  }

  public byte[] postForBytes(String url, String requestStr, boolean useKey) throws WxPayException {
    try {
      HttpClientBuilder httpClientBuilder = createHttpClientBuilder(useKey);
      HttpPost httpPost = this.createHttpPost(url, requestStr);
      try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
          final byte[] bytes = EntityUtils.toByteArray(response.getEntity());
          final String responseData = Base64.encodeBase64String(bytes);
          this.log
              .info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据(Base64编码后)】：{}", url, requestStr, responseData);
          return bytes;
        }
      } finally {
        httpPost.releaseConnection();
      }
    } catch (Exception e) {
      this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
      throw new WxPayException(e.getMessage(), e);
    }
  }

  public String post(String url, String requestStr, boolean useKey) throws WxPayException {
    try {
      HttpClientBuilder httpClientBuilder = this.createHttpClientBuilder(useKey);
      HttpPost httpPost = this.createHttpPost(url, requestStr);
      try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
          String responseString = EntityUtils
              .toString(response.getEntity(), StandardCharsets.UTF_8);
          this.log.info("\n【请求地址】：{}\n【请求数据】：{}\n【响应数据】：{}", url, requestStr, responseString);
          return responseString;
        }
      } finally {
        httpPost.releaseConnection();
      }
    } catch (Exception e) {
      this.log.error("\n【请求地址】：{}\n【请求数据】：{}\n【异常信息】：{}", url, requestStr, e.getMessage());
      throw new WxPayException(e.getMessage(), e);
    }
  }

  private StringEntity createEntry(String requestStr) {
    try {
      return new StringEntity(
          new String(requestStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
    } catch (UnsupportedEncodingException e) {
      //cannot happen
      this.log.error(e.getMessage(), e);
      return null;
    }
  }

  private HttpClientBuilder createHttpClientBuilder(boolean useKey) throws WxPayException {
    HttpClientBuilder httpClientBuilder = HttpClients.custom();
    if (useKey) {
      this.initSSLContext(httpClientBuilder);
    }
    return httpClientBuilder;
  }

  private HttpPost createHttpPost(String url, String requestStr) {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(this.createEntry(requestStr));
    httpPost.setConfig(RequestConfig.custom()
        .setConnectionRequestTimeout(this.getConfig().getHttpConnectionTimeout())
        .setConnectTimeout(this.getConfig().getHttpConnectionTimeout())
        .setSocketTimeout(this.getConfig().getHttpTimeout())
        .build());
    return httpPost;
  }

  private void initSSLContext(HttpClientBuilder httpClientBuilder) throws WxPayException {
    SSLContext sslContext = this.getConfig().getSslContext();
    if (null == sslContext) {
      sslContext = this.getConfig().initSSLContext();
    }
    SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
        new String[]{"TLSv1"}, null, new DefaultHostnameVerifier());
    httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
  }

  /**
   * 统一下单
   */
  public WxPayOrderResult unifiedorder(WxPayUnifiedOrderRequest request) throws WxPayException {

    request.checkAndSign(this.getConfig());

    String url = this.getPayBaseUrl() + "/pay/unifiedorder";
    String responseContent = this.post(url, request.toXML(), false);

    WxPayUnifiedOrderResult unifiedOrderResult = WxPayUnifiedOrderResult
        .fromXML(responseContent, WxPayUnifiedOrderResult.class);

    String prepayId = unifiedOrderResult.getPrepayId();
    if (StringUtils.isBlank(prepayId)) {
      throw new WxPayException(String.format("无法获取prepay id，错误代码： '%s'，信息：%s。",
          unifiedOrderResult.getErrCode(), unifiedOrderResult.getErrCodeDes()));
    }
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    String nonceStr = String.valueOf(System.currentTimeMillis());
    String signType = SignType.MD5;
    String appid = unifiedOrderResult.getAppid();
    if (StringUtils.isNotEmpty(unifiedOrderResult.getSubAppId())) {
      appid = unifiedOrderResult.getSubAppId();
    }
    WxPayOrderResult payResult = WxPayOrderResult.builder()
        .appId(appid)
        .timeStamp(timestamp)
        .nonceStr(nonceStr)
        .packageValue("prepay_id=" + prepayId)
        .signType(signType)
        .build();

    payResult.setPaySign(
        SignUtils.createSign(payResult, signType, this.getConfig().getMchKey(), null));

    return payResult;
  }

  /**
   * 申请退款
   */
  public WxPayRefundResult refund(WxPayRefundRequest request) throws WxPayException {

    request.checkAndSign(this.getConfig());

    String url = this.getPayBaseUrl() + "/secapi/pay/refund";
    if (this.getConfig().isUseSandboxEnv()) {
      url = PAY_BASE_URL + "/sandboxnew/pay/refund";
    }
    String responseContent = this.post(url, request.toXML(), true);
    WxPayRefundResult result = WxPayRefundResult.fromXML(responseContent);
    result.checkResult(this, request.getSignType(), true);
    return result;
  }

  /**
   * 查询订单
   */
  public WxPayOrderQueryResult queryOrder(WxPayOrderQueryRequest request) throws WxPayException {

    request.checkAndSign(this.getConfig());

    String url = this.getPayBaseUrl() + "/pay/orderquery";

    String responseContent = this.post(url, request.toXML(), false);

    if (StringUtils.isBlank(responseContent)) {
      throw new WxPayException("无响应结果");
    }
    WxPayOrderQueryResult result = WxPayOrderQueryResult
        .fromXML(responseContent, WxPayOrderQueryResult.class);

    result.checkResult(this, request.getSignType(), true);

    return result;
  }

  /**
   * 关闭订单
   */
  public WxPayOrderCloseResult closeOrder(WxPayOrderCloseRequest request) throws WxPayException {

    request.checkAndSign(this.getConfig());

    String url = this.getPayBaseUrl() + "/pay/closeorder";

    String responseContent = this.post(url, request.toXML(), false);

    WxPayOrderCloseResult result = WxPayOrderCloseResult
        .fromXML(responseContent, WxPayOrderCloseResult.class);

    result.checkResult(this, request.getSignType(), true);

    return result;
  }

  /**
   * 查询退款
   */
  public WxPayRefundQueryResult refundQuery(WxPayRefundQueryRequest request) throws WxPayException {

    request.checkAndSign(this.getConfig());

    String url = this.getPayBaseUrl() + "/pay/refundquery";

    String responseContent = this.post(url, request.toXML(), false);

    WxPayRefundQueryResult result = WxPayRefundQueryResult
        .fromXML(responseContent, WxPayRefundQueryResult.class);

    result.checkResult(this, request.getSignType(), true);

    return result;
  }

  /**
   * 解析支付结果通知.
   */
  public WxPayOrderNotifyResult parseOrderNotifyResult(String xmlData) throws WxPayException {
    try {
      log.debug("微信支付异步通知请求参数：{}", xmlData);
      WxPayOrderNotifyResult result = WxPayOrderNotifyResult.fromXML(xmlData);
      log.debug("微信支付异步通知请求解析后的对象：{}", result);
      result.checkResult(this, this.getConfig().getSignType(), false);
      return result;
    } catch (WxPayException e) {
      log.error(e.getMessage(), e);
      throw e;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new WxPayException("发生异常，" + e.getMessage(), e);
    }
  }

  /**
   * 解析退款结果通知
   */
  public WxPayRefundNotifyResult parseRefundNotifyResult(String xmlData) throws WxPayException {
    try {
      log.debug("微信支付退款异步通知参数：{}", xmlData);
      WxPayRefundNotifyResult result = WxPayRefundNotifyResult
          .fromXML(xmlData, this.getConfig().getMchKey());
      log.debug("微信支付退款异步通知解析后的对象：{}", result);
      return result;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new WxPayException("发生异常，" + e.getMessage(), e);
    }
  }

}
