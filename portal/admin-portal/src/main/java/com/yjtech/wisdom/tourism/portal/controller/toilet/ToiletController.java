package com.yjtech.wisdom.tourism.portal.controller.toilet;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.resource.toilet.service.ToiletService;
import com.yjtech.wisdom.tourism.resource.toilet.dto.ToiletCreateDto;
import com.yjtech.wisdom.tourism.resource.toilet.dto.ToiletPageQueryDto;
import com.yjtech.wisdom.tourism.resource.toilet.dto.ToiletUpdateDto;
import com.yjtech.wisdom.tourism.resource.toilet.entity.ToiletEntity;
import com.yjtech.wisdom.tourism.resource.toilet.vo.ToiletVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 后台管理-系统资源-厕所
 *
 * @author 李波
 * @description: 后台管理-系统配置-厕所
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/toilet")
public class ToiletController {
    @Autowired
    private ToiletService toiletService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<ToiletVo>> queryForPage(@RequestBody ToiletPageQueryDto query) {
        return JsonResult.success(toiletService.queryForPage(query));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<ToiletVo> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(toiletService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid ToiletCreateDto createDto) {

        AssertUtil.isTrue(toiletService.driveExist(createDto.getDeviceId(), null), "该设备编号已存在");

        ToiletEntity entity = BeanMapper.map(createDto, ToiletEntity.class);
        entity.setId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        entity.setEquipStatus((byte) 1);
        entity.setStatus((byte) 1);
        toiletService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid ToiletUpdateDto updateDto) {

        AssertUtil.isTrue(toiletService.driveExist(updateDto.getDeviceId(),updateDto.getId()), "该设备编号已存在");

        ToiletEntity entity = BeanMapper.map(updateDto, ToiletEntity.class);

        toiletService.updateById(entity);

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
        toiletService.removeById(idParam.getId());
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
        toiletService.updateStatus(updateStatusParam);

        return JsonResult.ok();
    }
}
