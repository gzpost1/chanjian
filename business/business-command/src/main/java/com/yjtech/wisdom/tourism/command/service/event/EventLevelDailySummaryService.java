package com.yjtech.wisdom.tourism.command.service.event;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.entity.event.EventLevelDailySummaryEntity;
import com.yjtech.wisdom.tourism.command.mapper.event.EventLevelDailySummaryMapper;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.infrastructure.utils.DictUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 事件-级别统计 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-12
 */
@Service
public class EventLevelDailySummaryService extends ServiceImpl<EventLevelDailySummaryMapper, EventLevelDailySummaryEntity> {

    public List<BaseVO> queryEventLevel(EventSumaryQuery query){

        List<BaseVO> list = Optional.ofNullable(this.getBaseMapper().queryEventLevel(query)).orElse(Lists.newArrayList());
        Map<String, String> map = list.stream().collect(Collectors.toMap(item -> item.getName(), item -> item.getValue()));
        BigDecimal sum = list.stream().map(vo -> new BigDecimal(vo.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        //补全数据  没有的类型补为0
        List<SysDictData> dictCache = DictUtils.getDictCache(EventContants.EVENT_LEVEL);
        ArrayList<BaseVO> result = Lists.newArrayList();
        for(SysDictData sysDictData:dictCache){
            if(map.containsKey(sysDictData.getDictValue())){
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.divide(new BigDecimal(map.get(sysDictData.getDictValue())), sum, 4).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value(map.get(sysDictData.getDictValue()))
                        .rate(value).build());
            }else {
                double value = sum.compareTo(BigDecimal.ZERO) == 0 ? 0D : MathUtil.divide(new BigDecimal("0"), sum, 4).doubleValue();
                result.add(BasePercentVO.builder().name(sysDictData.getDictLabel()).value("0")
                        .rate(value).build());
            }
        }
        return result;
    }
}
