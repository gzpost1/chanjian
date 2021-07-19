package com.yjtech.wisdom.tourism.portal.controller.integration;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistSaleRankListBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.service.FxDistApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 综合数据查询（一码游、珊瑚礁、景区中心、酒店中心）
 *
 * @Author horadirm
 * @Date 2021/7/19 13:51
 */
@RestController
@RequestMapping("/integration/")
public class IntegrationController {

    @Autowired
    private FxDistApiService fxDistApiService;

    /**
     * 查询商品销售列表
     * @param vo
     * @return
     */
    @PostMapping("queryProductSaleList")
    public JsonResult<List<FxDistSaleRankListBO>> queryProductSaleList(@RequestBody @Valid FxDistQueryVO vo) {
        vo.setAreaCode(AreaUtils.trimCode(vo.getAreaCode()));
        return JsonResult.success(fxDistApiService.queryProductSaleList(vo));
    }

}
