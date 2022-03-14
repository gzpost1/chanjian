package com.yjtech.wisdom.tourism.portal.controller.bigscreen;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.yjtech.wisdom.tourism.bigscreen.dto.RecommendParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.bigscreen.validate.RegisterValidationGroup;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.constant.AuditStatusConstants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.PhoneCodeEnum;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 注册信息
 *
 * @author Mujun
 * @since 2022-03-01
 */
@Slf4j
@RestController
@RequestMapping("/screen/register")
public class TbRegisterInfoController extends BaseCurdController<TbRegisterInfoService, TbRegisterInfoEntity, TbRegisterInfoParam> {

    @Autowired
    private SmsService smsService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ScreenTokenService tokenService;

    /**
     * 投资方注册
     */
    @PostMapping("investor")
    @IgnoreAuth
    public JsonResult investor(@RequestBody @Validated(RegisterValidationGroup.investor.class) TbRegisterInfoEntity registerInfoEntity) {
        validatePhone(registerInfoEntity.getPhone());
        validateSms(registerInfoEntity);
        encodepwd(registerInfoEntity);
        return JsonResult.success(super.create(registerInfoEntity));
    }

    private void validatePhone(String phone) {
        AssertUtil.isFalse(!service.checkPhone(phone), "该手机号已注册不能重复注册");
    }

    private void validateSms(TbRegisterInfoEntity registerInfoEntity) {
        //校验手机验证码
        String funcName =
                appConfig.getVersion() + "_" + PhoneCodeEnum.SYS_APP_LOGIN.getType();
        AssertUtil.isTrue(smsService
                        .validPhoneCode(registerInfoEntity.getPhone(), funcName, registerInfoEntity.getPhoneCode()),
                "短信验证码输入有误");
    }

    private void encodepwd(@Validated(RegisterValidationGroup.investor.class) @RequestBody TbRegisterInfoEntity registerInfoEntity) {
        String pwd = registerInfoEntity.getPwd();
        registerInfoEntity.setPwd(EncryptTypeHandler.AES.encrypt(registerInfoEntity.getPwd()));
    }

    /**
     * 业态方注册
     *
     * @return
     */
    @PostMapping("commercial")
    @IgnoreAuth
    public JsonResult<JsonResult> commercial(@RequestBody @Validated(RegisterValidationGroup.commercial.class) TbRegisterInfoEntity registerInfoEntity) {
        validatePhone(registerInfoEntity.getPhone());
        validateSms(registerInfoEntity);
        encodepwd(registerInfoEntity);
        return JsonResult.success(super.create(registerInfoEntity));
    }

    /**
     * 运营方注册
     *
     * @return
     */
    @PostMapping("operator")
    @IgnoreAuth
    public JsonResult<JsonResult> operator(@RequestBody @Validated(RegisterValidationGroup.operator.class) TbRegisterInfoEntity registerInfoEntity) {
        validatePhone(registerInfoEntity.getPhone());
        validateSms(registerInfoEntity);
        encodepwd(registerInfoEntity);
        return JsonResult.success(super.create(registerInfoEntity));
    }

    /**
     * 大屏 企业分布
     *
     * @return
     */
    @PostMapping("listCompany")
    public JsonResult<List<TbRegisterInfoEntity>> listCompany() {
        TbRegisterInfoEntity tbRegisterInfoEntity = TbRegisterInfoEntity.builder().blacklist(false).status(EntityConstants.ENABLED).auditStatus(AuditStatusConstants.SUCCESS).build();
        JsonResult<List<TbRegisterInfoEntity>> list = super.list(tbRegisterInfoEntity);
        return list;
    }

    /**
     * 大屏 根据类型找 企业 1.投资方 2.业态方 3.运营方
     *
     * @return
     */
    @PostMapping("queryForPageByType")
    @IgnoreAuth
    public JsonResult<Page<TbRegisterInfoEntity>> queryForPageByType(@RequestBody @Validated TbRegisterInfoParam param) {
        param.setAuditStatus(1);
        param.setBlacklist(false);
        param.setDescs(new String[]{TbRegisterInfoEntity.AUDIT_TIME});
        JsonResult<Page<TbRegisterInfoEntity>> pageJsonResult = super.queryForPage(param);
        return pageJsonResult;
    }

    /**
     * 根据登陆企业所在地推荐企业
     *
     * @return
     */
    @PostMapping("recommendCompany")
    public List<TbRegisterInfoEntity> recommendCompany(@RequestBody @Validated RecommendParam param) {
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String areaCode = loginUser.getAreaCode();
        TbRegisterInfoEntity queryRegisterInfoEntity = TbRegisterInfoEntity.builder().areaCode(areaCode).type(param.getType()).build();
        List<TbRegisterInfoEntity> list = service.list(queryRegisterInfoEntity);
        return list;
    }

//    /**
//     * 详细信息查询
//     * @param idParam
//     * @return
//     */
//    @Override
//    @PostMapping("/queryForDetail")
//    public JsonResult<TbRegisterInfoEntity> queryForDetail(
//            @RequestBody @Valid IdParam idParam) {
//        return JsonResult.success(service.getById(idParam.getId()));
//    }

}
