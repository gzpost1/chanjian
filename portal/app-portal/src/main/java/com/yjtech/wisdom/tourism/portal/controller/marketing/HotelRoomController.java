package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.NewestRoomScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
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

/**
 * 大屏_酒店房型
 *
 * @Author horadirm
 * @Date 2021/8/11 15:56
 */
@Slf4j
@RestController
@RequestMapping("/hotelRoom/data/")
public class HotelRoomController {

    @Autowired
    private MarketingHotelRoomService marketingHotelRoomService;
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
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceStatistics(vo)));
    }

    /**
     * 查询最新房型列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryNewestRoomInfo")
    public JsonResult<List<NewestRoomScreenDTO>> queryNewestRoomInfo(@RequestBody @Valid RoomScreenQueryVO vo) {
        return JsonResult.success(marketingHotelRoomService.queryNewestRoomInfo(vo));
    }

    /**
     * 查询房型价格趋势
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceAnalysis")
    public JsonResult<List<RoomPriceAnalysisDTO>> queryRoomPriceAnalysis(@RequestBody @Valid RoomScreenQueryVO vo) {
        return JsonResult.success(extensionExecutor.execute(HotelQryExtPt.class,
                buildBizScenario(HotelExtensionConstant.HOTEL_QUANTITY, vo.getIsSimulation()),
                extension -> extension.queryRoomPriceAnalysis(vo)));
    }

    /**
     * 构建业务扩展点
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.HOTEL, useCasePraiseType,
                EntityConstants.NO.equals(isSimulation) ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

}
