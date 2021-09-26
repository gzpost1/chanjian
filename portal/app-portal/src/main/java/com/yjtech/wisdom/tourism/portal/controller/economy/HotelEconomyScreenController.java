package com.yjtech.wisdom.tourism.portal.controller.economy;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 大屏_经济效益 酒店民宿
 *
 * @Author horadirm
 * @Date 2021/8/6 14:02
 */
@Slf4j
@RestController
@RequestMapping("/hotelEconomy/screen/")
public class HotelEconomyScreenController {

    @Resource
    private ExtensionExecutor extensionExecutor;


    /**
     * 查询房型价格统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceStatistics")
    public JsonResult<RoomTypePriceScreenDTO> queryRoomPriceStatistics(@RequestBody @Valid RoomScreenQueryVO vo) {
        //默认状态为启用
        vo.setStatus(EntityConstants.ENABLED);
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
        //默认状态为启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceAnalysisBigData(vo)));
    }

    /**
     * 查询房型价格分布
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomTypePriceDistribution")
    public JsonResult<List<BaseVO>> queryRoomTypePriceDistribution(@RequestBody @Valid RoomScreenQueryVO vo) {
        //默认状态为启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomTypePriceDistribution(vo)));
    }

    /**
     * 查询酒店房型价格排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceRank")
    public JsonResult<List<BaseVO>> queryRoomPriceRank(@RequestBody @Valid RoomScreenQueryVO vo) {
        //默认状态为启用
        vo.setStatus(EntityConstants.ENABLED);
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceRank(vo)));
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
