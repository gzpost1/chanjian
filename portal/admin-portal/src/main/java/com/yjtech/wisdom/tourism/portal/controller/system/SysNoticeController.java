package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.domain.SysNotice;
import com.yjtech.wisdom.tourism.system.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 公告 信息操作处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/notice")
public class SysNoticeController extends BaseController {
  @Autowired private SysNoticeService noticeService;

  /** 获取通知公告列表 */
  @PreAuthorize("@ss.hasPermi('system:notice:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysNotice>> list(SysNotice notice) {
    startPage();
    IPage<SysNotice> list = noticeService.selectNoticeList(notice);
    return JsonResult.success(list);
  }

  /** 根据通知公告编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:notice:query')")
  @GetMapping(value = "/{noticeId}")
  public JsonResult getInfo(@PathVariable Long noticeId) {
    return JsonResult.success(noticeService.selectNoticeById(noticeId));
  }

  /** 新增通知公告 */
  @PreAuthorize("@ss.hasPermi('system:notice:add')")
  @Log(title = "通知公告", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysNotice notice) {
    notice.setCreateUser(SecurityUtils.getUserId());
    return success(noticeService.insertNotice(notice));
  }

  /** 修改通知公告 */
  @PreAuthorize("@ss.hasPermi('system:notice:edit')")
  @Log(title = "通知公告", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysNotice notice) {
    notice.setUpdateUser(SecurityUtils.getUserId());
    return success(noticeService.updateNotice(notice));
  }

  /** 删除通知公告 */
  @PreAuthorize("@ss.hasPermi('system:notice:remove')")
  @Log(title = "通知公告", businessType = BusinessType.DELETE)
  @DeleteMapping("/{noticeIds}")
  public JsonResult remove(@PathVariable Long[] noticeIds) {
    return success(noticeService.deleteNoticeByIds(noticeIds));
  }
}
