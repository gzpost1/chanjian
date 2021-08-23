package com.yjtech.wisdom.tourism.portal.controller.marketing;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.StatusParam;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.MarketingEvaluateListDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 管理后台_评论
 *
 * @date 2021/8/17 20:51
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/evaluate/")
public class EvaluateController {

    @Autowired
    private MarketingEvaluateService marketingEvaluateService;


    /**
     * 查询评价分页列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<MarketingEvaluateListDTO>> queryForPage(@RequestBody @Valid EvaluateQueryVO vo) {
        return JsonResult.success(marketingEvaluateService.queryForPage(vo));
    }

    /**
     * 启/停用
     *
     * @param vo
     * @return
     */
    @PostMapping("updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid StatusParam vo) {
        return JsonResult.success(marketingEvaluateService.updateStatus(vo));
    }

}
