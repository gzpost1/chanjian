package com.yjtech.wisdom.tourism.systemconfig.temp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 系统配置-大屏模板库-创建
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigTempCreateDto {

    /**
     * name 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    @Length(max = 50, message = "模板名称最多50位")
    private String name;

    /**
     * img_url 模板缩略图
     */
    @NotBlank(message = "模板缩略图不能为空")
    private String imgUrl;

    /**
     * temp_data 模板数据([0,1][15,16])
     */
    @NotEmpty(message = "模板数据不能为空")
    private Integer[][] tempData;

}