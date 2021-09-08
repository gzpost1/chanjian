package com.yjtech.wisdom.tourism.portal.controller.venue;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.venue.dto.VenueDto;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.resource.venue.service.VenueMangerService;
import com.yjtech.wisdom.tourism.resource.venue.vo.UpdateVO;
import com.yjtech.wisdom.tourism.resource.venue.vo.VenueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理_文博场馆管理
 *
 * @author renguangqian
 * @date 2021/7/21 19:40
 */
@RestController
@RequestMapping("venue")
public class VenueMangerController {

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

    /**
     * 单条删除
     *
     * @param params
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(
            @RequestBody @Valid UpdateVO params) {
        return JsonResult.success(venueMangerService.removeById(params.getId()));
    }

    /**
     * 更新状态
     *
     * @param params
     * @return
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(
            @RequestBody @Valid UpdateVO params) {
        VenueEntity entity = BeanMapper.copyBean(params, VenueEntity.class);
        return JsonResult.success(venueMangerService.updateById(entity));
    }

    /**
     * 更新信息
     *
     * @param entity
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Validated VenueEntity entity) {
        return JsonResult.success(venueMangerService.updateById(entity));
    }

    /**
     * 详细信息查询
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<VenueEntity> queryForDetail(
            @RequestBody @Valid UpdateVO idParam) {
        VenueEntity t = venueMangerService.getById(idParam.getId());
        return JsonResult.success(t);
    }

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(
            @RequestBody @Valid VenueEntity entity) {
        return JsonResult.success(venueMangerService.save(entity));
    }
}
