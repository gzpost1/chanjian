package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictType;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
  @Autowired private SysDictTypeService dictTypeService;

  @PreAuthorize("@ss.hasPermi('system:dict:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysDictType>> list(SysDictType dictType) {
    IPage<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
    return JsonResult.success(list);
  }

  @Log(title = "字典类型", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:dict:export')")
  @GetMapping("/export")
  public JsonResult export(SysDictType dictType) {
    IPage<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
    ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
    return util.exportExcel(list.getRecords(), "字典类型");
  }

  /** 查询字典类型详细 */
  @PreAuthorize("@ss.hasPermi('system:dict:query')")
  @GetMapping(value = "/{dictId}")
  public JsonResult getInfo(@PathVariable Long dictId) {
    return JsonResult.success(dictTypeService.selectDictTypeById(dictId));
  }

  /** 新增字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:add')")
  @Log(title = "字典类型", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysDictType dict) {
    if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
      return JsonResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
    }
    dict.setCreateUser(SecurityUtils.getUserId());
    return success(dictTypeService.insertDictType(dict));
  }

  /** 修改字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:edit')")
  @Log(title = "字典类型", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysDictType dict) {
    if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
      return JsonResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
    }
    dict.setUpdateUser(SecurityUtils.getUserId());
    return success(dictTypeService.updateDictType(dict));
  }

  /** 删除字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:remove')")
  @Log(title = "字典类型", businessType = BusinessType.DELETE)
  @DeleteMapping("/{dictIds}")
  public JsonResult remove(@PathVariable Long[] dictIds) {
    return success(dictTypeService.deleteDictTypeByIds(dictIds));
  }

  /** 清空缓存 */
  @PreAuthorize("@ss.hasPermi('system:dict:remove')")
  @Log(title = "字典类型", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clearCache")
  public JsonResult clearCache() {
    dictTypeService.clearCache();
    // 删除缓存后更新缓存 避免工具类取不到缓存报空指针
    dictTypeService.init();
    return JsonResult.success();
  }

  /** 获取字典选择框列表 */
  @GetMapping("/optionselect")
  public JsonResult optionselect() {
    List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
    return JsonResult.success(dictTypes);
  }
}
