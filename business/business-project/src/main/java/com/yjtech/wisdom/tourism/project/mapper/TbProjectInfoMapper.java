package com.yjtech.wisdom.tourism.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.project.dto.ProjectQuery;
import com.yjtech.wisdom.tourism.project.entity.TbProjectInfoEntity;
import com.yjtech.wisdom.tourism.project.vo.ProjectAmountVo;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TbProjectInfoMapper extends BaseMapper<TbProjectInfoEntity> {


    /**
     * 查询列表
     *
     * @param page
     * @param params
     * @return
     */
    List<TbProjectInfoEntity> queryForList(Page page, @Param("params") ProjectQuery params);

    /**
     * 大屏-数据统计-平台项目累计总数
     */
    List<BaseVO> queryProjectNumTrend(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 大屏-数据统计-各区县招商项目数量分布
     */
    List<BaseVO> queryAreaProjectNumPie();

    /**
     * 大屏-数据统计-项目规划占地面积项目数分布
     */
    List<BaseVO> queryProjectPlanFootprintPie();

    /**
     * 大屏-数据统计-总投资额量级项目分布
     */
    List<BaseVO> queryInvestmentTotalTrend();

    /**
     * 大屏-数据统计-月度总投资额与引资金额需求趋势
     */
    List<ProjectAmountVo> queryProjectAmountTrend(@Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据企业id查询项目id列表
     *
     * @param companyId
     * @return
     */
    List<Long> queryIdListByCompanyId(@Param("companyId") Long companyId);
}