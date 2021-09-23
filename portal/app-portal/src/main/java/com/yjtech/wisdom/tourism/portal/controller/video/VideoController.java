package com.yjtech.wisdom.tourism.portal.controller.video;


import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.resource.video.domain.TbVideoParam;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *  前端控制器
 *
 * @author Mujun
 * @since 2021-07-05
 */
@Slf4j
@RestController
@RequestMapping("/screen/video")
    public class VideoController extends BaseQueryController<TbVideoService, TbVideoEntity, TbVideoParam> {
}
