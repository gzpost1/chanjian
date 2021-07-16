package com.yjtech.wisdom.tourism.resource.ticket.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.constant.VisitorSourceConstants;
import com.yjtech.wisdom.tourism.resource.ticket.entity.TicketVisitorSourceHourSummary;
import com.yjtech.wisdom.tourism.resource.ticket.mapper.TicketVisitorSourceHourSummaryMapper;
import com.yjtech.wisdom.tourism.resource.ticket.query.TicketSumaryQuery;
import com.yjtech.wisdom.tourism.resource.ticket.vo.CityInfo;
import com.yjtech.wisdom.tourism.resource.ticket.vo.ProvinceInfo;
import com.yjtech.wisdom.tourism.resource.ticket.vo.TicketSourceSummaryVo;
import com.yjtech.wisdom.tourism.resource.ticket.vo.VisitorSourceInfo;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-04
 */
@Service
public class TicketVisitorSourceHourSummaryService extends ServiceImpl<TicketVisitorSourceHourSummaryMapper, TicketVisitorSourceHourSummary> {


    public VisitorSourceInfo queryVisitorSourceTypeInfo(TicketSumaryQuery query) {
        List<TicketSourceSummaryVo> dbResult = this.getBaseMapper().sourceType(query);

        List<TicketSourceSummaryVo> inside = new ArrayList<>();
        Map<String, TicketSourceSummaryVo> outside = new HashMap<>();

        dbResult.forEach(entity -> {
            if (Objects.equals(entity.getSourceType(), VisitorSourceConstants.OUTSIDE_PROVINCE)) {
                String province = entity.getProvince();
                TicketSourceSummaryVo provinceEntity = outside.get(province);
                if (isNull(provinceEntity)) {
                    outside.put(province, entity);
                } else {
                    provinceEntity.setQuantity(provinceEntity.getQuantity() + entity.getQuantity());
                }
            } else {
                inside.add(entity);
            }
        });

        return VisitorSourceInfo.builder()
                .inside(inside.stream().filter(vo -> !"未知".equals(vo.getCity())).map(entity ->
                        CityInfo.builder().city(entity.getCity()).quantity(entity.getQuantity()).build())
                        .sorted((o1, o2) -> {
                            int id1 = o1.getQuantity();
                            int id2 = o2.getQuantity();
                            if (id1 == id2) {
                                return 0;
                            }
                            return (id1 > id2) ? -1 : 1;
                        }).collect(toList()))
                .outside(outside.values().stream().filter(vo -> !"未知".equals(vo.getProvince()))
                        .map(entity -> ProvinceInfo.builder().province(entity.getProvince()).quantity(entity.getQuantity()).build())
                        .sorted((o1, o2) -> {
                            int id1 = o1.getQuantity();
                            int id2 = o2.getQuantity();
                            if (id1 == id2) {
                                return 0;
                            }
                            return (id1 > id2) ? -1 : 1;
                        }).collect(toList())).build();
    }
}
