package com.yjtech.wisdom.tourism.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author liuhong
 */
public interface SysUserMapper {
  /**
   * 根据条件分页查询用户列表
   *
   * @param sysUser 用户信息
   * @return 用户信息集合信息
   */
  public IPage<SysUser> selectUserList(IPage<SysUser> pageDomain, @Param("args") SysUser sysUser);

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  public SysUser selectUserByUserName(String userName);

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  public SysUser selectUserById(Long userId);

  /**
   * 通过用户ID列表查询用户昵称列表
   *
   * @param userIdList 用户ID列表
   * @return 用户昵称列表
   */
  public List<String> selectUserNameListById(@Param("userIdList") List<?> userIdList);

  /**
   * 新增用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  public int insertUser(SysUser user);

  /**
   * 修改用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  public int updateUser(SysUser user);

  /**
   * 修改用户头像
   *
   * @param userName 用户名
   * @param avatar 头像地址
   * @return 结果
   */
  public int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

  /**
   * 重置用户密码
   *
   * @param userName 用户名
   * @param password 密码
   * @return 结果
   */
  public int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

  /**
   * 通过用户ID删除用户
   *
   * @param userId 用户ID
   * @return 结果
   */
  public int deleteUserById(Long userId);

  /**
   * 批量删除用户信息
   *
   * @param userIds 需要删除的用户ID
   * @return 结果
   */
  public int deleteUserByIds(Long[] userIds);

  /**
   * 校验用户名称是否唯一
   *
   * @param userName 用户名称
   * @return 结果
   */
  public int checkUserNameUnique(String userName);

  /**
   * 校验手机号码是否唯一
   *
   * @param phonenumber 手机号码
   * @return 结果
   */
  public SysUser checkPhoneUnique(String phonenumber);

  /**
   * 校验email是否唯一
   *
   * @param email 用户邮箱
   * @return 结果
   */
  public SysUser checkEmailUnique(String email);
}
