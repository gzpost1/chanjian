package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.dto.event.EventCreateDto;
import com.yjtech.wisdom.tourism.command.dto.event.EventUpdateDto;
import com.yjtech.wisdom.tourism.command.entity.event.EventAppointEntity;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventAppointService;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanService;
import com.yjtech.wisdom.tourism.command.vo.event.AppEmergencyPlanVO;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.constant.MessageConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.framework.manager.AsyncManager;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.message.admin.service.MessageMangerService;
import com.yjtech.wisdom.tourism.message.admin.vo.InitMessageVo;
import com.yjtech.wisdom.tourism.message.admin.vo.SendMessageVo;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;
import java.util.stream.Collectors;

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
    private EventService eventService;

    @Autowired
    private EmergencyPlanService emergencyPlanService;

    @Autowired
    private MessageMangerService messageMangerService;

    @Autowired
    private EventAppointService eventAppointService;

    /**
     * 我的上报  分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPageByCreateUser")
    public JsonResult<IPage<EventListVO>> queryForPage(@RequestBody PageQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperUser();
        IPage<EventListVO> pageResult = eventService.getBaseMapper().queryForList(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        eventService.tranDictVo(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 获得事件数量
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForNumByHandler")
    public JsonResult<Integer> queryForNumByHandler(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperHandler(query);
        return JsonResult.success(eventService.count(queryWrapper));
    }

    /**
     * 待处理 已处理  分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPageByHandler")
    public JsonResult<IPage<EventListVO>> queryForPageByHandler(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapperHandler(query);
        IPage<EventListVO> pageResult = eventService.getBaseMapper().queryForList(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        eventService.tranDictVo(pageResult.getRecords());
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<AppEventDetail> queryForDetail(@RequestBody @Valid IdParam idParam) {
        AppEventDetail appEventDetail = eventService.getBaseMapper().queryForDetail(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(appEventDetail),"该记录不存在");
        eventService.tranDict(Lists.newArrayList(appEventDetail));

        //设置预案
        if(Objects.nonNull(appEventDetail.getPlanId())){
            EmergencyPlanEntity entity = emergencyPlanService.getById(appEventDetail.getPlanId());
            emergencyPlanService.tranDic(Lists.newArrayList(entity));
            AppEmergencyPlanVO planVO = new AppEmergencyPlanVO();
            BeanUtils.copyProperties(entity, planVO);
            appEventDetail.setPlan(planVO);
        }
        //当前登录人是否是指定处理人员
        LambdaQueryWrapper<EventEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("JSON_CONTAINS(appoint_handle_personnel,JSON_ARRAY({0}))",String.valueOf(SecurityUtils.getUserId()));
        appEventDetail.setAppointPersonnel(eventService.count(queryWrapper) >0);
        return JsonResult.success(appEventDetail);
    }

    /**
     * 处理
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/handle")
    public JsonResult update(@RequestBody @Valid EventUpdateDto updateDto) {
        eventService.handle(updateDto);
        return JsonResult.ok();
    }

    /**
     * 事件上报
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult<EventEntity> create(@RequestBody @Valid EventCreateDto createDto) {
        EventEntity eventEntity = eventService.create(createDto);
        /**
         * 异步调用消息接口
         *
         *  避免相互依赖 只能将方法放在controller
         */
        AsyncManager.me().execute(
                new TimerTask() {
                    @Override
                    public void run() {
                        InitMessageVo vo = InitMessageVo.builder()
                                .eventId(String.valueOf(eventEntity.getId()))
                                .eventName(eventEntity.getName())
                                .eventType(MessageConstants.EVENT_TYPE_EMERGENCY)
                                .eventStatus(MessageConstants.EVENT_STATUS_APPOINT)
                                .eventHappenDate(DateTimeUtil.localDateToString(eventEntity.getEventDate(), "yyyy-MM-dd"))
                                .eventHappenAddress(eventEntity.getAddress())
                                .eventHappenPersonId(eventEntity.getCreateUser())
                                .build();
                        messageMangerService.initMessage(vo);
                        List<EventAppointEntity> list = eventAppointService.list();

                        //如没有指定指派人员 默认给超级管理员发信息
                        Long[] eventDealPersonIdArray;
                        Integer[] sendType;
                        if(CollectionUtils.isNotEmpty(list)){
                            //数据只有一条
                            List<String> appointPersonnel = list.get(0).getAppointPersonnel();
                            eventDealPersonIdArray = appointPersonnel.stream().map(p -> Long.valueOf(p)).collect(Collectors.toList()).toArray(new Long[appointPersonnel.size()]);
                            List<String> notice = list.get(0).getNotice();
                            sendType = notice.stream().map(p -> Integer.valueOf(p)).collect(Collectors.toList()).toArray(new Integer[notice.size()]);
                        }else{
                            eventDealPersonIdArray = new Long[]{1L};
                            sendType = new Integer[]{0};
                        }

                        SendMessageVo messageVo = SendMessageVo.builder()
                                .sendType(sendType)
                                .eventId(eventEntity.getId())
                                .title(MessageConstants.event_message)
                                .content(String.format(MessageConstants.event_content, eventEntity.getName()))
                                .eventDealPersonIdArray(eventDealPersonIdArray)
                                .build();
                        messageMangerService.sendMessage(messageVo);
                    }
                }
        );
        return JsonResult.success(eventEntity);
    }


}
