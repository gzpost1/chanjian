package com.yjtech.wisdom.tourism.portal.controller.wechat;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.WechatAuditStatus;
import com.yjtech.wisdom.tourism.common.constant.WechatAuthorizeStatus;
import com.yjtech.wisdom.tourism.common.constant.WechatReleaseStatus;
import com.yjtech.wisdom.tourism.common.core.domain.DeleteParam;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.wechat.wechat.dto.BindAppCompanyDto;
import com.yjtech.wisdom.tourism.wechat.wechat.dto.WechatAppForm;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.CodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatApp;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatAppCompany;
import com.yjtech.wisdom.tourism.wechat.wechat.query.AppIdQuery;
import com.yjtech.wisdom.tourism.wechat.wechat.query.WechatAppQuery;
import com.yjtech.wisdom.tourism.wechat.wechat.service.CodeTemplateService;
import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatAppCompanyService;
import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatAppService;
import com.yjtech.wisdom.tourism.wechat.wechat.vo.CodeTemplateVO;
import com.yjtech.wisdom.tourism.wechat.wechat.vo.WechatAppSelectItem;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 小程序管理
 *
 * @author wuyongchong
 * @since 2019/9/29.
 */
@RestController
@RequestMapping("/wechat-app")
public class WechatAppController {

    public static final long DEFAULT_TEMP_ID = 1L;
    @Autowired
    private WxOpenComponentService wxOpenComponentService;

//    @Autowired
//    private ScenicInfoService scenicInfoService;

    @Autowired
    private WechatAppService wechatAppService;

    @Autowired
    private CodeTemplateService codeTemplateService;

    @Autowired
    private WechatAppCompanyService wechatAppCompanyService;

//    @Autowired
//    private AppScenicInfoService appScenicInfoService;

    /**
     * 分页列表
     */
    @PostMapping("/pageList")
    @ResponseBody
    public JsonResult<IPage<WechatApp>> pageList(@RequestBody WechatAppQuery params) {

        IPage<WechatApp> pageResult = wechatAppService.queryForPage(params);

        return JsonResult.success(pageResult);
    }

    /**
     * 模板选择列表
     */
    @PostMapping("/getCodeTemplateList")
    @ResponseBody
    public JsonResult<List<CodeTemplateVO>> getCodeTemplateList() {
        return JsonResult.success(codeTemplateService.getCodeTemplateList());
    }

    /**
     * 新增
     */
    @PostMapping("/create")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:add')")
    @ResponseBody
    public JsonResult create(@RequestBody @Valid WechatAppForm postBody) {
        if (null != postBody.getId()) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "参数错误");
        }
        if (wechatAppService.appIdExsits(postBody.getAuthorizerAppid(), null)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序已经存在，不可重复创建");
        }
        WechatApp entity = BeanMapper.map(postBody, WechatApp.class);

        //默认待授权
        entity.setAuthorizeStatus(WechatAuthorizeStatus.WAIT_AUTHORIZE);
        //默认待提交审核
        entity.setAuditStatus(WechatAuditStatus.WAIT_SUBMIT_AUDIT);
        //默认待发布
        entity.setReleaseStatus(WechatReleaseStatus.WAIT_RELEASE);
        //默认启用状态
        entity.setStatus(EntityConstants.ENABLED);

        Long templateId = Objects.isNull(postBody.getTemplateId())? DEFAULT_TEMP_ID :postBody.getTemplateId();
        CodeTemplate codeTemplate = Optional
                .ofNullable(codeTemplateService.getById(templateId))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "模板不存在"));

        if (Objects.nonNull(codeTemplate)) {
            entity.setUserVersion(codeTemplate.getUserVersion());
            entity.setUserDesc(codeTemplate.getUserDesc());
        }

        //entity.setCompanyName(sysService.getCompanyName(postBody.getCompanyId()));

        wechatAppService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:edit')")
    @ResponseBody
    public JsonResult update(@RequestBody @Valid WechatAppForm postBody) {

        if (wechatAppService.appIdExsits(postBody.getAuthorizerAppid(), postBody.getId())) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序已经存在");
        }

        WechatApp entity = Optional.ofNullable(wechatAppService.getById(postBody.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

        if (entity.getAuditStatus().equals(WechatAuditStatus.AUDITING) || entity.getAuditStatus()
                .equals(WechatAuditStatus.AUDIT_DELAY)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序正在审核中，不可进行修改");
        }

        if (postBody.getAuthorizerAppid().equals(entity.getAuthorizerAppid())) {
            //模板和栏目修改
//            if (!postBody.getTemplateId().toString().equals(entity.getTemplateId().toString())
//                    || !postBody.getTabbarId().toString().equals(entity.getTabbarId().toString())) {
//                entity.setAuditStatus(WechatAuditStatus.WAIT_SUBMIT_AUDIT);
//                entity.setReleaseStatus(WechatReleaseStatus.WAIT_RELEASE);
//            }
        } else {
            //小程序修改
            entity.setAuthorizeStatus(WechatAuthorizeStatus.WAIT_AUTHORIZE);
            entity.setAuditStatus(WechatAuditStatus.WAIT_SUBMIT_AUDIT);
            entity.setReleaseStatus(WechatReleaseStatus.WAIT_RELEASE);
        }

        entity.setAuthorizerAppid(postBody.getAuthorizerAppid());
        entity.setAppName(postBody.getAppName());
        entity.setTemplateId(postBody.getTemplateId());
        entity.setTabbarId(postBody.getTabbarId());
        entity.setDescription(postBody.getDescription());

        CodeTemplate codeTemplate = Optional
                .ofNullable(codeTemplateService.getById(postBody.getTemplateId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "模板不存在"));

        entity.setUserVersion(codeTemplate.getUserVersion());
        entity.setUserDesc(codeTemplate.getUserDesc());

        wechatAppService.updateById(entity);
        return JsonResult.ok();
    }

    /**
     * 查看
     */
    @GetMapping("/view/{id}")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:query')")
    @ResponseBody
    public JsonResult view(@PathVariable Long id) {
        WechatApp entity = Optional.ofNullable(wechatAppService.getById(id))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));
        return JsonResult.success(entity);
    }

    /**
     * 授权
     */
    @PostMapping("/authorize")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:auth')")
    @ResponseBody
    public JsonResult gotoAuthorize(@RequestBody @Valid IdParam idParam) {

        WechatApp entity = Optional.ofNullable(wechatAppService.getById(idParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

        if (entity.getAuthorizeStatus().equals(WechatAuthorizeStatus.AUTHORIZED)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序已经授权，无须重复授权");
        }
        return JsonResult.success(wxOpenComponentService.getWxOpenProperties().getGotoAuthUrl());
    }

    /**
     * 上传代码
     */
    @PostMapping("/codeCommit")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:push')")
    @ResponseBody
    public JsonResult codeCommit(@RequestBody @Valid IdParam idParam) {

        WechatApp entity = Optional.ofNullable(wechatAppService.getById(idParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

        if (!Objects.equals(entity.getAuthorizeStatus(), WechatAuthorizeStatus.AUTHORIZED)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序未授权，不可提交代码");
        }

        wechatAppService.codeCommit(entity);

        return JsonResult.ok();
    }

    /**
     * 提交审核
     */
    @PostMapping("/submitAudit")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:verify')")
    @ResponseBody
    public JsonResult submitAudit(@RequestBody @Valid IdParam idParam) {

        WechatApp entity = Optional.ofNullable(wechatAppService.getById(idParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

        if (!Objects.equals(entity.getAuthorizeStatus(), WechatAuthorizeStatus.AUTHORIZED)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序没有授权或者已经取消授权");
        }

        if (Objects.equals(entity.getAuditStatus(), WechatAuditStatus.AUDIT_SUCCESS)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序已审核通过，不可重复提交审核");
        }
        if (Objects.equals(entity.getAuditStatus(), WechatAuditStatus.AUDITING)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序正在审核中，不可重复提交审核");
        }
        if (Objects.equals(entity.getAuditStatus(), WechatAuditStatus.AUDIT_DELAY)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序正在审核中，不可重复提交审核");
        }

        wechatAppService.submitAudit(entity);

        return JsonResult.ok();
    }

    /**
     * 撤回审核
     */
    @PostMapping("/undoCodeAudit")
    @ResponseBody
    public JsonResult undoCodeAudit(@RequestBody @Valid IdParam idParam) {
        WechatApp entity = Optional.ofNullable(wechatAppService.getById(idParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));
        //只可撤回审核中的小程序
        if (!Objects.equals(entity.getAuditStatus(), WechatAuditStatus.AUDITING)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "只可撤回审核中的小程序");
        }

        wechatAppService.undoCodeAudit(entity);

        return JsonResult.ok();
    }

    /**
     * 发布
     */
    @PostMapping("/release")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:release')")
    @ResponseBody
    public JsonResult release(@RequestBody @Valid IdParam idParam) {

        WechatApp entity = Optional.ofNullable(wechatAppService.getById(idParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

        if (!Objects.equals(entity.getAuditStatus(), WechatAuditStatus.AUDIT_SUCCESS)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序未审核通过，不可发布");
        }

        if (Objects.equals(entity.getReleaseStatus(), WechatReleaseStatus.RELEASEED)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "该小程序已经发布，不可重复发布");
        }

        wechatAppService.release(entity);

        return JsonResult.ok();
    }

    /**
     * 启用禁用
     */
    @PostMapping("/updateStatus")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:isStop')")
    @ResponseBody
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        WechatApp entity = Optional.ofNullable(wechatAppService.getById(updateStatusParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));
        entity.setStatus(updateStatusParam.getStatus());
        wechatAppService.updateById(entity);
        if (EntityConstants.ENABLED.equals(entity.getStatus())) {
            wechatAppService.enableAppFromCache(entity.getAuthorizerAppid());
        } else if (EntityConstants.DISABLED.equals(entity.getStatus())) {
            wechatAppService.disableAppFromCache(entity.getAuthorizerAppid());
        }
        return JsonResult.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @PreAuthorize("@ss.hasPermi('system:miniprogram:delete')")
    @ResponseBody
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        WechatApp entity = Optional.ofNullable(wechatAppService.getById(deleteParam.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "找不到记录"));

//        if (scenicInfoService.isBindWechatApp(entity.getAuthorizerAppid())) {
//            throw new CustomException(ErrorCode.PARAM_WRONG, "已被景区绑定，不可删除");
//        }

        if (!Objects.equals(entity.getStatus(), EntityConstants.DISABLED)) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "请先禁用，再删除");
        }
        wechatAppService.removeById(deleteParam.getId());
        return JsonResult.ok();
    }

    /**
     * 获取小程序选择列表
     */
    @PostMapping("/listForSelect")
    @ResponseBody
    public JsonResult<List<WechatAppSelectItem>> listForSelect(@RequestBody WechatAppQuery query) {

//        if (AuthUtil.isEnterpriseUser()) {
//            query.setCompanyId(AuthContextHandler.getCompanyId());
//        }

        List<WechatApp> list = wechatAppService.queryForList(query);

        List<WechatAppSelectItem> voList = Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(item -> WechatAppSelectItem
                        .builder().appId(item.getAuthorizerAppid()).appName(item.getAppName())
                        .build()).collect(
                        Collectors.toList());

        return JsonResult.success(voList);
    }

    /**
     * 绑定企业
     */
    @PostMapping("/bindAppCompanys")
    @ResponseBody
    public JsonResult bindAppCompanys(@RequestBody @Valid BindAppCompanyDto dto) {
        wechatAppCompanyService.bindAppCompanys(dto);
//        appScenicInfoService.removeAppScenicsFromCache(dto.getAppId());
        return JsonResult.ok();
    }

    /**
     * 获取绑定企业列表
     */
    @PostMapping("/queryBindAppCompanys")
    @ResponseBody
    public JsonResult<List<WechatAppCompany>> queryBindAppCompanys(
            @RequestBody @Valid AppIdQuery appIdQuery) {
        List<WechatAppCompany> list = wechatAppCompanyService
                .getCompanyListByAppId(appIdQuery.getAppId());
        return JsonResult.success(list);
    }


}
