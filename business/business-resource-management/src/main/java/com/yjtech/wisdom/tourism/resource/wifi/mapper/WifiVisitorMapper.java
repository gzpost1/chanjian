package com.yjtech.wisdom.tourism.resource.wifi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiVisitorEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WifiVisitorMapper extends BaseMapper<WifiVisitorEntity> {
    /**
     * 手机厂商top10
     */
    List<BaseVO> queryPhoneManufacturerTop10(@Param("params") TimeBaseQuery query);

    /**
     * 手机认证
     */
    List<BaseVO> queryAuthType();
}
