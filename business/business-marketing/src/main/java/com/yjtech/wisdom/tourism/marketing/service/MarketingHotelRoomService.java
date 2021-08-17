package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcHotelRoomParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaHotelRoomPO;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.marketing.entity.MarketingHotelRoomEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingHotelRoomMapper;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.NewestRoomScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomPriceAnalysisDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.RoomTypePriceScreenDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 酒店房型
 *
 * @Author horadirm
 * @Date 2020/11/26 9:29
 */
@Service
public class MarketingHotelRoomService extends ServiceImpl<MarketingHotelRoomMapper, MarketingHotelRoomEntity> {

    @Autowired
    private ZcInfoSyncService zcInfoSyncService;
    @Resource
    private MarketingHotelRoomMapper marketingHotelRoomMapper;

    /**
     * 同步创建酒店房型信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncCreate(ZcHotelRoomParam params){
        List<MarketingHotelRoomEntity> saveList = new ArrayList<>();
        //构建页码
        long pageNum = 1L;
        //获取酒店房型数据
        List<ZcOtaHotelRoomPO> hotelRoomList = zcInfoSyncService.getHotelRoomList(params);
        //分页获取
        while (!hotelRoomList.isEmpty()) {
            for(ZcOtaHotelRoomPO zcOtaHotelRoomPO : hotelRoomList){
                MarketingHotelRoomEntity entity = new MarketingHotelRoomEntity();
                entity.build(zcOtaHotelRoomPO);
                saveList.add(entity);
            }
            //获取下页
            params.setPageNum(++pageNum);
            hotelRoomList = zcInfoSyncService.getHotelRoomList(params);
        }

        //批量更新场所信息
        if(!saveList.isEmpty()){
            marketingHotelRoomMapper.insertBatch(saveList);
        }
    }

    /**
     * 查询房型价格统计
     * @param vo
     * @return
     */
    public RoomTypePriceScreenDTO queryRoomPriceStatistics(RoomScreenQueryVO vo){
        return baseMapper.queryRoomPriceStatistics(vo);
    }

    /**
     * 查询最新房型列表
     * @param vo
     * @return
     */
    public List<NewestRoomScreenDTO> queryNewestRoomInfo(RoomScreenQueryVO vo){
        return baseMapper.queryNewestRoomInfo(vo);
    }

    /**
     * 查询房型价格趋势
     * @param vo
     * @return
     */
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(RoomScreenQueryVO vo){
        return baseMapper.queryRoomPriceAnalysis(vo);
    }

    /**
     * 查询房型价格分布
     * @param vo
     * @return
     */
    public List<BaseVO> queryRoomTypePriceDistribution(RoomScreenQueryVO vo){
        return baseMapper.queryRoomTypePriceDistribution(vo);
    }

    /**
     * 查询酒店房型价格排行
     * @param vo
     * @return
     */
    public List<BaseVO> queryRoomPriceRank(RoomScreenQueryVO vo){
        return baseMapper.queryRoomPriceRank(vo);
    }




}
