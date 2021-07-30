package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.entity.plan.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventCommonQuery;
import com.yjtech.wisdom.tourism.command.query.event.EventQuery;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.service.plan.EmergencyPlanService;
import com.yjtech.wisdom.tourism.command.vo.event.AppEmergencyPlanVO;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdParam;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.yjtech.wisdom.tourism.common.exception.ErrorCode.EVENT_NOT_EXIST;

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
    private EmergencyPlanService emergencyPlanService;

    @Autowired
    private EventService eventService;


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
    public JsonResult<List<EventEntity>> queryForList(@RequestBody EventQuery query) {
        LambdaQueryWrapper<EventEntity> queryWrapper = eventService.getQueryWrapper(query);
        //给地图点位使用 默认为 处理中 未处理
        queryWrapper.and( q ->q.eq(EventEntity::getEventStatus, EventContants.UNTREATED)
                .or()
                .eq(EventEntity::getEventStatus, EventContants.PROCESSING));
        List<EventEntity> list = eventService.list(queryWrapper);
        eventService.tranDictEntity(list);
        for (EventEntity entity:list){
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
    public JsonResult<IPage<EventEntity>> queryForPage(@RequestBody EventQuery query) {
        LambdaQueryWrapper queryWrapper = eventService.getQueryWrapper(query);
        IPage<EventEntity> pageResult = eventService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        eventService.tranDictEntity(pageResult.getRecords());
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
        return JsonResult.success(appEventDetail);
    }



    /**
     * 应急事件统计（首页及事件页面）
     * @return
     */
    @PostMapping("/queryEventQuantity")
    public JsonResult<List<BaseVO>> queryEventQuantity(@RequestBody EventCommonQuery query){
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
        return JsonResult.success(AnalysisUtils.MultipleBuildAnalysis(query,trendVOS,false, EventTrendVO::getQuantity));
    }

    /**
     * 事件类型分布
     * @param query
     * @return
     */
    @PostMapping("/queryEventType")
    public JsonResult<List<BaseVO>> queryEventType(@RequestBody @Valid EventSumaryQuery query){
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
