package com.yjtech.wisdom.tourism.portal.controller.hotel;

import com.yjtech.wisdom.tourism.hotel.dto.TbHotelInfoEntityParam;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 大屏 酒店民宿
 *
 * @Author horadirm
 * @Date 2021/8/6 14:02
 */
@Slf4j
@RestController
@RequestMapping("/hotel/screen/")
public class HotelScreenController
        extends BaseCurdController<TbHotelInfoService, TbHotelInfoEntity, TbHotelInfoEntityParam> {


}
