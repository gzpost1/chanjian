package com.yjtech.wisdom.tourism.marketing.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaPlaceParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaHotelPO;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaScenicAreaPO;
import com.yjtech.wisdom.tourism.common.enums.DataSourceTypeEnum;
import com.yjtech.wisdom.tourism.common.service.ZcInfoSyncService;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingPlaceEntity;
import com.yjtech.wisdom.tourism.marketing.mapper.MarketingPlaceMapper;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.PlaceInfoDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.PlaceQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 营销推广 场所信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:11
 */
@Service
public class MarketingPlaceService extends ServiceImpl<MarketingPlaceMapper, TbMarketingPlaceEntity> {

    @Autowired
    private ZcInfoSyncService zcInfoSyncService;
    @Resource
    private MarketingPlaceMapper marketingPlaceMapper;

    /**
     * 同步创建场所信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void syncCreate(ZcOtaPlaceParam params){
        List<TbMarketingPlaceEntity> saveList = new ArrayList<>();

        //构建页码
        long hotelPageNum = 1L;
        params.setPageNum(hotelPageNum);
        //获取酒店数据
        List<ZcOtaHotelPO> hotelList = zcInfoSyncService.getHotelList(params);
        //分页获取
        while (!hotelList.isEmpty()) {
            for(ZcOtaHotelPO zcOtaHotelPO : hotelList){
                TbMarketingPlaceEntity entity = new TbMarketingPlaceEntity();
                entity.build(zcOtaHotelPO, null, DataSourceTypeEnum.DATA_SOURCE_TYPE_HOTEL);

                saveList.add(entity);
            }
            //获取下页
            params.setPageNum(++hotelPageNum);
            hotelList = zcInfoSyncService.getHotelList(params);
        }

        //构建页码
        long scenicSpotPageNum = 1L;
        params.setPageNum(scenicSpotPageNum);
        //获取景区数据
        List<ZcOtaScenicAreaPO> scenicAreaList = zcInfoSyncService.getScenicAreaList(params);
        //分页获取
        while (!scenicAreaList.isEmpty()) {
            for(ZcOtaScenicAreaPO zcOtaScenicAreaPO : scenicAreaList){
                TbMarketingPlaceEntity entity = new TbMarketingPlaceEntity();
                entity.build(zcOtaScenicAreaPO, null, DataSourceTypeEnum.DATA_SOURCE_TYPE_SCENIC_SPOT);

                saveList.add(entity);
            }
            //获取下页
            params.setPageNum(++scenicSpotPageNum);
            scenicAreaList = zcInfoSyncService.getScenicAreaList(params);
        }

        //批量更新场所信息
        saveBatch(saveList);
    }

    /**
     * 获取第三方场所信息列表
     * @return
     */
    public List<PlaceInfoDTO> getPlaceInfoList(PlaceQueryVO vo){
        return marketingPlaceMapper.getPlaceInfoList(vo);
    }

    /**
     * 物理删除所有场所信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(){
        marketingPlaceMapper.deleteAll();
    }



}
