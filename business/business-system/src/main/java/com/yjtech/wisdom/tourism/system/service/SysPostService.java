package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.constant.UserConstants;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.domain.SysPost;
import com.yjtech.wisdom.tourism.system.mapper.SysPostMapper;
import com.yjtech.wisdom.tourism.system.mapper.SysUserPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author liuhong
 */
@Service
public class SysPostService {
  @Autowired private SysPostMapper postMapper;

  @Autowired private SysUserPostMapper userPostMapper;

  /**
   * 查询岗位信息集合
   *
   * @param post 岗位信息
   * @return 岗位信息集合
   */
  public IPage<SysPost> selectPostList(SysPost post) {
    IPage<SysPost> pageDomain = TableSupport.buildIPageRequest();
    return postMapper.selectPostList(pageDomain, post);
  }

  /**
   * 查询所有岗位
   *
   * @return 岗位列表
   */
  public List<SysPost> selectPostAll() {
    return postMapper.selectPostAll();
  }

  /**
   * 通过岗位ID查询岗位信息
   *
   * @param postId 岗位ID
   * @return 角色对象信息
   */
  public SysPost selectPostById(Long postId) {
    return postMapper.selectPostById(postId);
  }

  /**
   * 根据用户ID获取岗位选择框列表
   *
   * @param userId 用户ID
   * @return 选中岗位ID列表
   */
  public List<Integer> selectPostListByUserId(Long userId) {
    return postMapper.selectPostListByUserId(userId);
  }

  /**
   * 校验岗位名称是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  public String checkPostNameUnique(SysPost post) {
    Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
    SysPost info = postMapper.checkPostNameUnique(post.getPostName());
    if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 校验岗位编码是否唯一
   *
   * @param post 岗位信息
   * @return 结果
   */
  public String checkPostCodeUnique(SysPost post) {
    Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
    SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
    if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue()) {
      return UserConstants.NOT_UNIQUE;
    }
    return UserConstants.UNIQUE;
  }

  /**
   * 通过岗位ID查询岗位使用数量
   *
   * @param postId 岗位ID
   * @return 结果
   */
  public int countUserPostById(Long postId) {
    return userPostMapper.countUserPostById(postId);
  }

  /**
   * 删除岗位信息
   *
   * @param postId 岗位ID
   * @return 结果
   */
  public int deletePostById(Long postId) {
    return postMapper.deletePostById(postId);
  }

  /**
   * 批量删除岗位信息
   *
   * @param postIds 需要删除的岗位ID
   * @return 结果
   * @throws Exception 异常
   */
  public int deletePostByIds(Long[] postIds) {
    for (Long postId : postIds) {
      SysPost post = selectPostById(postId);
      if (countUserPostById(postId) > 0) {
        throw new CustomException(String.format("%1$s已分配,不能删除", post.getPostName()));
      }
    }
    return postMapper.deletePostByIds(postIds);
  }

  /**
   * 新增保存岗位信息
   *
   * @param post 岗位信息
   * @return 结果
   */
  public int insertPost(SysPost post) {
    return postMapper.insertPost(post);
  }

  /**
   * 修改保存岗位信息
   *
   * @param post 岗位信息
   * @return 结果
   */
  public int updatePost(SysPost post) {
    return postMapper.updatePost(post);
  }
}
