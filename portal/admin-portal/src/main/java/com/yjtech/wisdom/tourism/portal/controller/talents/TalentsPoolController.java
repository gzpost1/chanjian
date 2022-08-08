package com.yjtech.wisdom.tourism.portal.controller.talents;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.talents.entity.TalentsPoolEntity;
import com.yjtech.wisdom.tourism.resource.talents.service.TalentsPoolService;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolCreateVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolQueryVO;
import com.yjtech.wisdom.tourism.resource.talents.vo.TalentsPoolUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-人才库管理
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/talents/pool/")
public class TalentsPoolController {

    @Autowired
    private TalentsPoolService talentsPoolService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid TalentsPoolCreateVO vo) {
        talentsPoolService.create(vo);
        return JsonResult.success();
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid TalentsPoolUpdateVO vo) {
        talentsPoolService.update(vo);
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
        talentsPoolService.updateStatus(updateStatusParam);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryForId")
    public JsonResult<TalentsPoolEntity> queryForId(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(talentsPoolService.queryForId(idParam.getId()));
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<TalentsPoolEntity>> queryForList(@RequestBody @Valid TalentsPoolQueryVO vo) {
        return JsonResult.success(talentsPoolService.queryForList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<TalentsPoolEntity>> queryForPage(@RequestBody @Valid TalentsPoolQueryVO vo) {
        return JsonResult.success(talentsPoolService.queryForPage(vo));
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        talentsPoolService.delete(deleteParam.getId());
        return JsonResult.success();
    }

}
