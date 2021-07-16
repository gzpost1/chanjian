package com.yjtech.wisdom.tourism.wechat.wechat.dto;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 12:29
 */
@Data
public class BindAppCompanyDto implements Serializable {

    /**
     * 小程序appId
     */
    @NotBlank(message = "小程序appId不能为空")
    private String appId;

    /**
     * 小程序名称
     */
    @NotBlank(message = "小程序名称不能为空")
    private String appName;

    @Valid
    private List<CompanyDto> companyList;

}
