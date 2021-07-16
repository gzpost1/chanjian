package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.annotation.RepeatSubmit;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 信息操作处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
  @Autowired private SysConfigService configService;

  /** 获取参数配置列表 */
  @PreAuthorize("@ss.hasPermi('system:config:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysConfig>> list(SysConfig config) {
    IPage<SysConfig> list = configService.selectConfigList(config);
    return JsonResult.success(list);
  }

  @Log(title = "参数管理", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:config:export')")
  @GetMapping("/export")
  public JsonResult export(SysConfig config) {
    IPage<SysConfig> list = configService.selectConfigList(config);
    ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
    return util.exportExcel(list.getRecords(), "参数数据");
  }

  /** 根据参数编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:config:query')")
  @GetMapping(value = "/{configId}")
  public JsonResult getInfo(@PathVariable Long configId) {
    return JsonResult.success(configService.selectConfigById(configId));
  }

  /** 根据参数键名查询参数值 */
  @GetMapping(value = "/configKey/{configKey}")
  public JsonResult getConfigKey(@PathVariable String configKey) {
    return JsonResult.success(configService.selectConfigByKey(configKey));
  }

  /** 新增参数配置 */
  @PreAuthorize("@ss.hasPermi('system:config:add')")
  @Log(title = "参数管理", businessType = BusinessType.INSERT)
  @PostMapping
  @RepeatSubmit
  public JsonResult add(@Validated @RequestBody SysConfig config) {
    if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
      return JsonResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    config.setCreateUser(SecurityUtils.getUserId());
    return success(configService.insertConfig(config));
  }

  /** 修改参数配置 */
  @PreAuthorize("@ss.hasPermi('system:config:edit')")
  @Log(title = "参数管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysConfig config) {
    if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
      return JsonResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
    }
    config.setUpdateUser(SecurityUtils.getUserId());
    return success(configService.updateConfig(config));
  }

  /** 删除参数配置 */
  @PreAuthorize("@ss.hasPermi('system:config:remove')")
  @Log(title = "参数管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{configIds}")
  public JsonResult remove(@PathVariable Long[] configIds) {
    return success(configService.deleteConfigByIds(configIds));
  }

  /** 清空缓存 */
  @PreAuthorize("@ss.hasPermi('system:config:remove')")
  @Log(title = "参数管理", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clearCache")
  public JsonResult clearCache() {
    configService.clearCache();
    return JsonResult.success();
  }
}
