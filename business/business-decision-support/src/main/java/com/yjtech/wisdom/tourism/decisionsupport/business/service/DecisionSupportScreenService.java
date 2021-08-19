package com.yjtech.wisdom.tourism.decisionsupport.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportTargetConstants;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionWarnItemDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionWarnWrapperDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnMockEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.instance.DecisionStrategyEnum;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionWarnMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionWarnMockMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.AnalyzeDecisionWarnVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnPageVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnVo;
import com.yjtech.wisdom.tourism.decisionsupport.common.execute.DecisionExecute;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;


/**
 * 辅助决策-大屏
 *
 * @author renguangqian
 * @date 2021/7/28 11:54
 */
@Service
@Slf4j
public class DecisionSupportScreenService extends ServiceImpl<DecisionWarnMapper, DecisionWarnEntity> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DecisionMapper decisionMapper;

    @Autowired
    private DecisionWarnMockMapper decisionWarnMockMapper;

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private Executor threadPoolTaskExecutor;

    @Value("${decision.dictType.missConclusionTextKey}")
    private String missConclusionTextKey;

    /**
     * 查询决策预警_分页
     *
     * @param vo
     * @return
     */
    public IPage<DecisionWarnWrapperDto> queryPageDecisionWarn (DecisionWarnPageVo vo) {
        // 上月第一天日期
        String currentLastMonthFirstDayStr = DateTimeUtil.getCurrentMonthFirstDayStr() + DecisionSupportConstants.START_TIME_STR;
        // 上月最后一天日期
        String currentLastMonthLastDayStr = DateTimeUtil.getCurrentMonthLastDayStr() + DecisionSupportConstants.END_TIME_STR;

        // 查询缺失话术
        String missConclusionText = sysConfigService.selectConfigByKey(missConclusionTextKey);

        DecisionWarnWrapperDto decisionWarnWrapperDto = initDecisionWarnWrapper(currentLastMonthFirstDayStr, currentLastMonthLastDayStr, missConclusionText);

        // 去数据库 查询符合条件的数据
        IPage<DecisionWarnItemDto> result;

        // 真实数据
        if (DecisionSupportConstants.ZERO_NUMBER.equals(vo.getIsSimulation())) {
            result = baseMapper.selectPage(
                    new Page<>(vo.getPageNo(), vo.getPageSize()),
                    new LambdaQueryWrapper<DecisionWarnEntity>()
                    .between(DecisionWarnEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr)
                    .ne(DecisionWarnEntity::getTargetId, DecisionSupportTargetConstants.ZHGK))
            .convert(v -> {
                // 设置缺失话术
                if (DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT.equals(v.getIsUseMissConclusionText())) {
                    v.setConclusionText(missConclusionText);
                }
                return JSONObject.parseObject(JSONObject.toJSONString(v), DecisionWarnItemDto.class);
            });
        }
        // 模拟数据
        else {
            result = decisionWarnMockMapper.selectPage(
                    new Page<>(vo.getPageNo(), vo.getPageSize()),
                    new LambdaQueryWrapper<DecisionWarnMockEntity>()
                    .ne(DecisionWarnMockEntity::getTargetId, DecisionSupportTargetConstants.ZHGK)
                    .between(DecisionWarnMockEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr))
            .convert(v -> {
                // 设置缺失话术
                if (DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT.equals(v.getIsUseMissConclusionText())) {
                    v.setConclusionText(missConclusionText);
                }
                return JSONObject.parseObject(JSONObject.toJSONString(v), DecisionWarnItemDto.class);
            });
        }

        // 构造数据
        decisionWarnWrapperDto.setList(result.getRecords());
        ArrayList<DecisionWarnWrapperDto> pageDataList = Lists.newArrayList();
        pageDataList.add(decisionWarnWrapperDto);

        // 构造分页信息
        Page<DecisionWarnWrapperDto> realResult = new Page<>();
        realResult.setSize(result.getSize());
        realResult.setTotal(result.getTotal());
        realResult.setCurrent(result.getCurrent());
        realResult.setPages(result.getPages());
        realResult.setRecords(pageDataList);

        return realResult;

    }

    /**
     * 决策预警列表查询
     *
     * @param vo
     * @return
     */
    public DecisionWarnWrapperDto queryDecisionWarnList (DecisionWarnVo vo) {

        // 上月第一天日期
        String currentLastMonthFirstDayStr = DateTimeUtil.getCurrentLastMonthFirstDayStr() + DecisionSupportConstants.START_TIME_STR;
        // 上月最后一天日期
        String currentLastMonthLastDayStr = DateTimeUtil.getCurrentLastMonthLastDayStr() + DecisionSupportConstants.END_TIME_STR;

        // 查询缺失话术
        String missConclusionText = sysConfigService.selectConfigByKey(missConclusionTextKey);

        DecisionWarnWrapperDto decisionWarnWrapperDto = initDecisionWarnWrapper(currentLastMonthFirstDayStr, currentLastMonthLastDayStr, missConclusionText);

        List<DecisionWarnItemDto> realResult;
        // 真实数据
        if (DecisionSupportConstants.ZERO_NUMBER.equals(vo.getIsSimulation())) {
            List<DecisionWarnEntity> result = baseMapper.selectList(new LambdaQueryWrapper<DecisionWarnEntity>()
                    .between(DecisionWarnEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr)
            );
            realResult = JSONObject.parseArray(JSONObject.toJSONString(result), DecisionWarnItemDto.class);
        }
        // 模拟数据
        else {
            List<DecisionWarnMockEntity> result = decisionWarnMockMapper.selectList(new LambdaQueryWrapper<DecisionWarnMockEntity>()
                    .between(DecisionWarnMockEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr)
            );
            realResult = JSONObject.parseArray(JSONObject.toJSONString(result), DecisionWarnItemDto.class);
        }

        // 构造数据
        decisionWarnWrapperDto.setList(realResult);

        return decisionWarnWrapperDto;
    }


    /**
     * 分析决策风险
     */
    public void analyzeDecisionWarn (AnalyzeDecisionWarnVo vo) {

        // 是否模拟数据
        Integer isSimulation = vo.getIsSimulation();

        // 查询所有 决策数据
        List<DecisionEntity> decisionList = decisionMapper.selectList(null);

        List<DecisionWarnEntity> list = warnHandle(decisionList, isSimulation);

        // 上月第一天日期
        String currentMonthFirstDayStr = DateTimeUtil.getCurrentMonthFirstDayStr() + DecisionSupportConstants.START_TIME_STR;
        // 上月最后一天日期
        String currentMonthLastDayStr = DateTimeUtil.getCurrentMonthLastDayStr() + DecisionSupportConstants.END_TIME_STR;

        // 先查询本月是否已经存在生成分析的数据，如果存在 则先删除本月数据
        Integer countTotal;
        // 真实数据
        if (DecisionSupportConstants.IMPL.equals(isSimulation)) {
            countTotal = baseMapper.selectCount(
                    new LambdaQueryWrapper<DecisionWarnEntity>()
                    .between(DecisionWarnEntity::getCreateTime, currentMonthFirstDayStr, currentMonthLastDayStr));
        }
        // 模拟数据
        else {
            countTotal = decisionWarnMockMapper.selectCount(
                    new LambdaQueryWrapper<DecisionWarnMockEntity>()
                    .between(DecisionWarnMockEntity::getCreateTime, currentMonthFirstDayStr, currentMonthLastDayStr));
        }

        if (countTotal > DecisionSupportConstants.ZERO_NUMBER) {
            // 真实数据
            if (DecisionSupportConstants.IMPL.equals(isSimulation)) {
                baseMapper.delete(
                        new LambdaQueryWrapper<DecisionWarnEntity>()
                        .between(DecisionWarnEntity::getCreateTime, currentMonthFirstDayStr, currentMonthLastDayStr));
            }
            // 模拟数据
            else {
                decisionWarnMockMapper.delete(
                        new LambdaQueryWrapper<DecisionWarnMockEntity>()
                        .between(DecisionWarnMockEntity::getCreateTime, currentMonthFirstDayStr, currentMonthLastDayStr));
            }
        }


        // 进行本月数据生成
        for (DecisionWarnEntity i : list) {
            try {
                // 真实数据
                if (DecisionSupportConstants.IMPL.equals(isSimulation)) {
                    baseMapper.insert(i);
                }
                // 模拟数据
                else {
                    DecisionWarnMockEntity decisionWarnMockEntity = JSONObject.parseObject(JSONObject.toJSONString(i), DecisionWarnMockEntity.class);
                    decisionWarnMockMapper.insert(decisionWarnMockEntity);
                }
            }catch (Exception e) {
                log.error("保存决策预警数据出错！原因：{}", e.getMessage());
            }
        }
    }

    /**
     * 处理 决策报警数据
     *
     * @param list
     * @return
     */
    private List<DecisionWarnEntity> warnHandle(List<DecisionEntity> list, Integer isSimulation) {

        ArrayList<DecisionWarnEntity> decisionWarnList = Lists.newArrayList();
        List<FutureTask<DecisionWarnEntity>> task = Lists.newArrayList();

        // 决策预警数据进行处理
        for (DecisionEntity entity : list) {
            FutureTask<DecisionWarnEntity> futureTask = new FutureTask<>(() -> dealWarnConfigAndCompute(entity, isSimulation));
            task.add(futureTask);
            threadPoolTaskExecutor.execute(futureTask);
        }
        task.forEach(i -> {
            try {
                DecisionWarnEntity decisionWarnEntity = i.get();
                if (!ObjectUtils.isEmpty(decisionWarnEntity))  {
                    decisionWarnList.add(decisionWarnEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 综合概况由于需要统计所有数据，单独处理
        DecisionWarnEntity comprehensiveEntity = dealComprehensive(list, decisionWarnList);
        if (!ObjectUtils.isEmpty(comprehensiveEntity)) {
            decisionWarnList.add(comprehensiveEntity);
        }

        return decisionWarnList;
    }

    /**
     * 处理具体 配置项
     *
     * @param entity
     * @return
     */
    private DecisionWarnEntity dealWarnConfigAndCompute(DecisionEntity entity, Integer isSimulation) {
        // 基础属性 设置
        DecisionWarnEntity result = null;

        int targetId = entity.getTargetId().intValue();

        switch (targetId) {
            // 省外游客
            case DecisionSupportTargetConstants.SWYK :
                result = DecisionExecute.get(DecisionStrategyEnum.PROVINCE_OUTSIDE_TOUR, entity, isSimulation);
                break;

            // 省内游客
            case DecisionSupportTargetConstants.SNYK :
                result = DecisionExecute.get(DecisionStrategyEnum.PROVINCE_INSIDE_TOUR, entity, isSimulation);
                break;

            // 整体游客
            case DecisionSupportTargetConstants.ZTYK :
                result = DecisionExecute.get(DecisionStrategyEnum.PROVINCE_ALL_TOUR, entity, isSimulation);
                break;

            // 整体景区客流
            case DecisionSupportTargetConstants.ZTJQKL :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_SCENIC_SPOTS_TOURIST_FLOW, entity, isSimulation);
                break;

            // 景区客流排行
            case DecisionSupportTargetConstants.JQKLPH :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_SCENIC_SPOTS_TOURIST_FLOW_RANKING, entity, isSimulation);
                break;

            // 整体景区满意度
            case DecisionSupportTargetConstants.ZTJQMYD :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_SCENIC_SPOTS_SATISFACTION, entity, isSimulation);
                break;

            // 景区满意度排行
            case DecisionSupportTargetConstants.JQMYDPH :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_SCENIC_SPOTS_SATISFACTION_RANKING, entity, isSimulation);
                break;

            // 整体酒店民宿满意度
            case DecisionSupportTargetConstants.ZTJDMSMYD :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_HOTEL_HOMESTAY_SATISFACTION, entity, isSimulation);
                break;

            // 酒店民宿满意度排行
            case DecisionSupportTargetConstants.JDMSMYDPH :
                result = DecisionExecute.get(DecisionStrategyEnum.OVERALL_HOTEL_HOMESTAY_SATISFACTION_RANKING, entity, isSimulation);
                break;

            // 投诉量
            case DecisionSupportTargetConstants.TSL :
                result = DecisionExecute.get(DecisionStrategyEnum.ONE_TRAVEL_COMPLAINTS_NUMBER, entity, isSimulation);
                break;

            // 订单量
            case DecisionSupportTargetConstants.DDL :
                result = DecisionExecute.get(DecisionStrategyEnum.ONE_TRAVEL_ORDER_NUMBER, entity, isSimulation);
                break;

            // 交易额
            case DecisionSupportTargetConstants.JYE :
                result = DecisionExecute.get(DecisionStrategyEnum.ONE_TRAVEL_TRANSACTIONS_NUMBER, entity, isSimulation);
                break;

            // 旅游投诉
            case DecisionSupportTargetConstants.LYTS :
                result = DecisionExecute.get(DecisionStrategyEnum.TOURIST_COMPLAINTS, entity, isSimulation);
                break;

            // 应急事件统计
            case DecisionSupportTargetConstants.YJSJTJ :
                result = DecisionExecute.get(DecisionStrategyEnum.EMERGENCY_EVENT, entity, isSimulation);
                break;

            // 高发应急事件
            case DecisionSupportTargetConstants.GBFYJSJ :
                result = DecisionExecute.get(DecisionStrategyEnum.HIGH_INCIDENCE_EMERGENCY_EVENT, entity, isSimulation);
                break;

            default:
                break;
        }
        // 避免 综合概况指标 无法进去上面条件
        if (!ObjectUtils.isEmpty(result)) {
            // 通用属性设置
            result.setWarnName(entity.getConfigName());
        }
        return result;
    }

    /**
     * 处理 综合概况 配置项
     *
     * @param list
     * @param decisionWarnList
     * @return
     */
    private DecisionWarnEntity dealComprehensive(List<DecisionEntity> list, List<DecisionWarnEntity> decisionWarnList) {
        DecisionWarnEntity decisionWarnEntity = null;
        for (DecisionEntity v : list) {
            if (DecisionSupportTargetConstants.ZHGK == v.getTargetId().intValue()) {
                decisionWarnEntity = DecisionExecute.get(DecisionStrategyEnum.COMPREHENSIVE, decisionWarnList, v);
            }
        }
        return decisionWarnEntity;
    }

    /**
     * 查询风险项数目
     *
     * @param type
     * @return
     */
    private Integer findRiskNumber (Integer type, String beginTime, String endTime) {
        return baseMapper.selectCount(
                new LambdaQueryWrapper<DecisionWarnEntity>()
                .eq(DecisionWarnEntity::getAlarmType, type)
                .between(DecisionWarnEntity::getCreateTime, beginTime, endTime)
                .ne(DecisionWarnEntity::getTargetId, DecisionSupportTargetConstants.ZHGK)
        );
    }

    /**
     * 初始化DecisionWarnWrapperDto，默认赋值
     *
     * @param beginTime
     * @param endTime
     * @param missConclusionText 缺失话术
     * @return
     */
    private DecisionWarnWrapperDto initDecisionWarnWrapper (String beginTime, String endTime, String missConclusionText) {
        // 低风险项数目
        Integer lowRiskNum = findRiskNumber(DecisionSupportConstants.LOW_RISK_TYPE, beginTime, endTime);
        // 中风险项数目
        Integer  mediumRiskNum = findRiskNumber(DecisionSupportConstants.MEDIUM_RISK_TYPE, beginTime, endTime);
        // 高风险项数目
        Integer  highRiskNum =findRiskNumber(DecisionSupportConstants.HIGH_RISK_TYPE, beginTime, endTime);

        // 获取上次分析时间
        Object lastTime = redisTemplate.opsForValue().get(DecisionSupportConstants.LAST_ANALYZE_DATE_KEY);

        // 设置本次分析时间
        redisTemplate.opsForValue().set(DecisionSupportConstants.LAST_ANALYZE_DATE_KEY, DateTimeUtil.getCurrentTime());

        String lastAnalyzeDate = "";
        if (!ObjectUtils.isEmpty(lastTime)) {
            lastAnalyzeDate = String.valueOf(lastTime);
        }

        String conclusionText = missConclusionText;

        // 查询概况设置的话术
        DecisionWarnEntity comprehensive = baseMapper.selectOne(new LambdaQueryWrapper<DecisionWarnEntity>()
                .eq(DecisionWarnEntity::getTargetId, DecisionSupportTargetConstants.ZHGK)
                .between(DecisionWarnEntity::getCreateTime, beginTime, endTime));
        // 概况是否使用缺失话术
        if (!DecisionSupportConstants.USE_MISS_CONCLUSION_TEXT.equals(comprehensive.getIsUseMissConclusionText())) {
            conclusionText = comprehensive.getConclusionText();
        }

        return  DecisionWarnWrapperDto.builder()
                .conclusionText(conclusionText)
                .warnTotal(lowRiskNum + mediumRiskNum + highRiskNum)
                .lowRiskNum(lowRiskNum)
                .mediumRiskNum(mediumRiskNum)
                .highRiskNum(highRiskNum)
                .analyzeDate(DateTimeUtil.getCurrentYearAndMonth())
                .lastAnalyzeDate(lastAnalyzeDate)
                .build();
    }

}
