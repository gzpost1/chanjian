package com.yjtech.wisdom.tourism.wechat.wechat.enumeration;

import com.yjtech.wisdom.tourism.common.constant.EnumInterface;
import com.yjtech.wisdom.tourism.common.exception.IExceptionType;

/**
 * 微信小程序错误码 Created by wuyongchong on 2019/12/12.
 */
public enum WechatErrorCode implements IExceptionType, EnumInterface {

  SYSTEM_BUSY("-1", "系统繁忙"),

  ERROR_40013("40013", "appid 无效"),
  ERROR_89002("89002", "该公众号/小程序未绑定微信开放平台帐号"),

  ERROR_85013("85013", "无效的自定义配置"),
  ERROR_85014("85014", "无效的模版编号"),
  ERROR_85043("85013", "模版错误"),
  ERROR_85044("85044", "代码包超过大小限制"),
  ERROR_85045("85045", "ext_json 有不存在的路径"),
  ERROR_85046("85046", "tabBar 中缺少 path"),
  ERROR_85047("85047", "pages 字段为空"),
  ERROR_85048("85048", "ext_json 解析失败"),
  ERROR_80082("80082", "没有权限使用该插件"),
  ERROR_80067("80067", "找不到使用的插件"),
  ERROR_80066("80066", "非法的插件版本"),

  ERROR_86000("86000", "不是由第三方代小程序进行调用"),
  ERROR_86001("86001", "不存在第三方的已经提交的代码"),
  ERROR_85006("85006", "标签格式错误"),
  ERROR_85007("85007", "页面路径错误"),
  ERROR_85008("85008", "类目填写错误"),
  ERROR_85009("85009", "已经有正在审核的版本"),
  ERROR_85010("85010", "item_list 有项目为空"),
  ERROR_85011("85011", "标题填写错误"),
  ERROR_85023("85023", "审核列表填写的项目数不在 1-5 以内"),
  ERROR_85077("85077", "小程序类目信息失效（类目中含有官方下架的类目，请重新选择类目）"),
  ERROR_86002("86002", "小程序还未设置昵称、头像、简介。请先设置完后再重新提交"),
  ERROR_85085("85085", "小程序提审数量已达本月上限"),
  ERROR_85086("85086", "提交代码审核之前需提前上传代码"),
  ERROR_85087("85087", "小程序已使用 api navigateToMiniProgram，请声明跳转 appid 列表后再次提交"),

  ERROR_87013("87013", "撤回次数达到上限（每天一次，每个月 10 次）"),

  ERROR_85019("85019", "没有审核版本"),
  ERROR_85020("85020", "审核状态未满足发布");

  private String code;
  private String msg;

  private WechatErrorCode(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public String code() {
    return code;
  }

  public String msg() {
    return msg;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return msg;
  }

  @Override
  public Object getValue() {
    return code;
  }
}