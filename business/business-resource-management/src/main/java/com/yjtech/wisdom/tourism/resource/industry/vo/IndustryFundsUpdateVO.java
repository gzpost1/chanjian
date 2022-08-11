package com.yjtech.wisdom.tourism.resource.industry.vo;

import com.yjtech.wisdom.tourism.resource.industry.entity.ProjectInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * 产业资金管理(TbIndustryFunds) 编辑VO
 *
 * @author horadirm
 * @since 2022-08-06 10:13:36
 */
@Data
public class IndustryFundsUpdateVO implements Serializable {
    private static final long serialVersionUID = -3268768432416885387L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 企业名称
     */
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,30}$", message = "姓名不合法，仅支持中文且最多30位")
    private String companyName;

    /**
     * 主营行业
     */
    @Size(min = 1, max = 300, message = "主营行业不合法，仅支持最多300位")
    private String mainIndustry;

    /**
     * 项目情况
     */
    private List<ProjectInfo> projectInfo;

}