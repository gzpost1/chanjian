package com.yjtech.wisdom.tourism.portal.controller.project;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelRelationService;
import com.yjtech.wisdom.tourism.project.service.TbProjectResourceService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 大屏-项目
 */
@RestController
@RequestMapping("/screen/project")
public class ProjectController {
    @Autowired
    private TbProjectInfoService projectInfoService;
    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;

    /**
     * 分页列表
     *
     * @Param: query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @IgnoreAuth
    @PostMapping("/queryForPage")
    public JsonResult<IPage<TbProjectInfoEntity>> queryForPage(@RequestBody ProjectQuery query) {
        IPage<TbProjectInfoEntity> page = projectInfoService.page(new Page<>(query.getPageNo(), query.getPageSize()), buildQueryWrapper(query));
        //构建已选中项目标签id列表
        List<TbProjectInfoEntity> records = page.getRecords();
        if(CollectionUtils.isNotEmpty(records)){
            for(TbProjectInfoEntity entity : records){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
            page.setRecords(records);
        }
        return JsonResult.success(page);
    }

    /**
     * 查询列表
     *
     * @Param: query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @IgnoreAuth
    @PostMapping("/queryForList")
    public JsonResult<List<TbProjectInfoEntity>> queryForList(@RequestBody ProjectQuery query) {
        List<TbProjectInfoEntity> list = projectInfoService.list(buildQueryWrapper(query));
        //构建已选中项目标签id列表
        if(CollectionUtils.isNotEmpty(list)){
            for(TbProjectInfoEntity entity : list){
                entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));
            }
        }
        return JsonResult.success(list);
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
        int viewNum = Optional.ofNullable(entity.getViewNum()).orElse(Integer.parseInt("0"));
        entity.setViewNum(++viewNum);

        entity.setResource(
                Optional.ofNullable(
                        projectResourceService.
                                list(new LambdaQueryWrapper<TbProjectResourceEntity>().eq(TbProjectResourceEntity::getProjectId, entity.getId())))
                        .orElse(new ArrayList<>())
        );

        //构建已选中项目标签id列表
        entity.setPitchOnLabelIdList(tbProjectLabelRelationService.queryForLabelIdListByProjectId(entity.getId()));

        projectInfoService.saveOrUpdate(entity);

        return JsonResult.success(entity);
    }

    /**
     * 获取推荐项目
     *
     * @Param: idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @IgnoreAuth
    @PostMapping("/queryRecommendProject")
    public JsonResult<List<TbProjectInfoEntity>> queryRecommendProject(@RequestBody ProjectQuery query) {
        LambdaQueryWrapper<TbProjectInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TbProjectInfoEntity::getStatus, "2");
        queryWrapper.likeRight(StringUtils.isNotBlank(query.getAreaCode()), TbProjectInfoEntity::getAreaCode, AreaUtils.trimCode(query.getAreaCode()));
        queryWrapper.orderByDesc(TbProjectInfoEntity::getViewNum);
        queryWrapper.orderByDesc(TbProjectInfoEntity::getCreateTime);
        queryWrapper.last(" limit 10");

        return JsonResult.success(projectInfoService.list(queryWrapper));
    }

    /**
     * 构建查询参数
     *
     * @param query
     * @return
     */
    private LambdaQueryWrapper buildQueryWrapper(ProjectQuery query){
        LambdaQueryWrapper<TbProjectInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getProjectName()), TbProjectInfoEntity::getProjectName, query.getProjectName());
        if(CollectionUtils.isEmpty(query.getStatus())){
            queryWrapper.eq(TbProjectInfoEntity::getStatus, "2");
        }else {
            queryWrapper.in(TbProjectInfoEntity::getStatus, query.getStatus());
        }
        queryWrapper.likeRight(StringUtils.isNotBlank(query.getAreaCode()), TbProjectInfoEntity::getAreaCode, AreaUtils.trimCode(query.getAreaCode()));
        queryWrapper.orderByDesc(TbProjectInfoEntity::getCreateTime);

        return queryWrapper;
    }

}
