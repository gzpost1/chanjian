package com.yjtech.wisdom.tourism.common.utils.hik;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** @Description @Author Mujun~ @Date 2020-09-28 11:29 */
public class HikUtils {
  public static final String ARTEMIS_PATH = "/artemis";

      public static final String URL_CAMERAS = "/api/resource/v1/cameras";
  public static final String URL_CAMERAS_SEARCH = "/api/resource/v2/camera/search";
  public static final String URL_CAMERAS_GET = "/api/nms/v1/online/camera/get";
  public static final String URL_PREVIEWURLS = "/api/video/v2/cameras/previewURLs";
  public static final String URL_PLAYBACKURLS = "/api/video/v2/cameras/playbackURLs";
  public static final String URL_TALKURLS = "/api/video/v1/cameras/talkURLs";
  public static final String URL_IASDEVICE = "/api/resource/v2/iasDevice/search";
  public static final String URL_EVENTSUBSCRIPTIONBYEVENTTYPES =
      "/api/eventService/v1/eventSubscriptionByEventTypes";
  // 获取根区域信息
  public static final String URL_REGIONS = "/api/resource/v1/regions/root";
  public static final String URL_EVENTSUBSCRIPTIONVIEW =
      "/api/eventService/v1/eventSubscriptionView";
  /** 取消订阅 */
  public static final String URL_UNSUBSCRIPTIONBYEVENTTYPES =
      "/api/eventService/v1/eventUnSubscriptionByEventTypes";

  public static final String URL_encodeDevice = "/api/resource/v2/encodeDevice/search";
  public static final String URL_resource = "/api/irds/v2/resource/resourcesByParams";
  public static final String URL_one_resource = "/api/irds/v1/resource/resource";
  public static final String URL_encode_device_get = "/api/nms/v1/online/encode_device/get";
  public static final String URL_resource_regions = "/api/resource/v1/regions";
  public static final String URL_resource_regions_root = "/api/resource/v1/regions/root";
  public static final String URL_resource_regions_nodesByParams = "/api/irds/v2/region/nodesByParams";




  public static final String protocol_type_rtsp = "rtsp";
  public static final String protocol_type_rtmp = "rtmp";
  public static final String protocol_type_hls = "hls";

  static {
    initConfig();
  }

  public static void initConfig() {
    /** STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数. */
    ArtemisConfig.host = "118.212.38.88:8443"; // artemis网关服务器ip端口
    ArtemisConfig.appKey = "23775469"; // 秘钥appkey
    ArtemisConfig.appSecret = "UeoV61Vf9ufCmotN0T2p"; // 秘钥appSecret
  }

  public static String sendReq(String path, JSONObject jsonBody) {
    jsonBody = Optional.ofNullable(jsonBody).orElse(new JSONObject());
    String contentType = "application/json";
    String body = Optional.ofNullable(jsonBody).orElse(new JSONObject()).toJSONString();
    System.out.println("reqBody:" + body);
    String resp =
        ArtemisHttpUtil.doPostStringArtemis(getPathMap(path), body, null, null, contentType, null);
    System.out.println("resp:" + resp);
    return resp;
  }

  public static Map<String, String> getPathMap(String path) {
    String previewURLsApi = ARTEMIS_PATH + path;
    return new HashMap<String, String>(2) {
      {
        put("https://", previewURLsApi); // 根据现场环境部署确认是http还是https
      }
    };
  }

  /**
   * 获取单个资源信息
   *
   * @return
   */
  public static JSONObject getCamerasSearch() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", "1");
    jsonBody.put("pageSize", "500");
    return jsonBody;
  }
  /**
   * 查询区域列表v2
   *
   * @return
   */
  public static JSONObject nodesByParams() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", "1");
    jsonBody.put("pageSize", "1000");
    jsonBody.put("resourceType", "camera");
    jsonBody.put("isSubRegion", true);
    String[] parentIndexCodes = {"root000000"};
    jsonBody.put("parentIndexCodes",parentIndexCodes);
    return jsonBody;
  }
  /**
   * 分页获取区域列表
   *
   * @return
   */
  public static JSONObject getResourceRegions() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", "1");
    jsonBody.put("pageSize", "1000");
    return jsonBody;
  }
  /**
   * 获取单个资源信息
   *
   * @return
   */
  public static JSONObject getOneResource() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("resourceType", "emerdevice");
    return jsonBody;
  }

  /**
   * 分页获取监控点资源
   *
   * @return
   */
  public static JSONObject getCamerasBody() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", 1);
    jsonBody.put("pageSize", 500);
    jsonBody.put("treeCode", 0);
    return jsonBody;
  }
  /**
   * 查询资源列表v2
   *
   * @return
   */
  public static JSONObject getResourceByType(String resourceType) {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", 1);
    jsonBody.put("pageSize", 500);
    jsonBody.put("resourceType", resourceType);
    return jsonBody;
  }
  /**
   * 查询资源列表v2
   *
   * @return
   */
  public static JSONObject getResource() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", 1);
    jsonBody.put("pageSize", 500);
    jsonBody.put("resourceType", "camera");
    return jsonBody;
  }
  /**
   * 分页获取监控点资源
   *
   * @return
   */
  public static JSONObject getEncodeDevice() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", 1);
    jsonBody.put("pageSize", 500);
    jsonBody.put("resourceType", "encodeDevice");
    return jsonBody;
  }

  /**
   * 获取监控点预览取流URLv2
   *
   * @return
   */
  public static JSONObject getPreviewUrlsBody(String cameraId, String protocolType) {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("cameraIndexCode", cameraId);
    //    jsonBody.put("streamType", 0);
    //        jsonBody.put("protocol", "rtsp");
    switch (protocolType) {
      case protocol_type_rtmp:
        return fillRtmpBody(jsonBody);
      case protocol_type_rtsp:
        return fillRtspBody(jsonBody);
      case protocol_type_hls:
        return fillHlsBody(jsonBody);
    }
    return fillRtspBody(jsonBody);
  }

  public static JSONObject getTalkURLs() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("cameraIndexCode", "26dedd4d2890410ba54a9e790610cbcd");
    jsonBody.put("expand", "streamform=rtp");
    return jsonBody;
  }

  public static JSONObject getPlaybackURLs() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("cameraIndexCode", "18c846bba5304e59bfe127e350230d36");
    jsonBody.put("beginDate", "2020-09-29T09:00:00.000+08:00");
    jsonBody.put("endDate", "2020-09-29T09:30:00.000+08:00");

    return fillRtspBody(jsonBody);
  }

  public static JSONObject getEventSubscriptionByEventTypes() {
    JSONObject jsonBody = new JSONObject();
    List<Integer> eventTypes = Lists.newLinkedList();
    String backUrl = "http://377d24d8.cpolar.io/singlepawnEquipment/receiveGps";
    eventTypes.add(851969);
    jsonBody.put("eventTypes", eventTypes);
    jsonBody.put("eventDest", backUrl);

    return fillRtspBody(jsonBody);
  }

  public static JSONObject unSubscriptionByEventTypes(List<Integer> eventTypes) {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("eventTypes", eventTypes);
    return fillRtspBody(jsonBody);
  }

  public static JSONObject fillRtspBody(JSONObject jsonBody) {
    jsonBody.put("protocol", "rtsp");
    jsonBody.put("transmode", 1);
    jsonBody.put("expand", "streamform=rtp");
    return jsonBody;
  }

  public static JSONObject fillRtmpBody(JSONObject jsonBody) {
    jsonBody.put("protocol", "rtmp");
    jsonBody.put("transmode", 1);
    jsonBody.put("expand", "streamform=rtp");
    jsonBody.put("streamType", "0");
    return jsonBody;
  }

  public static JSONObject fillHlsBody(JSONObject jsonBody) {
    jsonBody.put("protocol", "hls");
    jsonBody.put("transmode", 1);
//    jsonBody.put("expand", "streamform=0&videotype=h264");
    jsonBody.put("expand", "transcode=0&videotype=h264");
    jsonBody.put("streamType", "0");
    jsonBody.put("streamform", "ps");
    return jsonBody;
  }

  /**
   * 查询入侵报警主机列表v2
   *
   * @return
   */
  public static JSONObject getIasDevice() {
    JSONObject jsonBody = new JSONObject();
    jsonBody.put("pageNo", 1);
    jsonBody.put("pageSize", 500);
    return jsonBody;
  }
}
