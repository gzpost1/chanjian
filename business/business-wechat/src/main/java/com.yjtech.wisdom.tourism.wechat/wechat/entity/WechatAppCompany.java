package com.yjtech.wisdom.tourism.wechat.wechat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @author: wuyongchong
 * @date: 2020/8/27 11:05
 */
@Data
@TableName("tb_wechat_app_company")
public class WechatAppCompany implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 小程序appId
     */
    private String appId;

    /**
     * 小程序名称
     */
    private String appName;

    /**
     * 企业ID
     */
    private Long companyId;

    /**
     * 企业名称
     */
    private String companyName;

}
