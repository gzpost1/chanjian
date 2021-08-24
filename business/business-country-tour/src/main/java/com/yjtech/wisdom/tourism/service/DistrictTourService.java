package com.yjtech.wisdom.tourism.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.common.constant.DistrictBigDataConstants;
import com.yjtech.wisdom.tourism.common.constant.MockDataConstant;
import com.yjtech.wisdom.tourism.constant.DistrictExtensionConstant;
import com.yjtech.wisdom.tourism.dto.DataOverviewDto;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.dto.VisitorDto;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.extension.ExtensionExecutor;
import com.yjtech.wisdom.tourism.service.point.DistrictExtPt;
import com.yjtech.wisdom.tourism.system.domain.SysConfig;
import com.yjtech.wisdom.tourism.system.mapper.SysConfigMapper;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import com.yjtech.wisdom.tourism.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 游客结构
 *
 * @author renguangqian
 * @date 2021/8/11 19:29
 */
@Service
public class DistrictTourService {

    @Resource
    private ExtensionExecutor extensionExecutor;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysConfigMapper configMapper;

    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    public DataOverviewDto queryDataOverview(DataOverviewVo vo) {
        return extensionExecutor.execute(
                DistrictExtPt.class,
                buildBizScenario(
                        DistrictExtensionConstant.DISTRICT,
                        vo.getIsSimulation()),
                        extension -> extension.queryDataOverview(vo));
    }

    /**
     * 游客来源_分页查询
     *
     * @param vo
     * @return
     */
    public IPage<VisitorDto> queryPageVisitor(VisitorVo vo) {
        return extensionExecutor.execute(
                DistrictExtPt.class,
                buildBizScenario(
                        DistrictExtensionConstant.DISTRICT,
                        vo.getIsSimulation()),
                        extension -> extension.queryPageVisitor(vo));
    }

    /**
     * 本年客流趋势
     *
     * @param vo
     * @return
     */
    public List<MonthPassengerFlowDto> queryYearPassengerFlow(PassengerFlowVo vo) {
        return extensionExecutor.execute(
                DistrictExtPt.class,
                buildBizScenario(
                        DistrictExtensionConstant.DISTRICT,
                        vo.getIsSimulation()),
                        extension -> extension.queryYearPassengerFlow(vo));
    }

    /**
     * 本月客流趋势
     *
     * @param vo
     * @return
     */
    public List<MonthPassengerFlowDto> queryMonthPassengerFlow(MonthPassengerFlowVo vo) {
        return extensionExecutor.execute(
                DistrictExtPt.class,
                buildBizScenario(
                        DistrictExtensionConstant.DISTRICT,
                        vo.getIsSimulation()),
                        extension -> extension.queryMonthPassengerFlow(vo));
    }

    /**
     *  模拟数据规则 - 新增
     *
     * @param vo
     */
    public void saveMockRule(DistrictMockRuleVo vo) {
        // 先查询配置中心是否存在key
        SysConfig config = new SysConfig();
        config.setConfigKey(MockDataConstant.DISTRICT_TOUR_MOCK_KEY);
        SysConfig retConfig = configMapper.selectConfig(config);

        String json = JSONObject.toJSONString(vo);
        // 没有数据则插入
        if (ObjectUtils.isEmpty(retConfig)) {
            SysConfig sysConfig = new SysConfig();
            sysConfig.setConfigKey(MockDataConstant.DISTRICT_TOUR_MOCK_KEY);
            sysConfig.setConfigValue(json);
            sysConfig.setConfigName(DistrictBigDataConstants.MOCK_TOUR_SOURCE_DESC);
            sysConfig.setConfigType(DistrictBigDataConstants.CONFIG_TYPE_NOT_SYSTEM);
            sysConfigService.insertConfig(sysConfig);
        }
        // 有数据则更新
        else {
            config.setConfigValue(json);
            sysConfigService.updateConfig(config);
        }
    }

    /**
     * 构建
     *
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    private BizScenario buildBizScenario(String useCasePraiseType, Integer isSimulation) {
        return BizScenario.valueOf(ExtensionConstant.DISTRICT, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }


}
