package com.yjtech.wisdom.tourism.resource.depot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.resource.depot.entity.DepotEntity;
import com.yjtech.wisdom.tourism.resource.depot.entity.dto.SingleDepotUseDto;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotScreenUseVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotTrendVo;
import com.yjtech.wisdom.tourism.resource.depot.entity.vo.DepotUseVo;
import com.yjtech.wisdom.tourism.resource.depot.mapper.DepotMapper;
import com.yjtech.wisdom.tourism.resource.depot.query.DepotSummaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

/**
 * @Description 停车场业务处理
 * @Author zc
 * @Date 2021-07-04 11:40
 */
@Service
public class DepotService extends ServiceImpl<DepotMapper, DepotEntity> {

    @Autowired
    private DepotTrendSummaryService trendSummaryService;

    /**
     * @Description: 停车场使用情况（首页）
     * @Param:
     * @return:  DepotUseVo
     * @Author: zc
     * @Date: 2021-07-05
     */
    public DepotScreenUseVo depotUseDetails(){
        //查询所有停车场
        List<DepotEntity> depotList = list();
        //计算全部停车场车位数
        int spaceTotal = depotList.stream().mapToInt(DepotEntity::getSpaceTotal).sum();
        //计算全部停车场已使用车位数
        int spaceUsed = depotList.stream().mapToInt(DepotEntity::getSpaceUsed).sum();
        //获取总使用率
        double totalPercent = spaceTotal == 0 ?
                0D : MathUtil.divide(BigDecimal.valueOf(spaceUsed), BigDecimal.valueOf(spaceTotal), 3).doubleValue();

        //当日车辆趋势
        DepotSummaryQuery query = new DepotSummaryQuery();
        query.setBeginTime(LocalDate.now().atStartOfDay());
        query.setEndTime(LocalDateTime.now());
        query.setType((byte) 4);
        List<DepotTrendVo> depotTrendVos = trendSummaryService.getBaseMapper().queryTrend(query);
        List<BaseValueVO> voList = AnalysisUtils.MultipleBuildAnalysis(query, depotTrendVos,true, DepotTrendVo::getEnterQuantity, DepotTrendVo::getExitQuantity);

        return DepotScreenUseVo.builder()
                .totalPercent(totalPercent)
                .useSpace(spaceUsed)
                .surplusSpace(spaceTotal - spaceUsed)
                .totalSpace(spaceTotal)
                .trend(voList)
                .build();
    }

    /**
     * @Description: 停车场使用率
     * @Param:
     * @return:  DepotUseVo
     * @Author: zc
     * @Date: 2021-07-05
     */
    public DepotUseVo depotUse(){
        //查询所有停车场
        List<DepotEntity> depotList = list();
        //计算全部停车场车位数
        int spaceTotal = depotList.stream().mapToInt(DepotEntity::getSpaceTotal).sum();
        //计算全部停车场已使用车位数
        int spaceUsed = depotList.stream().mapToInt(DepotEntity::getSpaceUsed).sum();
        //获取总使用率
        double totalPercent = spaceTotal == 0 ?
                0D : MathUtil.divide(BigDecimal.valueOf(spaceUsed), BigDecimal.valueOf(spaceTotal), 3).doubleValue();
        //设置单个停车场车位总数、已使用数、使用率
        List<SingleDepotUseDto> singleDepotUseDtoList = new ArrayList<>();
        depotList.forEach(item ->{
            SingleDepotUseDto dto = SingleDepotUseDto.builder()
                    .name(item.getName())
                    .value(item.getSpaceTotal())
                    .useValue(item.getSpaceUsed())
                    .rate(isNull(item.getSpaceTotal()) || item.getSpaceTotal() == 0 ?
                            0D : MathUtil.divide(BigDecimal.valueOf(item.getSpaceUsed()), BigDecimal.valueOf(item.getSpaceTotal()), 3).doubleValue())
                    .build();
            singleDepotUseDtoList.add(dto);
        });
        return DepotUseVo.builder()
                .totalPercent(totalPercent)
                .depotUseList(singleDepotUseDtoList)
                .build();
    }
}
