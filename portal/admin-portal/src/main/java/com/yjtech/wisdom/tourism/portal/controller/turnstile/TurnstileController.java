package com.yjtech.wisdom.tourism.portal.controller.turnstile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.turnstile.entity.Turnstile;
import com.yjtech.wisdom.tourism.resource.turnstile.service.TurnstileService;
import com.yjtech.wisdom.tourism.resource.turnstile.vo.TurnstileCreateDto;
import com.yjtech.wisdom.tourism.resource.turnstile.vo.TurnstileQuery;
import com.yjtech.wisdom.tourism.resource.turnstile.vo.TurnstileUpdateDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理-闸机信息管理
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/turnstile")
public class TurnstileController {

    @Autowired
    private TurnstileService turnstileService;

    /**
     * 列表
     */
    @PostMapping("/queryForList")
    public JsonResult<List<Turnstile>> queryForList(@RequestBody TurnstileQuery query) {
        QueryWrapper<Turnstile> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq(StringUtils.isNotBlank(query.getStatus()), "status", query.getStatus());
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
        queryWrapper.eq(StringUtils.isNotBlank(query.getStatus()), "status", query.getStatus());
        queryWrapper.orderByDesc("create_time");
        IPage<Turnstile> pageResult = turnstileService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam id
     */
    @PostMapping("/queryForDetail")
    public JsonResult<Turnstile> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(turnstileService.getById(idParam.getId()));
    }

    /**
     * 新增
     *
     * @param createDto 新增信息
     */
    @PostMapping("/create")
    public JsonResult<?> create(@RequestBody @Valid TurnstileCreateDto createDto) {
        turnstileService.snExists(createDto.getSn(), null);
        Turnstile turnstile = BeanMapper.map(createDto, Turnstile.class);
        // 设置默认状态
        turnstile.setStatus((byte) 1);
        turnstile.setEquipStatus((byte) 1);
        turnstileService.save(turnstile);
        return JsonResult.ok();
    }

    /**
     * 更新
     *
     * @param updateDto 更新信息
     */
    @PostMapping("/update")
    public JsonResult<?> update(@RequestBody @Valid TurnstileUpdateDto updateDto) {
        Turnstile turnstileInDb = turnstileService.checkAndGet(updateDto.getId());
        turnstileService.snExists(updateDto.getSn(), updateDto.getId());

        Turnstile turnstile = BeanMapper.map(updateDto, Turnstile.class);
        turnstile.setStatus(turnstileInDb.getStatus());
        turnstile.setEquipStatus(turnstileInDb.getEquipStatus());
        turnstileService.updateById(turnstile);
        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam id
     */
    @PostMapping("/delete")
    public JsonResult<?> delete(@RequestBody @Valid IdParam idParam) {
        turnstileService.removeById(idParam.getId());
        return JsonResult.ok();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam 更新状态
     */
    @PostMapping("/updateStatus")
    public JsonResult<?> updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        Turnstile entity = turnstileService.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        turnstileService.updateById(entity);
        return JsonResult.ok();
    }

}
