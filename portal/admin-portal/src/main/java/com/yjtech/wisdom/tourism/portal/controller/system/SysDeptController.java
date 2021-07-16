package com.yjtech.wisdom.tourism.portal.controller.system;

import com.yjtech.wisdom.tourism.common.annotation.Log;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.enums.BusinessType;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.controller.BaseController;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDept;
import com.yjtech.wisdom.tourism.infrastructure.utils.SecurityUtils;
import com.yjtech.wisdom.tourism.system.service.SysDeptService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author liuhong
 */
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
  @Autowired private SysDeptService deptService;

  /** 获取部门列表 */
  @PreAuthorize("@ss.hasPermi('system:dept:list')")
  @GetMapping("/list")
  public JsonResult list(SysDept dept) {
    List<SysDept> depts = deptService.selectDeptList(dept);
    return JsonResult.success(depts);
  }

  /** 查询部门列表（排除节点） */
  @PreAuthorize("@ss.hasPermi('system:dept:list')")
  @GetMapping("/list/exclude/{deptId}")
  public JsonResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
    List<SysDept> depts = deptService.selectDeptList(new SysDept());
    Iterator<SysDept> it = depts.iterator();
    while (it.hasNext()) {
      SysDept d = (SysDept) it.next();
      if (d.getDeptId().intValue() == deptId
          || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
        it.remove();
      }
    }
    return JsonResult.success(depts);
  }

  /** 根据部门编号获取详细信息 */
  @PreAuthorize("@ss.hasPermi('system:dept:query')")
  @GetMapping(value = "/{deptId}")
  public JsonResult getInfo(@PathVariable Long deptId) {
    return JsonResult.success(deptService.selectDeptById(deptId));
  }

  /** 获取部门下拉树列表 */
  @GetMapping("/treeselect")
  public JsonResult treeselect(SysDept dept) {
    List<SysDept> depts = deptService.selectDeptList(dept);
    return JsonResult.success(deptService.buildDeptTreeSelect(depts));
  }

  /** 加载对应角色部门列表树 */
  @GetMapping(value = "/roleDeptTreeselect/{roleId}")
  public JsonResult roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
    List<SysDept> depts = deptService.selectDeptList(new SysDept());
    Map<String, Object> result = new HashMap<>();
    result.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
    result.put("depts", deptService.buildDeptTreeSelect(depts));
    return JsonResult.success(result);
  }

  /** 新增部门 */
  @PreAuthorize("@ss.hasPermi('system:dept:add')")
  @Log(title = "部门管理", businessType = BusinessType.INSERT)
  @PostMapping
  public JsonResult add(@Validated @RequestBody SysDept dept) {
    if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
      return JsonResult.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
    }
    dept.setUpdateUser(SecurityUtils.getUserId());
    return success(deptService.insertDept(dept));
  }

  /** 修改部门 */
  @PreAuthorize("@ss.hasPermi('system:dept:edit')")
  @Log(title = "部门管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public JsonResult edit(@Validated @RequestBody SysDept dept) {
    if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
      return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
    } else if (dept.getParentId().equals(dept.getDeptId())) {
      return JsonResult.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
    } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
        && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
      return JsonResult.error("该部门包含未停用的子部门！");
    }
    dept.setUpdateUser(SecurityUtils.getUserId());
    return success(deptService.updateDept(dept));
  }

  /** 删除部门 */
  @PreAuthorize("@ss.hasPermi('system:dept:remove')")
  @Log(title = "部门管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{deptId}")
  public JsonResult remove(@PathVariable Long deptId) {
    if (deptService.hasChildByDeptId(deptId)) {
      return JsonResult.error("存在下级部门,不允许删除");
    }
    if (deptService.checkDeptExistUser(deptId)) {
      return JsonResult.error("部门存在用户,不允许删除");
    }
    return success(deptService.deleteDeptById(deptId));
  }
}
