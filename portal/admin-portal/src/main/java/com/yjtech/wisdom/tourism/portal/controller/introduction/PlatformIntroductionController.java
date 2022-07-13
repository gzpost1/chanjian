package com.yjtech.wisdom.tourism.portal.controller.introduction;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.resource.introduction.entity.PlatformIntroductionEntity;
import com.yjtech.wisdom.tourism.resource.introduction.service.PlatformIntroductionService;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionCreateVO;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionQueryVO;
import com.yjtech.wisdom.tourism.resource.introduction.vo.PlatformIntroductionUpdateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-平台介绍
 *
 * @author horadirm
 * @date 2022/5/24 11:46
 */
@RestController
@RequestMapping("/platformIntroduction/")
public class PlatformIntroductionController {

    @Autowired
    private PlatformIntroductionService platformIntroductionService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid PlatformIntroductionCreateVO vo) {
        platformIntroductionService.create(vo);
        return JsonResult.success();
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid PlatformIntroductionUpdateVO vo) {
        platformIntroductionService.update(vo);
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
        platformIntroductionService.updateStatus(updateStatusParam);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryForId")
    public JsonResult<PlatformIntroductionEntity> queryForId(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(platformIntroductionService.getBaseMapper().selectById(idParam.getId()));
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<PlatformIntroductionEntity>> queryForList(@RequestBody @Valid PlatformIntroductionQueryVO vo) {
        return JsonResult.success(platformIntroductionService.queryForList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<PlatformIntroductionEntity>> queryForPage(@RequestBody @Valid PlatformIntroductionQueryVO vo) {
        return JsonResult.success(platformIntroductionService.queryForPage(vo));
    }

    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        platformIntroductionService.getBaseMapper().deleteById(deleteParam.getId());
        return JsonResult.success();
    }

}
