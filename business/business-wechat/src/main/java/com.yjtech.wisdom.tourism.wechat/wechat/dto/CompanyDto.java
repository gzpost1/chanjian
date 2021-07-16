package com.yjtech.wisdom.tourism.wechat.wechat.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 12:30
 */
@Data
public class CompanyDto implements Serializable {

    /**
     * 企业ID
     */
    @NotNull(message = "企业ID不能为空")
    private Long companyId;

    /**
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空")
    private String companyName;
}
