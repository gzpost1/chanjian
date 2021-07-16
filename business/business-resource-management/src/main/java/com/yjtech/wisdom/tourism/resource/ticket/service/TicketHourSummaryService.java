package com.yjtech.wisdom.tourism.resource.ticket.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketHourSummaryEntity;
import com.yjtech.wisdom.tourism.resource.ticket.extensionpoint.TicketQryExtPt;
import com.yjtech.wisdom.tourism.resource.ticket.mapper.TicketHourSummaryMapper;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSaleQuantityQuery;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.SaleTrendVO;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketFareCollectionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xulei
 * @since 2021-07-03
 */
@Service
public class TicketHourSummaryService extends ServiceImpl<TicketHourSummaryMapper, TicketHourSummaryEntity> {

    @Resource
    private ExtensionExecutor extensionExecutor;

    public List<SaleTrendVO> saleTrend(TicketSumaryQuery query) {
        // 调用扩展点执行器
        return extensionExecutor.execute(TicketQryExtPt.class, query.getBizScenario(),
                extension -> extension.querySaleTrend(query));
    }

    public List<SaleTrendVO> queryVisitTrend(TicketSumaryQuery query) {
        return extensionExecutor.execute(TicketQryExtPt.class, null,
                extension -> extension.queryVisitTrend(query));
    }

    /**
     * 售票数量统计
     *
     * @return
     */
    public JSONObject querySaleQuantity() {

        LocalDate now = LocalDate.now();
        JSONObject result = new JSONObject();
        TicketSumaryQuery todayQuery = new TicketSumaryQuery();
        todayQuery.setBeginTime(LocalDateTime.of(now, LocalTime.MIN));
        todayQuery.setEndTime(LocalDateTime.of(now, LocalTime.MAX));
        Integer today = this.getBaseMapper().querySaleQuantity(todayQuery);
        result.put("today", Objects.isNull(today) ? 0 : today);

        TicketSumaryQuery lastSevenDaysQuery = new TicketSumaryQuery();
        lastSevenDaysQuery.setBeginTime(LocalDateTime.of(now.plusDays(-6), LocalTime.MIN));
        lastSevenDaysQuery.setEndTime(LocalDateTime.of(now, LocalTime.MAX));
        Integer lastSevenDays = this.getBaseMapper().querySaleQuantity(lastSevenDaysQuery);
        result.put("lastSevenDays", Objects.isNull(lastSevenDays) ? 0 : lastSevenDays);

        TicketSumaryQuery monthQuery = new TicketSumaryQuery();
        monthQuery.setBeginTime(LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN));
        monthQuery.setEndTime(LocalDateTime.of(now, LocalTime.MAX));
        Integer month = this.getBaseMapper().querySaleQuantity(monthQuery);
        result.put("month", Objects.isNull(month) ? 0 : month);

        TicketSumaryQuery yearQuery = new TicketSumaryQuery();
        yearQuery.setBeginTime(LocalDateTime.of(now.with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));
        yearQuery.setEndTime(LocalDateTime.of(now, LocalTime.MAX));
        Integer year = this.getBaseMapper().querySaleQuantity(yearQuery);
        result.put("year", Objects.isNull(year) ? 0 : year);

        Integer total = this.getBaseMapper().querySaleQuantity(new TicketSumaryQuery());
        result.put("total", Objects.isNull(total) ? 0 : total);
        return result;
    }


    public List<BaseVO> queryFareCollection(){
        LocalDate now = LocalDate.now();
        TicketSumaryQuery todayQuery = new TicketSumaryQuery();
        todayQuery.setBeginTime(LocalDateTime.of(now, LocalTime.MIN));
        todayQuery.setEndTime(LocalDateTime.of(now, LocalTime.MAX));
        TicketFareCollectionVO vo = this.getBaseMapper().queryFareCollection(todayQuery);
        ArrayList<BaseVO> result = Lists.newArrayList();
        Integer willVisitGroupQuantity = vo.getWillVisitGroupQuantity();
        Integer groupQuantity = vo.getGroupQuantity();
        //应入园团队游客人数
        result.add(BaseVO.builder().name("willVisitGroupQuantity").value(Objects.isNull(willVisitGroupQuantity)?"0":String.valueOf(willVisitGroupQuantity)).build());
        //实际入园团队游客数
        result.add(BaseVO.builder().name("groupQuantity").value(Objects.isNull(groupQuantity)?"0":String.valueOf(groupQuantity)).build());
        //团队入园比率
        double groupRate =  willVisitGroupQuantity == 0 ? 0D : MathUtil.divide(new BigDecimal(String.valueOf(groupQuantity)), new BigDecimal(String.valueOf(willVisitGroupQuantity)), 4).doubleValue();
        result.add(BaseVO.builder().name("groupRate").value(String.valueOf(groupRate)).build());

        Integer willVisitIndividualQuantity = vo.getWillVisitIndividualQuantity();
        Integer individualQuantity = vo.getIndividualQuantity();
        //应入园散客数
        result.add(BaseVO.builder().name("willVisitIndividualQuantity").value(Objects.isNull(willVisitIndividualQuantity)?"0":String.valueOf(willVisitIndividualQuantity)).build());
        //实际入园散客数
        result.add(BaseVO.builder().name("individualQuantity").value(Objects.isNull(individualQuantity)?"0":String.valueOf(individualQuantity)).build());
        //团队入园比率
        double individualRate =  willVisitIndividualQuantity == 0 ? 0D : MathUtil.divide(new BigDecimal(String.valueOf(individualQuantity)), new BigDecimal(String.valueOf(willVisitIndividualQuantity)), 4).doubleValue();
        result.add(BaseVO.builder().name("individualRate").value(String.valueOf(individualRate)).build());
        return  result;
    }
}
