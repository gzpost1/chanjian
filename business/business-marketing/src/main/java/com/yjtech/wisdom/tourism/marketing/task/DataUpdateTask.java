package com.yjtech.wisdom.tourism.marketing.task;

import cn.hutool.core.date.DateUtil;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcHotelRoomParam;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaPlaceParam;
import com.yjtech.wisdom.tourism.common.enums.DataSourceTypeEnum;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.PlaceInfoDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.PlaceQueryVO;
import com.yjtech.wisdom.tourism.marketing.service.MarketingEvaluateService;
import com.yjtech.wisdom.tourism.marketing.service.MarketingHotelRoomService;
import com.yjtech.wisdom.tourism.marketing.service.MarketingPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 第三方数据更新定时任务
 *
 * @Date 2020/11/27 17:24
 * @Author horadirm
 */
@Slf4j
@Component("DataUpdateTask")
public class DataUpdateTask {

    @Autowired
    private MarketingPlaceService marketingPlaceService;
    @Autowired
    private MarketingHotelRoomService marketingHotelRoomService;
    @Autowired
    private MarketingEvaluateService marketingEvaluateService;

    /**
     * 同步场所信息（每月1日 00:05）
     * cron: 0 5 0 1 * ?
     */
    public void syncPlace(){
        log.info("====================> 开始同步 第三方（中测）场所信息：{} <====================\n", DateUtil.now());
        try {
            //优先物理删除所有
            marketingPlaceService.deleteAll();
            //同步创建
            marketingPlaceService.syncCreate(new ZcOtaPlaceParam());
        }catch (Exception e){
            log.info("====================> {} 同步 第三方（中测）场所信息 异常 <====================\n");
            e.printStackTrace();
        }
        log.info("====================> 第三方（中测）场所信息 同步完成：{} <====================\n", DateUtil.now());
    }

    /**
     * 同步评价信息（每日 00:25）
     * cron: 0 25 0 * * ?
     */
    public void syncEvaluate(){
        log.info("====================> 开始同步 第三方（中测）评价信息：{} <====================\n", DateUtil.now());
        try {
            //获取第三方场所信息列表
            List<PlaceInfoDTO> placeInfoList = marketingPlaceService.getPlaceInfoList(new PlaceQueryVO());

            for(PlaceInfoDTO placeInfoDTO : placeInfoList){
                ZcOtaEvaluateParam params = new ZcOtaEvaluateParam();
                params.setAreaCode(placeInfoDTO.getAreaCode());
                params.setTargetId(placeInfoDTO.getTpId());
                params.setTargetName(placeInfoDTO.getPlaceName());
                //同步第三方评价数据
                marketingEvaluateService.syncCreate(params);
            }
        }catch (Exception e){
            log.info("====================> {} 同步 第三方（中测）评价信息异常 <====================\n");
            e.printStackTrace();
        }
        log.info("====================> 第三方（中测）评价信息 同步完成：{} <====================\n", DateUtil.now());
    }

    /**
     * 同步酒店房型信息（每日 01:35）
     * cron: 0 35 1 * * ?
     */
    public void syncHotelRoom(){
        log.info("====================> 开始同步 第三方（中测）酒店房型信息：{} <====================\n", DateUtil.now());
        try {
            //获取可用酒店信息
            List<PlaceInfoDTO> placeInfoList = marketingPlaceService.getPlaceInfoList(new PlaceQueryVO(DataSourceTypeEnum.DATA_SOURCE_TYPE_HOTEL.getValue()));
            if(!placeInfoList.isEmpty()){
                for(PlaceInfoDTO placeInfoDTO : placeInfoList){
                    marketingHotelRoomService.syncCreate(new ZcHotelRoomParam(placeInfoDTO.getTpId(), null, null));
                }
            }
        }catch (Exception e){
            log.info("====================> {} 同步 第三方（中测）酒店房型信息异常 <====================\n");
            e.printStackTrace();
        }
        log.info("====================> 第三方（中测）酒店房型信息 同步完成：{} <====================\n", DateUtil.now());
    }

}
