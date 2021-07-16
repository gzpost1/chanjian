package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysDictDataService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据字典数据信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
  @Autowired private SysDictDataService dictDataService;

  @Autowired private SysDictTypeService dictTypeService;

  /** 查询字典数据列表 */
  @PreAuthorize("@ss.hasPermi('system:dict:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysDictData>> list(SysDictData dictData) {
    IPage<SysDictData> list = dictDataService.selectDictDataList(dictData);
    return JsonResult.success(list);
  }

  /** 导出字典数据 */
  @Log(title = "字典数据", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:dict:export')")
  @GetMapping("/export")
  public JsonResult export(SysDictData dictData) {
    IPage<SysDictData> list = dictDataService.selectDictDataList(dictData);
    ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
    return util.exportExcel(list.getRecords(), "字典数据");
  }

  /** 查询字典数据详细 */
  @PreAuthorize("@ss.hasPermi('system:dict:query')")
  @GetMapping(value = "/{dictCode}")
  public JsonResult getInfo(@PathVariable Long dictCode) {
    return JsonResult.success(dictDataService.selectDictDataById(dictCode));
  }

  /** 根据字典类型查询字典数据信息 */
  @GetMapping(value = "/type/{dictType}")
  public JsonResult dictType(@PathVariable String dictType) {
    return JsonResult.success(dictTypeService.selectDictDataByType(dictType));
  }

  /** 新增字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:add')")
  @Log(title = "字典数据", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysDictData dict) {
    dict.setCreateUser(SecurityUtils.getUserId());
    return success(dictDataService.insertDictData(dict));
  }

  /** 修改保存字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:edit')")
  @Log(title = "字典数据", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysDictData dict) {
    dict.setUpdateUser(SecurityUtils.getUserId());
    return success(dictDataService.updateDictData(dict));
  }

  /** 删除字典类型 */
  @PreAuthorize("@ss.hasPermi('system:dict:remove')")
  @Log(title = "字典类型", businessType = BusinessType.DELETE)
  @DeleteMapping("/{dictCodes}")
  public JsonResult remove(@PathVariable Long[] dictCodes) {
    return success(dictDataService.deleteDictDataByIds(dictCodes));
  }
}
