package com.yjtech.wisdom.tourism.wechat.wechat.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatTabbar;
import com.yjtech.wisdom.tourism.wechat.wechat.mapper.WechatTabbarMapper;
import com.yjtech.wisdom.tourism.wechat.wxopen.bean.ma.WxMaOpenTabBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小程序tabbar 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2020-04-03
 */
@Service
public class WechatTabbarService extends ServiceImpl<WechatTabbarMapper, WechatTabbar> {

    @Autowired
    private WechatTabbarMapper wechatTabbarMapper;

    public WxMaOpenTabBar getTabbar(Long id) {
        WechatTabbar entity = wechatTabbarMapper.selectById(id);
        return entity.getTabBar();
    }

}
