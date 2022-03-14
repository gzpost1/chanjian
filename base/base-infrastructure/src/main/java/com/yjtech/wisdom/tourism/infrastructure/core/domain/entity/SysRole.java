package com.yjtech.wisdom.tourism.infrastructure.core.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 角色表 sys_role
 *
 * @author liuhong
 */
@Data
public class SysRole extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 角色ID */
  private Long roleId;

  /** 角色名称 */
  @NotBlank(message = "角色名称不能为空")
  @Size(min = 0, max = 30, message = "角色名称长度不能超过30个字符")
  private String roleName;

  /** 角色权限 */
//  @NotBlank(message = "权限字符不能为空")
  @Size(min = 0, max = 100, message = "权限字符长度不能超过100个字符")
  private String roleKey;

  /** 角色排序 */
//  @NotBlank(message = "显示顺序不能为空")
  private String roleSort;

  /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
  private String dataScope;

  /** 角色状态（0正常 1停用） */
  private String status;

  /** 用户是否存在此角色标识 默认不存在 */
  private boolean flag = false;

  /** 菜单组 */
  private Long[] menuIds;

  /** 部门组（数据权限） */
  private Long[] deptIds;

  /** 备注 */
  @Size(max = 100, message = "备注长度不能超过100个字符")
  private String remark;

  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  /** 权限类型 (详见字典account_authority_type)*/
  private Byte permissionType;

  public SysRole() {}

  public SysRole(Long roleId) {
    this.roleId = roleId;
  }

  public boolean isAdmin() {
    return isAdmin(this.roleId);
  }

  public static boolean isAdmin(Long roleId) {
    return roleId != null && 1L == roleId;
  }
}
