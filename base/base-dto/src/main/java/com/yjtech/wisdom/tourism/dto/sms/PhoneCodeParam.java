package com.yjtech.wisdom.tourism.dto.sms;

import com.yjtech.wisdom.tourism.common.validator.IsMobile;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by wuyongchong on 2019/10/10.
 */
@Data
public class PhoneCodeParam implements Serializable {
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @IsMobile(message = "手机号格式有误")
    private String phone;


}
