package com.yjtech.wisdom.tourism.portal.controller.project;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.service.TbFavoriteService;
import com.yjtech.wisdom.tourism.chat.service.ChatMessageService;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.DemoExtraData;
import com.yjtech.wisdom.tourism.common.bean.DemoExtraListener;
import com.yjtech.wisdom.tourism.common.bean.index.DataAnalysisDTO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.enums.*;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.ExcelFormReadUtil;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysRole;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.portal.controller.common.BusinessCommonController;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.dto.ProjectResourceQuery;
import com.yjtech.wisdom.tourism.project.dto.ProjectUpdateStatusParam;
import com.yjtech.wisdom.tourism.project.dto.ProjectUpdateTopParam;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.entity.TbProjectResourceEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectInfoService;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelRelationService;
import com.yjtech.wisdom.tourism.project.service.TbProjectResourceService;
import com.yjtech.wisdom.tourism.resource.notice.service.NoticeService;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeCreateVO;
import com.yjtech.wisdom.tourism.system.service.SysDictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 后台管理-项目
 */
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController extends BusinessCommonController {
    @Autowired
    private TbProjectInfoService projectInfoService;
    @Autowired
    private TbProjectResourceService projectResourceService;
    @Autowired
    private TbProjectLabelRelationService tbProjectLabelRelationService;    
	@Autowired
    private SysDictDataService sysDictDataService;
	@Autowired
    private NoticeService noticeService;
	@Autowired
    private TbFavoriteService tbFavoriteService;
	@Autowired
    private ChatMessageService chatMessageService;


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
        queryWrapper.last(" order by ifnull(update_time,create_time) desc");
        IPage<TbProjectInfoEntity> pageResult = projectInfoService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
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
//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        entity.setDeleted(Byte.valueOf("0"));
        entity.setStatus(Byte.valueOf("0"));
        validateProjectName(null, entity.getProjectName());
        entity.setId(IdWorker.getInstance().nextId());
//        entity.setCreateUser(loginUser.getUser().getUserId());
        //浏览次数开关默认开启
        entity.setViewNumFlag(EntityConstants.ENABLED);
        //收藏次数开关默认开启
        entity.setCollectNumFlag(EntityConstants.ENABLED);
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
        //发送项目审核通知
        try {
            sendProjectAuditNotice(entity.getId(), entity.getProjectName(), entity.getStatus());
        }catch (Exception e){
            log.error("******************** 发送项目审核通知异常 ********************");
            e.printStackTrace();
        }
        return JsonResult.ok();
    }

    /**
     * 更新浏览次数展示开关
     *
     * @param param
     * @return
     */
    @PostMapping("/updateViewNumFlag")
    public JsonResult updateViewNumFlag(@RequestBody @Valid UpdateStatusParam param) {
        Assert.isTrue(SysRole.isAdmin(SecurityUtils.getUserId()), "当前用户无权限操作");
        projectInfoService.update(new LambdaUpdateWrapper<TbProjectInfoEntity>()
                .eq(TbProjectInfoEntity::getId, param.getId())
                .set(null != param.getStatus(), TbProjectInfoEntity::getViewNumFlag, param.getStatus()));
        return JsonResult.success();
    }

    /**
     * 更新收藏次数展示开关
     *
     * @param param
     * @return
     */
    @PostMapping("/updateCollectNumFlag")
    public JsonResult updateCollectNumFlag(@RequestBody @Valid UpdateStatusParam param) {
        Assert.isTrue(SysRole.isAdmin(SecurityUtils.getUserId()), "当前用户无权限操作");
        projectInfoService.update(new LambdaUpdateWrapper<TbProjectInfoEntity>()
                .set(null != param.getStatus(), TbProjectInfoEntity::getCollectNumFlag, param.getStatus())
                .eq(TbProjectInfoEntity::getId, param.getId()));
        return JsonResult.success();
    }

    /**
     * 下载模板
     *
     * @param request
     * @param response
     */
    @GetMapping("getTemplate")
    public void getTemplate(HttpServletRequest request, HttpServletResponse response) {
        getTemplate(ImportInfoTypeEnum.PROJECT, request, response);
    }

    /**
     * 招商引资数据导入
     *
     * @param
     * @return
     */
    @RequestMapping("info/importExcel")
    public JsonResult<TbProjectInfoEntity> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
//        InputStream fileIs = this.getClass().getClassLoader().getResourceAsStream("files/excel/招商平台项目信息登记表（模板）（试行）.xlsx");

        if(Objects.isNull(file)){
            throw new CustomException("上传附件不能为空");

        }
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        List<DemoExtraData> list = EasyExcel.read(file.getInputStream(), DemoExtraData.class, new DemoExtraListener())
                // 需要读取合并单元格信息 默认不读取
                .extraRead(CellExtraTypeEnum.MERGE).sheet().doReadSync();

        TbProjectInfoEntity entity = new ExcelFormReadUtil<TbProjectInfoEntity>().readExcel(list, TbProjectInfoEntity.class, 1);
        if (Objects.isNull(entity)) {
            throw new CustomException("导入excel格式有误");
        }

        //校验项目名称是否重复
        if(StringUtils.isNotBlank(entity.getProjectName())){
            validateProjectName(null, entity.getProjectName());
        }

        //校验参数的合法性
        new ExcelFormReadUtil<TbProjectInfoEntity>().validateExcelReadData(entity,"entity");

        //构建区域信息
        String[] areaInfo = entity.getAreaCode().split("-");
        entity.setAreaName(areaInfo[0]);
        entity.setAreaCode(areaInfo[1]);

        return JsonResult.success(entity);
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
     * 发送项目审核通知
     *
     * @param projectId
     * @param projectName
     * @param auditStatus
     */
    private void sendProjectAuditNotice(Long projectId, String projectName, Byte auditStatus){
        //查询模板类型
        Byte noticeTemplateType = NoticeTemplateTypeEnum.getNoticeTemplateTypeByAuditStatus(auditStatus);
        if(null == noticeTemplateType){
            return;
        }
        //查询 消息模板类型 信息
        String noticeTemplate = sysDictDataService.selectDictLabel(Constants.DICT_TYPE_NOTICE_TEMPLATE, noticeTemplateType.toString());
        //构建项目申报模板消息
        noticeService.create(new NoticeCreateVO(String.format(noticeTemplate, projectName),
                NoticeTypeEnum.NOTICE_TYPE_PROGRAM_DECLARE.getType(), null, projectId.toString()));
    }


    /**
     * 下载zip压缩包（项目信息，PPT，mp4）
     *
     * @param request
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam(value = "id")  Long id,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        projectInfoService.download(id,request,response);
    }

    /**
     * 查看数据-数据统计
     *
     * @param vo
     * @return
     */
    @PostMapping("/queryDataStatistics")
    public JsonResult<ProjectDataStatisticsDTO> queryDataStatistics(@RequestBody @Valid ProjectDataStatisticsQueryVO vo) {
        //获取点赞数、收藏数
        ProjectDataStatisticsDTO dto = tbFavoriteService.queryDataStatistics(vo);

        TbProjectInfoEntity project = projectInfoService.getById(vo.getProjectId());
        Assert.notNull(project, "项目信息不存在");
        //获取浏览数
        dto.setViewNum(null == project.getViewNum() ? 0 : project.getViewNum());
        //获取留言数
        dto.setMessageNum(StringUtils.isBlank(project.getCompanyId()) ? 0 : chatMessageService.queryMessageStatistics(project.getCompanyId()));

        return JsonResult.success(dto);
    }

    /**
     * 查询趋势-浏览数、点赞数、留言数、收藏数
     * <p style="color:red">
     *     该接口开始日期与结束日期必传
     * </p>
     *
     * @param vo
     * @return
     */
    @PostMapping("queryAnalysis")
    public JsonResult<DataAnalysisDTO> queryAnalysis(@RequestBody @Valid ProjectDataStatisticsQueryVO vo) {
        Assert.notNull(vo.getBeginDate(), "开始日期不能为空");
        Assert.notNull(vo.getEndDate(), "结束日期不能为空");
        //获取浏览数
        List<BaseVO> list1 = projectInfoService.queryViewNumAnalysis(vo);

        //查询的收藏，默认数据来源为项目
        vo.setFavouriteSource(FavouriteSourceEnum.FAVOURITE_SOURCE_PROJECT.getType());

        //获取点赞数
        //设置收藏类型为点赞
        vo.setFavouriteType(FavouriteTypeEnum.FAVOURITE_TYPE_LIKE.getType());
        List<BaseVO> list2 = tbFavoriteService.queryAnalysis(vo);

        //获取收藏数
        //设置收藏类型为收藏
        vo.setFavouriteType(FavouriteTypeEnum.FAVOURITE_TYPE_COLLECT.getType());
        List<BaseVO> list3 = tbFavoriteService.queryAnalysis(vo);

        TbProjectInfoEntity project = projectInfoService.getById(vo.getProjectId());
        Assert.notNull(project, "项目信息不存在");
        //获取留言数
        List<BaseVO> list4;
        //企业id为空时，则构建默认
        if(StringUtils.isBlank(project.getCompanyId())){
            list4 = DateUtils.getInitBaseVO(vo.getBeginDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    vo.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                    DateUtils.YYYY_MM_DD, Calendar.DAY_OF_YEAR);
        } else {
            //设置所属企业id
            vo.setCompanyId(project.getCompanyId());
            list4 = chatMessageService.queryAnalysis(vo);
        }

        return JsonResult.success(new DataAnalysisDTO(list1, list2, list3, list4));
    }

}
