package com.yjtech.wisdom.tourism.bigscreen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.bigscreen.dto.MyFavoritesVo;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbFavoriteEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbFavoriteMapper;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 企业的收藏 服务实现类
 *
 * @author Mujun
 * @since 2022-03-08
 */
@Service
public class TbFavoriteService extends BaseMybatisServiceImpl<TbFavoriteMapper, TbFavoriteEntity>  {
    public List<MyFavoritesVo> myFavorites(@Param("companyId") Long companyId) {
        return baseMapper.queryMyfavorites(companyId);
    }

    public boolean checkExist(Long companyId,Long favoriteId,Integer type){
        QueryWrapper<TbFavoriteEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbFavoriteEntity.COMPANY_ID,companyId);
        queryWrapper.eq(TbFavoriteEntity.FAVORITE_ID,favoriteId);
        queryWrapper.eq(TbFavoriteEntity.TYPE,type);
        TbFavoriteEntity favoriteEntity = baseMapper.selectOne(queryWrapper);
        if(Objects.nonNull(favoriteEntity)){
            return true;
        }
        return false;
    }

}
