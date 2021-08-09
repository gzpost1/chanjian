package com.yjtech.wisdom.tourism.resource.wifi.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiVisitorEntity;
import com.yjtech.wisdom.tourism.resource.wifi.mapper.WifiVisitorMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WifiVisitorService extends ServiceImpl<WifiVisitorMapper, WifiVisitorEntity> {

    /**
     * 手机厂商top10
     */
    public List<BaseVO> queryPhoneManufacturerTop10(TimeBaseQuery query) {
        return baseMapper.queryPhoneManufacturerTop10(query);
    }

    /**
     * 手机认证
     */
    public List<BasePercentVO> queryAuthType() {
        List<BaseVO> baseVOS = baseMapper.queryAuthType();

        ArrayList<BasePercentVO> vos = new ArrayList<>();
        if(CollectionUtils.isEmpty(baseVOS)){
            return vos;
        }
        int sum = baseVOS.stream().mapToInt(item -> Integer.parseInt(item.getValue())).sum();
        baseVOS.forEach(item ->{
            BasePercentVO vo = BasePercentVO.builder()
                    .name(item.getName())
                    .value(item.getValue())
                    .rate(sum == 0 ?
                            0D : MathUtil.divide(BigDecimal.valueOf(Integer.parseInt(item.getValue())), BigDecimal.valueOf(sum), 3).doubleValue())
                    .build();
            vos.add(vo);
        });
        return vos;
    }
}
