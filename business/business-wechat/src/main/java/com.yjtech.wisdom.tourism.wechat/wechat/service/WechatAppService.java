package com.yjtech.wisdom.tourism.wechat.wechat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.constant.WechatAuditStatus;
import com.yjtech.wisdom.tourism.common.constant.WechatAuthorizeStatus;
import com.yjtech.wisdom.tourism.common.constant.WechatReleaseStatus;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.JsonUtil;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.utils.EnumUtil;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.CodeTemplate;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatApp;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatAppCompany;
import com.yjtech.wisdom.tourism.wechat.wechat.enumeration.WechatErrorCode;
import com.yjtech.wisdom.tourism.wechat.wechat.mapper.WechatAppCompanyMapper;
import com.yjtech.wisdom.tourism.wechat.wechat.mapper.WechatAppMapper;
import com.yjtech.wisdom.tourism.wechat.wechat.query.WechatAppQuery;
import com.yjtech.wisdom.tourism.wechat.wechat.vo.ExtJsonVO;
import com.yjtech.wisdom.tourism.wechat.wxcommon.error.WxErrorException;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaOpenCommitExtInfo;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaOpenTabBar;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxOpenMaCategory;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxOpenMaSubmitAudit;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.message.WxOpenMaSubmitAuditMessage;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.result.*;
import com.yjtech.wisdom.tourism.wechat.wxopen.config.WxOpenProperties;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenComponentService;
import com.yjtech.wisdom.tourism.wechat.wxopen.service.WxOpenMaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yjtech.wisdom.tourism.common.constant.CacheKeyContants.WECHATAPP_STATUS_CACHE_KEY_PREFIX;


/**
 * Created by wuyongchong on 2019/9/23.
 */
@Slf4j
@Service
public class WechatAppService extends ServiceImpl<WechatAppMapper, WechatApp> {

    private static final Logger logger = LoggerFactory.getLogger(WechatAppService.class);

    @Autowired
    private WechatAppMapper wechatAppMapper;

    @Autowired
    private CodeTemplateService codeTemplateService;

    @Autowired
    private com.yjtech.wisdom.tourism.wechat.wechat.service.WechatTabbarService wechatTabbarService;

    @Autowired
    private WxOpenComponentService wxOpenComponentService;

    @Autowired
    private WxOpenMaService wxOpenMaService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private WechatAppCompanyMapper wechatAppCompanyMapper;


    public List<WechatApp> queryByCompanyId(Long companyId) {
        List<WechatApp> appList = wechatAppMapper.selectList(
                new QueryWrapper<WechatApp>().eq("company_id", companyId));
        if (null == appList) {
            return Lists.newArrayList();
        }
        return appList;
    }

    /**
     * 根据appId获取信息
     */
    public WechatApp getByAppId(String authorizerAppid) {
        List<WechatApp> appList = wechatAppMapper.selectList(
                new QueryWrapper<WechatApp>().eq("authorizer_appid", authorizerAppid)
                        .last("limit 1"));
        if (null != appList && appList.size() > 0) {
            return appList.get(0);
        }
        return null;
    }

    /**
     * 取消授权处理
     */
    public void unauthorize(String authorizerAppid) {
        WechatApp wechatApp = getByAppId(authorizerAppid);
        if (null != wechatApp) {
            wechatApp.setAuthorizeStatus(WechatAuthorizeStatus.CANCEL_AUTHORIZE);
            wechatApp.setAuditStatus(WechatAuditStatus.WAIT_SUBMIT_AUDIT);
            wechatApp.setReleaseStatus(WechatReleaseStatus.WAIT_RELEASE);
            wechatApp.setAuthorizeTime(new Date());
            wechatAppMapper.updateById(wechatApp);
        }
    }

    /**
     * 提交审核成功处理
     */
    public void auditSuccess(String appId) {
        WechatApp wechatApp = getByAppId(appId);
        if (null != wechatApp) {
            wechatApp.setAuditStatus(WechatAuditStatus.AUDIT_SUCCESS);
            wechatApp.setAuditSuccTime(new Date());
            wechatAppMapper.updateById(wechatApp);
        }
    }

    /**
     * 提交审核失败处理
     */
    public void auditFail(String appId, String reason) {
        WechatApp wechatApp = getByAppId(appId);
        if (null != wechatApp) {
            wechatApp.setAuditStatus(WechatAuditStatus.AUDIT_FAIL);
            wechatApp.setAuditReason(reason);
            wechatApp.setAuditFailTime(new Date());
            wechatAppMapper.updateById(wechatApp);
        }
    }

    //审核延后处理
    public void auditDelay(String appId, String reason) {
        WechatApp wechatApp = getByAppId(appId);
        if (null != wechatApp) {
            wechatApp.setAuditStatus(WechatAuditStatus.AUDIT_DELAY);
            wechatApp.setAuditReason(reason);
            wechatApp.setAuditFailTime(new Date());
            wechatAppMapper.updateById(wechatApp);
        }
    }


    /**
     * 发布成功处理
     */
    public void releaseSuccess(String appId) {
        WechatApp wechatApp = getByAppId(appId);
        if (null != wechatApp) {
            wechatApp.setReleaseTime(new Date());
            wechatApp.setReleaseStatus(WechatReleaseStatus.RELEASEED);
            wechatApp.setAuditStatus(WechatAuditStatus.WAIT_SUBMIT_AUDIT);
            wechatAppMapper.updateById(wechatApp);
        }
    }

    /**
     * 发布失败处理
     */
    public void releaseFail(String appId, String errmsg) {
        WechatApp wechatApp = getByAppId(appId);
        if (null != wechatApp) {
            wechatApp.setReleaseTime(new Date());
            wechatApp.setReleaseStatus(WechatReleaseStatus.RELEASE_FAIL);
            wechatApp.setReleaseReason(errmsg);
            wechatAppMapper.updateById(wechatApp);
        }
    }

    /**
     * 判断appId是否存在
     */
    public boolean appIdExsits(String authorizerAppid, Long id) {
        QueryWrapper<WechatApp> queryWrapper = new QueryWrapper<WechatApp>()
                .eq("authorizer_appid", authorizerAppid);
        if (id != null) {
            queryWrapper.ne("id", id);
        }
        Integer count = wechatAppMapper.selectCount(queryWrapper);
        return count >= 1;
    }

    /**
     * 设置域地址
     */
    public void setDomains(String appId) throws WxErrorException {

        WxOpenProperties wxOpenProperties = wxOpenComponentService.getWxOpenProperties();

        WxOpenMaDomainResult wxOpenMaDomainResult = wxOpenMaService
                .modifyDomain(appId, "set", wxOpenProperties.getRequestDomainList(),
                        wxOpenProperties.getWsrequestDomainList(),
                        wxOpenProperties.getUploadDomainList(),
                        wxOpenProperties.getDownloadDomainList());

        wxOpenMaService
                .setWebViewDomain(appId, "set", wxOpenProperties.getWebViewDomainList());
    }

    /**
     * 生成自定义配置信息
     */
    public ExtJsonVO genExtJsonObj(WechatApp entity) {

        ExtJsonVO extInfo = new ExtJsonVO();

        extInfo.setExtEnable(true);
        extInfo.setExtAppid(entity.getAuthorizerAppid());
        extInfo.setDirectCommit(false);
        Map<String, Object> extMap = Maps.newHashMap();
        extMap.put("appId", entity.getAuthorizerAppid());
        //extMap.put("companyId", entity.getCompanyId().toString());
        extInfo.setExt(extMap);

        return extInfo;
    }

    /**
     * 上传代码
     */
    @Transactional(rollbackFor = Exception.class)
    public void codeCommit(WechatApp entity) {

        CodeTemplate codeTemplate = Optional
                .ofNullable(codeTemplateService.getById(entity.getTemplateId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "模板不存在"));

        try {
            //设置小程序域名
            setDomains(entity.getAuthorizerAppid());

            //上传小程序代码
            entity.setCodeCommitTime(new Date());

            ExtJsonVO extJsonInfo = genExtJsonObj(entity);
            entity.setExtJson(JsonUtil.writeValueAsString(extJsonInfo));

            WxMaOpenCommitExtInfo extInfo = WxMaOpenCommitExtInfo.INSTANCE();
            extInfo.setExtEnable(extJsonInfo.getExtEnable());
            extInfo.setDirectCommit(extJsonInfo.getDirectCommit());
            extInfo.setExtAppid(entity.getAuthorizerAppid());
            extInfo.setExtMap(extJsonInfo.getExt());

            WxMaOpenTabBar tabbar = wechatTabbarService.getTabbar(entity.getTabbarId());
            if (StringUtils.isBlank(tabbar.getPosition())) {
                tabbar.setPosition("bottom");
            }
            extInfo.setTabBar(tabbar);

            logger.info(JsonUtil.writeValueAsString(extInfo));

            WxOpenResult result = wxOpenMaService
                    .codeCommit(entity.getAuthorizerAppid(), codeTemplate.getTemplateId(),
                            codeTemplate.getUserVersion(),
                            codeTemplate.getUserDesc(), extInfo);

            entity.setUserVersion(codeTemplate.getUserVersion());
            entity.setUserDesc(codeTemplate.getUserDesc());

            wechatAppMapper.updateById(entity);

        } catch (WxErrorException e) {
            log.error("微信请求错误",e);
            WechatErrorCode enumItem = EnumUtil
                    .getEnumItem(WechatErrorCode.class,
                            String.valueOf(e.getError().getErrorCode()));
            if (null != enumItem) {
                throw new CustomException(enumItem.getCode(), enumItem.getMessage());
            } else {
                throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "微信请求错误");
            }
        }
    }

    /**
     * 提交审核
     */
    @Transactional(rollbackFor = Exception.class)
    public void submitAudit(WechatApp entity) {

        CodeTemplate codeTemplate = Optional
                .ofNullable(codeTemplateService.getById(entity.getTemplateId()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "模板不存在"));

        try {
            //设置小程序域名
            setDomains(entity.getAuthorizerAppid());

            //上传小程序代码
            entity.setCodeCommitTime(new Date());

            ExtJsonVO extJsonInfo = genExtJsonObj(entity);
            entity.setExtJson(JsonUtil.writeValueAsString(extJsonInfo));

            WxMaOpenCommitExtInfo extInfo = WxMaOpenCommitExtInfo.INSTANCE();
            extInfo.setExtEnable(extJsonInfo.getExtEnable());
            extInfo.setDirectCommit(extJsonInfo.getDirectCommit());
            extInfo.setExtAppid(entity.getAuthorizerAppid());
            extInfo.setExtMap(extJsonInfo.getExt());

            WxMaOpenTabBar tabbar = wechatTabbarService.getTabbar(entity.getTabbarId());
            if (StringUtils.isBlank(tabbar.getPosition())) {
                tabbar.setPosition("bottom");
            }
            extInfo.setTabBar(tabbar);

            logger.info(JsonUtil.writeValueAsString(extInfo));

            WxOpenResult result = wxOpenMaService
                    .codeCommit(entity.getAuthorizerAppid(), codeTemplate.getTemplateId(),
                            codeTemplate.getUserVersion(),
                            codeTemplate.getUserDesc(), extInfo);

            //获取审核时可填写的类目信息
            WxOpenMaCategoryListResult maCategoryListResult = wxOpenMaService
                    .getCategoryList(entity.getAuthorizerAppid());
            List<WxOpenMaCategory> categoryList = maCategoryListResult.getCategoryList();

            //获取已上传的代码的页面列表
            WxOpenMaPageListResult pageListResult = wxOpenMaService
                    .getPageList(entity.getAuthorizerAppid());
            List<String> pageList = pageListResult.getPageList();

            //构建审核项列表
            WxOpenMaCategory wxOpenMaCategory = categoryList.get(0);
            WxOpenMaSubmitAudit submitAuditItem = new WxOpenMaSubmitAudit();
            submitAuditItem.setPagePath(pageList.get(0));
            submitAuditItem.setFirstClass(wxOpenMaCategory.getFirstClass());
            submitAuditItem.setSecondClass(wxOpenMaCategory.getSecondClass());
            submitAuditItem.setFirstId(wxOpenMaCategory.getFirstId());
            submitAuditItem.setSecondId(wxOpenMaCategory.getSecondId());

            WxOpenMaSubmitAuditMessage submitAuditMessage = new WxOpenMaSubmitAuditMessage();
            submitAuditMessage.setItemList(Lists.newArrayList(submitAuditItem));

            entity.setCategoryList(JsonUtil.writeValueAsString(categoryList));
            entity.setPageList(JsonUtil.writeValueAsString(pageList));
            entity.setItemList(JsonUtil.writeValueAsString(submitAuditMessage.getItemList()));
            entity.setSubmitAuditTime(new Date());

            //提交审核
            WxOpenMaSubmitAuditResult wxOpenMaSubmitAuditResult = wxOpenMaService
                    .submitAudit(entity.getAuthorizerAppid(), submitAuditMessage);

            entity.setAuditId(wxOpenMaSubmitAuditResult.getAuditId());
            entity.setAuditStatus(WechatAuditStatus.AUDITING);
            entity.setReleaseStatus(WechatReleaseStatus.WAIT_RELEASE);

            entity.setUserVersion(codeTemplate.getUserVersion());
            entity.setUserDesc(codeTemplate.getUserDesc());

            wechatAppMapper.updateById(entity);

        } catch (WxErrorException e) {
            log.error("微信请求错误",e);
            WechatErrorCode enumItem = EnumUtil
                    .getEnumItem(WechatErrorCode.class,
                            String.valueOf(e.getError().getErrorCode()));
            if (null != enumItem) {
                throw new CustomException(enumItem.getCode(), enumItem.getMessage());
            } else {
                throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "微信请求错误");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void undoCodeAudit(WechatApp entity) {
        try {
            WxOpenResult wxOpenResult = wxOpenMaService.undoCodeAudit(entity.getAuthorizerAppid());
            if (wxOpenResult.isSuccess()) {
                entity.setAuditStatus(WechatAuditStatus.AUDIT_WITHDRAW);
                wechatAppMapper.updateById(entity);
            }
        } catch (WxErrorException e) {
            log.error("微信请求错误",e);
            WechatErrorCode enumItem = EnumUtil
                    .getEnumItem(WechatErrorCode.class,
                            String.valueOf(e.getError().getErrorCode()));
            if (null != enumItem) {
                throw new CustomException(enumItem.getCode(), enumItem.getMessage());
            } else {
                throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "微信请求错误");
            }
        }
    }

    /**
     * 发布
     */
    @Transactional(rollbackFor = Exception.class)
    public void release(WechatApp entity) {
        try {
            WxOpenResult openResult = wxOpenMaService.releaesAudited(entity.getAuthorizerAppid());
            boolean success = openResult.isSuccess();
            if (openResult.isSuccess()) {
                releaseSuccess(entity.getAuthorizerAppid());
            } else {
                releaseFail(entity.getAuthorizerAppid(), openResult.getErrmsg());
            }
            if (!success) {
                JsonResult.error(ErrorCode.REQUEST_THIRDPARTY_FAILURE.getCode(), "发布失败");
            }
        } catch (WxErrorException e) {
            log.error("微信请求错误",e);
            WechatErrorCode enumItem = EnumUtil
                    .getEnumItem(WechatErrorCode.class,
                            String.valueOf(e.getError().getErrorCode()));
            if (null != enumItem) {
                throw new CustomException(enumItem.getCode(), enumItem.getMessage());
            } else {
                throw new CustomException(ErrorCode.REQUEST_THIRDPARTY_FAILURE, "发布失败");
            }
        }
    }

    public void enableAppFromCache(String appId) {
        stringRedisTemplate.opsForValue()
                .set(WECHATAPP_STATUS_CACHE_KEY_PREFIX + appId, EntityConstants.ENABLED.toString());
    }

    public void disableAppFromCache(String appId) {
        stringRedisTemplate.opsForValue().set(WECHATAPP_STATUS_CACHE_KEY_PREFIX + appId,
                EntityConstants.DISABLED.toString());
    }

    public boolean isAppDisabled(String appId) {
        String appStatus = stringRedisTemplate.opsForValue()
                .get(WECHATAPP_STATUS_CACHE_KEY_PREFIX + appId);

        if (StringUtils.isBlank(appStatus)) {
            WechatApp byAppId = getByAppId(appId);
            if (null == byAppId) {
                return true;
            }
            stringRedisTemplate.opsForValue()
                    .set(WECHATAPP_STATUS_CACHE_KEY_PREFIX + appId, byAppId.getStatus().toString());
            appStatus = stringRedisTemplate.opsForValue()
                    .get(WECHATAPP_STATUS_CACHE_KEY_PREFIX + appId);
        }
        if (StringUtils.isNotBlank(appStatus) && EntityConstants.DISABLED.toString()
                .equals(appStatus)) {
            return true;
        }
        return false;
    }

    public void updateCompanyName(Long companyId, String companyName) {
        UpdateWrapper<WechatApp> updateWrapper = Wrappers.<WechatApp>update();
        updateWrapper.set("company_name", companyName);
        updateWrapper.set("update_time", new Date());
        updateWrapper.eq("company_id", companyId);
        wechatAppMapper.update(null, updateWrapper);
    }

    public IPage<WechatApp> queryForPage(WechatAppQuery query) {

        QueryWrapper<WechatApp> queryWrapper = new QueryWrapper<>();

        if (null != query.getAppId()) {
            queryWrapper.eq("authorizer_appid", query.getAppId());
        }

        if (StringUtils.isNotBlank(query.getAppName())) {
            queryWrapper.like("app_name", query.getAppName().trim());
        }

        if (null != query.getCompanyId() || StringUtils.isNotBlank(query.getCompanyName())) {

            LambdaQueryWrapper<WechatAppCompany> lambdaQueryWrapper = new LambdaQueryWrapper<WechatAppCompany>();

            if (null != query.getCompanyId()) {
                lambdaQueryWrapper.eq(WechatAppCompany::getCompanyId, query.getCompanyId());
            }

            if (StringUtils.isNotBlank(query.getCompanyName())) {
                lambdaQueryWrapper
                        .like(WechatAppCompany::getCompanyName, query.getCompanyName().trim());
            }

            List<WechatAppCompany> selectList = wechatAppCompanyMapper
                    .selectList(lambdaQueryWrapper);

            if (CollectionUtils.isNotEmpty(selectList)) {
                List<String> appIdList = selectList.stream().map(item -> item.getAppId())
                        .collect(Collectors.toList());

                queryWrapper.in("authorizer_appid", appIdList);

            } else {
                queryWrapper.eq("authorizer_appid", "0");
            }
        }

        if (null != query.getStatus()) {
            queryWrapper.eq("status", query.getStatus());
        }

        queryWrapper.orderByDesc("create_time", "id");

        return wechatAppMapper
                .selectPage(new Page<WechatApp>(query.getPageNo(), query.getPageSize()),
                        queryWrapper);
    }

    public List<WechatApp> queryForList(WechatAppQuery query) {

        QueryWrapper<WechatApp> queryWrapper = new QueryWrapper<>();

        if (null != query.getAppId()) {
            queryWrapper.eq("authorizer_appid", query.getAppId());
        }

        if (StringUtils.isNotBlank(query.getAppName())) {
            queryWrapper.like("app_name", query.getAppName().trim());
        }

        if (null != query.getCompanyId() || StringUtils.isNotBlank(query.getCompanyName())) {

            LambdaQueryWrapper<WechatAppCompany> lambdaQueryWrapper = new LambdaQueryWrapper<WechatAppCompany>();

            if (null != query.getCompanyId()) {
                lambdaQueryWrapper.eq(WechatAppCompany::getCompanyId, query.getCompanyId());
            }

            if (StringUtils.isNotBlank(query.getCompanyName())) {
                lambdaQueryWrapper
                        .like(WechatAppCompany::getCompanyName, query.getCompanyName().trim());
            }

            List<WechatAppCompany> selectList = wechatAppCompanyMapper
                    .selectList(lambdaQueryWrapper);

            if (CollectionUtils.isNotEmpty(selectList)) {
                List<String> appIdList = selectList.stream().map(item -> item.getAppId())
                        .collect(Collectors.toList());

                queryWrapper.in("authorizer_appid", appIdList);

            } else {
                queryWrapper.eq("authorizer_appid", "0");
            }
        }

        if (null != query.getStatus()) {
            queryWrapper.eq("status", query.getStatus());
        }

        queryWrapper.orderByDesc("create_time", "id");

        return wechatAppMapper.selectList(queryWrapper);
    }
}
