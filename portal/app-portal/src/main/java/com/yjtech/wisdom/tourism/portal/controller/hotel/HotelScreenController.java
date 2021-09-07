package com.yjtech.wisdom.tourism.portal.controller.hotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.hotel.dto.HotelScreenDetailDTO;
import com.yjtech.wisdom.tourism.hotel.dto.HotelSelectInfoDTO;
import com.yjtech.wisdom.tourism.hotel.dto.TbHotelInfoEntityParam;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.hotel.vo.HotelScreenQueryVO;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏 酒店民宿
 *
 * @Author horadirm
 * @Date 2021/8/6 14:02
 */
@Slf4j
@RestController
@RequestMapping("/hotel/screen/")
public class HotelScreenController extends BaseCurdController<TbHotelInfoService, TbHotelInfoEntity, TbHotelInfoEntityParam> {

    @Autowired
    private TbHotelInfoService tbHotelInfoService;


    /**
     * 查询酒店大屏分页
     *
     * @return
     */
    @PostMapping("queryScreenPage")
    public JsonResult<IPage<HotelScreenDetailDTO>> queryScreenPage(@RequestBody @Valid HotelScreenQueryVO vo) {
        return JsonResult.success(tbHotelInfoService.queryScreenPage(vo));
    }

    /**
     * 查询酒店类型分布
     *
     * @return
     */
    @PostMapping("queryHotelTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryHotelTypeDistribution(@RequestBody @Valid HotelScreenQueryVO vo) {
        return JsonResult.success(tbHotelInfoService.queryHotelTypeDistribution(vo));
    }

    /**
     * 查询酒店星级分布
     *
     * @return
     */
    @PostMapping("queryHotelStarDistribution")
    public JsonResult<List<BasePercentVO>> queryHotelStarDistribution(@RequestBody @Valid HotelScreenQueryVO vo) {
        return JsonResult.success(tbHotelInfoService.queryHotelStarDistribution(vo));
    }

    /**
     * 查询酒店下拉选信息
     * @param params
     * @return
     */
    @PostMapping("/queryHotelSelectInfo")
    public JsonResult<IPage<HotelSelectInfoDTO>> queryHotelSelectInfo(@RequestBody @Valid PageQuery params) {
        return JsonResult.success(tbHotelInfoService.queryHotelSelectInfo(params));
    }

}
