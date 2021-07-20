package com.yjtech.wisdom.tourism.portal.controller.turnstile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.resource.turnstile.entity.Turnstile;
import com.yjtech.wisdom.tourism.resource.turnstile.service.TurnstileService;
import com.yjtech.wisdom.tourism.resource.turnstile.vo.TurnstileQuery;
import com.yjtech.wisdom.tourism.system.domain.IconSpotEnum;
import com.yjtech.wisdom.tourism.system.service.IconService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 大屏-闸机信息
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/screen/turnstile")
public class TurnstileController {

    @Autowired
    private TurnstileService turnstileService;

    @Autowired
    private IconService iconService;

    /**
     * 列表
     */
    @PostMapping("/queryForList")
    public JsonResult<List<Turnstile>> queryForList(@RequestBody TurnstileQuery query) {
        QueryWrapper<Turnstile> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq("status", "1");
        queryWrapper.orderByDesc("create_time");
        List<Turnstile> list = turnstileService.list(queryWrapper);
        return JsonResult.success(list);
    }

    /**
     * 分页列表
     *
     * @param query 参数
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<Turnstile>> queryForPage(@RequestBody TurnstileQuery query) {
        QueryWrapper<Turnstile> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq("status", "1");
        queryWrapper.orderByDesc("create_time");
        IPage<Turnstile> pageResult = turnstileService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }
}
