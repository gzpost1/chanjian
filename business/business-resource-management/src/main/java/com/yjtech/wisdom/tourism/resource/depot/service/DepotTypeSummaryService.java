package com.yjtech.wisdom.tourism.resource.depot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotTypeSummaryEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTypeVo;
import com.yjtech.wisdom.tourism.resource.depot.mapper.DepotTypeSummaryMapper;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description 车辆类型业务处理
 * @Author zc
 * @Date 2021-07-04 11:40
 */
@Service
public class DepotTypeSummaryService extends ServiceImpl<DepotTypeSummaryMapper, DepotTypeSummaryEntity> {

    /** 
     * @Description:  车辆类型分布
     * @Param:   timeType 1-年 2-月 3-周  4-日
     * @return:  List
     * @Author: zc
     * @Date: 2021-07-05 
     */
    public List<BasePercentVO> queryType(DepotSummaryQuery query){
        List<DepotTypeVo> depotTypeVos = baseMapper.queryType(query);
        int sum = depotTypeVos.stream().mapToInt(DepotTypeVo::getQuantity).sum();

        List<BasePercentVO> percentVOS = new ArrayList<>();
        if(CollectionUtils.isEmpty(depotTypeVos)){
            return percentVOS;
        }
        depotTypeVos.forEach(item ->{
            BasePercentVO vo = new BasePercentVO();
            vo.setName(item.getName());
            vo.setValue(String.valueOf(item.getQuantity()));
            vo.setRate(sum == 0 ? 0 : MathUtil.divide(BigDecimal.valueOf(item.getQuantity()), BigDecimal.valueOf(sum), 3).doubleValue());
            percentVOS.add(vo);
        });
        return percentVOS;
    }
}
