package com.yjtech.wisdom.tourism.portal.controller.wifi;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiCreateDto;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiPageQueryDto;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiUpdateDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiEntity;
import com.yjtech.wisdom.tourism.resource.wifi.service.WifiService;
import com.yjtech.wisdom.tourism.resource.wifi.vo.WifiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 后台管理-系统资源-wifi
 *
 * @author 李波
 * @description: 后台管理-系统配置-图标库
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/wifi")
public class WifiController {
    @Autowired
    private WifiService wifiService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<WifiVo>> queryForPage(@RequestBody WifiPageQueryDto query) {
        return JsonResult.success(wifiService.queryForPage(query));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<WifiVo> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(wifiService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid WifiCreateDto createDto) {

        AssertUtil.isTrue(wifiService.driveExist(createDto.getDeviceId(), null), "该设备编号已存在");

        WifiEntity entity = BeanMapper.map(createDto, WifiEntity.class);
        entity.setId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        entity.setEquipStatus((byte) 1);
        entity.setStatus((byte) 1);
        wifiService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid WifiUpdateDto updateDto) {

        AssertUtil.isTrue(wifiService.driveExist(updateDto.getDeviceId(),updateDto.getId()), "该设备编号已存在");

        WifiEntity entity = BeanMapper.map(updateDto, WifiEntity.class);

        wifiService.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     *
     * @param idParam
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        wifiService.removeById(idParam.getId());
        return JsonResult.ok();
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam updateStatusParam) {
        wifiService.updateStatus(updateStatusParam);

        return JsonResult.ok();
    }
}
