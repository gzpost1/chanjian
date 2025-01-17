package com.yjtech.wisdom.tourism.bigscreen.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.bigscreen.dto.RecommendParam;
import com.yjtech.wisdom.tourism.bigscreen.dto.TbRegisterInfoParam;
import com.yjtech.wisdom.tourism.bigscreen.entity.TbRegisterInfoEntity;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注册信息 Mapper 接口
 * </p>
 *
 * @author MJ~
 * @since 2022-03-02
 */
public interface TbRegisterInfoMapper extends BaseMybatisMapper<TbRegisterInfoEntity> {

    @MapKey("id")
    Map<Long, TbRegisterInfoEntity> selectMap();

    List<TbRegisterInfoEntity> recommendCompany(@Param("params")RecommendParam param);

    IPage<TbRegisterInfoEntity> queryForPageByType(Page page, @Param("params")TbRegisterInfoParam param);

    List<BaseVO> queryCorpTypeDistributed();

    /**
     * 根据时间区间查询数据统计
     *
     * @param params
     * @return
     */
    DataStatisticsDTO queryDataStatisticsByDuration(@Param("params") DataStatisticsQueryVO params);

    /**
     * 查询趋势
     *
     * @param params
     * @return
     */
    List<BaseVO> queryAnalysis(@Param("params") DataStatisticsQueryVO params);
}
