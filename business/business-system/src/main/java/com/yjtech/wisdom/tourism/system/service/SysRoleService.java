package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.annotation.DataScope;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.common.utils.spring.SpringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysRole;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.domain.SysRoleDept;
import com.yjtech.wisdom.tourism.system.domain.SysRoleMenu;
import com.yjtech.wisdom.tourism.system.mapper.SysRoleDeptMapper;
import com.yjtech.wisdom.tourism.system.mapper.SysRoleMapper;
import com.yjtech.wisdom.tourism.system.mapper.SysRoleMenuMapper;
import com.yjtech.wisdom.tourism.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 *
 * @author liuhong
 */
@Service
public class SysRoleService {
  @Autowired private SysRoleMapper roleMapper;

  @Autowired private SysRoleMenuMapper roleMenuMapper;

  @Autowired private SysUserRoleMapper userRoleMapper;

  @Autowired private SysRoleDeptMapper roleDeptMapper;

  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @return 角色数据集合信息
   */
  @DataScope(deptAlias = "d")
  public IPage<SysRole> selectRoleList(SysRole role) {
    IPage<SysRole> pageDomain = TableSupport.buildIPageRequest();
    return roleMapper.selectRoleList(pageDomain, role);
  }

  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @return 角色数据集合信息
   */
  @DataScope(deptAlias = "d")
  public IPage<SysRole> selectRoleListAll(SysRole role) {
    return roleMapper.selectRoleList(new Page<>(1, Integer.MAX_VALUE), role);
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  public Set<String> selectRolePermissionByUserId(Long userId) {
    List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
    Set<String> permsSet = new HashSet<>();
    for (SysRole perm : perms) {
      if (StringUtils.isNotNull(perm)) {
        permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
      }
    }
    return permsSet;
  }

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  public List<SysRole> selectRoleAll() {
    return SpringUtils.getAopProxy(this).selectRoleListAll(new SysRole()).getRecords();
  }

  /**
   * 根据用户ID获取角色选择框列表
   *
   * @param userId 用户ID
   * @return 选中角色ID列表
   */
  public List<Integer> selectRoleListByUserId(Long userId) {
    return roleMapper.selectRoleListByUserId(userId);
  }

  /**
   * 通过角色ID查询角色
   *
   * @param roleId 角色ID
   * @return 角色对象信息
   */
  public SysRole selectRoleById(Long roleId) {
    return roleMapper.selectRoleById(roleId);
  }

  /**
   * 校验角色名称是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  public String checkRoleNameUnique(SysRole role) {
    Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
    SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
    if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验角色权限是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  public String checkRoleKeyUnique(SysRole role) {
    Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
    SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
    if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验角色是否允许操作
   *
   * @param role 角色信息
   */
  public void checkRoleAllowed(SysRole role) {
    if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
      throw new CustomException("不允许操作超级管理员角色");
    }
  }

  /**
   * 通过角色ID查询角色使用数量
   *
   * @param roleId 角色ID
   * @return 结果
   */
  public int countUserRoleByRoleId(Long roleId) {
    return userRoleMapper.countUserRoleByRoleId(roleId);
  }

  /**
   * 新增保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  public int insertRole(SysRole role) {
    // 新增角色信息
    roleMapper.insertRole(role);
    return insertRoleMenu(role);
  }

  /**
   * 修改保存角色信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  public int updateRole(SysRole role) {
    // 修改角色信息
    roleMapper.updateRole(role);
    // 删除角色与菜单关联
    roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
    return insertRoleMenu(role);
  }

  /**
   * 修改角色状态
   *
   * @param role 角色信息
   * @return 结果
   */
  public int updateRoleStatus(SysRole role) {
    return roleMapper.updateRole(role);
  }

  /**
   * 修改数据权限信息
   *
   * @param role 角色信息
   * @return 结果
   */
  @Transactional
  public int authDataScope(SysRole role) {
    // 修改角色信息
    roleMapper.updateRole(role);
    // 删除角色与部门关联
    roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
    // 新增角色和部门信息（数据权限）
    return insertRoleDept(role);
  }

  /**
   * 新增角色菜单信息
   *
   * @param role 角色对象
   */
  public int insertRoleMenu(SysRole role) {
    int rows = 1;
    // 新增用户与角色管理
    List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
    for (Long menuId : role.getMenuIds()) {
      SysRoleMenu rm = new SysRoleMenu();
      rm.setRoleId(role.getRoleId());
      rm.setMenuId(menuId);
      list.add(rm);
    }
    if (list.size() > 0) {
      rows = roleMenuMapper.batchRoleMenu(list);
    }
    return rows;
  }

  /**
   * 新增角色部门信息(数据权限)
   *
   * @param role 角色对象
   */
  public int insertRoleDept(SysRole role) {
    int rows = 1;
    // 新增角色与部门（数据权限）管理
    List<SysRoleDept> list = new ArrayList<SysRoleDept>();
    for (Long deptId : role.getDeptIds()) {
      SysRoleDept rd = new SysRoleDept();
      rd.setRoleId(role.getRoleId());
      rd.setDeptId(deptId);
      list.add(rd);
    }
    if (list.size() > 0) {
      rows = roleDeptMapper.batchRoleDept(list);
    }
    return rows;
  }

  /**
   * 通过角色ID删除角色
   *
   * @param roleId 角色ID
   * @return 结果
   */
  public int deleteRoleById(Long roleId) {
    return roleMapper.deleteRoleById(roleId);
  }

  /**
   * 批量删除角色信息
   *
   * @param roleIds 需要删除的角色ID
   * @return 结果
   */
  public int deleteRoleByIds(Long[] roleIds) {
    for (Long roleId : roleIds) {
      checkRoleAllowed(new SysRole(roleId));
      SysRole role = selectRoleById(roleId);
      if (countUserRoleByRoleId(roleId) > 0) {
        throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
      }
    }
    return roleMapper.deleteRoleByIds(roleIds);
  }
}
