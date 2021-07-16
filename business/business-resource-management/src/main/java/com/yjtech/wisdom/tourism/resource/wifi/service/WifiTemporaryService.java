package com.yjtech.wisdom.tourism.resource.wifi.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiTemporaryEntity;
import com.yjtech.wisdom.tourism.resource.wifi.mapper.WifiTemporaryMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

/**
 * zc
 */
@Service
public class WifiTemporaryService extends ServiceImpl<WifiTemporaryMapper, WifiTemporaryEntity> {

    /**
     * 1、注册来宾总数，2、当前在线来宾数，3、历史在线峰值
     * @return List
     */
    public List<BaseVO> queryTemporaryList() {
        List<WifiTemporaryEntity> list = list();

        List<BaseVO> baseVO = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            baseVO.add(BaseVO.builder().name("1").value("0").build());
            baseVO.add(BaseVO.builder().name("2").value("0").build());
            baseVO.add(BaseVO.builder().name("3").value("0").build());
            return baseVO;
        }
        list.forEach(item -> baseVO.add(BaseVO.builder().name(item.getType()).value(String.valueOf(item.getValue())).build()));
        return baseVO;
    }
}
