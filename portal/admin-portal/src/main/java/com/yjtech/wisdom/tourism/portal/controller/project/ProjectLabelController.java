package com.yjtech.wisdom.tourism.portal.controller.project;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelService;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelCreateVO;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelQueryVO;
import com.yjtech.wisdom.tourism.project.vo.ProjectLabelUpdateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理后台-项目标签
 *
 * @date 2022/5/18 18:18
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/projectLabel/")
public class ProjectLabelController {

    @Autowired
    private TbProjectLabelService tbProjectLabelService;


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @PostMapping("create")
    public JsonResult create(@RequestBody @Valid ProjectLabelCreateVO vo) {
        tbProjectLabelService.create(vo);
        return JsonResult.success();
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @PostMapping("update")
    public JsonResult update(@RequestBody @Valid ProjectLabelUpdateVO vo) {
        tbProjectLabelService.update(vo);
        return JsonResult.success();
    }

    /**
     * 根据id查询信息
     *
     * @param idParam
     * @return
     */
    @PostMapping("queryById")
    public JsonResult<TbProjectLabelEntity> queryById(@RequestBody @Valid IdParam idParam) {
        TbProjectLabelEntity entity = tbProjectLabelService.getById(idParam.getId());
        Assert.notNull(entity, ErrorCode.NOT_FOUND.getMessage());
        return JsonResult.success(entity);
    }

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForList")
    public JsonResult<List<TbProjectLabelEntity>> queryForList(@RequestBody @Valid ProjectLabelQueryVO vo) {
        return JsonResult.success(tbProjectLabelService.queryForList(vo));
    }

    /**
     * 查询分页
     *
     * @param vo
     * @return
     */
    @PostMapping("queryForPage")
    public JsonResult<IPage<TbProjectLabelEntity>> queryForPage(@RequestBody @Valid ProjectLabelQueryVO vo) {
        return JsonResult.success(tbProjectLabelService.queryForPage(vo));
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        tbProjectLabelService.updateStatus(updateStatusParam);
        return JsonResult.success();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     */
    @PostMapping("delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        tbProjectLabelService.delete(idParam.getId());
        return JsonResult.success();
    }

    /**
     * 查询可用标签列表
     *
     * @return
     */
    @PostMapping("queryEnableForList")
    public JsonResult<List<TbProjectLabelEntity>> queryEnableForList() {
        return JsonResult.success(tbProjectLabelService.queryEnableForList());
    }

}
