package com.yjtech.wisdom.tourism.decisionsupport.base.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.LastMonthDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.TargetDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.WarnConfigDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.entity.WarnConfigEntity;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.TargetLibraryMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.WarnConfigMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.VisitNumberVo;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.WarnConfigVo;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.integration.service.DistrictBigDataService;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.system.vo.PlatformVO;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 指标库基础查询方法
 *
 * @author renguangqian
 * @date 2021/7/27 15:24
 */
@Service
public class TargetQueryService {

    @Autowired
    private TargetLibraryMapper targetLibraryMapper;

    @Autowired
    private WarnConfigMapper warnConfigMapper;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private DistrictBigDataService districtBigDataService;

    @Autowired
    private DistrictTourService districtTourService;

    /**
     * GET 指标列表查询
     */
    public List<TargetDto> queryTarget() {
        return JSONObject.parseArray(
                JSONObject.toJSONString(targetLibraryMapper.selectList(null)),
                TargetDto.class);
    }

    /**
     * POST 根据指标id查询 预警配置项
     *
     * @param vo
     * @return
     */
    public List<WarnConfigDto> queryWarnConfig(WarnConfigVo vo) {
        return JSONObject.parseArray(
                JSONObject.toJSONString(warnConfigMapper.selectList(
                        new LambdaQueryWrapper<WarnConfigEntity>()
                                .eq(WarnConfigEntity::getTargetId, vo.getTargetId())
                )),
                WarnConfigDto.class);
    }

    /**
     * GET 获取统计年月
     *
     * @return
     */
    public LastMonthDto queryLastMonth() {
        return LastMonthDto.builder()
                .yearMonth(DateTimeUtil.getCurrentLastMonthStr())
               .build();
    }

    /**
     * GET 获取平台简称
     *
     * @return
     */
    public PlatformVO queryPlatformSimpleName() {
        return JSONObject.parseObject(
                JSONObject.toJSONString(platformService.getPlatform()),
                PlatformVO.class);
    }

    /**
     * 省外客流数据查询
     */
    public String queryProvinceOutsideNumber () {
        String beginDate = DateTimeUtil.getCurrentLastMonthFirstDayStr();
        String endTime = DateTimeUtil.getCurrentLastMonthLastDayStr();
        // 请求参数构造
        VisitNumberVo visitNumberVo = VisitNumberVo.builder()
                .statisticsType(DecisionSupportConstants.PROVINCE_OUTSIDE_TYPE)
                .beginDate(beginDate)
                .endDate(endTime)
                .build();
        String result = districtBigDataService.requestDistrict(DistrictPathEnum.VISIT_NUMBER.getPath(),
                visitNumberVo,
                DistrictPathEnum.VISIT_NUMBER.getDesc());
        return String.valueOf(JsonUtils.getValueByKey(result, "data"));
    }

    /**
     * 省外客流环比数据查询
     */
    public String queryHbProvinceOutside () {
        List<MonthPassengerFlowDto> yearPassengerFlowDto = districtTourService.queryYearPassengerFlow(
                PassengerFlowVo.builder()
                        .beginDate(DateTimeUtil.getCurrentYearStr() + "-01-01 00:00:00")
                        .endDate(DateTimeUtil.getCurrentYearStr() + "-12-31 23:59:59")
                        .statisticsType("12")
                        .build());
        // 上月 年月日期
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        String hbScale = "-";

        for (int i = 0; i < yearPassengerFlowDto.size(); i++) {
            if (i == 0) {
                continue;
            }
            MonthPassengerFlowDto item = yearPassengerFlowDto.get(i);

            if (currentLastMonthStr.equals(item.getDate())) {
                // 上上月人数
                Integer beforeNumber = yearPassengerFlowDto.get(i - 1).getNumber();
                // 上月人数
                Integer curNumber = item.getNumber();

                if (0 != curNumber) {
                    hbScale = MathUtil.divide(new BigDecimal(curNumber - beforeNumber), new BigDecimal(curNumber), 2).toString();
                }
            }
        }

        return hbScale;
    }

    /**
     * 省外客流同比数据查询
     */
    public String queryTbProvinceOutside () {
        List<MonthPassengerFlowDto> yearPassengerFlowDto = districtTourService.queryYearPassengerFlow(
                PassengerFlowVo.builder()
                        .beginDate(DateTimeUtil.getCurrentYearStr() + "-01-01 00:00:00")
                        .endDate(DateTimeUtil.getCurrentYearStr() + "-12-31 23:59:59")
                        .statisticsType("12")
                        .build());

        // 上月 年月日期
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();

        // 上一年 上月 年月日期
        String lastYearLastMonthStr = DateTimeUtil.getLastYearLastMonthStr();

        // 同比
        String tbScale = "-";

        // 上月人数
        Integer lastMonthNumber = 0;

        // 去年 上月人数
        Integer lastYearLastMonthNumber = 0;

        for (int i = 0; i < yearPassengerFlowDto.size(); i++) {
            if (i == 0) {
                continue;
            }
            MonthPassengerFlowDto item = yearPassengerFlowDto.get(i);

            if (currentLastMonthStr.equals(item.getDate())) {
                lastMonthNumber = item.getNumber();
            }
            else if (lastYearLastMonthStr.equals(item.getDate())) {
                lastYearLastMonthNumber = item.getNumber();
            }
        }

        if (0 != lastMonthNumber) {
            tbScale = MathUtil.divide(new BigDecimal(lastMonthNumber - lastYearLastMonthNumber), new BigDecimal(lastMonthNumber), 2).toString();
        }

        return tbScale;
    }

}
