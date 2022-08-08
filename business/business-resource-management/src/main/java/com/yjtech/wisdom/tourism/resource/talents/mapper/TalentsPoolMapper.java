package com.yjtech.wisdom.tourism.resource.talents.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsDTO;
import com.yjtech.wisdom.tourism.common.bean.index.DataStatisticsQueryVO;
import com.yjtech.wisdom.tourism.resource.talents.entity.TalentsPoolEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 人才库管理(TbTalentsPool)表数据库访问层
 *
 * @author horadirm
 * @since 2022-08-06 09:37:15
 */
public interface TalentsPoolMapper extends BaseMapper<TalentsPoolEntity> {


    /**
     * 根据时间区间查询数据统计
     *
     * @param params
     * @return
     */
    DataStatisticsDTO queryDataStatisticsByDuration(@Param("params") DataStatisticsQueryVO params);

}