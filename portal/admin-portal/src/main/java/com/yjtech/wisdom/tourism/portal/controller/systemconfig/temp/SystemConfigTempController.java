package com.yjtech.wisdom.tourism.portal.controller.systemconfig.temp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.core.domain.IdParam;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.IdWorker;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.systemconfig.temp.dto.SystemconfigTempCreateDto;
import com.yjtech.wisdom.tourism.systemconfig.temp.dto.SystemconfigTempPageQueryDto;
import com.yjtech.wisdom.tourism.systemconfig.temp.dto.SystemconfigTempUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.temp.entity.SystemconfigTempEntity;
import com.yjtech.wisdom.tourism.systemconfig.temp.service.SystemconfigTempService;
import com.yjtech.wisdom.tourism.systemconfig.temp.vo.SystemconfigTempVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 后台管理-系统配置-大屏博模板库
 *
 * @author 李波
 * @description: 后台管理-系统配置-图标库
 * @date 2021/7/211:40
 */
@RestController
@RequestMapping("/systemconfig/temp")
public class SystemConfigTempController {

    @Autowired
    private SystemconfigTempService systemconfigTempService;

    /**
     * 分页
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<SystemconfigTempVo>> queryForPage(@RequestBody SystemconfigTempPageQueryDto query) {
        return JsonResult.success(systemconfigTempService.queryForPage(query));
    }

    /**
     * 详情
     *
     * @param idParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<SystemconfigTempVo> queryForDetail(@RequestBody @Valid IdParam idParam) {
        return JsonResult.success(systemconfigTempService.queryForDetail(idParam.getId()));
    }


    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid SystemconfigTempCreateDto createDto) {
        AssertUtil.isTrue(systemconfigTempService.findNameExist(createDto.getName(),null),"该模板名称已存在，请修改！");

        SystemconfigTempEntity entity = new SystemconfigTempEntity();
        BeanUtils.copyProperties(createDto,entity);
        entity.setId(IdWorker.getInstance().nextId());
        entity.setDeleted((byte) 0);
        systemconfigTempService.save(entity);

        return JsonResult.ok();
    }

    /**
     * 编辑
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid SystemconfigTempUpdateDto updateDto) {
        AssertUtil.isTrue(systemconfigTempService.findNameExist(updateDto.getName(),updateDto.getId()),"该模板名称已存在，请修改！");

        //首先校验模板是否被使用，如果已经被使用则不可修改
        int existnums = Optional.ofNullable(systemconfigTempService.findTempMenusNum(updateDto.getId())).orElse(0);
        if (existnums > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "已有大屏菜单关联，不可编辑，请更改后再编辑！");
        }

        SystemconfigTempEntity entity = new SystemconfigTempEntity();
        BeanUtils.copyProperties(updateDto,entity);


        systemconfigTempService.updateById(entity);

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
        //首先校验模板是否被使用，如果已经被使用则不可删除
        int existnums = Optional.ofNullable(systemconfigTempService.findTempMenusNum(idParam.getId())).orElse(0);
        if (existnums > 0) {
            return JsonResult.error(ErrorCode.BUSINESS_EXCEPTION.code(), "已有大屏菜单关联该模板，请取消关联后删除！");
        } else {
            systemconfigTempService.removeById(idParam.getId());
        }

        return JsonResult.ok();
    }

}
