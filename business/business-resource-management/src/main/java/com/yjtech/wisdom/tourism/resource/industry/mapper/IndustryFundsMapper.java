package com.yjtech.wisdom.tourism.resource.industry.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.resource.industry.entity.IndustryFundsEntity;
import com.yjtech.wisdom.tourism.resource.industry.vo.IndustryFundsQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产业资金管理(TbIndustryFunds)表数据库访问层
 *
 * @author horadirm
 * @since 2022-08-06 10:13:36
 */
public interface IndustryFundsMapper extends BaseMapper<IndustryFundsEntity> {


    /**
     * 查询列表
     *
     * @param page
     * @param params
     * @return
     */
    List<IndustryFundsEntity> queryForList(Page page, @Param("params") IndustryFundsQueryVO params);

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    IndustryFundsEntity queryForId(@Param("id") Long id);



}