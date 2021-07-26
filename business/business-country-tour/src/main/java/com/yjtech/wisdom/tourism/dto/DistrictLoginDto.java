package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 区县大数据登录
 *
 * @author renguangqian
 * @date 2021/7/22 16:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictLoginDto implements Serializable {

    private static final long serialVersionUID = -8496355901949116891L;

    /**
     * 登录类型 3-第三方，不使用验证码
     */
    private Integer loginType = 3;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

}
