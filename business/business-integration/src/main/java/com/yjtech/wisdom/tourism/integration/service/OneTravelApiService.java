package com.yjtech.wisdom.tourism.integration.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.AreaBaseVO;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.integration.mapper.OneTravelApiMapper;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelAreaVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelComplaintListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelMagicVisitPvBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.onetravel.OneTravelVisitStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.OneTravelQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 一码游Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:03
 */
@DS("oneTravel")
@Service
public class OneTravelApiService {

    @Resource
    private OneTravelApiMapper oneTravelApiMapper;


    /**
     * 查询访问统计
     * @return
     */
    @Transactional(readOnly = true)
    public OneTravelVisitStatisticsBO queryVisitStatistics(){
        return oneTravelApiMapper.queryVisitStatistics();
    }

    /**
     * 查询市级访问统计
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<OneTravelAreaVisitStatisticsBO> queryCityVisitStatistics(AreaBaseVO vo) {
        List<OneTravelAreaVisitStatisticsBO> visitStatisticsList = oneTravelApiMapper.queryCityVisitStatistics(vo);

        double Proportion = 0;
        OneTravelMagicVisitPvBO magicVisitPvEntity = oneTravelApiMapper.queryDailySum();
        if (magicVisitPvEntity != null) {
            Proportion = magicVisitPvEntity.getMagicData() / 10;
        }

        for (OneTravelAreaVisitStatisticsBO visitStatisticsBO : visitStatisticsList) {
            visitStatisticsBO.setValue((long) (visitStatisticsBO.getValue() + visitStatisticsBO.getValue() * Proportion / oneTravelApiMapper.userCityTotalSum()));
        }

        return visitStatisticsList;
    }

    /**
     * 查询所有省级、直辖市、自治区、特别行政区访问统计
     * @return
     */
    @Transactional(readOnly = true)
    public List<OneTravelAreaVisitStatisticsBO> queryProvinceVisitStatistics() {
        List<OneTravelAreaVisitStatisticsBO> visitStatisticsList = oneTravelApiMapper.queryProvinceVisitStatistics();

        double proportion = 0;
        OneTravelMagicVisitPvBO magicVisitPvEntity = oneTravelApiMapper.queryDailySum();
        if (magicVisitPvEntity != null) {
            proportion = magicVisitPvEntity.getMagicData() / 10.0;
        }

        for (OneTravelAreaVisitStatisticsBO visitStatisticsBO : visitStatisticsList) {
            visitStatisticsBO.setValue((long) (visitStatisticsBO.getValue() + visitStatisticsBO.getValue() * proportion / oneTravelApiMapper.userProvinceTotalSum()));
        }

        return visitStatisticsList;
    }

    /**
     * 查询投诉统计
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public Integer queryComplaintStatistics(OneTravelQueryVO vo){
        return oneTravelApiMapper.queryComplaintStatistics(vo);
    }

    /**
     * 查询投诉列表
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<OneTravelComplaintListBO> queryComplaintForPage(OneTravelQueryVO vo){
        return oneTravelApiMapper.queryComplaintForPage(new Page<>(vo.getPageNo(), vo.getPageSize()), vo);
    }

    /**
     * 查询投诉状态分布
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<BasePercentVO> queryComplaintDistribution(OneTravelQueryVO vo){
        return oneTravelApiMapper.queryComplaintDistribution(vo);
    }

    /**
     * 查询用户年龄分布
     * @return
     */
    @Transactional(readOnly = true)
    public List<BasePercentVO> queryUserAgeDistribution(){
        return oneTravelApiMapper.queryUserAgeDistribution();
    }

}
