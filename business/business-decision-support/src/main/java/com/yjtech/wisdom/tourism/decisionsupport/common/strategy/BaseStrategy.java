package com.yjtech.wisdom.tourism.decisionsupport.common.strategy;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.BaseValueVO;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.dto.MonthPassengerFlowDto;
import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysDictData;
import com.yjtech.wisdom.tourism.mybatis.utils.AnalysisUtils;
import com.yjtech.wisdom.tourism.service.impl.DistrictTourImplService;
import com.yjtech.wisdom.tourism.system.service.SysDictTypeService;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.decisionsupport.DecisionMockDTO;
import com.yjtech.wisdom.tourism.vo.PassengerFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 基础策略
 *
 * @author renguangqian
 * @date 2021/8/5 10:46
 */
@Component
@Slf4j
public abstract class BaseStrategy {

    private static final int HUNDRED = 100;

    private static final String DEFAULT_STR = "-";

    private static final String LOW_LINE_STR = "_";

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    private static final String DEFAULT_MOCK_RULE = "[{\"name\":\"省外游客\",\"value\":\"3\"},{\"name\":\"省内游客\",\"value\":\"3\"},{\"name\":\"整体游客\",\"value\":\"3\"},{\"name\":\"整体景区客流\",\"value\":\"3\"},{\"name\":\"景区客流排行\",\"value\":\"3\"},{\"name\":\"整体景区满意度\",\"value\":\"3\"},{\"name\":\"景区满意度排行\",\"value\":\"3\"},{\"name\":\"整体酒店民宿满意度\",\"value\":\"3\"},{\"name\":\"酒店民宿满意度排行\",\"value\":\"3\"},{\"name\":\"投诉量\",\"value\":\"3\"},{\"name\":\"订单量\",\"value\":\"3\"},{\"name\":\"交易额\",\"value\":\"3\"},{\"name\":\"旅游投诉\",\"value\":\"3\"},{\"name\":\"应急事件统计\",\"value\":\"3\"},{\"name\":\"高发应急事件\",\"value\":\"3\"}]";

    @Autowired
    private TargetQueryService targetQueryService;

    @Autowired
    private DistrictTourImplService districtTourService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 初始化方法
     *
     * @return
     */
    public Object init(){return null;}

    /**
     * 初始化方法
     *
     * @return
     */
    public Object init(DecisionEntity entity, Byte isSimulation){return null;}

    /**
     * 初始化方法 - 综合概况
     *
     * @return
     */
    public Object init(List<DecisionWarnEntity> list, DecisionEntity entity){return null;}


    /**
     * 获取统计年月
     *
     * @return
     */
    protected String getCurrentLastMonthStr() {
        return DateTimeUtil.getCurrentLastMonthStr();
    }

    /**
     * 获取平台简称
     *
     * @return
     */
    protected String getPlatformSimpleName() {
        return targetQueryService.queryPlatformSimpleName().getSimpleName();
    }

    /**
     * 文本报警处理
     *
     * @param decisionInfo
     * @param entity
     */
    protected void textAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String queryResult) {
        textAlarmDeal(decisionInfo, entity, queryResult, DecisionSupportConstants.IMPL);
    }

    /**
     * 文本报警处理
     *
     * @param decisionInfo
     * @param entity
     */
    protected void textAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String queryResult, Byte isSimulation) {
        HashMap<String, Object> riskTypeMap = getRiskType();
        // 模拟数据
        if (DecisionSupportConstants.MOCK.equals(isSimulation) && !MapUtils.isEmpty(riskTypeMap)) {
            if (!ObjectUtils.isEmpty(getMockRule())) {
                AtomicBoolean isStop = new AtomicBoolean(false);
                for (DecisionMockDTO data: getMockRule()) {
                    riskTypeMap.forEach((k, v) -> {
                        if (data.getName().equals(entity.getTargetName()) && data.getValue().equals(v)) {
                            entity.setAlarmType(Integer.parseInt(data.getValue()));
                            entity.setAlarmTypeText(k);
                            isStop.set(true);
                            return;
                        }
                    });
                    if (isStop.get()) {
                        return;
                    }
                }
            }
            return;
        }

        // 文本
        if (DecisionSupportConstants.DECISION_WARN_TYPE_TEXT.equals(decisionInfo.getRiskType())
                && !StringUtils.isEmpty(queryResult)) {

            switch (decisionInfo.getRiskType()) {
                // 低风险
                case DecisionSupportConstants.LOW_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.LOW_RISK_TYPE);
                    break;

                // 中风险
                case DecisionSupportConstants.MEDIUM_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.MEDIUM_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.MEDIUM_RISK_TYPE);
                    break;

                // 高风险
                case DecisionSupportConstants.HIGH_RISK_TYPE:
                    entity.setAlarmTypeText(DecisionSupportConstants.HIGH_RISK_TYPE_TEXT);
                    entity.setAlarmType(DecisionSupportConstants.HIGH_RISK_TYPE);
                    break;

                // 无风险
                default:
                    break;
            }
        }
    }

    /**
     * 数值类报警处理
     *
     * @param decisionInfo
     * @param entity
     * @param scale 百分比
     */
    protected void numberAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String scale) {
        numberAlarmDeal(decisionInfo, entity, scale, DecisionSupportConstants.IMPL);
    }

    /**
     * 数值类报警处理
     *
     * @param decisionInfo
     * @param entity
     * @param scale 百分比
     */
    protected void numberAlarmDeal(DecisionEntity decisionInfo, DecisionWarnEntity entity, String scale, Byte isSimulation) {
        HashMap<String, Object> riskTypeMap = getRiskType();
        log.info("【报警类数值】开始处理");
        // 模拟数据
        if (DecisionSupportConstants.MOCK.equals(isSimulation) && !MapUtils.isEmpty(riskTypeMap)) {
            log.info("【报警类数值】-->模拟数据<--");
            if (!ObjectUtils.isEmpty(getMockRule())) {
                AtomicBoolean isStop = new AtomicBoolean(false);
                for (DecisionMockDTO data : getMockRule()) {
                    riskTypeMap.forEach((k, v) -> {
                        if (data.getName().equals(entity.getTargetName()) && data.getValue().equals(v)) {
                            entity.setAlarmType(Integer.parseInt(data.getValue()));
                            entity.setAlarmTypeText(k);
                            isStop.set(true);
                            return;
                        }
                    });
                    if (isStop.get()) {
                        log.info("【报警类数值-模拟数据-处理结果：{}】", JSONObject.toJSONString(entity));
                        return;
                    }
                }
            }
        }
        log.info("【报警类数值】-->真实数据<--");
        log.info("【报警类数值】决策模板数据：{}", JSONObject.toJSONString(decisionInfo));
        log.info("【报警类数值】数据库数据：{}", JSONObject.toJSONString(entity));
        log.info("【报警类数值】比例：{}", scale);

        if (DEFAULT_STR.equals(scale)) {
            return;
        }
        if (ObjectUtils.isEmpty(decisionInfo)) {
            return;
        }
        // 数值
        if (DecisionSupportConstants.DECISION_WARN_TYPE_NUMBER.equals(decisionInfo.getConfigType()) && !StringUtils.isEmpty(decisionInfo.getConfigType())) {
            switch (decisionInfo.getChangeType()) {
                // 上升
                case DecisionSupportConstants.NUMBER_TYPE_UP:
                    numberAlarmHandle(decisionInfo, entity, scale, DecisionSupportConstants.NUMBER_TYPE_UP_SIGN);
                    break;

                // 下降
                case DecisionSupportConstants.NUMBER_TYPE_DOWN:
                    numberAlarmHandle(decisionInfo, entity, scale, DecisionSupportConstants.NUMBER_TYPE_DOWN_SIGN);
                    break;

                // 忽略变化 不做处理
                default:
                    break;
            }
        }
    }

    /**
     * 数值类型处理方式
     *
     * @param decisionInfo
     * @param entity
     * @param targetNumber
     * @param sign
     */
    private void numberAlarmHandle(DecisionEntity decisionInfo, DecisionWarnEntity entity, String targetNumber, int sign) {
        log.info("=================【数值类型处理】=======================");
        // 低风险预警值
        Double lowRiskThreshold = decisionInfo.getLowRiskThreshold() * HUNDRED * sign;
        // 中风险预警值
        Double mediumRiskThreshold = decisionInfo.getMediumRiskThreshold() * HUNDRED * sign;
        // 高风险预警值
        Double highRiskThreshold = decisionInfo.getHighRiskThreshold() * HUNDRED * sign;
        log.info("【数值类型处理】低风险预警指：{}", lowRiskThreshold);
        log.info("【数值类型处理】中风险预警指：{}", mediumRiskThreshold);
        log.info("【数值类型处理】高风险预警指：{}", highRiskThreshold);
        double tbValue = Double.parseDouble(targetNumber);
        log.info("【数值类型处理】同比值：{}", tbValue);

        // 低风险
        if (tbValue > lowRiskThreshold && tbValue < mediumRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.LOW_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.LOW_RISK_TYPE);
        }
        // 中风险
        else if (tbValue > mediumRiskThreshold && tbValue < highRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.MEDIUM_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.MEDIUM_RISK_TYPE);
        }
        // 高风险
        else if (tbValue > highRiskThreshold) {
            entity.setAlarmTypeText(DecisionSupportConstants.HIGH_RISK_TYPE_TEXT);
            entity.setAlarmType(DecisionSupportConstants.HIGH_RISK_TYPE);
        }
        // 无风险 则不需要报警
        else {
            // not do someThing
        }
    }

    /**
     * 计算比例
     *
     * @param bcs 除数
     * @param cs 被除数
     */
    protected String computeScale(Integer bcs, Integer cs) {
        if (0 != bcs) {
            // 比例 = （本月生成 - 上月生成 ）/ 本月生成
            return MathUtil.calPercent(new BigDecimal(bcs - cs), new BigDecimal(bcs), 1).toString();
        }
        return "-";
    }
    /**
     * 计算比例
     *
     * @param bcs 除数
     * @param cs 被除数
     */
    protected String computeScale(Double bcs, Double cs) {
        if (0 != bcs) {
            // 比例 = （本月生成 - 上月生成 ）/ 本月生成
            return MathUtil.calPercent(new BigDecimal(bcs - cs), new BigDecimal(bcs), 1).toString();
        }
        return "-";
    }

    /**
     * 设置比例
     *
     * @param scale
     * @return
     */
    protected static String getScale(String scale) {
        if (StringUtils.isEmpty(scale)
                || DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE.equals(scale)
                || DecisionSupportConstants.ZERO.equals(scale)
                || DecisionSupportConstants.NULL.equals(scale)) {
            return DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        }
        double scaleDouble;
        try {
            scaleDouble = new BigDecimal(scale).divide(new BigDecimal(1), 1).doubleValue();
        }catch (Exception e) {
            return DecisionSupportConstants.MISS_CONCLUSION_TEXT_SCALE_VALUE;
        }
        return scaleDouble > DecisionSupportConstants.ZERO_NUMBER ?
                DecisionSupportConstants.ADD + scale  + DecisionSupportConstants.PERCENT
                : DecisionSupportConstants.REDUCE + scale + DecisionSupportConstants.PERCENT;
    }

    /**
     * 获取图表数据
     *
     * @return
     */
    protected List<BaseValueVO> getProvinceCharData(String statisticsType, Byte isSimulation) {
        String beginDate = DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.START_DATE_STR;
        String endTime = DateTimeUtil.getCurrentYearStr() + DecisionSupportConstants.END_DATE_STR;

        // 请求参数构造
        PassengerFlowVo yearPassengerFlowVo = new PassengerFlowVo();
        yearPassengerFlowVo.setIsSimulation(isSimulation);
        yearPassengerFlowVo.setStatisticsType(statisticsType);
        yearPassengerFlowVo.setBeginTime(DateTimeUtil.getLocalDateTime(beginDate));
        yearPassengerFlowVo.setEndTime(DateTimeUtil.getLocalDateTime(endTime));
        yearPassengerFlowVo.setType(DecisionSupportConstants.YEAR_MONTH);
        List<MonthPassengerFlowDto> yearPassengerFlowDtos = districtTourService.queryYearPassengerFlow(yearPassengerFlowVo);
        yearPassengerFlowDtos = yearPassengerFlowDtos.stream().map(v -> {
            v.setTime(v.getDate());
            return v;
        }).collect(Collectors.toList());
        return AnalysisUtils.MultipleBuildAnalysis(
                yearPassengerFlowVo,
                yearPassengerFlowDtos,
                true,
                MonthPassengerFlowDto::getNumber, MonthPassengerFlowDto::getTbNumber, MonthPassengerFlowDto::getHbScale, MonthPassengerFlowDto::getTbScale);
    }

    /**
     * 构建业务扩展点
     *
     * @param useCasePraiseType
     * @param isSimulation
     * @return
     */
    public BizScenario buildBizScenario(String useCasePraiseType, Byte isSimulation) {
        String biz = useCasePraiseType;
        if (useCasePraiseType.contains(LOW_LINE_STR)) {
            biz = lineToHump(useCasePraiseType);
        }

        return BizScenario.valueOf(biz, useCasePraiseType
                , isSimulation == 0 ? ExtensionConstant.SCENARIO_IMPL : ExtensionConstant.SCENARIO_MOCK);
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取模拟数据规则
     *
     * @return
     */
    public List<DecisionMockDTO> getMockRule() {
        List<DecisionMockDTO> mockRuleData;
        String configValue = JSONObject.toJSONString(redisTemplate.opsForValue().get(Constants.SIMULATION_KEY +SimulationConstants.DECISION));
        if (!StringUtils.isEmpty(configValue) && !DecisionSupportConstants.NULL.equals(configValue)) {
            Object list = JsonUtils.getValueByKey(configValue, DecisionSupportConstants.LIST);
            if (!ObjectUtils.isEmpty(list)) {
                configValue = JSONObject.toJSONString(list);
            }
            mockRuleData  = JSONObject.parseArray(configValue, DecisionMockDTO.class);
        }
        // 获取不到，使用初始化模拟规则
        else {
            mockRuleData =  JSONObject.parseArray(DEFAULT_MOCK_RULE, DecisionMockDTO.class);
        }
        return mockRuleData;
    }

    /**
     * 获取风险等级
     *
     * @return
     */
    public HashMap<String, Object> getRiskType() {
        HashMap<String, Object> riskTypeMap = Maps.newHashMap();
        List<SysDictData> sysDictData = sysDictTypeService.selectDictDataByType(DecisionSupportConstants.RISK_TYPE);
        if (!CollectionUtils.isEmpty(sysDictData)) {
            sysDictData.forEach(v -> riskTypeMap.put(v.getDictLabel(), v.getDictValue()));
        }
        return riskTypeMap;
    }
}
