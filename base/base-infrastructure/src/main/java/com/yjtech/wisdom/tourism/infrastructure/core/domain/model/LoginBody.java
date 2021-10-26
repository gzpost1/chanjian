package com.yjtech.wisdom.tourism.infrastructure.core.domain.model;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author liuhong
 */
@Data
public class LoginBody {
  /** 用户名 */
  private String username;

  /** 用户密码 */
  private String password;

  /** 验证码 */
  private String code;

  /** 唯一标识 */
  private String uuid = "";

  private Boolean appUser;

  /**
   * 推送标识
   */
  private String pushToken;

  /**
   * 是否需要验证码 0否  1/不填 需要
   */
  private String isNeedPic;

  /**
   * 0正常 1长时间token
   */
  private String tokenType;
}
