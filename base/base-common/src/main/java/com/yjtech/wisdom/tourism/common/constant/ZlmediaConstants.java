package com.yjtech.wisdom.tourism.common.constant;

/**
 * ZLMedia 常量
 *
 * @date 2021/9/18 16:36
 * @author horadirm
 */
public class ZlmediaConstants {

    /**
     * 标准产监 流标识
     */
    public static final String INDUSTRY_MONITORING_STANDARD_MARK = "standard";

    /**
     * 标准产监 视频 流标识
     */
    public static final String INDUSTRY_MONITORING_STANDARD_VIDEO_MARK = "standard_video_";

    /**
     * 默认延时时长 100毫秒
     */
    public static final Long DEFAULT_DELAYED_DURATION = 100L;

    /**
     * zlmedia接口路由-新增流代理
     */
    public static final String ZLMEDIA_INTERFACE_ADD_STREAM_PROXY = "/index/api/addStreamProxy";

    /**
     * zlmedia接口路由-关闭流
     */
    public static final String ZLMEDIA_INTERFACE_CLOSE_STREAMS = "/index/api/close_streams";


    /** ********** zlmedia缓存key ********** */
    /**
     * zlmedia服务地址
     */
    public static final String ZLMEDIA_SERVER_URL_KEY = "zlmedia.server.url";

    /**
     * zlmedia密钥
     */
    public static final String ZLMEDIA_SECRET_KEY = "zlmedia.secret";

    /**
     * zlmedia主机
     */
    public static final String ZLMEDIA_HOST_KEY = "zlmedia.server.host";

    /**
     * zlmedia模式
     */
    public static final String ZLMEDIA_APP_MODEL_KEY = "zlmedia.app.model";

}
