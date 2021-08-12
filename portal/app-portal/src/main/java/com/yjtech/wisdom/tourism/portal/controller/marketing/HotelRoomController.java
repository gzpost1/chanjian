package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
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

import javax.validation.Valid;
import java.util.List;

/**
 * 酒店房型 大屏
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


    /**
     * 查询房型价格统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryRoomPriceStatistics")
    public JsonResult<RoomTypePriceScreenDTO> queryRoomPriceStatistics(@RequestBody @Valid RoomScreenQueryVO vo) {
        return JsonResult.success(marketingHotelRoomService.queryRoomPriceStatistics(vo));
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
        return JsonResult.success(marketingHotelRoomService.queryRoomPriceAnalysis(vo));
    }

}
