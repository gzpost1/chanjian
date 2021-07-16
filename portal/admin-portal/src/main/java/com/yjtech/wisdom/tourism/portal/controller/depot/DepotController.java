package com.yjtech.wisdom.tourism.portal.controller.depot;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.CreateDepotDto;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.QueryDepotDto;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.UpdateDepotDto;
import com.yjtech.wisdom.tourism.resource.depot.service.DepotService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

/**
 * 后台管理-停车场
 * @Description
 * @Author zc
 * @Date 2020-09-15 13:41
 */
@RestController
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    /**
     * 增加
     * @Param:  depotDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid CreateDepotDto depotDto) {
        DepotEntity entity = new DepotEntity();
        BeanUtils.copyProperties(depotDto, entity);
        entity.setDepotId(IdWorker.getId());
        if(depotDto.getWarnUsedRate().compareTo(depotDto.getAlarmUsedRate()) == 1){
            throw new CustomException(ErrorCode.PARAM_WRONG, "报警使用率需大于等于预警使用率");
        }
        depotService.save(entity);
        return JsonResult.success();
    }

    /**
     * 删除
     * @Param: idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
        depotService.remove(new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, idParam.getId()));
        return JsonResult.success();
    }

    /**
     * 修改
     * @Param: depotDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid UpdateDepotDto depotDto) {
        if(depotDto.getWarnUsedRate().compareTo(depotDto.getAlarmUsedRate()) == 1){
            throw new CustomException(ErrorCode.PARAM_WRONG, "报警使用率需大于等于预警使用率");
        }
        DepotEntity depot = Optional.ofNullable(depotService.getOne(new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, depotDto.getDepotId())))
                .orElseThrow(() -> new CustomException("id不存在"));
        BeanUtils.copyProperties(depotDto, depot);
        depotService.update(depot, new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, depotDto.getDepotId()));
        return JsonResult.success();
    }

    /**
     * 查询列表
     * @Param:  queryDepotDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<DepotEntity>> queryForPage(@RequestBody QueryDepotDto queryDepotDto) {
        Page page = new Page(queryDepotDto.getPageNo(), queryDepotDto.getPageSize());
        LambdaQueryWrapper<DepotEntity> query = new LambdaQueryWrapper<>();
        query.like(StringUtils.isNoneBlank(queryDepotDto.getName()), DepotEntity::getName, queryDepotDto.getName());
        query.eq(!isNull(queryDepotDto.getStatus()), DepotEntity::getStatus, queryDepotDto.getStatus());
        query.orderByDesc(DepotEntity::getCreateTime);
        return JsonResult.success(depotService.page(page, query));
    }

    /**
     * 查询详情
     * @Param:  idParam
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/queryForDetail")
    public JsonResult<DepotEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(depotService.getOne(new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, idParam.getId())));
    }

    /**
     * 启用/停用
     * @Param:  depotDto
     * @return:
     * @Author: zc
     * @Date: 2021-07-14
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam depotDto) {
        DepotEntity depot = Optional.ofNullable(depotService.getOne(new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, depotDto.getId())))
                .orElseThrow(() -> new CustomException("id不存在"));
        depot.setStatus(depotDto.getStatus());
        depotService.update(depot, new LambdaQueryWrapper<DepotEntity>().eq(DepotEntity::getDepotId, depotDto.getId()));
        return JsonResult.success();
    }
}
