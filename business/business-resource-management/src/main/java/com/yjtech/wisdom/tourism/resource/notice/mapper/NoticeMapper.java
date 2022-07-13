package com.yjtech.wisdom.tourism.resource.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.resource.notice.dto.NoticeScreenScrollDTO;
import com.yjtech.wisdom.tourism.resource.notice.entity.NoticeEntity;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告（通知）管理(TbNotice)表数据库访问层
 *
 * @author horadirm
 * @since 2022-07-07 14:48:45
 */
public interface NoticeMapper extends BaseMapper<NoticeEntity> {


    /**
     * 查询管理后台列表
     *
     * @param page
     * @param params
     * @return
     */
    List<NoticeEntity> queryForAdminList(Page page, @Param("params") NoticeQueryVO params);

    /**
     * 查询大屏列表
     *
     * @param page
     * @param params
     * @return
     */
    List<NoticeScreenScrollDTO> queryForScreenList(Page page, @Param("params") NoticeQueryVO params);


}