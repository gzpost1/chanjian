package com.yjtech.wisdom.tourism.portal.controller.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.event.dto.EmergencyEventCreateDto;
import com.yjtech.wisdom.tourism.event.dto.EventHeartbeatDto;
import com.yjtech.wisdom.tourism.event.entity.EmergencyEventEntity;
import com.yjtech.wisdom.tourism.event.service.EmergencyEventService;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 应急事件 app H5 小程序
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/emergency/event/app")
public class EmergencyEventAppController {

    @Autowired
    private EmergencyEventService emergencyEventService;


    /**
     * 列表
     *
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<EmergencyEventEntity>> queryForList() {
        LambdaQueryWrapper queryWrapper = emergencyEventService.getQueryWrapper();
        List<EmergencyEventEntity> list = emergencyEventService.list(queryWrapper);
        emergencyEventService.tranDict(list);
        return JsonResult.success(list);
    }

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<EmergencyEventEntity>> queryForPage(@RequestBody PageQuery query) {
        LambdaQueryWrapper queryWrapper = emergencyEventService.getQueryWrapper();
        IPage<EmergencyEventEntity> pageResult = emergencyEventService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        emergencyEventService.tranDict(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<EmergencyEventEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        EmergencyEventEntity eventEntity = emergencyEventService.getById(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(eventEntity),"该记录不存在");
        emergencyEventService.tranDict(Lists.newArrayList(eventEntity));
        return JsonResult.success(eventEntity);
    }


    /**
     * 事件上报
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult<EmergencyEventEntity> create(@RequestBody @Valid EmergencyEventCreateDto createDto) {
        return JsonResult.success(emergencyEventService.create(createDto));
    }


    /**
     * 与直播的心跳连接
     */
    @PostMapping("/eventHeartbeatConnection")
    public JsonResult eventHeartbeatConnection(@RequestBody @Valid EventHeartbeatDto dto){
        LambdaUpdateWrapper<EmergencyEventEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(EmergencyEventEntity::getId,dto.getId());
        updateWrapper.set(EmergencyEventEntity::getVideoStatus,dto.getVideoStatus())
                .set(EmergencyEventEntity::getVideoHeartTime,LocalDateTime.now());
        emergencyEventService.update(updateWrapper);
        return JsonResult.ok();
    }

}
