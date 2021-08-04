package com.yjtech.wisdom.tourism.infrastructure.core.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author liuhong
 */
@Data
public class SysUser extends BaseEntity {
  private static final long serialVersionUID = 1L;

  /** 用户ID */
  private Long userId;

  /** 部门ID */
  private Long deptId;

  /** 用户账号 */
  @NotBlank(message = "用户账号不能为空")
  @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
  private String userName;

  /** 用户昵称 */
  @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
  private String nickName;

  /** 用户邮箱 */
  @Email(message = "邮箱格式不正确")
  @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
  private String email;

  /** 手机号码 */
  @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
  private String phonenumber;

  /** 用户性别 */
  private String sex;

  /** 用户头像 */
  private String avatar;

  /** 密码 */
  @JsonIgnore @JsonProperty private String password;

  /** 盐加密 */
  private String salt;

  /** 帐号状态（0正常 1停用） */
  private String status;

  /** 最后登陆IP */
  private String loginIp;

  /** 最后登陆时间 */
  private Date loginDate;

  /** 部门对象 */
  private SysDept dept;

  /** 角色对象 */
  private List<SysRole> roles;

  /** 角色组 */
  private Long[] roleIds;

  /** 岗位组 */
  private Long[] postIds;

  /** 备注 */
  @Size(max = 100, message = "备注长度不能超过100个字符")
  private String remark;

  /** app推送数据所需的token */
  private String pushToken;

  /** 创建时间 */
  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  @TableField(exist = false)
  private String roleName;
  @TableField(exist = false)
  private Long roleId;

  public SysUser() {}

  public SysUser(Long userId) {
    this.userId = userId;
  }

  public boolean isAdmin() {
    return isAdmin(this.userId);
  }

  public static boolean isAdmin(Long userId) {
    return userId != null && 1L == userId;
  }
}
