package com.yjtech.wisdom.tourism.portal.controller.video;


import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.resource.video.domain.TbVideoParam;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *  后台管理 视频监控
 *
 * @author Mujun
 * @since 2021-07-05
 */
@Slf4j
@RestController
@RequestMapping("/video")
    public class TbVideoController extends BaseCurdController<TbVideoService, TbVideoEntity, TbVideoParam> {
}
