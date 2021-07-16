package com.yjtech.wisdom.tourism.resource.wifi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.resource.wifi.dto.WifiBaseDto;
import com.yjtech.wisdom.tourism.resource.wifi.entity.WifiSummaryEntity;
import com.yjtech.wisdom.tourism.resource.wifi.query.WifiSummaryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WifiSummaryMapper extends BaseMapper<WifiSummaryEntity> {

    List<WifiBaseDto> queryCurrentConnectNum(@Param("params") WifiSummaryQuery query);

    List<WifiBaseDto> queryConnectionDuration(@Param("params") WifiSummaryQuery query);
}