package com.yjtech.wisdom.tourism.wechat.wechat.query;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 12:27
 */
@Data
public class AppIdQuery implements Serializable {

    @NotBlank(message = "小程序appId不能为空")
    private String appId;
}
