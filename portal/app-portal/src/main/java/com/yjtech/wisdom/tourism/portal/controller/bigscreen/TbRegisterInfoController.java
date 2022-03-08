package com.yjtech.wisdom.tourism.portal.controller.bigscreen;


import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.bigscreen.validate.RegisterValidationGroup;
import com.yjtech.wisdom.tourism.common.constant.AuditStatusConstants;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 投资方注册
     */
    @PostMapping("investor")
    public void investor(@RequestBody @Validated(RegisterValidationGroup.investor.class) TbRegisterInfoEntity registerInfoEntity) {
        encodepwd(registerInfoEntity);
        super.create(registerInfoEntity);
    }

    private void encodepwd(@Validated(RegisterValidationGroup.investor.class) @RequestBody TbRegisterInfoEntity registerInfoEntity) {
        String pwd = registerInfoEntity.getPwd();
        registerInfoEntity.setPwd(EncryptTypeHandler.AES.encrypt(registerInfoEntity.getPwd()));
    }

    /**
     * 业态方注册
     */
    @PostMapping("commercial")
    public void commercial(@RequestBody @Validated(RegisterValidationGroup.commercial.class) TbRegisterInfoEntity registerInfoEntity) {
        encodepwd(registerInfoEntity);
        super.create(registerInfoEntity);
    }

    /**
     * 运营方注册
     */
    @PostMapping("operator")
    public void operator(@RequestBody @Validated(RegisterValidationGroup.operator.class) TbRegisterInfoEntity registerInfoEntity) {
        encodepwd(registerInfoEntity);
        super.create(registerInfoEntity);
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
    public JsonResult<List<TbRegisterInfoEntity>> listCompany(@RequestBody @Validated TbRegisterInfoEntity tbRegisterInfoEntity) {
//        TbRegisterInfoEntity tbRegisterInfoEntity = TbRegisterInfoEntity.builder().blacklist(false).status(EntityConstants.ENABLED).auditStatus(AuditStatusConstants.SUCCESS).build();
        JsonResult<List<TbRegisterInfoEntity>> list = super.list(tbRegisterInfoEntity);
        return list;
    }


}
