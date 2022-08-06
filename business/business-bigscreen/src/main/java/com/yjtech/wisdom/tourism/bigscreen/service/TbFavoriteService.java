package com.yjtech.wisdom.tourism.bigscreen.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.FavoriteIsCheckParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.MyFavoritesVo;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbFavoriteEntity;
import com.yjtech.wisdom.tourism.bigscreen.mapper.TbFavoriteMapper;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.common.enums.FavouriteSourceEnum;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 企业的收藏 服务实现类
 *
 * @author Mujun
 * @since 2022-03-08
 */
@Service
public class TbFavoriteService extends BaseMybatisServiceImpl<TbFavoriteMapper, TbFavoriteEntity>  {
    public Page<MyFavoritesVo> queryMyfavorites(PageQuery param, @Param("companyId") Long companyId) {
        Page<MyFavoritesVo> page = new Page<MyFavoritesVo>(param.getPageNo(), param.getPageSize());
        return baseMapper.queryMyfavorites(page,companyId);
    }

    public TbFavoriteEntity getFavorite(Long companyId,Long favoriteId,Integer type,Integer favoriteType){
        QueryWrapper<TbFavoriteEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbFavoriteEntity.COMPANY_ID,companyId);
        queryWrapper.eq(TbFavoriteEntity.FAVORITE_ID,favoriteId);
        queryWrapper.eq(TbFavoriteEntity.TYPE,type);
        queryWrapper.eq(TbFavoriteEntity.FAVORITE_TYPE,favoriteType);
        TbFavoriteEntity favoriteEntity = baseMapper.selectOne(queryWrapper);
        return favoriteEntity;
    }

    public boolean  exist(Long companyId,Long favoriteId,Integer type,Integer favoriteType){
        TbFavoriteEntity favorite = getFavorite(companyId, favoriteId, type, favoriteType);
        if(Objects.nonNull(favorite)){
            return true;
        }
        return false;
    }

    public Integer queryTHumbsUp(FavoriteIsCheckParam param) {
        QueryWrapper<TbFavoriteEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(TbFavoriteEntity.FAVORITE_ID,param.getFavoriteId());
        queryWrapper.eq(TbFavoriteEntity.TYPE,param.getType());
        queryWrapper.eq(TbFavoriteEntity.FAVORITE_TYPE,param.getFavoriteType());
        return Optional.ofNullable(this.baseMapper.selectCount(queryWrapper)).orElse(0);
    }

    /**
     * 查询数据统计
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public ProjectDataStatisticsDTO queryDataStatistics(ProjectDataStatisticsQueryVO vo){
        //当前数据来源默认为项目
        vo.setFavouriteSource(FavouriteSourceEnum.FAVOURITE_SOURCE_PROJECT.getType());
        return baseMapper.queryDataStatistics(vo);
    }

    /**
     * 查询趋势
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<BaseVO> queryAnalysis(ProjectDataStatisticsQueryVO vo){
        return baseMapper.queryAnalysis(vo);
    }
}
