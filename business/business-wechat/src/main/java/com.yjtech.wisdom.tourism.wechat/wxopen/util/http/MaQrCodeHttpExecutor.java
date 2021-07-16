package com.yjtech.wisdom.tourism.wechat.wxopen.util.http;

import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaQrcodeParam;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.FileUtils;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.DefaultApacheHttpClientBuilder;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.InputStreamResponseHandler;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.Utf8ResponseHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by wuyongchong on 2019/9/8.
 */
public class MaQrCodeHttpExecutor {

  private static CloseableHttpClient httpClient;

  static {
    httpClient = DefaultApacheHttpClientBuilder.get().build();
  }

  public static File getQrCode(String uri, String accessToken, WxMaQrcodeParam qrcodeParam)
      throws WxErrorException {
    try {
      String uriWithAccessToken =
          uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;
      return execute(uriWithAccessToken, qrcodeParam);
    } catch (WxErrorException e) {
      WxError error = e.getError();
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error, e);
      }
      return null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static File execute(String uri, WxMaQrcodeParam qrcodeParam)
      throws WxErrorException, IOException {
    if (qrcodeParam != null && StringUtils.isNotBlank(qrcodeParam.getPagePath())) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?")
          ? "path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8")
          : "&path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8");
    }
    HttpGet httpGet = new HttpGet(uri);
    try (CloseableHttpResponse response = httpClient.execute(httpGet);
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0) {
        // 出错
        if (ContentType.TEXT_PLAIN.getMimeType()
            .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
          String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
          throw new WxErrorException(WxError.fromJson(responseContent));
        }
      }
      return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg",
          Files.createTempDirectory("qrcode").toFile());
    } finally {
      httpGet.releaseConnection();
    }
  }
}
