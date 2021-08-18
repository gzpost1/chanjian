package com.yjtech.wisdom.tourism.portal.controller.index;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 首页
 *
 * @author 李波
 * @description: 首页
 * @date 2021/7/1410:26
 */
@RestController
@RequestMapping("/screen/index")
public class IndexController {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;


//    /**
//     * 查询评价统计
//     *
//     * @param vo
//     * @return
//     */
//    @PostMapping("queryEvaluateStatistics")
//    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatistics(@RequestBody @Valid EvaluateQueryVO vo) {
//        MarketingEvaluateStatisticsDTO evaluateStatistics = marketingEvaluateService.queryEvaluateStatistics(vo);
//
//        return JsonResult.success();
//    }

}
