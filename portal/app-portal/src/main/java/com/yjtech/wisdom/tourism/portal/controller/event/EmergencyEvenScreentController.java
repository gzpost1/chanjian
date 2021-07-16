package com.yjtech.wisdom.tourism.portal.controller.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.event.dto.EmergencyEventUpdateDto;
import com.yjtech.wisdom.tourism.event.entity.EmergencyEventEntity;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.event.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.event.query.EmergencyEventQuery;
import com.yjtech.wisdom.tourism.event.query.EventCommonQuery;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import com.yjtech.wisdom.tourism.event.service.EmergencyEventService;
import com.yjtech.wisdom.tourism.event.service.EventDailySummaryService;
import com.yjtech.wisdom.tourism.event.service.EventLevelDailySummaryService;
import com.yjtech.wisdom.tourism.event.service.EventTypeDailySummaryService;
import com.yjtech.wisdom.tourism.event.vo.EventTrendVO;
import com.yjtech.wisdom.tourism.event.vo.EventVideoStatusVO;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 应急事件  大屏
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/emergency/event/screen")
public class EmergencyEvenScreentController {

    @Autowired
    private SysConfigService configService;

    @Autowired
    private EmergencyEventService emergencyEventService;


    @Autowired
    private IconService iconService;

    @Resource
    private ExtensionExecutor extensionExecutor;

    /**
     * 列表 用于地图展示 只显示未处理和处理中
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForList")
    public JsonResult<List<EmergencyEventEntity>> queryForList(@RequestBody EmergencyEventQuery query) {
        LambdaQueryWrapper<EmergencyEventEntity> queryWrapper = emergencyEventService.getQueryWrapper(query);
        //给地图点位使用 默认为 处理中 未处理
        queryWrapper.and( q ->q.eq(EmergencyEventEntity::getEventStatus,EventContants.UNTREATED)
                .or()
                .eq(EmergencyEventEntity::getEventStatus,EventContants.PROCESSING));
        //只展示 app 小程序 H5上报的事件
        queryWrapper.in(EmergencyEventEntity::getEventSource,EventContants.LITTLE_SOURCE,EventContants.H5_SOURCE,EventContants.APP_SOURCE);
        List<EmergencyEventEntity> list = emergencyEventService.list(queryWrapper);
        emergencyEventService.tranDict(list);
        for (EmergencyEventEntity entity:list){
            entity.setIconUrl(iconService.queryIconUrl(IconSpotEnum.EVENT,entity.getEventStatus()));
        }
        return JsonResult.success(list);
    }

    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<EmergencyEventEntity>> queryForPage(@RequestBody EmergencyEventQuery query) {
        LambdaQueryWrapper queryWrapper = emergencyEventService.getQueryWrapper(query);
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
     * 处理
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/handle")
    public JsonResult update(@RequestBody @Valid EmergencyEventUpdateDto updateDto) {
        EmergencyEventEntity eventEntity = emergencyEventService.getById(updateDto.getId());
        AssertUtil.isFalse(Objects.equals(EventContants.PROCESSED,eventEntity.getEventStatus()),"该事件不能被处理");
        BeanUtils.copyProperties(updateDto,eventEntity);
        eventEntity.setEventStatus(EventContants.PROCESSED);
        emergencyEventService.updateById(eventEntity);
        return JsonResult.ok();
    }

    /**
     *  查询直播的状态
     */
    @PostMapping("/queryVideoStatus")
    public JsonResult<EventVideoStatusVO> queryVideoStatus(@RequestBody @Valid IdParam idParam) {
        EmergencyEventEntity entity = emergencyEventService.getById(idParam.getId());
        AssertUtil.isFalse(Objects.isNull(entity),"该事件不存在");
        EventVideoStatusVO vo = new EventVideoStatusVO();
        vo.setId(entity.getId());

        LocalDateTime videoHeartTime = entity.getVideoHeartTime();
        //状态为直播中且一段时间内没有收到心跳 则返回直播中断
        String tinterval = configService.selectConfigByKey(EventContants.HEARTBEA_TINTERVAL);
        if(EventContants.LIVE.equals(entity.getVideoStatus()) && LocalDateTime.now().isAfter(videoHeartTime.plusSeconds(Long.valueOf(tinterval)))){
           vo.setVideoStatus(EventContants.LIVE_INTERRUPTED);
           vo.setVideoStatusName(DictUtils.getDictLabel(EventContants.EVENT_VIDEO_STATUS,EventContants.LIVE_INTERRUPTED));

           //更新数据库状态
           LambdaUpdateWrapper<EmergencyEventEntity> updateWrapper = new LambdaUpdateWrapper<>();
           updateWrapper.eq(EmergencyEventEntity::getId,entity.getId());
           updateWrapper.set(EmergencyEventEntity::getVideoStatus,EventContants.LIVE_INTERRUPTED);
           emergencyEventService.update(updateWrapper);
       }else{
           vo.setVideoStatus(entity.getVideoStatus());
           vo.setVideoStatusName(DictUtils.getDictLabel(EventContants.EVENT_VIDEO_STATUS,entity.getVideoStatus()));
       }



        return JsonResult.success(vo);
    }

    /**
     * 应急事件统计
     * @return
     */
    @PostMapping("/queryEventQuantity")
    public  JsonResult<List<BaseVO>> queryEventQuantity(@RequestBody EventCommonQuery query){
        return JsonResult.success(
                extensionExecutor.execute(EventQryExtPt.class,
                        buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                        EventQryExtPt::queryEventQuantity));
    }

    /**
     * 事件发生趋势
     * @param query
     * @return
     */
    @PostMapping("/queryTrend")
    public JsonResult<List<BaseValueVO>> querySaleTrend(@RequestBody @Valid EventSumaryQuery query){
        List<EventTrendVO> trendVOS = extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.querySaleTrend(query));
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(query,trendVOS, EventTrendVO::getQuantity));
    }

    /**
     * 事件类型分布
     * @param query
     * @return
     */
    @PostMapping("/queryEventType")
    public JsonResult<List<BaseVO >> queryEventType(@RequestBody @Valid EventSumaryQuery query){
        List<BaseVO> percentVOS = extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEventType(query));
        return JsonResult.success(percentVOS);
    }

    /**
     * 事件级别分布
     * @param query
     * @return
     */
    @PostMapping("/queryLevelType")
    public JsonResult<List<BaseVO>> queryEventLevel(@RequestBody @Valid EventSumaryQuery query){
        List<BaseVO> baseVOS = extensionExecutor.execute(EventQryExtPt.class,
                buildBizScenario(EventExtensionConstant.EVENT_QUANTITY, query.getIsSimulation()),
                extension -> extension.queryEventLevel(query));
        return JsonResult.success(baseVOS);
    }

    private BizScenario buildBizScenario(String useCasePraiseType, Integer isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.BIZ_EVENT, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }
}
