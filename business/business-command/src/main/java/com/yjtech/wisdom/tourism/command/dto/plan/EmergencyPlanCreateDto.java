package com.yjtech.wisdom.tourism.command.dto.plan;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Getter
@Setter
public class EmergencyPlanCreateDto implements Serializable {
    /**
    * 名称
    */
    @NotBlank(message = "名称不能为空")
    @Length(max = 30,message = "名称长度必须小于30位")
    private String name;

    /**
    * 类型
    */
    @NotNull(message = "类型不能为空")
    private Long type;

    /**
    * 发布机构
    */
    @NotBlank(message = "发布机构不能为空")
    @Length(max = 30,message = "发布机构长度必须小于30位")
    private String releaseInstitution;

    /**
    * 发布日期
    */
    @NotNull(message = "发布时间不能为空")
    private Date releaseDate;

    /**
    * 发布内容
    */
    @NotBlank(message = "发布内容不能为空")
    private String content;



}
