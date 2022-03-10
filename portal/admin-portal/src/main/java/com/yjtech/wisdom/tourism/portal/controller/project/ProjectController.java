package com.yjtech.wisdom.tourism.portal.controller.project;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.dto.ProjectResourceQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 后台管理-项目
 */
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private TbProjectInfoService projectInfoService;
    @Autowired
    private TbProjectResourceService projectResourceService;

    /**
     * 分页列表
     *
     * @Param: query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<TbProjectInfoEntity>> queryForPage(@RequestBody ProjectQuery query) {
        LambdaQueryWrapper<TbProjectInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getProjectName()), TbProjectInfoEntity::getProjectName, query.getProjectName());
        queryWrapper.in(CollectionUtils.isNotEmpty(query.getStatus()), TbProjectInfoEntity::getStatus, query.getStatus());
        queryWrapper.orderByDesc(TbProjectInfoEntity::getCreateTime);
        IPage<TbProjectInfoEntity> pageResult = projectInfoService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @Param: idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForDetail")
    public JsonResult<TbProjectInfoEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        TbProjectInfoEntity entity = Optional.ofNullable(projectInfoService.getById(idParam.getId())).orElse(new TbProjectInfoEntity());

        if (!Objects.isNull(entity.getId())) {
            entity.setResource(
                    Optional.ofNullable(
                            projectResourceService.
                                    list(new LambdaQueryWrapper<TbProjectResourceEntity>().eq(TbProjectResourceEntity::getProjectId, entity.getId())))
                            .orElse(new ArrayList<TbProjectResourceEntity>())
            );
        }
        return JsonResult.success(entity);
    }

    /**
     * 上传资源
     *
     * @Param: createDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/uploadResource")
    public JsonResult uploadResource(@RequestBody @Valid TbProjectResourceEntity entity) {

        if (Objects.isNull(entity.getId())) {
            entity.setDeleted(Byte.valueOf("0"));
        }

        projectResourceService.saveOrUpdate(entity);

        return JsonResult.ok();
    }

    /**
     * 获取资源详情
     *
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/getResourceBYIDAndType")
    public JsonResult<TbProjectResourceEntity> getResourceBYIDAndType(@RequestBody @Valid ProjectResourceQuery query) {

        LambdaUpdateWrapper<TbProjectResourceEntity> wrapper = new LambdaUpdateWrapper<TbProjectResourceEntity>();
        wrapper.eq(TbProjectResourceEntity::getProjectId, query.getProjectId());
        wrapper.eq(TbProjectResourceEntity::getResourceType, query.getResourceType());
        return JsonResult.success(projectResourceService.getOne(wrapper));
    }

    /**
     * 新增
     *
     * @Param: createDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid TbProjectInfoEntity entity) {
        entity.setDeleted(Byte.valueOf("0"));
        entity.setStatus(Byte.valueOf("0"));
        validateProjectName(null,entity.getProjectName());
        entity.setId(IdWorker.getInstance().nextId());

        projectInfoService.save(entity);

        return JsonResult.ok();
    }

    public void validateProjectName(Long id ,String name){
        LambdaQueryWrapper<TbProjectInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if(Objects.nonNull(id)){
            wrapper.ne(TbProjectInfoEntity::getId,id);
        }
        wrapper.eq(TbProjectInfoEntity::getProjectName,name);

        List<TbProjectInfoEntity> list = projectInfoService.list(wrapper);
        if(CollectionUtils.isNotEmpty(list)){
            throw new CustomException("项目名称重复");
        }
    }
    /**
     * 更新
     *
     * @Param: updateDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid TbProjectInfoEntity entity) {

        validateProjectName(entity.getId(),entity.getProjectName());

        projectInfoService.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        projectInfoService.delete(idParam.getId());

        return JsonResult.ok();
    }

    /**
     * 更新状态 状态 0待审核 1审核中 2已发布 3不予发布 4下架
     *
     * @Param: param
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam param) {
        TbProjectInfoEntity entity = projectInfoService.getById(param.getId());
        entity.setStatus(param.getStatus());
        projectInfoService.updateById(entity);
        return JsonResult.ok();
    }


}
