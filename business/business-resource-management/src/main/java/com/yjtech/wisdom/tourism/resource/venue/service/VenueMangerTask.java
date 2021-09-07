package com.yjtech.wisdom.tourism.resource.venue.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.resource.venue.dto.AmapPoiResponse;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.system.domain.Platform;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.system.service.SysDictDataService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文博场馆 定时拉取高德数据
 *
 * @author renguangqian
 * @date 2021/9/7 10:17
 */
@Component
@Slf4j
public class VenueMangerTask {

    @Autowired
    private AmapConfig amapConfig;
    @Autowired
    private AmapDataService amapDataService;
    @Autowired
    private VenueMapAmapService venueMapAmapService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private SysDictTypeService sysDictTypeService;

    private CopyOnWriteArrayList<Map<String, Object>> exceptionPoilist = new CopyOnWriteArrayList<>();

    public void saveOrUpdateBatchAmap() {

        Function<AmapPoiResponse, VenueEntity> function = poi ->poiResponsesToEntity(poi);

        List<VenueEntity> resultList = Lists.newArrayList();
        // 获取平台信息：行政区划
        Platform platform = platformService.getPlatform();

        // 获取查询范围
        Collection<String> venue = amapConfig.getVenue().values();
        venue.forEach(v -> {
            Map<String, Object> map = amapDataService.getMap(v, platform.getAreaCode().substring(0, 6));
            amapDataService.queryPage(
                    map,
                    exceptionPoilist,
                    resultList,
                    function);
        });

        try {
            // 插入数据
            venueMapAmapService.deleteAndInsert(resultList, platform.getAreaCode().substring(0, 6));
        }catch (Exception e){
            log.error("插入异常",e);
        }
    }


    /**
     *  将高德的数据转化为 所需的entity
     * @param poi
     * @return
     */
    public VenueEntity poiResponsesToEntity(AmapPoiResponse poi){
        //坐标
        List<String> location = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(poi.getLocation());
        //图片地址
        List<String> photosUrl = Optional.ofNullable(poi.getPhotos()).orElse(Lists.newArrayList()).stream().map(photos -> {
            return photos.getUrl();
        }).collect(Collectors.toList());

        List<SysDictData> venueTypes = sysDictTypeService.selectDictDataByType("venue_type");
        String venueValue = "";
        for (SysDictData sysDictData : venueTypes) {
            // 博物馆
            if ("140100".equals(poi.getTypecode())) {
                venueValue = sysDictData.getDictValue();
            }
            // 美术馆
            if ("140400".equals(poi.getTypecode())) {
                venueValue = sysDictData.getDictValue();
            }
            // 图书馆
            if ("140500".equals(poi.getTypecode())) {
                venueValue = sysDictData.getDictValue();
            }
        }
        return VenueEntity.builder()
                .id(poi.getId())
                .venueName(poi.getName())
                .venueValue(venueValue)
                .venueType("venue_type")
                .position("[]".equals(poi.getAddress()) ? null : poi.getAddress())
                .longitude(location.get(0))
                .latitude(location.get(1))
                .phone("[]".equals(poi.getTel()) ? null : poi.getTel())
                .frontPicUrl(CollectionUtils.isEmpty(photosUrl) ? null : photosUrl.get(0))
                .otherPicUrl(photosUrl)
                .areaCode(poi.getAdcode())
                .build();
    }
}
