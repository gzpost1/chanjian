package com.yjtech.wisdom.tourism.portal.controller.location;


import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.resource.location.domain.TbDeviceLocationParam;
import com.yjtech.wisdom.tourism.resource.location.entity.TbDeviceLocationEntity;
import com.yjtech.wisdom.tourism.resource.location.service.TbDeviceLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 定位设备 前端控制器
 *
 * @author Mujun
 * @since 2021-07-07
 */
@Slf4j
@RestController
@RequestMapping("/deviceLocation")
    public class TbDeviceLocationController extends BaseCurdController<TbDeviceLocationService, TbDeviceLocationEntity, TbDeviceLocationParam> {
}
