package com.yjtech.wisdom.tourism.wechat.wechat.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by wuyongchong on 2019/9/29.
 */
@Data
public class WechatAppForm implements Serializable {

    private Long id;

    /**
     * 企业ID
     */
    //@NotNull(message = "请选择企业")
    //private Long companyId;

    /**
     * 小程序名称
     */
    @NotBlank(message = "小程序名称不能为空")
    private String appName;

    /**
     * 授权信息-授权方appid
     */
    @NotBlank(message = "小程序AppId不能为空")
    private String authorizerAppid;

    /**
     * 代码模板ID
     */
//    @NotNull(message = "小程序模板ID不能为空")
    private Long templateId;

    /**
     * 小程序底部栏目
     */
//    @NotNull(message = "小程序底部栏目不能为空")
    private Long tabbarId;


    /**
     * 小程序描述
     */
    private String description;

}
