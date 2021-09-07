package com.yjtech.wisdom.tourism.portal.controller.index;

import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.TravelComplaintQryExtPt;
import com.yjtech.wisdom.tourism.command.vo.travelcomplaint.TravelComplaintScreenQueryVO;
import com.yjtech.wisdom.tourism.common.bean.index.ComplaintStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.HotelStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.ScenicBuildingDTO;
import com.yjtech.wisdom.tourism.common.bean.index.TodayRealTimeStatisticsDTO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanUtils;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelExtensionConstant;
import com.yjtech.wisdom.tourism.integration.extensionpoint.OneTravelQryExtPt;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistOrderStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import com.yjtech.wisdom.tourism.integration.service.OneTravelApiService;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import com.yjtech.wisdom.tourism.mybatis.entity.IndexQueryVO;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.IndexScenicQryExtPt;
import com.yjtech.wisdom.tourism.resource.scenic.extensionpoint.ScenicExtensionConstant;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSummaryQuery;
import com.yjtech.wisdom.tourism.resource.video.dto.ScreenVideoListDTO;
import com.yjtech.wisdom.tourism.resource.video.service.TbVideoService;
import com.yjtech.wisdom.tourism.resource.video.vo.ScreenVideoQueryVO;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 大屏_首页 综合总览
 *
 * @date 2021/8/19 11:16
 * @author horadirm
 */
@RestController
@RequestMapping("/screen/index/")
public class IndexController {

    @Autowired
    private TbVideoService tbVideoService;
    @Autowired
    private OneTravelApiService oneTravelApiService;
    @Autowired
    private DistrictTourService districtTourService;
    @Resource
    private ExtensionExecutor extensionExecutor;


    /**
     * 今日实时统计
     *
     * @param vo
     * @return
     */
    @PostMapping("todayRealTimeStatistics")
    public JsonResult<TodayRealTimeStatisticsDTO> todayRealTimeStatistics(@RequestBody @Valid IndexQueryVO vo) {
        //根据票务统计获取接待人数
        //构建票务查询条件，默认获取今天
        TicketSummaryQuery ticketSummaryQuery = BeanUtils.copyBean(vo, TicketSummaryQuery.class);
        LocalDateTime now = LocalDateTime.now();
        ticketSummaryQuery.setBeginTime(now);
        ticketSummaryQuery.setBeginTime(now);
        TodayRealTimeStatisticsDTO dto = extensionExecutor.execute(IndexScenicQryExtPt.class,
                buildScenicBizScenario(ScenicExtensionConstant.INDEX_SCENIC_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryVisitStatistics(ticketSummaryQuery));

        //一码游访问次数
        dto.setOneTravelVisit(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> new BigDecimal(extension.queryOneTravelVisitIndex(vo))));

        return JsonResult.success(dto);
    }

    /**
     * 景区建设
     *
     * @param vo
     * @return
     */
    @PostMapping("scenicBuilding")
    public JsonResult<ScenicBuildingDTO> scenicBuilding(@RequestBody @Valid IndexQueryVO vo) {
        ScenicBuildingDTO dto = extensionExecutor.execute(IndexScenicQryExtPt.class,
                buildScenicBizScenario(ScenicExtensionConstant.INDEX_SCENIC_QUANTITY, vo.getIsSimulation()),
                extension -> extension.scenicBuilding(vo));
        return JsonResult.success(dto);
    }

    /**
     * 酒店民宿
     *[
     * @param vo
     * @return
     */
    @PostMapping("hotelStatistics")
    public JsonResult<HotelStatisticsDTO> hotelStatistics(@RequestBody @Valid IndexQueryVO vo) {
        //构建查询参数
        EvaluateQueryVO evaluateQueryVO = buildEvaluateCondition(vo);
        //获取酒店评论统计
        MarketingEvaluateStatisticsDTO evaluateStatistics = extensionExecutor.execute(HotelQryExtPt.class,
                buildHotelBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateStatisticsIndex(evaluateQueryVO));

        //构建房型价格查询条件
        RoomScreenQueryVO roomScreenQueryVO = BeanUtils.copyBean(vo, RoomScreenQueryVO.class);
        //默认酒店状态-启用
        roomScreenQueryVO.setStatus(EntityConstants.ENABLED);
        RoomTypePriceScreenDTO roomPriceStatistics = extensionExecutor.execute(HotelQryExtPt.class,
                buildHotelBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceStatistics(roomScreenQueryVO));

        return JsonResult.success(new HotelStatisticsDTO(evaluateStatistics.getEvaluateTotal(), evaluateStatistics.getSatisfaction(), roomPriceStatistics.getAveragePrice()));
    }

    /**
     * 重点景区监控
     *
     * @param vo
     * @return
     */
    @PostMapping("scenicVideoPage")
    public JsonResult<List<ScreenVideoListDTO>> scenicVideoPage(@RequestBody @Valid ScreenVideoQueryVO vo) {
        //默认景区状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //默认监控状态-在线
        vo.setEquipStatus(EntityConstants.ENABLED);

        return JsonResult.success(tbVideoService.queryScreenVideoList(vo));
    }

    /**
     * 一码游交易
     *
     * @param vo
     * @return
     */
    @PostMapping("oneTravelTrade")
    public JsonResult<FxDistOrderStatisticsBO> oneTravelTrade(@RequestBody @Valid IndexQueryVO vo) {
        //构建一码游订单查询条件
        FxDistQueryVO fxDistQueryVO = BeanUtils.copyBean(vo, FxDistQueryVO.class);
        return JsonResult.success(extensionExecutor.execute(OneTravelQryExtPt.class,
                buildOneTravelBizScenario(OneTravelExtensionConstant.ONE_TRAVEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryOneTravelTradeIndex(fxDistQueryVO)));
    }

    /**
     * 投诉
     *
     * @param vo
     * @return
     */
    @PostMapping("complaint")
    public JsonResult<ComplaintStatisticsDTO> complaint(@RequestBody @Valid TravelComplaintScreenQueryVO vo) {
        //构建一码游投诉查询条件
        OneTravelQueryVO oneTravelQueryVO = BeanUtils.copyBean(vo, OneTravelQueryVO.class);

        return JsonResult.success(new ComplaintStatisticsDTO(oneTravelApiService.queryComplaintStatistics(oneTravelQueryVO), extensionExecutor.execute(TravelComplaintQryExtPt.class,
                buildTravelComplaintBizScenario(TravelComplaintExtensionConstant.TRAVEL_COMPLAINT_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryTravelComplaintTotalToIndex(vo))));
    }

    /**
     * 游客结构
     *
     * @param vo
     * @return
     */
    @PostMapping("touristStatistics")
    public JsonResult<DataOverviewDto> touristStatistics(@RequestBody @Valid IndexQueryVO vo) {
        //构建游客结构查询条件
        DataOverviewVo dataOverviewVo = new DataOverviewVo();
        dataOverviewVo.setBeginDate(vo.getBeginTime().toLocalDate().toString());
        dataOverviewVo.setEndDate(vo.getEndTime().toLocalDate().toString());
        dataOverviewVo.setIsSimulation(vo.getIsSimulation().intValue());

        DataOverviewDto dto = districtTourService.queryDataOverview(dataOverviewVo);
        //计算省内游客占比
        dto.setProvinceInsideRate(null == dto.getAllTouristNum() || 0 == dto.getAllTouristNum() ?
                BigDecimal.ZERO :  new BigDecimal(dto.getProvinceInsideTouristNum()).divide(new BigDecimal(dto.getAllTouristNum()),1,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
        //计算省外游客占比
        dto.setProvinceOutsideRate(null == dto.getAllTouristNum() || 0 == dto.getAllTouristNum() ?
                BigDecimal.ZERO :  new BigDecimal(dto.getProvinceOutsideTouristNum()).divide(new BigDecimal(dto.getAllTouristNum()),1,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));

        return JsonResult.success(dto);
    }

    /**
     * 构建评论查询条件
     * @param vo
     * @return
     */
    private EvaluateQueryVO buildEvaluateCondition(IndexQueryVO vo){
        //构建评价查询条件
        EvaluateQueryVO evaluateQueryVO = BeanUtils.copyBean(vo, EvaluateQueryVO.class);
        //默认场所状态-启用
        evaluateQueryVO.setStatus(EntityConstants.ENABLED);
        //默认评价状态-启用
        evaluateQueryVO.setEquipStatus(EntityConstants.ENABLED);

        return evaluateQueryVO;
    }


    /**
     * 构建旅游投诉业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildTravelComplaintBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.TRAVEL_COMPLAINT, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

    /**
     * 构建酒店民宿业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildHotelBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.HOTEL, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

    /**
     * 构建景区业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildScenicBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.SCENIC, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

    /**
     * 构建一码游业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildOneTravelBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.ONE_TRAVEL, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
