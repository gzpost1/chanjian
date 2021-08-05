package com.yjtech.wisdom.tourism.portal.controller.location;


import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.resource.location.domain.TbDeviceLocationParam;
import com.yjtech.wisdom.tourism.resource.location.entity.TbDeviceLocationEntity;
import com.yjtech.wisdom.tourism.resource.location.service.TbDeviceLocationService;
import com.yjtech.wisdom.tourism.resource.location.vo.StaticsNumVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 定位设备 前端控制器
 *
 * @author Mujun
 * @since 2021-07-07
 */
@Slf4j
@RestController
@RequestMapping("/screen/deviceLocation")
public class DeviceLocationController extends BaseQueryController<TbDeviceLocationService, TbDeviceLocationEntity, TbDeviceLocationParam> {


    /**
     * 设备统计
     *
     * @param
     * @return
     */
    @PostMapping("/statistics")
    public JsonResult<List<StaticsNumVo>> statistics() {
        return JsonResult.success(service.staticsNum());
    }

}
