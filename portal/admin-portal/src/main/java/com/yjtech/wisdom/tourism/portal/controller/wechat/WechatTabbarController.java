package com.yjtech.wisdom.tourism.portal.controller.wechat;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.infrastructure.constant.EntityConstants;
import com.yjtech.wisdom.tourism.wechat.wechat.entity.WechatTabbar;
import com.yjtech.wisdom.tourism.wechat.wechat.service.WechatTabbarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序底部栏目管理
 *
 * @author wuyongchong
 * @since 2020/4/3.
 */
@RestController
@RequestMapping("/wechat-tabbar")
public class WechatTabbarController {

    @Autowired
    private WechatTabbarService wechatTabbarService;

    /**
     * 小程序底部栏目选择列表
     */
    @PostMapping("/listForSelect")
    @ResponseBody
    public JsonResult<List<WechatTabbar>> listForSelect() {
        QueryWrapper<WechatTabbar> queryWrapper = new QueryWrapper<WechatTabbar>()
                .eq("status", EntityConstants.ENABLED);
        queryWrapper.orderByAsc("sort");
        List<WechatTabbar> list = wechatTabbarService.list(queryWrapper);
        return JsonResult.success(list);
    }

}
