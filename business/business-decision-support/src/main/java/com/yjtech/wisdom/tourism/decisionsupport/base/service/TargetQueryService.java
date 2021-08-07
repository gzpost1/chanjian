package com.yjtech.wisdom.tourism.decisionsupport.base.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
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
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.system.vo.PlatformVO;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 指标库基础查询方法
 *
 * @author renguangqian
 * @date 2021/7/27 15:24
 */
@Service
public class TargetQueryService {

    private static final String FIRST_STRING = "-01-01 00:00:00";
    private static final String END_STRING = "-12-31 23:59:59";
    private static final String PROVINCE_OUTSIDE_TYPE = "12";
    private static final String DATA = "data";

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

    @Autowired
    private SysConfigService sysConfigService;

    @Value("${tourist.configAreaCodeKey}")
    private String configAreaCodeKey;

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

        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);

        // 请求参数构造
        VisitNumberVo visitNumberVo = VisitNumberVo.builder()
                .statisticsType(DecisionSupportConstants.PROVINCE_OUTSIDE_TYPE)
                .beginDate(beginDate)
                .endDate(endTime)
                .adcode(areaCode)
                .build();
        String result = districtBigDataService.requestDistrict(DistrictPathEnum.VISIT_NUMBER.getPath(),
                visitNumberVo,
                DistrictPathEnum.VISIT_NUMBER.getDesc());
        return String.valueOf(JsonUtils.getValueByKey(result, DATA));
    }

    /**
     * 省外客流环比数据查询
     */
    public String queryHbProvinceOutside () {
        PassengerFlowVo passengerFlowVo = new PassengerFlowVo();
        passengerFlowVo.setStatisticsType(PROVINCE_OUTSIDE_TYPE);
        passengerFlowVo.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + FIRST_STRING));
        passengerFlowVo.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + END_STRING));
        passengerFlowVo.setType((byte)2);

        List<MonthPassengerFlowDto> yearPassengerFlowDto = districtTourService.queryYearPassengerFlow(passengerFlowVo);
        // 上月 年月日期
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        AtomicReference<String> hbScale = new AtomicReference<>("-");
        yearPassengerFlowDto.forEach(v -> {
            if (currentLastMonthStr.equals(v.getDate())) {
                hbScale.set(v.getHbScale());
            }
        });

        return hbScale.get();
    }

    /**
     * 省外客流同比数据查询
     */
    public String queryTbProvinceOutside () {

        PassengerFlowVo passengerFlowVo = new PassengerFlowVo();
        passengerFlowVo.setStatisticsType(PROVINCE_OUTSIDE_TYPE);
        passengerFlowVo.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + FIRST_STRING));
        passengerFlowVo.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + END_STRING));
        passengerFlowVo.setType((byte)2);
        List<MonthPassengerFlowDto> yearPassengerFlowDto = districtTourService.queryYearPassengerFlow(passengerFlowVo);

        // 上月 年月日期
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();

        // 环比
        AtomicReference<String> tbScale = new AtomicReference<>("-");
        yearPassengerFlowDto.forEach(v -> {
            if (currentLastMonthStr.equals(v.getDate())) {
                tbScale.set(v.getTbScale());
            }
        });

        return tbScale.get();
    }

}
