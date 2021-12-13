package com.yjtech.wisdom.tourism.decisionsupport.base.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.TargetDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.dto.WarnConfigDto;
import com.yjtech.wisdom.tourism.decisionsupport.base.entity.WarnConfigEntity;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.TargetLibraryMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.mapper.WarnConfigMapper;
import com.yjtech.wisdom.tourism.decisionsupport.base.vo.WarnConfigVo;
import com.yjtech.wisdom.tourism.decisionsupport.common.constant.TargetQueryConstants;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.service.DistrictTourService;
import com.yjtech.wisdom.tourism.system.service.PlatformService;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.system.vo.PlatformVO;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
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

    @Autowired
    private TargetLibraryMapper targetLibraryMapper;

    @Autowired
    private WarnConfigMapper warnConfigMapper;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private DistrictTourService districtTourService;

    @Autowired
    private SysConfigService sysConfigService;

    private static DataOverviewDto cache;

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
    public DataOverviewDto queryProvinceNumber(String statisticsType, Byte isSimulation) {
        String beginDate = DateTimeUtil.getCurrentLastMonthFirstDayStr();
        String endTime = DateTimeUtil.getCurrentLastMonthLastDayStr();

        String areaCode = sysConfigService.selectConfigByKey(configAreaCodeKey);

        // 请求参数构造
        DataOverviewVo dataOverviewVo = new DataOverviewVo();
        dataOverviewVo.setStatisticsType(statisticsType);
        dataOverviewVo.setBeginDate(beginDate);
        dataOverviewVo.setEndDate(endTime);
        dataOverviewVo.setAdcode(areaCode);
        dataOverviewVo.setIsSimulation(isSimulation);
        dataOverviewVo.setType(DecisionSupportConstants.YEAR_MONTH);

        return districtTourService.queryDataOverview(dataOverviewVo);

    }

    /**
     * 省 客流环比、同比数据查询
     *
     * @param sign
     */
    public String queryProvinceScale(String sign, String statisticsType, Byte isSimulation) {
        PassengerFlowVo passengerFlowVo = new PassengerFlowVo();
        passengerFlowVo.setStatisticsType(statisticsType);
        passengerFlowVo.setBeginTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + TargetQueryConstants.FIRST_STRING));
        passengerFlowVo.setEndTime(DateTimeUtil.getLocalDateTime(DateTimeUtil.getCurrentYearStr() + TargetQueryConstants.END_STRING));
        passengerFlowVo.setType(DecisionSupportConstants.YEAR_MONTH);
        passengerFlowVo.setIsSimulation(isSimulation);

        List<MonthPassengerFlowDto> yearPassengerFlowDto = districtTourService.queryYearPassengerFlow(passengerFlowVo);
        // 上月 年月日期
        String currentLastMonthStr = DateTimeUtil.getCurrentLastMonthStr();

        AtomicReference<String> scale = new AtomicReference<>(TargetQueryConstants.DEFAULT_STR);
        yearPassengerFlowDto.forEach(v -> {
            if (currentLastMonthStr.equals(v.getDate())) {
                // 环比
                if (TargetQueryConstants.PROVINCE_SCALE_HB.equals(sign)) {
                    scale.set(v.getHbScale());
                }
                // 同比
                else if (TargetQueryConstants.PROVINCE_SCALE_TB.equals(sign)) {
                    scale.set(v.getTbScale());
                }
            }
        });

        return scale.get();
    }
}
