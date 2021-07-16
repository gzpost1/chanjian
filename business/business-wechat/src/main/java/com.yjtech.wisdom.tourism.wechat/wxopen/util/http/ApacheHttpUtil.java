package com.yjtech.wisdom.tourism.wechat.wxopen.util.http;

import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxError;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.FileUtils;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.DefaultApacheHttpClientBuilder;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.InputStreamResponseHandler;
import com.yjtech.wisdom.tourism.wechat.wxopen.util.http.apache.Utf8ResponseHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by wuyongchong on 2019/9/7.
 */
public class ApacheHttpUtil {

  private static final Pattern PATTERN = Pattern.compile(".*filename=\"(.*)\"");

  private static CloseableHttpClient httpClient;

  static {
    httpClient = DefaultApacheHttpClientBuilder.get().build();
  }

  public static String get(String uri, String queryParam) throws WxErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }
    HttpGet httpGet = new HttpGet(uri);

    try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
    } finally {
      httpGet.releaseConnection();
    }
  }

  public static String post(String uri, String postEntity) throws WxErrorException, IOException {

    HttpPost httpPost = new HttpPost(uri);

    if (postEntity != null) {
      StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
      httpPost.setEntity(entity);
    }

    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      if (responseContent.isEmpty()) {
        throw new WxErrorException(WxError.builder().errorCode(9999).errorMsg("无响应内容").build());
      }

      if (responseContent.startsWith("<xml>")) {
        //xml格式输出直接返回
        return responseContent;
      }

      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
    } finally {
      httpPost.releaseConnection();
    }
  }

  public static String upload(String uri, File file) throws WxErrorException, IOException {

    HttpPost httpPost = new HttpPost(uri);

    if (file != null) {
      HttpEntity entity = MultipartEntityBuilder
          .create()
          .addBinaryBody("media", file)
          .setMode(HttpMultipartMode.RFC6532)
          .build();
      httpPost.setEntity(entity);
    }
    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
      String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
      WxError error = WxError.fromJson(responseContent);
      if (error.getErrorCode() != 0) {
        throw new WxErrorException(error);
      }
      return responseContent;
    } finally {
      httpPost.releaseConnection();
    }
  }

  public static File download(String uri, String queryParam, File tmpDir)
      throws WxErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    HttpGet httpGet = new HttpGet(uri);

    try (CloseableHttpResponse response = httpClient.execute(httpGet);
        InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0) {
        if (contentTypeHeader[0].getValue()
            .startsWith(ContentType.APPLICATION_JSON.getMimeType())) {
          // application/json; encoding=utf-8 下载媒体文件出错
          String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
          throw new WxErrorException(WxError.fromJson(responseContent));
        }
      }

      String fileName = getFileName(response);
      if (StringUtils.isBlank(fileName)) {
        fileName = String.valueOf(System.currentTimeMillis());
      }

      return FileUtils.createTmpFile(inputStream, FilenameUtils.getBaseName(fileName),
          FilenameUtils.getExtension(fileName), tmpDir);

    } finally {
      httpGet.releaseConnection();
    }
  }

  private static String getFileName(CloseableHttpResponse response) throws WxErrorException {
    Header[] contentDispositionHeader = response.getHeaders("Content-disposition");
    if (contentDispositionHeader == null || contentDispositionHeader.length == 0) {
      throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
    }
    return extractFileNameFromContentString(contentDispositionHeader[0].getValue());
  }

  private static String extractFileNameFromContentString(String content) throws WxErrorException {
    if (content == null || content.length() == 0) {
      throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
    }
    Matcher m = PATTERN.matcher(content);
    if (m.matches()) {
      return m.group(1);
    }
    throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
  }

}
