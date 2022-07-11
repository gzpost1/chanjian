package com.yjtech.wisdom.tourism.resource.notice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.common.core.domain.UpdateStatusParam;
import com.yjtech.wisdom.tourism.common.enums.NoticeTypeEnum;
import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.exception.ErrorCode;
import com.yjtech.wisdom.tourism.resource.notice.dto.NoticeScreenScrollDTO;
import com.yjtech.wisdom.tourism.resource.notice.entity.NoticeEntity;
import com.yjtech.wisdom.tourism.resource.notice.mapper.NoticeMapper;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeCreateVO;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeQueryVO;
import com.yjtech.wisdom.tourism.resource.notice.vo.NoticeUpdateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公告（通知）管理(TbNotice)表服务实现类
 *
 * @author horadirm
 * @since 2022-07-07 14:48:46
 */
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, NoticeEntity> {


    /**
     * 新增
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void create(NoticeCreateVO vo){
        NoticeEntity entity = new NoticeEntity();
        entity.buildCreate(vo);

        baseMapper.insert(entity);
    }

    /**
     * 编辑
     *
     * @param vo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(NoticeUpdateVO vo){
        NoticeEntity entity = baseMapper.selectById(vo.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.buildUpdate(vo);

        baseMapper.updateById(entity);
    }

    /**
     * 更新状态
     *
     * @param updateStatusParam
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(UpdateStatusParam updateStatusParam){
        NoticeEntity entity = baseMapper.selectById(updateStatusParam.getId());
        if(null == entity){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        entity.setStatus(updateStatusParam.getStatus());

        baseMapper.updateById(entity);
    }

    /**
     * 管理后台-查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<NoticeEntity> queryForAdminList(NoticeQueryVO vo){
        return baseMapper.queryForAdminList(null, vo);
    }

    /**
     * 管理后台-查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<NoticeEntity> queryForAdminPage(NoticeQueryVO vo){
        Page<NoticeEntity> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        page.setRecords(baseMapper.queryForAdminList(page, vo));
        return page;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id){
        //查询公告状态
        int count = baseMapper.selectCount(new LambdaQueryWrapper<NoticeEntity>()
                .eq(NoticeEntity::getId, id)
                .eq(NoticeEntity::getStatus, EntityConstants.ENABLED));
        if(count > 0){
            throw new CustomException(ErrorCode.BUSINESS_EXCEPTION, "请停用当前公告，再进行删除操作");
        }
        baseMapper.deleteById(id);
    }

    /**
     * 更新消息已读
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateNoticeRead(Long id){
        this.update(new LambdaUpdateWrapper<NoticeEntity>()
                .set(NoticeEntity::getReadFlag, EntityConstants.ENABLED)
                //默认更新类型为：项目申报通知
                .eq(NoticeEntity::getType, NoticeTypeEnum.NOTICE_TYPE_PROGRAM_DECLARE.getType())
                .eq(NoticeEntity::getId, id));
    }


    /** ******************** 大屏 ******************** */

    /**
     * 大屏-查询列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<NoticeScreenScrollDTO> queryForScreenList(NoticeQueryVO vo){
        return baseMapper.queryForScreenList(null, vo);
    }

    /**
     * 大屏-查询分页
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<NoticeScreenScrollDTO> queryForScreenPage(NoticeQueryVO vo){
        Page<NoticeScreenScrollDTO> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        page.setRecords(baseMapper.queryForScreenList(page, vo));
        return page;
    }

}