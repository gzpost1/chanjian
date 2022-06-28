package com.yjtech.wisdom.tourism.bigscreen.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 快速注册
 *
 * @author renguangqian
 * @date 2022年06月23日 14:25
 */
@Data
public class QuickRegisterVo {
    /**
     * 公司名称
     */
    @Length(max = 30)
    @NotBlank(message = "企业名称不能为空")
    private String companyName;
    /**
     * 联系人手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Length(max = 13, message = "请输入正确手机号")
    private String phone;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String pwd;
}
