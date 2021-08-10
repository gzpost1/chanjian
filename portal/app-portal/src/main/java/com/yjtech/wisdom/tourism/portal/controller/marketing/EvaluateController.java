package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateStatisticsDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateScreenQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 大屏 评论
 *
 * @Author horadirm
 * @Date 2021/8/10 19:52
 */
@Slf4j
@RestController
@RequestMapping("/evaluate/data/")
public class EvaluateController {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;


    /**
     * 查询评价统计
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateStatistics")
    public JsonResult<MarketingEvaluateStatisticsDTO> queryEvaluateStatistics(@RequestBody @Valid EvaluateScreenQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryEvaluateStatistics(vo));
    }

    /**
     * 查询评价类型分布
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateTypeDistribution")
    public JsonResult<List<BasePercentVO>> queryEvaluateTypeDistribution(@RequestBody @Valid EvaluateScreenQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryEvaluateTypeDistribution(vo));
    }

    /**
     * 查询评价热词排行
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateHotRank")
    public JsonResult<List<BaseVO>> queryEvaluateHotRank(@RequestBody @Valid EvaluateScreenQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryEvaluateHotRank(vo));
    }

    /**
     * 查询评价分页列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryEvaluateHotRank")
    public JsonResult<IPage<MarketingEvaluateListDTO>> queryForPage(@RequestBody @Valid EvaluateScreenQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryForPage(vo));
    }

}
