package com.yjtech.wisdom.tourism.portal.controller.project;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.constant.AuditStatusConstants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.CompanyRoleEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.dto.ProjectResourceQuery;
import com.yjtech.wisdom.tourism.project.dto.ProjectUpdateStatusParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectUpdateTopParam;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelRelationService;
import com.yjtech.wisdom.tourism.project.service.TbProjectResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

/**
 * 大屏-项目申报
 *
 * @date 2022/7/7 11:45
 * @author horadirm
 */
@Slf4j
@RestController
@RequestMapping("/screen/project/declare")
public class ProjectDeclareController {
    @Autowired
    private TbProjectInfoService projectInfoService;
    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;
    @Autowired
    private ScreenTokenService screenTokenService;


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
        //获取当前企业用户信息
        ScreenLoginUser screenLoginUser = screenTokenService.getLoginUser(ServletUtils.getRequest());
        //默认查询当前企业用户所属的项目
        query.setCompanyId(screenLoginUser.getId());
        if (query.getShowStatus() == null) {
            query.setShowStatus(1);
        }
        if (query.getAuditStatus() == null) {
            query.setAuditStatus(Collections.singletonList(1));
        }
        IPage<TbProjectInfoEntity> pageResult = projectInfoService.customPage(query);
        //构建已选中项目标签id列表
        List<TbProjectInfoEntity> records = pageResult.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for(TbProjectInfoEntity entity : records){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
            pageResult.setRecords(records);
        }
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
            //构建已选中项目标签id列表
            entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
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

        TbProjectInfoEntity byId = projectInfoService.getById(entity.getProjectId());
        byId.setUpdateTime(new Date());
        projectInfoService.saveOrUpdate(byId);
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
        validateProjectName(null, entity.getProjectName());
        entity.setId(IdWorker.getInstance().nextId());
        //构建项目-标签关联
        buildProjectLabelRelation(projectInfoService.save(entity), entity.getId(), entity.getPitchOnLabelIdList());

        return JsonResult.ok();
    }

    public void validateProjectName(Long id, String name) {
        LambdaQueryWrapper<TbProjectInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(id)) {
            wrapper.ne(TbProjectInfoEntity::getId, id);
        }
        wrapper.eq(TbProjectInfoEntity::getProjectName, name);

        List<TbProjectInfoEntity> list = projectInfoService.list(wrapper);
        if (CollectionUtils.isNotEmpty(list)) {
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

        validateProjectName(entity.getId(), entity.getProjectName());
        entity.setUpdateTime(new Date());

        //构建项目-标签关联
        buildProjectLabelRelation(projectInfoService.updateById(entity), entity.getId(), entity.getPitchOnLabelIdList());

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
    public JsonResult updateStatus(@RequestBody @Valid ProjectUpdateStatusParam param) {
        TbProjectInfoEntity entity = projectInfoService.getById(param.getId());
        entity.setStatus(param.getStatus());
        entity.setUpdateTime(new Date());
        projectInfoService.updateById(entity);
        return JsonResult.ok();
    }

    /**
     * 修改置顶状态
     * @Param:
     * @return:
     */
    @PostMapping("/updateTopStatus")
    public JsonResult updateTopStatus(@RequestBody @Valid ProjectUpdateTopParam param) {
        TbProjectInfoEntity entity = Optional.ofNullable(projectInfoService.getById(param.getId()))
                .orElseThrow(() -> new CustomException("项目不存在"));
        entity.setIsTop(param.getIsTop());
        projectInfoService.updateById(entity);
        return JsonResult.success();
    }

    /**
     * 构建项目-标签关联
     *
     * @param result
     * @param projectId
     * @param labelIdList
     */
    private void buildProjectLabelRelation(boolean result, Long projectId, List<Long> labelIdList){
        //构建项目-标签关联
        if(result){
            tbProjectLabelRelationService.build(projectId, labelIdList);
        }
    }

    /**
     * 校验用户
     */
    private void checkUser(){
        //获取当前企业用户信息
        ScreenLoginUser screenLoginUser = screenTokenService.getLoginUser(ServletUtils.getRequest());
        Assert.isTrue(null != screenLoginUser
                && AuditStatusConstants.SUCCESS.equals(screenLoginUser.getAuditStatus())
                && CollectionUtils.isNotEmpty(screenLoginUser.getType())
                && screenLoginUser.getType().contains(CompanyRoleEnum.COMPANY_ROLE_PROJECT.getType()), "操作失败：当前企业用户非项目方或未审核通过");
    }

}
