package com.yjtech.wisdom.tourism.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.infrastructure.core.page.TableSupport;
import com.yjtech.wisdom.tourism.system.domain.SysNotice;
import com.yjtech.wisdom.tourism.system.mapper.SysNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公告 服务层实现
 *
 * @author liuhong
 */
@Service
public class SysNoticeService {
  @Autowired private SysNoticeMapper noticeMapper;

  /**
   * 查询公告信息
   *
   * @param noticeId 公告ID
   * @return 公告信息
   */
  public SysNotice selectNoticeById(Long noticeId) {
    return noticeMapper.selectNoticeById(noticeId);
  }

  /**
   * 查询公告列表
   *
   * @param notice 公告信息
   * @return 公告集合
   */
  public IPage<SysNotice> selectNoticeList(SysNotice notice) {
    IPage<SysNotice> pageDomain = TableSupport.buildIPageRequest();
    return noticeMapper.selectNoticeList(pageDomain, notice);
  }

  /**
   * 新增公告
   *
   * @param notice 公告信息
   * @return 结果
   */
  public int insertNotice(SysNotice notice) {
    return noticeMapper.insertNotice(notice);
  }

  /**
   * 修改公告
   *
   * @param notice 公告信息
   * @return 结果
   */
  public int updateNotice(SysNotice notice) {
    return noticeMapper.updateNotice(notice);
  }

  /**
   * 删除公告对象
   *
   * @param noticeId 公告ID
   * @return 结果
   */
  public int deleteNoticeById(Long noticeId) {
    return noticeMapper.deleteNoticeById(noticeId);
  }

  /**
   * 批量删除公告信息
   *
   * @param noticeIds 需要删除的公告ID
   * @return 结果
   */
  public int deleteNoticeByIds(Long[] noticeIds) {
    return noticeMapper.deleteNoticeByIds(noticeIds);
  }
}
