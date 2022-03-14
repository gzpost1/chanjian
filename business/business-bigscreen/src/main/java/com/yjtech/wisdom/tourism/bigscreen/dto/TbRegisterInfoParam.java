package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 注册信息
 *
 * @author MJ~
 * @since 2022-03-02
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbRegisterInfoParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
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
     * 所在地区域编码
     */
    private String areaCode;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Byte status;

    @TableLogic
    @Builder.Default
    private Byte deleted = 0;


    /**
     * 审核状态 0.待审核 1.通过 2.驳回
     */
    private Integer auditStatus;

    /**
     * 是否是加入黑名单
     */
    private Boolean blacklist;

    /**
     * 联系人
     */
    private String contact;


}
