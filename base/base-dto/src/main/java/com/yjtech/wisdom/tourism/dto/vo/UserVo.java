package com.yjtech.wisdom.tourism.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author renguangqian
 * @date 2021/7/22 15:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends DateBaseVo implements Serializable {

    private static final long serialVersionUID = 2519431468535747165L;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 账户
     */
    @NotNull
    private String userName;

    /**
     * 密码
     */
    @NotNull
    private String password;
}
