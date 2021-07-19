package com.yjtech.wisdom.tourism.command.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
/**
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Getter
@Setter
public class EmergencyPlanTypeCreateDto implements Serializable {
    /**
    * 事件名称
    */
    @NotBlank(message = "类型名称不能为空")
    @Length(max = 30,message = "事件名称长度必须小于30位")
    private String name;



}
