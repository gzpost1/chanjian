package com.yjtech.wisdom.tourism.resource.ticket.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.resource.scenic.entity.vo.ScenicBaseVo;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicScreenQuery;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketCheckEntity;
import com.yjtech.wisdom.tourism.resource.ticket.mapper.TicketCheckMapper;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketCheckService extends ServiceImpl<TicketCheckMapper, TicketCheckEntity> {

    public Integer queryCheckNumByTime(ScenicScreenQuery query) {
        return baseMapper.queryCheckNumByTime(query);
    }

    public List<SaleTrendVO> queryCheckTrendByTime(ScenicScreenQuery query) {
        return baseMapper.queryCheckTrendByTime(query);
    }

    public IPage<ScenicBaseVo> queryPassengerFlowTop5(ScenicScreenQuery query) {
        return baseMapper.queryPassengerFlowTop5(new Page(query.getPageNo(), query.getPageSize()), query);
    }
}
