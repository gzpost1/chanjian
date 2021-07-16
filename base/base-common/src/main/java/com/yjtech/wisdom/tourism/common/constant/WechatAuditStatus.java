package com.yjtech.wisdom.tourism.common.constant;

/**
 * 审核状态(-1：待提交审核 0：审核成功 1:审核失败 2:审核中 3:已撤回 4:审核延后) Created by wuyongchong on 2019/11/4.
 */
public class WechatAuditStatus {

  //待提交审核
  public static final Byte WAIT_SUBMIT_AUDIT = -1;

  //审核成功
  public static final Byte AUDIT_SUCCESS = 0;

  //审核失败
  public static final Byte AUDIT_FAIL = 1;

  //审核中
  public static final Byte AUDITING = 2;

  //已撤回
  public static final Byte AUDIT_WITHDRAW = 3;

  //审核延后
  public static final Byte AUDIT_DELAY = 4;
}
