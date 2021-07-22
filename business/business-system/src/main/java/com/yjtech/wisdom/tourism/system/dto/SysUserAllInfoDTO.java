package com.yjtech.wisdom.tourism.system.dto;

import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户全量信息
 *
 * @Date 2021/7/22 19:29
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUserAllInfoDTO implements Serializable {

    private static final long serialVersionUID = 7749341823451117103L;

    /**
     * 用户基础信息
     */
    private SysUser user;

    /**
     * 用户角色集合
     */
    private Set<String> roles;

    /**
     * 用户权限集合
     */
    private Set<String> permissions;

}
