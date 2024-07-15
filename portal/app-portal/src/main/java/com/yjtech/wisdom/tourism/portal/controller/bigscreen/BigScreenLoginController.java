package com.yjtech.wisdom.tourism.portal.controller.bigscreen;

import com.chinaunicom.yunjingtech.sms.service.SmsService;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.bigscreen.service.TbRegisterInfoService;
import com.yjtech.wisdom.tourism.common.annotation.IgnoreAuth;
import com.yjtech.wisdom.tourism.common.config.AppConfig;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.constant.PhoneCodeEnum;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.ServletUtils;
import com.yjtech.wisdom.tourism.framework.web.service.ScreenTokenService;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginBody;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.model.ScreenLoginUser;
import com.yjtech.wisdom.tourism.mybatis.typehandler.EncryptTypeHandler;
import com.yjtech.wisdom.tourism.system.vo.UpdatePasswordNewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 认证模块
 */
@Slf4j
@RestController
@RequestMapping("/screen/auth")
public class BigScreenLoginController {
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private SmsService smsService;
    @Autowired
    private TbRegisterInfoService companyInfoService;
    @Autowired
    private ScreenTokenService tokenService;

    /**
     * 大屏用户登录
     * @param loginBody
     * @return
     */
    @PostMapping("login")
    @IgnoreAuth
    public JsonResult<String> login(@RequestBody @Validated ScreenLoginBody loginBody) {
        //校验手机验证码
        String funcName =
                appConfig.getVersion() + "_" + PhoneCodeEnum.SYS_APP_LOGIN.getType();
        String phoneCode = loginBody.getPhoneCode();
        String phone = loginBody.getPhone();
        String loginPwd = loginBody.getPassword();
        TbRegisterInfoEntity companyInfo = companyInfoService.queryByPhone(phone);
        AssertUtil.isFalse(Objects.isNull(companyInfo), "该公司不存在");

        //注册流程改变，快速注册直接成功，屏蔽该断言
        // AssertUtil.isFalse(!Objects.equals(companyInfo.getAuditStatus(),1), "该企业尚未完成注册！");

        AssertUtil.isFalse(Objects.equals(companyInfo.getStatus(), EntityConstants.DISABLED), "该公司状态不正常");
        if (Objects.nonNull(phoneCode)) {
            AssertUtil.isTrue(smsService
                            .validPhoneCode(phone, funcName, phoneCode),
                    "短信验证码输入有误");
        }else if(Objects.nonNull(loginPwd)){
//            AssertUtil.isFalse(!Objects.equals(EncryptTypeHandler.AES.encrypt(loginPwd),companyInfo.getPwd()),"输入密码不正确");
        }else{
            return JsonResult.error("请输入手机验证码或者登录密码");
        }
        ScreenLoginUser loginUser = new ScreenLoginUser();
        BeanUtils.copyProperties(companyInfo,loginUser);
        return JsonResult.success(tokenService.createToken(loginUser));
    }

    /**
     * 退出登录
     * @param
     * @return
     */
    @PostMapping("loginout")
    public JsonResult loginout() {
        //获取当前用户信息
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Assert.notNull(loginUser, ErrorCode.NO_PERMISSION.getMessage());
        //登出
        tokenService.delLoginUser(loginUser.getToken());
        return JsonResult.success();
    }
    /**
     * 查询登陆用户信息
     * @param
     * @return
     */
    @PostMapping("queryLoginInfo")
    public JsonResult queryLoginInfo() {
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return JsonResult.success(loginUser);
    }

    /**
     * 密码修改
     * @param
     * @return
     */
    @PostMapping("/updatePassword")
    public JsonResult updatePassword(@RequestBody @Validated UpdatePasswordNewVO vo) {
        //获取当前用户信息
        ScreenLoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Assert.notNull(loginUser, ErrorCode.NO_PERMISSION.getMessage());

        // 更新密码
        TbRegisterInfoEntity tbRegisterInfoEntity = companyInfoService.queryByPhone(loginUser.getPhone());
        tbRegisterInfoEntity.setPwd(EncryptTypeHandler.AES.encrypt(vo.getNewPassword()));
        companyInfoService.updateById(tbRegisterInfoEntity);

        // 修改缓存等相关操作
        ScreenLoginUser loginInfo = new ScreenLoginUser();
        BeanUtils.copyProperties(tbRegisterInfoEntity,loginInfo);
        return JsonResult.success(tokenService.createToken(loginInfo));
    }

}
