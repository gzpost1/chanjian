package com.yjtech.wisdom.tourism.portal.controller.countrytour;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.SelectVo;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseQueryController;
import com.yjtech.wisdom.tourism.service.TbCountryTourService;
import com.yjtech.wisdom.tourism.vo.CountryTourQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 大屏-乡村游
 *
 * @author songjun
 * @since 2021/7/19 15:03
 */
@RestController
@RequestMapping("screen/countrytour")
public class CountryTourScreenController extends BaseQueryController<TbCountryTourService,TbCountryTour,CountryTourQuery> {
    private final TbCountryTourService countryTourService;

    public CountryTourScreenController(TbCountryTourService countryTourService) {
        this.countryTourService = countryTourService;
    }

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("page")
    public JsonResult<Page<TbCountryTour>> page(@RequestBody @Validated CountryTourQuery query) {
        return JsonResult.success(countryTourService.pageForScreen(query));
    }

    @Override
    public JsonResult<TbCountryTour> queryForDetail(@RequestBody @Valid IdParam idParam) {
        CountryTourQuery countryTourQuery = new CountryTourQuery();
        countryTourQuery.setId(idParam.getId());
        Page<TbCountryTour> pageForScreen = countryTourService.pageForScreen(countryTourQuery);
        List<TbCountryTour> records = pageForScreen.getRecords();
        if(Objects.nonNull(records) && records.size()>0){
            return JsonResult.success(records.get(0));
        }
        return null;
    }

    /**
     * 统计
     *
     * @return
     */
    @PostMapping("statistic")
    public JsonResult<List<SelectVo>> statistic() {
        return JsonResult.success(countryTourService.statistic());
    }
}
