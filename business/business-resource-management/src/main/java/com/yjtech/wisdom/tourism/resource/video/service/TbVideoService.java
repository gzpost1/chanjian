package com.yjtech.wisdom.tourism.resource.video.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.video.dto.ScreenVideoListDTO;
import com.yjtech.wisdom.tourism.resource.video.entity.TbVideoEntity;
import com.yjtech.wisdom.tourism.resource.video.mapper.TbVideoMapper;
import com.yjtech.wisdom.tourism.resource.video.vo.ScreenVideoQueryVO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 视频设备 服务实现类
 *
 * @author Mujun
 * @since 2021-08-02
 */
@Service
public class TbVideoService extends BaseMybatisServiceImpl<TbVideoMapper, TbVideoEntity>  {

    //根据景区id查询监控
    public IPage<TbVideoEntity> queryVideoByScenicId(ScenicScreenQuery query){
        LambdaQueryWrapper<TbVideoEntity> queryWrapper = new LambdaQueryWrapper<TbVideoEntity>();
        queryWrapper.eq(TbVideoEntity::getSecenicId, query.getScenicId());
        queryWrapper.orderByDesc(TbVideoEntity::getEquipStatus);
        queryWrapper.orderByDesc(TbVideoEntity::getSort);
        IPage page = page(new Page(query.getPageNo(), query.getPageSize()), queryWrapper);
        return page;
    }

    /**
     * 查询景区监控列表
     * @param vo
     * @return
     */
    public List<ScreenVideoListDTO> queryScreenVideoList(ScreenVideoQueryVO vo){
        return baseMapper.queryScreenVideoList(vo);
    }
}
