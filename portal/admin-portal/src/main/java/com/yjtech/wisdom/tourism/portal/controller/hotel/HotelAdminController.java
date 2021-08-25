package com.yjtech.wisdom.tourism.portal.controller.hotel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.hotel.dto.HotelSelectInfoDTO;
import com.yjtech.wisdom.tourism.hotel.dto.TbHotelInfoEntityParam;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 管理后台 酒店民宿
 *
 * @Author horadirm
 * @Date 2021/8/6 14:02
 */
@Slf4j
@RestController
@RequestMapping("/hotel")
public class HotelAdminController
        extends BaseCurdController<TbHotelInfoService, TbHotelInfoEntity, TbHotelInfoEntityParam> {

    /**
     * 更新
     * @param params
     * @return
     */
    @Override
    public JsonResult update(@RequestBody TbHotelInfoEntity params) {
        return super.update(params);
    }

    /**
     * 新增
     * @param params
     * @return
     */
    @Override
    public JsonResult create(@RequestBody @Valid TbHotelInfoEntity params) {
        return super.create(params);
    }

    /**
     * 查询酒店下拉选信息
     * @param params
     * @return
     */
    @PostMapping("/queryHotelSelectInfo")
    public JsonResult<IPage<HotelSelectInfoDTO>> queryHotelSelectInfo(@RequestBody @Valid PageQuery params) {
        return JsonResult.success(this.service.queryHotelSelectInfo(params));
    }
}
