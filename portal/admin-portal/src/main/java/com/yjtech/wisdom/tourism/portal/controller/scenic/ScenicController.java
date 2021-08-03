package com.yjtech.wisdom.tourism.portal.controller.scenic;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.scenic.dto.ScenicCreateDto;
import com.yjtech.wisdom.tourism.resource.scenic.dto.ScenicUpdateDto;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.resource.scenic.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static com.yjtech.wisdom.tourism.resource.scenic.utils.ScenicUtil.dateCompare;

/**
 * 后台管理-景区管理
 * @Description
 * @Author zc
 * @Date 2021-07-15 13:41
 */
@RestController
@RequestMapping("/scenic")
public class ScenicController {

    @Autowired
    private ScenicService scenicService;

    /**
     * 分页查询
     * @Param:  query
     * @return:
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ScenicEntity>> queryForPage(@RequestBody ScenicPageQuery query) {
        return JsonResult.success(scenicService.queryForPage(query));
    }

    /**
     * 增加
     * @Param:  createDto
     * @return:
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid ScenicCreateDto createDto) {
        ScenicEntity entity = BeanMapper.copyBean(createDto, ScenicEntity.class);
        //时间比较
        dateCompare(entity);
        //默认为启用状态
        entity.setStatus((byte) 1);
        scenicService.save(entity);
        return JsonResult.success();
    }

    /**
     * 修改
     * @Param:  updateDto
     * @return:
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid ScenicUpdateDto updateDto) {
        ScenicEntity entity = BeanMapper.copyBean(updateDto, ScenicEntity.class);
        //时间比较
        dateCompare(entity);
        scenicService.updateById(entity);
        return JsonResult.success();
    }

    /**
     * 详情
     * @Param:  idParam
     * @return:
     */
    @PostMapping("/queryForDetail")
    public JsonResult<ScenicEntity> queryForDetail(@RequestBody @Valid IdParam idParam) {
        ScenicEntity entity = scenicService.getOne(
                new LambdaQueryWrapper<ScenicEntity>().eq(ScenicEntity::getId, idParam.getId()));
        return JsonResult.success(entity);
    }

    /**
     * 删除
     * @Param:  idParam
     * @return:
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid IdParam idParam) {
       scenicService.remove(new LambdaQueryWrapper<ScenicEntity>().eq(ScenicEntity::getId, idParam.getId()));
       return JsonResult.success();
    }

    /**
     * 启用/停用
     * @Param:  param
     * @return:
     */
    @PostMapping("/updateStatus")
    public JsonResult updateStatus(@RequestBody @Valid UpdateStatusParam param) {
        ScenicEntity entity = Optional.ofNullable(
                scenicService.getOne(new LambdaQueryWrapper<ScenicEntity>().eq(ScenicEntity::getId, param.getId())))
                .orElseThrow(() -> new CustomException("景区不存在"));
        entity.setStatus(param.getStatus());
        scenicService.updateById(entity);
        return JsonResult.success();
    }
}
