package com.yjtech.wisdom.tourism.common.bean.zlmedia;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 流发现 VO
 *
 * @date 2021/9/18 15:29
 * @author horadirm
 */
@Data
public class StreamFoundVO implements Serializable {

    private static final long serialVersionUID = -7073702353521296423L;

    /**
     * 流应用名
     */
    private String app;

    /**
     * TCP链接唯一ID
     */
    private String id;

    /**
     * 播放器ip
     */
    private String ip;

    /**
     * 播放url参数
     */
    private String params;

    /**
     * 播放器端口号
     */
    private String port;

    /**
     * 播放的协议，可能是rtsp、rtmp
     */
    private String schema;

    /**
     * 流ID
     */
    @NotBlank(message = "流id不能为空")
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
