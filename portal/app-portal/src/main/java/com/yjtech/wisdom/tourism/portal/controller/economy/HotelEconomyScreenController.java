package com.yjtech.wisdom.tourism.portal.controller.economy;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.hotel.dto.TbHotelInfoEntityParam;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
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
public class HotelEconomyScreenController
        extends BaseCurdController<TbHotelInfoService, TbHotelInfoEntity, TbHotelInfoEntityParam> {


    @Autowired
    private MarketingHotelRoomService marketingHotelRoomService;


    /**
     * 查询房型价格统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceStatistics")
    public JsonResult<RoomTypePriceScreenDTO> queryRoomPriceStatistics(@RequestBody @Valid RoomScreenQueryVO vo) {
        //默认状态为启用
        vo.setStatus(Constants.STATUS_NEGATIVE.byteValue());
        return JsonResult.success(marketingHotelRoomService.queryRoomPriceStatistics(vo));
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
        vo.setStatus(Constants.STATUS_NEGATIVE.byteValue());
        return JsonResult.success(marketingHotelRoomService.queryRoomPriceAnalysis(vo));
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
        vo.setStatus(Constants.STATUS_NEGATIVE.byteValue());
        return JsonResult.success(marketingHotelRoomService.queryRoomTypePriceDistribution(vo));
    }


}