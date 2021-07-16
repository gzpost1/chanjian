package com.yjtech.wisdom.tourism.event.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.event.entity.EventDailySummaryEntity;
import com.yjtech.wisdom.tourism.event.mapper.EventDailySummaryMapper;
import com.yjtech.wisdom.tourism.event.mapper.EventStatusDailySummaryMapper;
import com.yjtech.wisdom.tourism.event.query.EventSumaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 事件-统计 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
@Service
public class EventDailySummaryService extends ServiceImpl<EventDailySummaryMapper, EventDailySummaryEntity>{

    @Autowired
    private EventStatusDailySummaryMapper eventStatusDailySummaryMapper;


    public List<BaseVO> queryEventQuantity(){
        ArrayList<BaseVO> result = Lists.newArrayList();
        LocalDate now = LocalDate.now();
        EventSumaryQuery monthQuery = new EventSumaryQuery();
        monthQuery.setBeginTime( LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN));
        monthQuery.setEndTime( LocalDateTime.of(now, LocalTime.MAX));
        Integer month = this.getBaseMapper().queryQuantity(monthQuery);
        result.add(BaseVO.builder().name("month").value(String.valueOf(month)).build());

        EventSumaryQuery yearQuery = new EventSumaryQuery();
        yearQuery.setBeginTime( LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));
        yearQuery.setEndTime( LocalDateTime.of(now, LocalTime.MAX));
        Integer year = this.getBaseMapper().queryQuantity(yearQuery);
        result.add(BaseVO.builder().name("year").value(String.valueOf(year)).build());

        EventSumaryQuery statusQuery = new EventSumaryQuery();
        statusQuery.setEventStatus(EventContants.UNTREATED);
        Integer untreated = eventStatusDailySummaryMapper.queryQuantityByStatus(statusQuery);

        statusQuery.setEventStatus(EventContants.PROCESSED);
        Integer processed = eventStatusDailySummaryMapper.queryQuantityByStatus(statusQuery);
        BigDecimal sum = new BigDecimal(String.valueOf(untreated + processed));

        double untreatedRate = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.divide(new BigDecimal(String.valueOf(untreated)), sum, 4).doubleValue();
        result.add(BasePercentVO.builder().name("untreated").value(String.valueOf(untreated))
                .rate(untreatedRate).build());

        double processedRate = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.divide(new BigDecimal(String.valueOf(processed)), sum, 4).doubleValue();
        result.add(BasePercentVO.builder().name("processed").value(String.valueOf(processed))
                .rate(processedRate).build());
        return result;
    }
}
