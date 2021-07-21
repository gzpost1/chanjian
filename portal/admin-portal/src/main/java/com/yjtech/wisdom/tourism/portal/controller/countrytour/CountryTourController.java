package com.yjtech.wisdom.tourism.portal.controller.countrytour;

import com.yjtech.wisdom.tourism.entity.TbCountryTour;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.service.TbCountryTourService;
import com.yjtech.wisdom.tourism.vo.CountryTourQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 乡村游
 *
 * @author songjun
 * @since 2021/7/19 14:40
 */
@RestController
@RequestMapping("countrytour")
public class CountryTourController extends BaseCurdController<TbCountryTourService, TbCountryTour, CountryTourQuery> {
}
