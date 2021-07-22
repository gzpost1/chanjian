package com.yjtech.wisdom.tourism.portal.controller.countrytour;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.service.TbCountryTourService;
import com.yjtech.wisdom.tourism.vo.CountryTourQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songjun
 * @since 2021/7/19 15:03
 */
@RestController
@RequestMapping("screen/countrytour")
public class CountryTourController {
    private final TbCountryTourService countryTourService;

    public CountryTourController(TbCountryTourService countryTourService) {
        this.countryTourService = countryTourService;
    }

    public JsonResult<Page<TbCountryTour>> page(@RequestBody @Validated CountryTourQuery query) {
        return JsonResult.success(countryTourService.page(query));
    }

    public JsonResult<List<SelectVo>> statistic() {
        return JsonResult.success(countryTourService.statistic());
    }
}
