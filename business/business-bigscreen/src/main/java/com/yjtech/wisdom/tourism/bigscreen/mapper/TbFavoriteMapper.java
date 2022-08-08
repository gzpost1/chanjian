package com.yjtech.wisdom.tourism.bigscreen.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.MyFavoritesVo;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbFavoriteEntity;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.project.ProjectDataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业的收藏 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2022-03-08
 */
public interface TbFavoriteMapper extends BaseMybatisMapper<TbFavoriteEntity> {
    Page<MyFavoritesVo> queryMyfavorites(Page<MyFavoritesVo> page, @Param("companyId") Long companyId);

    /**
     * 查询数据统计
     *
     * @param params
     * @return
     */
    ProjectDataStatisticsDTO queryDataStatistics(@Param("params") ProjectDataStatisticsQueryVO params);

    /**
     * 查询趋势
     *
     * @param params
     * @return
     */
    List<BaseVO> queryAnalysis(@Param("params") ProjectDataStatisticsQueryVO params);
}
