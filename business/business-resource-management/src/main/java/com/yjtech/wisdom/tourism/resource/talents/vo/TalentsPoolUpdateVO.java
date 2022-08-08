package com.yjtech.wisdom.tourism.resource.talents.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 人才库管理(TbTalentsPool) 编辑VO
 *
 * @author horadirm
 * @since 2022-08-06 09:37:15
 */
@Data
public class TalentsPoolUpdateVO implements Serializable {
    private static final long serialVersionUID = 7308542048523011405L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 姓名
     */
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,6}$", message = "姓名不合法，仅支持中文且最多6位")
    private String name;

    /**
     * 单位
     */
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,99}$", message = "单位不合法，仅支持中文且最多99位")
    private String organization;

    /**
     * 职位/职称
     */
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,99}$", message = "职位/职称不合法，仅支持中文且最多99位")
    private String jobTitle;

    /**
     * 主攻领域
     */
    @Size(min = 1, max = 300, message = "主攻领域不合法，仅支持99位")
    private String mainAreas;

    /**
     * 备注
     */
    @Size(min = 1, max = 300, message = "备注不合法，仅支持99位")
    private String remark;

}