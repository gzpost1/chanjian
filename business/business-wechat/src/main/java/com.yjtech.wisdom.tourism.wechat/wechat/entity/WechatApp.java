package com.yjtech.wisdom.tourism.wechat.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * tb_wechat_app
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_wechat_app")
public class WechatApp extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 企业ID
     */
    //private Long companyId;

    /**
     * 企业名称
     */
    //private String companyName;

    /**
     * 小程序名称
     */
    private String appName;

    /**
     * 授权信息-授权方appid
     */
    private String authorizerAppid;

    /**
     * 授权信息-授权方接口调用凭据
     */
    @JsonIgnore
    private String authorizerAccessToken;

    /**
     * 授权信息-令牌有效期 默认2小时
     */
    @JsonIgnore
    private Integer authorizerExpires;

    /**
     * 授权信息-接口调用凭据刷新令牌
     */
    @JsonIgnore
    private String authorizerRefreshToken;

    /**
     * 授权信息-授权给开发者的权限集列表 json格式
     */
    private String funcInfo;

    /**
     * 授权时间
     */
    private Date authorizeTime;

    /**
     * 授权状态(1：已授权 2:取消授权)
     */
    private Byte authorizeStatus;

    /**
     * 授权方昵称
     */
    private String nickName;

    /**
     * 授权方头像
     */
    private String headImg;

    /**
     * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    private Byte serviceTypeInfo;

    /**
     * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    private Byte verifyTypeInfo;

    /**
     * 授权方公众号的原始ID
     */
    private String userName;

    /**
     * 公众号的主体名称
     */
    private String principalName;

    /**
     * 授权方公众号所设置的微信号
     */
    private String alias;

    /**
     * 用以了解以下功能的开通状况（0代表未开通，1代表已开通）： open_store:是否开通微信门店功能 open_scan:是否开通微信扫商品功能 open_pay:是否开通微信支付功能
     * open_card:是否开通微信卡券功能 open_shake:是否开通微信摇一摇功能 json格式
     */
    private String businessInfo;

    /**
     * 二维码图片的URL
     */
    private String qrcodeUrl;

    /**
     * 帐号介绍
     */
    private String signature;

    /**
     * 可根据这个字段判断是否为小程序类型授权
     */
    private String miniProgramInfo;

    /**
     * 上传小程序代码时间
     */
    private Date codeCommitTime;

    /**
     * 代码模板ID
     */
    private Long templateId;

    /**
     * 第三方自定义的配置
     */
    private String extJson;

    /**
     * 代码版本号
     */
    private String userVersion;

    /**
     * 代码描述
     */
    private String userDesc;

    /**
     * 可填选的类目列表
     */
    private String categoryList;

    /**
     * 页面配置列表
     */
    private String pageList;

    /**
     * 代码包提交审核时间
     */
    private Date submitAuditTime;

    /**
     * 提交审核项列表
     */
    private String itemList;

    /**
     * 审核编号
     */
    private Long auditId;

    /**
     *  审核状态(-1：待提交审核 0：审核成功 1:审核失败 2:审核中 3:已撤回 4:审核延后)
     */
    private Byte auditStatus;

    /**
     * 审核被拒绝时，返回的拒绝原因
     */
    private String auditReason;

    /**
     * 审核成功时间
     */
    private Date auditSuccTime;

    /**
     * 审核失败时间
     */
    private Date auditFailTime;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 发布状态，其中1为成功，2失败
     */
    private Byte releaseStatus;

    /**
     * 发布失败原因
     */
    private String releaseReason;

    /**
     * 小程序底部栏目
     */
    private Long tabbarId;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 小程序描述
     */
    private String description;
}