package com.yjtech.wisdom.tourism.resource.wifi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiBaseDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiSummaryEntity;
import com.yjtech.wisdom.tourism.resource.wifi.mapper.WifiSummaryMapper;
import com.yjtech.wisdom.tourism.resource.wifi.query.WifiSummaryQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * zc
 */
@Service
public class WifiSummaryService extends ServiceImpl<WifiSummaryMapper, WifiSummaryEntity> {

    public List<BaseValueVO> queryConnectionDuration(WifiSummaryQuery query) {

        List<WifiBaseDto> dtoList = baseMapper.queryConnectionDuration(query);
        //横坐标
        List<String> abscissa = Arrays.asList("1H", "2H", "3H", "4H", "5H", "6H", "7H", "8H", ">8H");
        //纵坐标对应值
        ArrayList<Integer> values = new ArrayList<>();

        ArrayList<BaseValueVO> vos = new ArrayList<>();
        vos.add(BaseValueVO.builder().name("coordinate").value(abscissa).build());
        if(CollectionUtils.isEmpty(dtoList)){
            abscissa.forEach(item -> values.add(0));
        }else {
            Map<String, Integer> map = new HashMap<>();
            dtoList.forEach(item -> map.put(item.getTime(),item.getQuantity()));
            abscissa.forEach(item ->{
                if(map.containsKey(item)){
                    values.add(map.get(item));
                }else {
                    values.add(0);
                }
            });
        }
        vos.add(BaseValueVO.builder().name("quantity").value(values).build());
        return vos;
    }
}
