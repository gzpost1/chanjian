package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.marketing.service.MarketingPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 营销推广 第三方数据
 *
 * @Author horadirm
 * @Date 2020/11/20 18:02
 */
@Slf4j
@RestController
@RequestMapping("/marketing/data/")
public class MarketingDataController {

    @Autowired
    private MarketingPlaceService marketingPlaceService;
    @Autowired
    private MarketingEvaluateService marketingEvaluateService;



}
