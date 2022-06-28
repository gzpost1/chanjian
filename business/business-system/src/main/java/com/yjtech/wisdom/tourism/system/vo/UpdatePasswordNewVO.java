package com.yjtech.wisdom.tourism.system.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改密码 VO
 *
 * @Author horadirm
 * @Date 2021/8/10 11:17
 */
@Data
public class UpdatePasswordNewVO implements Serializable {

    private static final long serialVersionUID = -558887241221341653L;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
