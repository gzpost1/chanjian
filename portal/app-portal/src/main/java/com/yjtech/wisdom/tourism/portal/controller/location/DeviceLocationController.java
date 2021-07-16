package com.yjtech.wisdom.tourism.portal.controller.location;


import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.redis.config.RedisConfig;
import com.yjtech.wisdom.tourism.resource.location.domain.TbDeviceLocationParam;
import com.yjtech.wisdom.tourism.resource.location.entity.MongoDeviceLocationDto;
import com.yjtech.wisdom.tourism.resource.location.entity.TbDeviceLocationEntity;
import com.yjtech.wisdom.tourism.resource.location.service.MongoDeviceLocationService;
import com.yjtech.wisdom.tourism.resource.location.service.TbDeviceLocationService;
import com.yjtech.wisdom.tourism.resource.location.vo.StaticsNumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    private MongoDeviceLocationService mongoDeviceLocationService;

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

    /**
     * 查看轨迹
     *
     * @param
     * @return
     */
    @GetMapping("/track")
    @Cacheable(value = RedisConfig.ONE_HOUR, key = "'track:'+#deviceId")
    public JsonResult<List<MongoDeviceLocationDto>> track(@RequestParam(name = "deviceId") String deviceId) {
        LocalDateTime beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endDate = LocalDateTime.now();

        MongoDeviceLocationDto queryEntity = MongoDeviceLocationDto.builder().deviceId(deviceId).build();
        Criteria beginDateCriteria = Criteria.where("createTime").gt(beginDate);
        Criteria endDateCriteria = Criteria.where("createTime").lt(endDate);

        Sort sort = Sort.by(Sort.Order.asc("createTime"));
        List<MongoDeviceLocationDto> deviceLocations =
                mongoDeviceLocationService.list(queryEntity, sort, beginDateCriteria, endDateCriteria);

        return JsonResult.success(deviceLocations);
    }
}
