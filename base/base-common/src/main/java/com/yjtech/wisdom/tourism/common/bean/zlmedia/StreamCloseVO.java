package com.yjtech.wisdom.tourism.common.bean.zlmedia;

import lombok.Data;

import java.io.Serializable;

/**
 * 流操作 VO
 *
 * @date 2021/9/18 15:29
 * @author horadirm
 */
@Data
public class StreamCloseVO implements Serializable {

    private static final long serialVersionUID = -4363072599073952454L;

    /**
     * 流应用名
     */
    private String app;

    /**
     * rtsp或rtmp
     */
    private String schema;

    /**
     * 流ID
     */
    private String stream;

    /**
     * 流虚拟主机
     */
    private String vhost;

    /**
     * 服务器id,通过配置文件设置
     */
    private String mediaServerId;

}
