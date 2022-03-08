package com.yjtech.wisdom.tourism.infrastructure.core.domain.model;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * 大屏登陆用户
 *
 * @author Mujun
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenLoginUser {
    private static final long serialVersionUID = 1L;
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 企业类型
     */
    private String companyType;

    /**
     * 公司英文名
     */
    private String companyNameEn;

    /**
     * 联系人手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String pwd;

    private String address;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 经度
     */
    private BigDecimal longtitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 标签
     */
    private String label;

    /**
     * 注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 注册年份
     */
    private Integer registeredYear;

    /**
     * 主营业务
     */
    private String mainBusiness;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 投资方向
     */
    private String investmentDirection;

    /**
     * 曾参与投资的项目
     */
    private String investmentProject;

    /**
     * 资质
     */
    private String qualificationImgs;

    /**
     * 业态方向
     */
    private String commercialDirection;

    /**
     * 运营方向
     */
    private String operationDirection;

    /**
     * 1.投资方 2.业态方 3.运营方
     */
    private Integer type;

    private Long createUser;

    private Long updateUser;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Byte status;

    @TableLogic
    @Builder.Default
    private Byte deleted = 0;


    /** 用户唯一标识 */
    private String token;

    /** 登陆时间 */
    private Long loginTime;

    /** 过期时间 */
    private Long expireTime;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 权限列表 */
    private Set<String> permissions;

    /** 用户信息 */
    private SysUser user;

    /**
     * 0正常 1长时间token
     */
    private String tokenType;


}
