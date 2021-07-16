package com.yjtech.wisdom.tourism.portal.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.poi.ExcelUtil;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.domain.SysPost;
import com.yjtech.wisdom.tourism.system.service.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
  @Autowired private SysPostService postService;

  /** 获取岗位列表 */
  @PreAuthorize("@ss.hasPermi('system:post:list')")
  @GetMapping("/list")
  public JsonResult<IPage<SysPost>> list(SysPost post) {
    IPage<SysPost> list = postService.selectPostList(post);
    return JsonResult.success(list);
  }

  @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
  @PreAuthorize("@ss.hasPermi('system:post:export')")
  @GetMapping("/export")
  public JsonResult export(SysPost post) {
    IPage<SysPost> list = postService.selectPostList(post);
    ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
    return util.exportExcel(list.getRecords(), "岗位数据");
  }

  /** 根据岗位编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:post:query')")
  @GetMapping(value = "/{postId}")
  public JsonResult getInfo(@PathVariable Long postId) {
    return JsonResult.success(postService.selectPostById(postId));
  }

  /** 新增岗位 */
  @PreAuthorize("@ss.hasPermi('system:post:add')")
  @Log(title = "岗位管理", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysPost post) {
    if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
      return JsonResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
      return JsonResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setCreateUser(SecurityUtils.getUserId());
    return success(postService.insertPost(post));
  }

  /** 修改岗位 */
  @PreAuthorize("@ss.hasPermi('system:post:edit')")
  @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysPost post) {
    if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
      return JsonResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
    } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
      return JsonResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
    }
    post.setUpdateUser(SecurityUtils.getUserId());
    return success(postService.updatePost(post));
  }

  /** 删除岗位 */
  @PreAuthorize("@ss.hasPermi('system:post:remove')")
  @Log(title = "岗位管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{postIds}")
  public JsonResult remove(@PathVariable Long[] postIds) {
    return success(postService.deletePostByIds(postIds));
  }

  /** 获取岗位选择框列表 */
  @GetMapping("/optionselect")
  public JsonResult optionselect() {
    List<SysPost> posts = postService.selectPostAll();
    return JsonResult.success(posts);
  }
}
