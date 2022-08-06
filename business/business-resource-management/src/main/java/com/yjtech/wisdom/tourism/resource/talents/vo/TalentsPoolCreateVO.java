package com.yjtech.wisdom.tourism.resource.talents.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 人才库管理(TbTalentsPool) 创建VO
 *
 * @author horadirm
 * @since 2022-08-06 09:37:15
 */
@Data
public class TalentsPoolCreateVO implements Serializable {
    private static final long serialVersionUID = -1570484459063264199L;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,6}$", message = "姓名不合法，仅支持中文且最多6位")
    private String name;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,99}$", message = "单位不合法，仅支持中文且最多99位")
    private String organization;

    /**
     * 职位/职称
     */
    @NotBlank(message = "职位/职称不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,99}$", message = "职位/职称不合法，仅支持中文且最多99位")
    private String jobTitle;

    /**
     * 主攻领域
     */
    @NotBlank(message = "主攻领域不能为空")
    @Size(min = 1, max = 300, message = "主攻领域不合法，仅支持300位")
    private String mainAreas;

    /**
     * 备注
     */
    @Size(min = 1, max = 300, message = "备注不合法，仅支持300位")
    private String remark;

}