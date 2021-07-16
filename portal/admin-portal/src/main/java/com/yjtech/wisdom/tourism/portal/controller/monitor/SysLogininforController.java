package com.yjtech.wisdom.tourism.portal.controller.monitor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.system.domain.SysLogininfor;
import com.yjtech.wisdom.tourism.system.service.SysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
  @Autowired private SysLogininforService logininforService;

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysLogininfor>> list(SysLogininfor logininfor) {
    IPage<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
    return JsonResult.success(list);
  }

  @Log(title = "登陆日志", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
  @GetMapping("/export")
  public JsonResult export(SysLogininfor logininfor) {
    IPage<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
    ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
    return util.exportExcel(list.getRecords(), "登陆日志");
  }

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
  @Log(title = "登陆日志", businessType = BusinessType.DELETE)
  @DeleteMapping("/{infoIds}")
  public JsonResult remove(@PathVariable Long[] infoIds) {
    return JsonResult.success(logininforService.deleteLogininforByIds(infoIds));
  }

  @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
  @Log(title = "登陆日志", businessType = BusinessType.CLEAN)
  @DeleteMapping("/clean")
  public JsonResult clean() {
    logininforService.cleanLogininfor();
    return JsonResult.success();
  }
}
