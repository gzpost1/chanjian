package com.yjtech.wisdom.tourism.portal.controller.venue;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueDto;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueScaleDto;
import com.yjtech.wisdom.tourism.resource.venue.service.VenueMangerService;
import com.yjtech.wisdom.tourism.resource.venue.vo.VenueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 大屏_文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:40
 */
@RestController
@RequestMapping("screen/venue")
public class VenueMangerController {

    @Autowired
    private VenueMangerService venueMangerService;

    /**
     * 查询文博场馆列表_分页
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryPage")
    private JsonResult<IPage<VenueDto>> queryPage (@RequestBody @Validated VenueVo vo) {
        vo.setStatus((byte)1);
        return JsonResult.success(venueMangerService.queryPage(vo));
    }

    /**
     * 查询文博场馆分布比列
     *
     * @return
     */
    @GetMapping("queryScale")
    private JsonResult<List<VenueScaleDto>> queryScale () {
        return JsonResult.success(venueMangerService.queryScale());
    }
}
