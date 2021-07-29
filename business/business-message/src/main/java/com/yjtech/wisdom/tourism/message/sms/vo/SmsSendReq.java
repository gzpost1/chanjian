package com.yjtech.wisdom.tourism.message.sms.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xulei
 * @create 2021-03-05 10:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSendReq {

    private String cpcode;

    private String msg;

    private String mobiles;

    private String excode;

    private String templetid;

    private String sign;
}
