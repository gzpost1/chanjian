package com.yjtech.wisdom.tourism.resource.depot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotStaySummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotStayVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTrendVo;
import com.yjtech.wisdom.tourism.resource.depot.mapper.DepotStaySummaryMapper;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @Description 停车场停留时长业务处理
 * @Author zc
 * @Date 2021-07-04 11:40
 */
@Service
public class DepotStaySummaryService extends ServiceImpl<DepotStaySummaryMapper, DepotStaySummaryEntity> {

    /**
     * @Description:  停留时长
     * @Param:   timeType 1-年 2-月 3-周  4-日
     * @return:  List
     * @Author: zc
     * @Date: 2021-07-05
     */
    public List<BaseValueVO> queryType(DepotSummaryQuery query) {
        List<DepotStayVo> depotTrendVos = baseMapper.queryStay(query);

        String[] arr = {"1H","2H","3H","4H","5H","6H","7H","8H","8H~1D",">1D"};

        List<BaseValueVO> valueVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(depotTrendVos)) {
            return valueVOS;
        }
        List<String> x = Arrays.asList(arr);
        BaseValueVO x_vo = new BaseValueVO();
        x_vo.setName("coordinate");
        x_vo.setValue(x);
        valueVOS.add(x_vo);

        List<Integer> value = new ArrayList<>();

        Map<String, DepotStayVo> map = new HashMap<>();
        depotTrendVos.forEach(item -> map.put(item.getName(),item));
        x.forEach(item ->{
            if (map.containsKey(item)) {
                DepotStayVo depotStayVo = map.get(item);
                value.add(depotStayVo.getQuantity());
            } else {
                value.add(0);
            }
        });

        BaseValueVO valueVo = new BaseValueVO();
        valueVo.setName("quantity");
        valueVo.setValue(value);
        valueVOS.add(valueVo);
        return valueVOS;
    }
}
