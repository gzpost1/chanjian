package com.yjtech.wisdom.tourism.portal.controller.hotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.EvaluateSatisfactionRankDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingHotelRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 大屏_酒店民宿大数据
 *
 * @date 2021/8/13 11:13
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/hotel/bigData/screen/")
public class HotelBigDataScreenController {

    @Autowired
    private MarketingHotelRoomService marketingHotelRoomService;
    @Resource
    private ExtensionExecutor extensionExecutor;


    /**
     * 查询评价统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatistics(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateStatisticsBigData(vo)));
    }

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateTypeDistribution(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateTypeDistributionBigData(vo)));
    }

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateHotRank")
    public JsonResult<List<BaseVO>> queryEvaluateHotRank(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateHotRankBigData(vo)));
    }

    /**
     * 查询评价排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateRank")
    public JsonResult<IPage<BaseVO>> queryEvaluateRank(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateRank(vo)));
    }

    /**
     * 查询满意度排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateSatisfactionRank")
    public JsonResult<IPage<EvaluateSatisfactionRankDTO>> queryEvaluateSatisfactionRank(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        //设置默认评论状态-启用
        vo.setEquipStatus(Objects.isNull(vo.getEquipStatus()) ? EntityConstants.ENABLED : vo.getEquipStatus());

        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateSatisfactionRank(vo)));
    }

    /**
     * 查询房型价格统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceStatistics")
    public JsonResult<RoomTypePriceScreenDTO> queryRoomPriceStatistics(@RequestBody @Valid RoomScreenQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceStatisticsBigData(vo)));
    }

    /**
     * 查询房型价格趋势
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceAnalysis")
    public JsonResult<List<RoomPriceAnalysisDTO>> queryRoomPriceAnalysis(@RequestBody @Valid RoomScreenQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceAnalysisBigData(vo)));
    }

    /**
     * 查询评价热度（评价量）趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryEvaluateAnalysis(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateAnalysisBigData(vo)));
    }

    /**
     * 查询评价满意度趋势、同比、环比
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateSatisfactionAnalysis")
    public JsonResult<List<AnalysisBaseInfo>> queryEvaluateSatisfactionAnalysis(@RequestBody @Valid EvaluateQueryVO vo) {
        //设置默认酒店状态-启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryEvaluateSatisfactionAnalysisBigData(vo)));
    }


    /**
     * 构建酒店民宿业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.HOTEL, useCasePraiseType,
                EntityConstants.NO.equals(isSimulation) ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
