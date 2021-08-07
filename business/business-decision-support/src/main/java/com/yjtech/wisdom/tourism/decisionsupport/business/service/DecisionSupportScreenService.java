package com.yjtech.wisdom.tourism.decisionsupport.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportConstants;
import com.yjtech.wisdom.tourism.common.constant.DecisionSupportTargetConstants;
import com.yjtech.wisdom.tourism.common.enums.DecisionSupportConfigEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.decisionsupport.base.service.TargetQueryService;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionWarnItemDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.dto.DecisionWarnWrapperDto;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.entity.DecisionWarnEntity;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.mapper.DecisionWarnMapper;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnPageVo;
import com.yjtech.wisdom.tourism.decisionsupport.business.vo.DecisionWarnVo;
import com.yjtech.wisdom.tourism.decisionsupport.common.execute.DecisionExecute;
import com.yjtech.wisdom.tourism.decisionsupport.business.instance.DecisionStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


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
    private TargetQueryService targetQueryService;

    /**
     * 查询决策预警_分页
     *
     * @param vo
     * @return
     */
    public IPage<DecisionWarnWrapperDto> queryPageDecisionWarn (DecisionWarnPageVo vo) {
        // 上月第一天日期
        String currentLastMonthFirstDayStr = DateTimeUtil.getCurrentLastMonthFirstDayStr() + " 00:00:00";
        // 上月最后一天日期
        String currentLastMonthLastDayStr = DateTimeUtil.getCurrentLastMonthLastDayStr() + " 23:59:59";

        DecisionWarnWrapperDto decisionWarnWrapperDto = initDecisionWarnWrapper(currentLastMonthFirstDayStr, currentLastMonthLastDayStr);

        // 去数据库 查询符合条件的数据
        IPage<DecisionWarnItemDto> result = baseMapper.selectPage(new Page<>(vo.getPageNo(), vo.getPageSize()),
                new LambdaQueryWrapper<DecisionWarnEntity>()
                        .between(DecisionWarnEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr)
        ).convert(v -> JSONObject.parseObject(JSONObject.toJSONString(v), DecisionWarnItemDto.class));

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
        String currentLastMonthFirstDayStr = DateTimeUtil.getCurrentLastMonthFirstDayStr() + " 00:00:00";
        // 上月最后一天日期
        String currentLastMonthLastDayStr = DateTimeUtil.getCurrentLastMonthLastDayStr() + " 23:59:59";

        DecisionWarnWrapperDto decisionWarnWrapperDto = initDecisionWarnWrapper(currentLastMonthFirstDayStr, currentLastMonthLastDayStr);

        List<DecisionWarnEntity> result = baseMapper.selectList(new LambdaQueryWrapper<DecisionWarnEntity>()
                .between(DecisionWarnEntity::getCreateTime, currentLastMonthFirstDayStr, currentLastMonthLastDayStr)
        );

        List<DecisionWarnItemDto> realResult = JSONObject.parseArray(JSONObject.toJSONString(result), DecisionWarnItemDto.class);
        // 构造数据
        decisionWarnWrapperDto.setList(realResult);

        return decisionWarnWrapperDto;
    }


    /**
     * 分析决策风险
     */
    public void analyzeDecisionWarn () {
        // 查询所有 决策数据
        List<DecisionEntity> decisionList = decisionMapper.selectList(null);

        List<DecisionWarnEntity> list = warnHandle(decisionList);

        // 上月第一天日期
        String currentMonthFirstDayStr = DateTimeUtil.getCurrentMonthFirstDayStr() + " 00:00:00";
        // 上月最后一天日期
        String currentMonthLastDayStr = DateTimeUtil.getCurrentMonthLastDayStr() + " 23:59:59";

        // 先查询本月是否已经存在生成分析的数据，如果存在 则先删除本月数据
        LambdaQueryWrapper<DecisionWarnEntity> queryWrapper = new LambdaQueryWrapper<DecisionWarnEntity>()
                .between(DecisionWarnEntity::getCreateTime, currentMonthFirstDayStr, currentMonthLastDayStr);
        Integer countTotal = baseMapper.selectCount(queryWrapper);
        if (countTotal > 0) {
            baseMapper.delete(queryWrapper);
        }

        // 进行本月数据生成
        for (DecisionWarnEntity i : list) {
            try {
                baseMapper.insert(i);
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
    private List<DecisionWarnEntity> warnHandle(List<DecisionEntity> list) {

        ArrayList<DecisionWarnEntity> decisionWarnList = Lists.newArrayList();

        // 决策预警数据进行处理
        for (DecisionEntity entity : list) {
            decisionWarnList.add(dealWarnConfigAndCompute(entity));
        }

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
    private DecisionWarnEntity dealWarnConfigAndCompute(DecisionEntity entity) {
        // 基础属性 设置
        DecisionWarnEntity result = null;

        int targetId = entity.getTargetId().intValue();

        switch (targetId) {
            // 省外游客
            case DecisionSupportTargetConstants.SWYK :
                result = DecisionExecute.get(DecisionStrategyEnum.PROVINCE_OUTSIDE_TOUR, entity);
                break;

            // 省内游客
            case DecisionSupportTargetConstants.SNYK :
                break;

            // 整体游客
            case DecisionSupportTargetConstants.ZTYK :
                break;

            // 整体景区客流
            case DecisionSupportTargetConstants.ZTJQKL :
                break;

            // 景区客流排行
            case DecisionSupportTargetConstants.JQKLPH :
                break;

            // 整体景区满意度
            case DecisionSupportTargetConstants.ZTJQMYD :
                break;

            // 景区满意度排行
            case DecisionSupportTargetConstants.JQMYDPH :
                break;

            // 整体酒店民宿满意度
            case DecisionSupportTargetConstants.ZTJDMSMYD :
                break;

            // 酒店民宿满意度排行
            case DecisionSupportTargetConstants.JDMSMYDPH :
                break;

            // 投诉量
            case DecisionSupportTargetConstants.TSL :
                break;

            // 订单量
            case DecisionSupportTargetConstants.DDL :
                break;

            // 交易额
            case DecisionSupportTargetConstants.JYE :
                break;

            // 旅游投诉
            case DecisionSupportTargetConstants.LYTS :
                break;

            // 应急事件统计
            case DecisionSupportTargetConstants.YJSJTJ :
                break;

            // 高发应急事件
            case DecisionSupportTargetConstants.GBFYJSJ :
                break;

            default:
                result = DecisionWarnEntity.builder().build();
                break;
        }

        // 通用属性设置
        result.setWarnName(entity.getConfigName());
        return result;
    }

    /**
     * 话术统一处理
     *
     * @param conclusionText
     * @return
     */
    private String dealConclusionText(String conclusionText) {
        String result = "";
        if (!StringUtils.isEmpty(conclusionText)) {
            // 平台简称
            result = conclusionText.replaceAll(DecisionSupportConfigEnum.PLATFORM_SIMPLE_NAME.getKey(), targetQueryService.queryPlatformSimpleName().getSimpleName());

            // 统计年月
            result = result.replaceAll(DecisionSupportConfigEnum.YEAR_MONTH_STATISTICAL.getKey(), targetQueryService.queryLastMonth().getYearMonth());

            // 省外游客数量
            result = result.replaceAll(DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_NUM.getKey(), targetQueryService.queryProvinceOutsideNumber());

            // 环比（较上月）
            result = result.replaceAll(DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_HB.getKey(), targetQueryService.queryHbProvinceOutside());

            // 同比（较去年同月）
            result = result.replaceAll(DecisionSupportConfigEnum.PROVINCE_OUTSIDE_TOUR_TB.getKey(), targetQueryService.queryTbProvinceOutside());
        }
        return result;
    }

    /**
     * 数值类型 配置项处理
     *
     * @param entity
     * @param result
     * @return
     */
    private DecisionWarnEntity dealNumber(DecisionEntity entity, DecisionWarnEntity result) {
        int id = entity.getConfigId().intValue();

        switch (id) {


            // 省内游客 _省内游客数量 （数值）
            case DecisionSupportConstants.SNYK_SNYKSL :
                break;

            // 省内游客 _环比变化（较上月） （数值）
            case DecisionSupportConstants.SNYK_HBBH :
                break;

            // 省内游客 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.SNYK_TBBH :
                break;

            // 整体游客 _整体游客数量 （数值）
            case DecisionSupportConstants.ZTYK_ZTYKSL :
                break;

            // 整体游客 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTYK_HBBH :
                break;

            // 整体游客 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTYK_TBBH :
                break;

            // 整体景区客流 _全部景区接待数量 （数值）
            case DecisionSupportConstants.ZTJQKL_QBJQJDSL :
                break;

            // 整体景区客流 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJQKL_HBBH :
                break;

            // 整体景区客流 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJQKL_TBBH :
                break;
            // 景区客流排行 _游客流失最多景区接待量 （数值）
            case DecisionSupportConstants.JQKLPH_YKLSZDJQJDL :
                break;

            // 景区客流排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JQKLPH_HBBH :
                break;

            // 景区客流排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JQKLPH_TBBH :
                break;

            // 整体景区满意度 _整体景区评价数量 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQPJSL :
                break;

            // 整体景区满意度 _整体景区好评数量 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQHPSL :
                break;

            // 整体景区满意度 _整体景区满意度 （数值）
            case DecisionSupportConstants.ZTJQMYD_ZTJQMYD :
                break;

            // 整体景区满意度 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJQMYD_HBBH :
                break;

            // 整体景区满意度 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJQMYD_TBBH :
                break;

            // 景区满意度排行 _满意度下降最多景区评价量 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQPJL :
                break;

            // 景区满意度排行 _满意度下降最多景区好评量 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQHPL :
                break;

            // 景区满意度排行 _满意度下降最多景区满意度 （数值）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQMYD :
                break;

            // 景区满意度排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JQMYDPH_HBBH :
                break;

            // 景区满意度排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JQMYDPH_TBBH :
                break;

            // 整体酒店民宿满意度 _整体酒店民宿评价数量 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSPJSL :
                break;

            // 整体酒店民宿满意度 _整体酒店民宿好评数量 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSHPSL :
                break;

            // 整体酒店民宿满意度 _整体酒店民宿满意度 （数值）
            case DecisionSupportConstants.ZTJDMSMYD_ZTJDMSMYD :
                break;

            // 整体酒店民宿满意度 _环比变化（较上月） （数值）
            case DecisionSupportConstants.ZTJDMSMYD_HBBH :
                break;

            // 整体酒店民宿满意度 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.ZTJDMSMYD_TBBH :
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿名称 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSMC :
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿评价量 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSPJL :
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿好评量 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSHPL :
                break;

            // 酒店民宿满意度排行 _满意度下降最多酒店民宿满意度 （数值）
            case DecisionSupportConstants.JDMSMYDPH_MYDXJZDJDMSMYD :
                break;

            // 酒店民宿满意度排行 _环比变化（较上月） （数值）
            case DecisionSupportConstants.JDMSMYDPH_HBBH :
                break;

            // 酒店民宿满意度排行 _同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JDMSMYDPH_TBBH :
                break;

            // 投诉量_一码游投诉数量 （数值）
            case DecisionSupportConstants.TSL_YMYTSL :
                break;

            // 投诉量_环比变化（较上月） （数值）
            case DecisionSupportConstants.TSL_HBBH :
                break;

            // 投诉量_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.TSL_TBBH :
                break;

            // 订单量_一码游订单数量 （数值）
            case DecisionSupportConstants.DDL_YMLDDSL :
                break;

            // 订单量_环比变化（较上月） （数值）
            case DecisionSupportConstants.DDL_HBBH :
                break;

            // 订单量_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.DDL_TBBH :
                break;

            // 交易额_一码游交易额 （数值）
            case DecisionSupportConstants.JYE_YMYJYE :
                break;

            // 交易额_环比变化（较上月） （数值）
            case DecisionSupportConstants.JYE_HBBH :
                break;

            // 交易额_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.JYE_TBBH :
                break;

            // 旅游投诉_旅游投诉数量 （数值）
            case DecisionSupportConstants.LYTS_YLTSSL :
                break;

            // 旅游投诉_环比变化（较上月） （数值）
            case DecisionSupportConstants.LYTS_HBBH :
                break;

            // 旅游投诉_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.LYTS_TBBH :
                break;


            // 应急事件统计_应急事件数量 （数值）
            case DecisionSupportConstants.YJSJTJ_YJSJSL :
                break;

            // 应急事件统计_环比变化（较上月） （数值）
            case DecisionSupportConstants.YJSJTJ_HBBH :
                break;

            // 应急事件统计_同比变化（较去年同月） （数值）
            case DecisionSupportConstants.YJSJTJ_TBBH :
                break;

            // 高并发应急事件_应急事件数量 （数值）
            case DecisionSupportConstants.GBFYJSJ_YJSJSL :
                break;

            // 高并发应急事件_高发事件类型环比变化（较上月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLXHBBH :
                break;

            // 高并发应急事件_高发事件类型同比变化（较去年同月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLXTBBH :
                break;

            // 高并发应急事件_高发事件等级环比变化（较上月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJHBBH :
                break;

            // 高并发应急事件_高发事件等级同比变化（较去年同月） （数值）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJTBBH :
                break;

            default:
                break;
        }
        return null;
    }

    /**
     * 文本类 配置项处理
     *
     * @param entity
     * @param result
     * @return
     */
    private DecisionWarnEntity dealText(DecisionEntity entity, DecisionWarnEntity result) {
        int id = entity.getConfigId().intValue();
        result.setWarnName(entity.getConfigName());
        switch (id) {


            // 省内游客 _统计年月 （文本）
            case DecisionSupportConstants.SNYK_TJNY :
                break;

            // 省内游客 _平台简称 （文本）
            case DecisionSupportConstants.SNYK_PTJC :
                break;

            // 整体游客 _统计年月 （文本）
            case DecisionSupportConstants.ZTYK_TJNY :
                break;

            // 整体游客 _平台简称 （文本）
            case DecisionSupportConstants.ZTYK_PTJC :
                break;

            // 整体景区客流 _统计年月 （文本）
            case DecisionSupportConstants.ZTJQKL_TJNY :
                break;

            // 整体景区客流 _平台简称 （文本）
            case DecisionSupportConstants.ZTJQKL_PTJC :
                break;

            // 景区客流排行 _统计年月 （文本）
            case DecisionSupportConstants.JQKLPH_TJNY :
                break;

            // 景区客流排行 _游客流失最多景区名称 （文本）
            case DecisionSupportConstants.JQKLPH_YKLSZDJQMC :
                break;

            // 景区客流排行 _其他游客流失景区名称 （文本）
            case DecisionSupportConstants.JQKLPH_QTYKLSJQMC :
                break;

            // 整体景区满意度 _统计年月 （文本）
            case DecisionSupportConstants.ZTJQMYD_TJNY :
                break;

            // 景区满意度排行 _统计年月 （文本）
            case DecisionSupportConstants.JQMYDPH_TJNY :
                break;

            // 景区满意度排行 _满意度下降最多景区名称 （文本）
            case DecisionSupportConstants.JQMYDPH_MYDXJZDJQMC :
                break;

            // 景区满意度排行 _其他满意度下降景区名称 （文本）
            case DecisionSupportConstants.JQMYDPH_QTMYDXJJQMC :
                break;

            // 整体酒店民宿满意度 _统计年月 （文本）
            case DecisionSupportConstants.ZTJDMSMYD_TJNY :
                break;

            // 酒店民宿满意度排行 _统计年月 （文本）
            case DecisionSupportConstants.JDMSMYDPH_TJNY :
                break;

            // 酒店民宿满意度排行 _其他满意度下降酒店民宿名称 （文本）
            case DecisionSupportConstants.JDMSMYDPH_QTMYDXJJDMSMC :
                break;

            // 投诉量_统计年月 （文本）
            case DecisionSupportConstants.TSL_TJNY :
                break;

            // 订单量_统计年月 （文本）
            case DecisionSupportConstants.DDL_TJNY :
                break;

            // 交易额_统计年月 （文本）
            case DecisionSupportConstants.JYE_TJNY :
                break;

            // 旅游投诉_统计年月 （文本）
            case DecisionSupportConstants.LYTS_TJNY :
                break;

            // 应急事件统计_统计年月 （文本）
            case DecisionSupportConstants.YJSJTJ_TJNY :
                break;
            // 高并发应急事件_统计年月 （文本）
            case DecisionSupportConstants.GBFYJSJ_TJNY :
                break;

            // 高并发应急事件_高发事件类型 （文本）
            case DecisionSupportConstants.GBFYJSJ_GBFSJLX :
                break;

            // 高并发应急事件_高发事件等级 （文本）
            case DecisionSupportConstants.GBFYJSJ_GBFSJDJ :
                break;

            default:
                break;
        }
        return null;
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
        );
    }

    /**
     * 初始化DecisionWarnWrapperDto，默认赋值
     *
     * @return
     */
    private DecisionWarnWrapperDto initDecisionWarnWrapper (String beginTime, String endTime) {
        // 低风险项数目
        Integer lowRiskNum = findRiskNumber(DecisionSupportConstants.LOW_RISK_TYPE, beginTime, endTime);
        // 中风险项数目
        Integer  mediumRiskNum = findRiskNumber(DecisionSupportConstants.MEDIUM_RISK_TYPE, beginTime, endTime);
        // 高风险项数目
        Integer  highRiskNum =findRiskNumber(DecisionSupportConstants.HIGH_RISK_TYPE, beginTime, endTime);

        // 获取上次分析时间
        String lastAnalyzeDate = String.valueOf(redisTemplate.opsForValue().get(DecisionSupportConstants.LAST_ANALYZE_DATE_KEY));

        return  DecisionWarnWrapperDto.builder()
                .warnTotal(lowRiskNum + mediumRiskNum + highRiskNum)
                .lowRiskNum(lowRiskNum)
                .mediumRiskNum(mediumRiskNum)
                .highRiskNum(highRiskNum)
                .analyzeDate(DateTimeUtil.getCurrentYearAndMonth())
                .lastAnalyzeDate(lastAnalyzeDate)
                .build();
    }

}
