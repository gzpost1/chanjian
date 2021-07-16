package com.yjtech.wisdom.tourism.wechat.wechat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaOpenTabBar;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小程序tabbar
 * </p>
 *
 * @author wuyongchong
 * @since 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "tb_wechat_tabbar", autoResultMap = true)
public class WechatTabbar extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private Long id;


    /**
     * 企业名称
     */
    private String name;


    /**
     * tabBar
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private WxMaOpenTabBar tabBar;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;


}
