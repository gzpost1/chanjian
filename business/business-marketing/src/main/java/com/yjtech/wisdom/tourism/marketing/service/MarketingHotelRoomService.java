package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcHotelRoomParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaHotelRoomPO;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingHotelRoomEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingHotelRoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 营销推广 酒店房型
 *
 * @Author horadirm
 * @Date 2020/11/26 9:29
 */
@Service
public class MarketingHotelRoomService extends ServiceImpl<MarketingHotelRoomMapper, TbMarketingHotelRoomEntity> {

    @Autowired
    private ZcInfoSyncService zcInfoSyncService;
    @Resource
    private MarketingHotelRoomMapper marketingHotelRoomMapper;

    /**
     * 同步创建酒店房型信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncCreate(ZcHotelRoomParam params){
        List<TbMarketingHotelRoomEntity> saveList = new ArrayList<>();

        //构建页码
        long pageNum = 1L;
        //获取酒店房型数据
        List<ZcOtaHotelRoomPO> hotelRoomList = zcInfoSyncService.getHotelRoomList(params);
        //分页获取
        while (!hotelRoomList.isEmpty()) {
            for(ZcOtaHotelRoomPO zcOtaHotelRoomPO : hotelRoomList){
                TbMarketingHotelRoomEntity entity = new TbMarketingHotelRoomEntity();
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




}
