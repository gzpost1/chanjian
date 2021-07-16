package com.yjtech.wisdom.tourism.wechat.wxopen.service;

import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.ApacheHttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuyongchong on 2019/9/7.
 */
public abstract class AbstractWxOpenService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private int retrySleepMillis = 1000;
  private int maxRetryTimes = 3;

  protected static final JsonParser JSON_PARSER = new JsonParser();

  protected static final Gson GSON = new Gson();


  /**
   * 将字符串对象转化为GsonArray对象
   *
   * @param strList
   * @return
   */
  protected JsonArray toJsonArray(List<String> strList) {
    JsonArray jsonArray = new JsonArray();
    if (strList != null && !strList.isEmpty()) {
      for (String str : strList) {
        jsonArray.add(str);
      }
    }
    return jsonArray;
  }

  public String doHttpGet(String url) throws WxErrorException {
    return doHttpGet(url, null);
  }

  public String doHttpGet(String url, String queryParam) throws WxErrorException {
    try {
      String result = ApacheHttpUtil.get(url, queryParam);
      this.log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", url, queryParam, result);
      return result;
    } catch (WxErrorException e) {
      WxError error = e.getError();
      if (error.getErrorCode() != 0) {
        this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", url, queryParam, error);
        throw new WxErrorException(error, e);
      }
      return null;
    } catch (IOException e) {
      this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", url, queryParam, e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public String doHttpGetWithRetry(String url) throws WxErrorException {
    return doHttpGetWithRetry(url, null);
  }

  public String doHttpGetWithRetry(String url, String queryParam) throws WxErrorException {
    int retryTimes = 0;
    do {
      try {
        return doHttpGet(url, queryParam);
      } catch (WxErrorException e) {
        if (retryTimes + 1 > this.maxRetryTimes) {
          log.warn("重试达到最大次数【{}】", maxRetryTimes);
          //最后一次重试失败后，直接抛出异常，不再等待
          throw new RuntimeException("微信服务端异常，超出重试次数");
        }
        WxError error = e.getError();
        // -1 系统繁忙, 1000ms后重试
        if (error.getErrorCode() == -1) {
          int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
          try {
            log.warn("微信系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
          }
        } else {
          throw e;
        }
      }
    } while (retryTimes++ < this.maxRetryTimes);
    log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

  public String doHttpPost(String url, String postData) throws WxErrorException {
    try {
      String result = ApacheHttpUtil.post(url, postData);
      this.log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", url, postData, result);
      return result;
    } catch (WxErrorException e) {
      WxError error = e.getError();
      if (error.getErrorCode() != 0) {
        this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", url, postData, error);
        throw new WxErrorException(error, e);
      }
      return null;
    } catch (IOException e) {
      this.log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", url, postData, e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public String doHttpPostWithRetry(String url, String postData) throws WxErrorException {
    int retryTimes = 0;
    do {
      try {
        return doHttpPost(url, postData);
      } catch (WxErrorException e) {
        if (retryTimes + 1 > this.maxRetryTimes) {
          log.warn("重试达到最大次数【{}】", maxRetryTimes);
          //最后一次重试失败后，直接抛出异常，不再等待
          throw new RuntimeException("微信服务端异常，超出重试次数");
        }
        WxError error = e.getError();
        // -1 系统繁忙, 1000ms后重试
        if (error.getErrorCode() == -1) {
          int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
          try {
            log.warn("微信系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
            Thread.sleep(sleepMillis);
          } catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
          }
        } else {
          throw e;
        }
      }
    } while (retryTimes++ < this.maxRetryTimes);
    log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
    throw new RuntimeException("微信服务端异常，超出重试次数");
  }

}
