package com.yjtech.wisdom.tourism.portal.controller.broadcast;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastCreateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.dto.BroadcastUpdateDto;
import com.yjtech.wisdom.tourism.resource.broadcast.query.BroadcastDeviceQuery;
import com.yjtech.wisdom.tourism.resource.broadcast.service.BroadcastService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 后台管理-广播
 *
 * @author wuyongchong
 * @since 2020-09-16
 */
@RestController
@RequestMapping("/broadcast")
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    /**
     * 分页列表
     * @Param:  query
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<BroadcastEntity>> queryForPage(@RequestBody BroadcastDeviceQuery query) {
        QueryWrapper<BroadcastEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        queryWrapper.orderByDesc("create_time");
        IPage<BroadcastEntity> pageResult = broadcastService.page(new Page<>(query.getPageNo(), query.getPageSize()), queryWrapper);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     * @Param: idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForDetail")
    public JsonResult<BroadcastEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(broadcastService.getById(idParam.getId()));
    }

    /**
     * 新增
     * @Param: createDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid BroadcastCreateDto createDto) {

        BroadcastEntity entity = BeanMapper.map(createDto, BroadcastEntity.class);

        if (null == createDto.getStatus()) {
            entity.setStatus(EntityConstants.ENABLED);
        }
        broadcastService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 更新
     * @Param:  updateDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid BroadcastUpdateDto updateDto) {

        BroadcastEntity entity = BeanMapper.map(updateDto, BroadcastEntity.class);

        broadcastService.updateById(entity);

        return JsonResult.ok();
    }

    /**
     * 删除
     * @Param:
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {

        broadcastService.removeById(idParam.getId());

        return JsonResult.ok();
    }

    /**
     * 更新状态
     * @Param: param
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam param) {
        BroadcastEntity entity = broadcastService.getById(param.getId());
        entity.setStatus(param.getStatus());
        broadcastService.updateById(entity);
        return JsonResult.ok();
    }


}
