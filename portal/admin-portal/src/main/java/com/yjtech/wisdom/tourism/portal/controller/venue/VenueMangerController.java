package com.yjtech.wisdom.tourism.portal.controller.venue;

import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.resource.venue.service.VenueMangerService;
import com.yjtech.wisdom.tourism.resource.venue.vo.VenueVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台管理_文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:40
 */
@RestController
@RequestMapping("venue")
public class VenueMangerController extends BaseCurdController<VenueMangerService, VenueEntity, VenueVo> {
}
