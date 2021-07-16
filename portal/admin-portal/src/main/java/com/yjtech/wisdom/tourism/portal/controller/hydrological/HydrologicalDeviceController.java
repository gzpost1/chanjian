package com.yjtech.wisdom.tourism.portal.controller.hydrological;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.hydrological.entity.HydrologicalDevice;
import com.yjtech.wisdom.tourism.resource.hydrological.service.HydrologicalDeviceService;
import com.yjtech.wisdom.tourism.resource.hydrological.vo.HydrologicalDeviceCreateDto;
import com.yjtech.wisdom.tourism.resource.hydrological.vo.HydrologicalDeviceQuery;
import com.yjtech.wisdom.tourism.resource.hydrological.vo.HydrologicalDeviceUpdateDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理-水位水文信息监测
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/hydrological-device")
public class HydrologicalDeviceController {

    @Autowired
    private HydrologicalDeviceService deviceService;

    /**
     * 列表
     */
    @PostMapping("/queryForList")
    public JsonResult<List<HydrologicalDevice>> queryForList(@RequestBody HydrologicalDeviceQuery query) {
        QueryWrapper<HydrologicalDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq(StringUtils.isNotBlank(query.getStatus()), "status", query.getStatus());
        queryWrapper.orderByDesc("create_time");
        List<HydrologicalDevice> list = deviceService.list(queryWrapper);
        return JsonResult.success(list);
    }

    /**
     * 分页列表
     *
     * @param query 参数
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<HydrologicalDevice>> queryForPage(@RequestBody HydrologicalDeviceQuery query) {
        QueryWrapper<HydrologicalDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.eq(StringUtils.isNotBlank(query.getStatus()), "status", query.getStatus());
        queryWrapper.orderByDesc("create_time");
        IPage<HydrologicalDevice> pageResult = deviceService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     *
     * @param idParam id
     */
    @PostMapping("/queryForDetail")
    public JsonResult<HydrologicalDevice> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(deviceService.getById(idParam.getId()));
    }

    /**
     * 新增
     *
     * @param createDto 新增信息
     */
    @PostMapping("/create")
    public JsonResult<?> create(@RequestBody @Valid HydrologicalDeviceCreateDto createDto) {
        deviceService.snExists(createDto.getSn(), null);
        HydrologicalDevice hydrologicalDevice = BeanMapper.map(createDto, HydrologicalDevice.class);
        // 设置默认状态
        hydrologicalDevice.setStatus((byte) 1);
        hydrologicalDevice.setEquipStatus((byte) 1);
        deviceService.save(hydrologicalDevice);
        return JsonResult.ok();
    }

    /**
     * 更新
     *
     * @param updateDto 更新信息
     */
    @PostMapping("/update")
    public JsonResult<?> update(@RequestBody @Valid HydrologicalDeviceUpdateDto updateDto) {
        HydrologicalDevice hydrologicalDeviceInDb = deviceService.checkAndGet(updateDto.getId());
        deviceService.snExists(updateDto.getSn(), updateDto.getId());

        HydrologicalDevice hydrologicalDevice = BeanMapper.map(updateDto, HydrologicalDevice.class);
        hydrologicalDevice.setStatus(hydrologicalDeviceInDb.getStatus());
        hydrologicalDevice.setEquipStatus(hydrologicalDeviceInDb.getEquipStatus());
        deviceService.updateById(hydrologicalDevice);
        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam id
     */
    @PostMapping("/delete")
    public JsonResult<?> delete(@RequestBody @Valid IdParam idParam) {
        deviceService.removeById(idParam.getId());
        return JsonResult.ok();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam 更新状态
     */
    @PostMapping("/updateStatus")
    public JsonResult<?> updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        HydrologicalDevice entity = deviceService.getById(updateStatusParam.getId());
        entity.setStatus(updateStatusParam.getStatus());
        deviceService.updateById(entity);
        return JsonResult.ok();
    }

}
