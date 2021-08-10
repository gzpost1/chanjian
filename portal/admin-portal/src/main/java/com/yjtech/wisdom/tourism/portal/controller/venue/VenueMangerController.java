package com.yjtech.wisdom.tourism.portal.controller.venue;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueDto;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.resource.venue.service.VenueMangerService;
import com.yjtech.wisdom.tourism.resource.venue.vo.VenueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台管理_文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:40
 */
@RestController
@RequestMapping("venue")
public class VenueMangerController extends BaseCurdController<VenueMangerService, VenueEntity, VenueVo> {

    @Autowired
    private VenueMangerService venueMangerService;

    /**
     * 分页查询-最新
     *
     * @param params
     * @return
     */
   @PostMapping("queryPage")
    public JsonResult<IPage<VenueDto>> queryPage(@RequestBody @Validated VenueVo params) {
        return JsonResult.success(venueMangerService.queryPage(params));
    }

    /**
     * 列表查询-最新
     *
     * @param params
     * @return
     */
   @PostMapping("queryList")
    public JsonResult<List<VenueDto>> queryList(@RequestBody VenueVo params) {
        return JsonResult.success(venueMangerService.queryList(params));
    }
}
