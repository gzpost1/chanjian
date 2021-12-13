package com.yjtech.wisdom.tourism.portal.controller.video;


import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.resource.video.domain.TbVideoParam;
import com.yjtech.wisdom.tourism.resource.video.dto.ScreenVideoListDTO;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import com.yjtech.wisdom.tourism.resource.video.vo.ScreenVideoQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏监控
 *
 * @author Mujun
 * @since 2021-07-05
 */
@Slf4j
@RestController
@RequestMapping("/screen/video")
public class VideoController extends BaseQueryController<TbVideoService, TbVideoEntity, TbVideoParam> {


    /**
     * 监控明细
     *
     * @param idParam
     * @return
     */
    @PostMapping("scenicVideoDetail")
    public JsonResult<ScreenVideoListDTO> scenicVideoPage(@RequestBody @Valid IdParam idParam) {
        ScreenVideoQueryVO queryVO =new  ScreenVideoQueryVO();
        queryVO.setId(idParam.getId());
        List<ScreenVideoListDTO> screenVideoListDTOS = service.queryScreenVideoList(queryVO);
        if (screenVideoListDTOS.size() > 0) {
            return JsonResult.success(screenVideoListDTOS.get(0));
        }
        return JsonResult.success(null);
    }

}
