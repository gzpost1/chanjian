package com.yjtech.wisdom.tourism.portal.controller.bigscreen;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.yjtech.wisdom.tourism.bigscreen.dto.QuickRegisterVo;
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
import com.yjtech.wisdom.tourism.common.enums.CompanyRoleEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseCurdController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import com.yjtech.wisdom.tourism.project.entity.TbProjectLabelEntity;
import com.yjtech.wisdom.tourism.project.service.TbProjectLabelService;
import com.yjtech.wisdom.tourism.system.domain.TagEntity;
import com.yjtech.wisdom.tourism.system.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * v1.5_注册信息
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

    @Autowired
    private TbRegisterInfoService tbRegisterInfoService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TbProjectLabelService projectLabelService;

    /**
     * 快速注册
     *
     * @return
     */
    @PostMapping("/quickRegister")
    @IgnoreAuth
    public JsonResult quickRegister (@RequestBody @Validated QuickRegisterVo quickRegisterVo) {
        TbRegisterInfoEntity tbRegisterInfoEntity = JSONObject.parseObject(JSONObject.toJSONString(quickRegisterVo), TbRegisterInfoEntity.class);
        validatePhone(tbRegisterInfoEntity.getPhone());
        encodepwd(tbRegisterInfoEntity);
        // 新增四种类型
        List<String> type = Arrays.asList("1","2","3","4");
        tbRegisterInfoEntity.setType(type);
        // 添加四种类型的标签
        addLabel(tbRegisterInfoEntity);

        return JsonResult.success(super.create(tbRegisterInfoEntity));
    }

    /**
     * 注册/完善 企业信息
     *
     * @return
     */
    @PostMapping("/registerCompanyInfo")
    @IgnoreAuth
    public JsonResult registerCompanyInfo (@RequestBody @Validated TbRegisterInfoEntity registerInfoEntity) {
        // 有id则代表 更新， 无id 则代表插入
        if (null == registerInfoEntity.getId()) {
            validatePhone(registerInfoEntity.getPhone());
            encodepwd(registerInfoEntity);
            super.create(registerInfoEntity);
        }else {
            TbRegisterInfoEntity dbData = tbRegisterInfoService.getById(registerInfoEntity.getId());
            if (ObjectUtils.isEmpty(dbData)) {
                throw new CustomException("数据库不存在id为： " + registerInfoEntity.getId() + "的数据");
            }

            // 不是用户当前手机号，校验手机号码是否存在
            if (!dbData.getPhone().equals(registerInfoEntity.getPhone())) {
                validatePhone(registerInfoEntity.getPhone());
            }
            super.update(registerInfoEntity);
        }

        TbRegisterInfoEntity tbRegisterInfoEntity = tbRegisterInfoService.queryByPhone(registerInfoEntity.getPhone());
        // 更新缓存
        ScreenLoginUser loginUser = new ScreenLoginUser();
        BeanUtils.copyProperties(tbRegisterInfoEntity,loginUser);
        return JsonResult.success(tokenService.createToken(loginUser));
    }

    /**
     * 详细信息查询
     * @param idParam
     * @return
     */
    @Override
    @PostMapping("/queryForDetail")
    public JsonResult<TbRegisterInfoEntity> queryForDetail(
            @RequestBody @Validated IdParam idParam) {
        return JsonResult.success(service.getById(idParam.getId()));
    }

    /**
     * 查询微信注册用户状态
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryWechatRegisterStatus")
    @IgnoreAuth
    public JsonResult<TbRegisterInfoEntity> queryWechatRegisterStatus (@RequestBody @Validated IdParam idParam) {
        Object one = service.getOne(new LambdaQueryWrapper<TbRegisterInfoEntity>().eq(TbRegisterInfoEntity::getWeChatUserId, idParam.getId()));
        TbRegisterInfoEntity tbRegisterInfoEntity = JSONObject.parseObject(JSONObject.toJSONString(one), TbRegisterInfoEntity.class);
        return JsonResult.success(tbRegisterInfoEntity);
    }


    /**
     * 大屏 企业分布
     *
     * @return
     */
    @IgnoreAuth
    @PostMapping("listCompany")
    public JsonResult<List<TbRegisterInfoEntity>> listCompany(@RequestBody TbRegisterInfoParam param) {
        TbRegisterInfoEntity tbRegisterInfoEntity = TbRegisterInfoEntity.builder()
                .blacklist(false)
                .status(EntityConstants.ENABLED)
                .auditStatus(AuditStatusConstants.SUCCESS)
                .likeAreaCode(AreaUtils.trimCode(AreaUtils.trimCode(param.getAreaCode())))
                .build();
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
    public JsonResult<IPage<TbRegisterInfoEntity>> queryForPageByType(@RequestBody @Validated TbRegisterInfoParam param) {
        return JsonResult.success(tbRegisterInfoService.queryForPageByType(param));
    }

    /**
     * 根据登陆企业标签推荐企业
     *
     * @return
     */
    @PostMapping("recommendCompany")
    public List<TbRegisterInfoEntity> recommendCompany(@RequestBody @Validated RecommendParam param) {
//        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return tbRegisterInfoService.recommendCompany(param);
    }







    //----------------- 废弃 ----------------------//

    /**
     * 投资方注册
     */
    @PostMapping("investor")
    @IgnoreAuth
    public JsonResult investor(@RequestBody @Validated(RegisterValidationGroup.investor.class) TbRegisterInfoEntity registerInfoEntity) {
        validatePhone(registerInfoEntity.getPhone());
//        validateSms(registerInfoEntity);
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
//        validateSms(registerInfoEntity);
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
//        validateSms(registerInfoEntity);
        encodepwd(registerInfoEntity);
        return JsonResult.success(super.create(registerInfoEntity));
    }

    private void addLabel(TbRegisterInfoEntity tbRegisterInfoEntity){
        // 查询所有标签
        List<TagEntity> tageList = tagService.list();
        Map<String, List<String>> tagMap = new HashMap<>();
        tageList.forEach(tagEntity -> {
            tagMap.put(tagEntity.getEnterpriseRole(), tagEntity.getTagInfo());
        });

        List<TbProjectLabelEntity> proList = projectLabelService.list();
        List<String> proTag = proList.stream().map(TbProjectLabelEntity::getName).collect(Collectors.toList());

        tbRegisterInfoEntity.setInvestmentLabel(tagMap.get(CompanyRoleEnum.COMPANY_ROLE_INVESTOR.getDescribe()));
        tbRegisterInfoEntity.setOperationLabel(tagMap.get(CompanyRoleEnum.COMPANY_ROLE_OPERATOR.getDescribe()));
        tbRegisterInfoEntity.setCommercialLabel(tagMap.get(CompanyRoleEnum.COMPANY_ROLE_BUSINESS.getDescribe()));
        tbRegisterInfoEntity.setProjectLabel(proTag);
    }

    //----------------- 废弃 ----------------------//

}
