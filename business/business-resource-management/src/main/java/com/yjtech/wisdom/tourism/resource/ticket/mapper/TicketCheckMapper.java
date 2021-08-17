package com.yjtech.wisdom.tourism.resource.ticket.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketCheckEntity;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketCheckMapper extends BaseMapper<TicketCheckEntity> {

    Integer queryCheckNumByTime(@Param("params") ScenicScreenQuery params);

    List<SaleTrendVO> queryCheckTrendByTime(@Param("params") ScenicScreenQuery params);

    IPage<ScenicBaseVo> queryPassengerFlowTop5(Page page, @Param("params") ScenicPageQuery params);
}
