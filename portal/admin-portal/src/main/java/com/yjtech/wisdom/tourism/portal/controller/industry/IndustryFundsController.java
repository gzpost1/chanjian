package com.yjtech.wisdom.tourism.portal.controller.industry;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.industry.entity.IndustryFundsEntity;
import com.yjtech.wisdom.tourism.resource.industry.service.IndustryFundsService;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsCreateVO;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsQueryVO;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-产业资金管理
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/industry/funds/")
public class IndustryFundsController {

    @Autowired
    private IndustryFundsService industryFundsService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid IndustryFundsCreateVO vo) {
        industryFundsService.create(vo);
        return JsonResult.success();
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid IndustryFundsUpdateVO vo) {
        industryFundsService.update(vo);
        return JsonResult.success();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        industryFundsService.updateStatus(updateStatusParam);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryForId")
    public JsonResult<IndustryFundsEntity> queryForId(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(industryFundsService.queryForId(idParam.getId()));
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<IndustryFundsEntity>> queryForList(@RequestBody @Valid IndustryFundsQueryVO vo) {
        return JsonResult.success(industryFundsService.queryForList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<IndustryFundsEntity>> queryForPage(@RequestBody @Valid IndustryFundsQueryVO vo) {
        return JsonResult.success(industryFundsService.queryForPage(vo));
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        industryFundsService.delete(deleteParam.getId());
        return JsonResult.success();
    }

}
