package com.yjtech.wisdom.tourism.integration.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yjtech.wisdom.tourism.integration.mapper.SmartTravelApiMapper;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.SmartTravelAreaReservationListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.SmartTravelReservationRankListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.SmartTravelReservationStatisticsBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.SmartTravelScenicInfoBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.SmartTravelQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景区中心Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:05
 */
@DS("smartTravel")
@Service
public class SmartTravelApiService {

    @Resource
    private SmartTravelApiMapper smartTravelApiMapper;


    /**
     * 查询区域下的预约统计
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public SmartTravelReservationStatisticsBO queryReservationStatistics(SmartTravelQueryVO vo) {
        return smartTravelApiMapper.queryReservationStatistics(vo);
    }

    /**
     * 查询景区列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<SmartTravelScenicInfoBO> queryScenicList(SmartTravelQueryVO vo) {
        return smartTravelApiMapper.queryScenicList(vo);
    }

    /**
     * 查询景区预约列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<SmartTravelReservationRankListBO> queryScenicReservationList(SmartTravelQueryVO vo) {
        return smartTravelApiMapper.queryScenicReservationList(vo);
    }

    /**
     * 查询区域预约列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<SmartTravelAreaReservationListBO> queryAreaReservationList(SmartTravelQueryVO vo) {
        return smartTravelApiMapper.queryAreaReservationList(vo);
    }

}
