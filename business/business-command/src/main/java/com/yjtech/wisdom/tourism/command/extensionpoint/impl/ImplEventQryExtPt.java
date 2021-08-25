package com.yjtech.wisdom.tourism.command.extensionpoint.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventExtensionConstant;
import com.yjtech.wisdom.tourism.command.extensionpoint.EventQryExtPt;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.service.event.EventService;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 事务模拟数据扩展点
 *
 * @author xulei
 * @create 2021-07-14 14:56
 */
@Extension(bizId = ExtensionConstant.BIZ_EVENT,
        useCase = EventExtensionConstant.EVENT_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_IMPL)
public class ImplEventQryExtPt implements EventQryExtPt {


    @Autowired
    private EventService eventService;


    @Override
    public List<BaseVO> queryEventQuantity(EventSumaryQuery query) {
        return eventService.queryEventQuantity(query);
    }

    @Override
    public List<BaseValueVO> querySaleTrend(EventSumaryQuery query) {

        //当年开始时间
        query.setBeginTime((LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(TemporalAdjusters.firstDayOfYear())));
        //当年结束时间
         query.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear()));
        query.setType((byte)2);
        //今年
        LinkedList<Object> thisYear = new LinkedList<>(AnalysisUtils.supplementTime(query, eventService.getBaseMapper().queryTrend(query), true, EventTrendVO::getQuantity));
        //去年
        query.setBeginTime(query.getBeginTime().plusYears(-1));
        query.setEndTime(query.getEndTime().plusYears(-1));
        List<BaseValueVO> result = AnalysisUtils.MultipleBuildAnalysis(query, eventService.getBaseMapper().queryTrend(query), true, EventTrendVO::getQuantity);
        List<?> lastYear = Lists.newArrayList();
        for (BaseValueVO vo : result){
            if(Objects.equals(vo.getName(),"quantity")){
                lastYear = vo.getValue();
            }
        }

        //如果是1月  则它的上月就是去年12月 这里加上去年12月
        thisYear.addFirst(lastYear.get(lastYear.size() - 1));
        int size = thisYear.size();
        List<HashMap<String, Object>> resultMap = Lists.newArrayList();
        for (int i = 1; i < size; i++) {
            //较上月变化=（当月事件数量-上一月事件数量）/上一月事件数量*100%
            Object mom = "";
            //较去年变化=（当月事件数量-去年同月事件数量）/去年同月事件数量
            Object yoy = "";
            if (Objects.equals(thisYear.get(i - 1), 0)) {
                mom = "-";
            } else {
                BigDecimal thisMon = new BigDecimal(String.valueOf(thisYear.get(i)));
                BigDecimal lastMon = new BigDecimal(String.valueOf(thisYear.get(i - 1)));
                mom = MathUtil.calPercent(thisMon.subtract(lastMon), lastMon, 3);
            }

            if (Objects.equals(lastYear.get(i - 1), 0)) {
                yoy = "-";
            } else {
                BigDecimal thisMon = new BigDecimal(String.valueOf(thisYear.get(i)));
                BigDecimal lastYearMon = new BigDecimal(String.valueOf(lastYear.get(i - 1)));
                yoy = MathUtil.calPercent(thisMon.subtract(lastYearMon), lastYearMon, 3);
            }
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("data", thisYear.get(i));
            map.put("mom", mom);
            map.put("yoy", yoy);
            resultMap.add(map);

        }
        result.add(BaseValueVO.builder().name("thisYear").value(resultMap).build());
        return result;
    }

    @Override
    public List<BaseVO> queryEventType(EventSumaryQuery query) {
        return eventService.queryEventType(query);
    }

    @Override
    public List<BaseVO> queryEventLevel(EventSumaryQuery query) {
        return eventService.queryEventLevel(query);
    }


}
