package com.yjtech.wisdom.tourism.portal.controller.media;

import com.yjtech.wisdom.tourism.common.bean.zlmedia.StreamCloseVO;
import com.yjtech.wisdom.tourism.common.bean.zlmedia.StreamFoundVO;
import com.yjtech.wisdom.tourism.common.bean.zlmedia.ZlmediaResult;
import com.yjtech.wisdom.tourism.common.bean.zlmedia.ZlmediaTaskBaseInfo;
import com.yjtech.wisdom.tourism.common.task.ZlmediaDelayedTask;
import com.yjtech.wisdom.tourism.framework.manager.ZlmediaDelayQueueManager;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * zlm媒体服务
 *
 * @date 2021/9/18 14:51
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/index/hook/")
public class ZlmediaController {

    @Autowired
    private ZlmediaDelayQueueManager zlmediaDelayQueueManager;
    @Autowired
    private TbVideoService tbVideoService;

    /**
     * 标准产监 流标识
     */
    private static final String INDUSTRY_MONITORING_STANDARD_MARK = "standard";

    /**
     * 标准产监 视频 流标识
     */
    private static final String INDUSTRY_MONITORING_STANDARD_VIDEO_MARK = "standard_video_";

    /**
     * 默认延时时长 100毫秒
     */
    private static final Long DEFAULT_DELAYED_DURATION = 100L;


    /**
     * 流发现
     * @param vo
     * @return
     */
    @PostMapping("on_stream_not_found")
    public ZlmediaResult queryStream(@RequestBody @Valid StreamFoundVO vo) {
        String stream = vo.getStream();
        //判断流请求来源
        if(stream.contains(INDUSTRY_MONITORING_STANDARD_VIDEO_MARK)){
            //获取视频信息
            TbVideoEntity videoEntity = tbVideoService.getById(Long.valueOf(stream.replace(INDUSTRY_MONITORING_STANDARD_VIDEO_MARK, "")));
            //视频信息不为空，则创建延时任务进行
            if(null != videoEntity){
                zlmediaDelayQueueManager.put(new ZlmediaDelayedTask(new ZlmediaTaskBaseInfo(videoEntity.getId().toString(), stream, videoEntity.getUrl()), DEFAULT_DELAYED_DURATION));
            }
        }
        return ZlmediaResult.success();
    }

    /**
     * 流关闭
     * @param vo
     * @return
     */
    @PostMapping("on_stream_none_reader")
    public ZlmediaResult closeStream(@RequestBody @Valid StreamCloseVO vo) {
        String stream = vo.getStream();
        //如果为标准产监标识的流，则通知媒体服务器关闭
        if(stream.contains(INDUSTRY_MONITORING_STANDARD_MARK)){
            log.info("******************** 已通知需要关闭的流：{} ********************", stream);
            return ZlmediaResult.successClose();
        }
        return ZlmediaResult.failClose();
    }

}
