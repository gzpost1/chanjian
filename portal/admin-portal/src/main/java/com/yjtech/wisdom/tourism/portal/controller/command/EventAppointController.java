package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.command.dto.event.EventAppointCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventAppointUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventAppointEntity;
import com.yjtech.wisdom.tourism.command.service.event.EventAppointService;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 配置人员表
 *
 * @author wuyongchong
 * @since 2021-07-21
 */
@RestController
@RequestMapping("/event-appoint")
public class EventAppointController {

    @Autowired
    private EventAppointService eventAppointService;

    /**
     * 判断当前登录人是否在配置人员表中
     * @return
     */
    @PostMapping("/queryUserAppoint")
    public JsonResult<Boolean> queryUserAppoint(){
        return JsonResult.success(eventAppointService.queryUserAppoint());
    }

    /**
     * 列表
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<EventAppointEntity>> queryForList() {
        QueryWrapper<EventAppointEntity> queryWrapper = new QueryWrapper<>();
        List<EventAppointEntity> list = eventAppointService.list(queryWrapper);
        return JsonResult.success(list);
    }



    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid EventAppointCreateDto createDto) {
        EventAppointEntity entity = BeanMapper.map(createDto, EventAppointEntity.class);
        eventAppointService.save(entity);
        //TODO 发送消息
        return JsonResult.ok();
    }

    /**
     * 更新
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid EventAppointUpdateDto updateDto) {
        EventAppointEntity entity = BeanMapper.map(updateDto, EventAppointEntity.class);
        eventAppointService.updateById(entity);
        //TODO 发送消息
        return JsonResult.ok();
    }


}
