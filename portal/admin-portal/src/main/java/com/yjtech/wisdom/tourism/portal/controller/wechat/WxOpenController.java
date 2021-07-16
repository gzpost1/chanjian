package com.yjtech.wisdom.tourism.portal.controller.wechat;

import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.FileToBase64;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatApp;
import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatAppService;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.WxOpenMaCodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.*;
import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenMaService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 小程序授权管理
 *
 * @author wuyongchong
 * @since 2020/1/15.
 */
@Controller
@RequestMapping("/wxopen")
public class WxOpenController {

    @Autowired
    private WxOpenComponentService wxOpenComponentService;

    @Autowired
    private WxOpenMaService wxOpenMaService;

    @Autowired
    private WxOpenTemplateService wxOpenTemplateService;

    @Autowired
    private WechatAppService wechatAppService;

    @GetMapping("/get_authorizer_info")
    @ResponseBody
    public JsonResult getAuthorizerInfo(@RequestParam String appId) throws WxErrorException {
        WxOpenAuthorizerInfoResult authorizerInfo = wxOpenComponentService.getAuthorizerInfo(appId);
        return JsonResult.success(authorizerInfo);
    }

    @GetMapping("/getAccountBasicInfo")
    @ResponseBody
    public JsonResult getAccountBasicInfo(@RequestParam String appId) throws WxErrorException {
        WxFastMaAccountBasicInfoResult accountBasicInfo = wxOpenMaService
                .getAccountBasicInfo(appId);
        return JsonResult.success(accountBasicInfo);
    }

    @GetMapping("/getDomain")
    @ResponseBody
    public JsonResult getDomain(@RequestParam String appId) throws WxErrorException {
        WxOpenMaDomainResult wxOpenMaDomainResult = wxOpenMaService.getDomain(appId);
        return JsonResult.success(wxOpenMaDomainResult);
    }

    @PostMapping("/setDomain")
    @ResponseBody
    public JsonResult setDomain(@RequestParam String appId) throws WxErrorException {

        WxOpenProperties wxOpenProperties = wxOpenComponentService.getWxOpenProperties();

        WxOpenMaDomainResult wxOpenMaDomainResult = wxOpenMaService
                .modifyDomain(appId, "set", wxOpenProperties.getRequestDomainList(),
                        wxOpenProperties.getWsrequestDomainList(),
                        wxOpenProperties.getUploadDomainList(),
                        wxOpenProperties.getDownloadDomainList());

        return JsonResult.ok();
    }

    @GetMapping("/getWebViewDomain")
    @ResponseBody
    public JsonResult getWebViewDomain(@RequestParam String appId) throws WxErrorException {
        return JsonResult.success(wxOpenMaService.getWebViewDomain(appId));
    }

    @PostMapping("/setWebViewDomain")
    @ResponseBody
    public JsonResult setWebViewDomain(@RequestParam String appId) throws WxErrorException {
        wxOpenMaService.setWebViewDomain(appId, "set", wxOpenComponentService.getWxOpenProperties()
                .getWebViewDomainList());
        return JsonResult.ok();
    }

    @GetMapping("/getTemplateList")
    @ResponseBody
    public JsonResult<List<WxOpenMaCodeTemplate>> getTemplateList() {
        List<WxOpenMaCodeTemplate> templateList = Lists.newArrayList();
        try {
            templateList = wxOpenTemplateService.getTemplateList();
        } catch (WxErrorException e) {
            throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "系统出现错误");
        }
        return JsonResult.success(templateList);
    }

    /**
     * 获取已设置的二级类目及用于代码审核的可选三级类目
     */
    @GetMapping("/getCategory")
    @ResponseBody
    public JsonResult getCategory(@RequestParam String appId) throws WxErrorException {
        return JsonResult.success(wxOpenMaService.getCategoryList(appId));
    }

    /**
     * 获取小程序的第三方提交代码的页面配置
     */
    @GetMapping("/getPageList")
    @ResponseBody
    public JsonResult getPageList(@RequestParam String appId) throws WxErrorException {
        return JsonResult.success(wxOpenMaService.getPageList(appId));
    }

    /**
     * 查询某个指定版本的审核状态
     */
    @GetMapping("/getAuditStatus")
    @ResponseBody
    public JsonResult getAuditStatus(@RequestParam String appId, @RequestParam Long auditid)
            throws WxErrorException {
        WxOpenMaQueryAuditResult queryAuditResult = wxOpenMaService.getAuditStatus(appId, auditid);
        return JsonResult.success(queryAuditResult);
    }

    /**
     * 查询最新一次提交的审核状态
     */
    @GetMapping("/getLatestAuditStatus")
    @ResponseBody
    public JsonResult getLatestAuditStatus(@RequestParam String appId) throws WxErrorException {
        WxOpenMaQueryAuditResult queryAuditResult = wxOpenMaService.getLatestAuditStatus(appId);
        return JsonResult.success(queryAuditResult);
    }

    /**
     * 小程序审核撤回
     */
    @GetMapping("/undoCodeAudit")
    @ResponseBody
    public JsonResult undoCodeAudit(@RequestParam String appId)
            throws WxErrorException {
        WxOpenResult wxOpenResult = wxOpenMaService.undoCodeAudit(appId);
        if ("0".equals(wxOpenResult.getErrcode())) {
            WechatApp wechatApp = wechatAppService.getByAppId(appId);
            if (null != wechatApp) {
                wechatApp.setAuditStatus(Byte.valueOf("3"));
                wechatAppService.updateById(wechatApp);
            }
        }
        return JsonResult.success(wxOpenResult);
    }

    /**
     * 小程序版本回退
     */
    @GetMapping("/revertCodeReleaes")
    @ResponseBody
    public JsonResult revertCodeReleaes(@RequestParam String appId)
            throws WxErrorException {
        return JsonResult.success(wxOpenMaService.revertCodeReleaes(appId));
    }

    /**
     * 获取体验二维码
     */
    @GetMapping("/getTestQrcode")
    @ResponseBody
    public JsonResult getTestQrcode(@RequestParam String appId, HttpServletRequest request,
                                    HttpServletResponse response) {
        File file = null;
        try {
            file = wxOpenMaService.getTestQrcode(appId, null, null);
            if (null == file) {
                throw new CustomException(ErrorCode.PARAM_WRONG, "获取体验二维码失败，请检查AppId是否正确");
            }
            String base64File = FileToBase64.encodeBase64File(file);
            return JsonResult.success("data:image/png;base64," + base64File);
        } catch (WxErrorException e) {
            throw new CustomException(ErrorCode.PARAM_WRONG, "获取体验二维码失败，请检查AppId是否正确");
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN, "系统异常");
        }
    }

}
