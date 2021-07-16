package com.yjtech.wisdom.tourism.portal.controller.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.dto.area.AreaInfoVO;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.system.service.AreaService;
import com.yjtech.wisdom.tourism.systemconfig.area.dto.AreaAdminDto;
import com.yjtech.wisdom.tourism.systemconfig.area.dto.AreaAdminUpdateDto;
import com.yjtech.wisdom.tourism.systemconfig.area.entity.AreaAdminEntity;
import com.yjtech.wisdom.tourism.systemconfig.area.query.AbbreviationQuery;
import com.yjtech.wisdom.tourism.systemconfig.area.query.AreaAdminQuery;
import com.yjtech.wisdom.tourism.systemconfig.area.query.CodeParam;
import com.yjtech.wisdom.tourism.systemconfig.area.query.DeleteParam;
import com.yjtech.wisdom.tourism.systemconfig.area.service.AreaAdminService;
import com.yjtech.wisdom.tourism.systemconfig.area.vo.AreaAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 行政区域管理
 *
 * @author xulei
 * @since 2021-07-02
 */
@RestController
@RequestMapping("/area-admin")
public class AreaAdminController {

    @Autowired
    private AreaAdminService areaAdminService;

    @Autowired
    private AreaService areaService;


    /**
     * 分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/queryForPage")
    public JsonResult<IPage<AreaAdminVO>> queryForPage(@RequestBody AreaAdminQuery query) {
        IPage<AreaAdminVO> pageResult = areaAdminService.getBaseMapper().queryForPage(new Page<>(query.getPageNo(), query.getPageSize()), query);
        return JsonResult.success(pageResult);
    }

    /**
     * 详情
     * @param codeParam
     * @return
     */
    @PostMapping("/queryForDetail")
    public JsonResult<AreaAdminEntity> queryForDetail(@RequestBody @Valid CodeParam codeParam) {
        return JsonResult.success(areaAdminService.getById(codeParam.getCode()));
    }


    /**
     * 根据省编码获得简称
     *
     * @param query
     * @return
     */
    @PostMapping("/queryAbbreviationByProvince")
    public JsonResult<String> queryAbbreviationByProvince(@RequestBody @Valid AbbreviationQuery query) {
        AreaInfoVO areaInfoByCode = areaService.getAreaInfoByCode(query.getAreaCode());
        return JsonResult.success(Objects.isNull(areaInfoByCode)?null:areaInfoByCode.getAbbreviation());
    }

    /**
     * 新增
     *
     * @param createDto
     * @return
     */
    @PostMapping("/create")
    public JsonResult create(@RequestBody @Valid AreaAdminDto createDto) {
        AreaAdminEntity entity = areaAdminService.checkData(createDto);
        areaAdminService.save(entity);
        return JsonResult.ok();
    }

    /**
     * 更新
     *
     * @param updateDto
     * @return
     */
    @PostMapping("/update")
    public JsonResult update(@RequestBody @Valid AreaAdminUpdateDto updateDto) {
        areaAdminService.update(updateDto);
        return JsonResult.ok();
    }


    /**
     * 删除
     *
     * @param deleteParam
     * @return
     */
    @PostMapping("/delete")
    public JsonResult delete(@RequestBody @Valid DeleteParam deleteParam) {
        areaAdminService.removeById(deleteParam.getCode());
        return JsonResult.ok();
    }

    /**
     * 导出
     * @param query
     * @return
     */
    @PostMapping("/export")
    public JsonResult export(@RequestBody AreaAdminQuery query) {
        List<AreaAdminVO> areaAdminVOS = areaAdminService.getBaseMapper().queryForPage(query);
        ExcelUtil<AreaAdminVO> util = new ExcelUtil<>(AreaAdminVO.class);
        return util.exportExcel(areaAdminVOS, "行政区域");
    }

}
