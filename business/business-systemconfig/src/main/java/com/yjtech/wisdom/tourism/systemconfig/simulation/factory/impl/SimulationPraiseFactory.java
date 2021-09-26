package com.yjtech.wisdom.tourism.systemconfig.simulation.factory.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.Arith;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise.SimulationPraiseDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise.SimulationPraiseHotWordDto;
import com.yjtech.wisdom.tourism.systemconfig.simulation.factory.SimulationFactory;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 李波
 * @description: 口碑
 * @date 2021/7/1412:07
 */
@Component(value = SimulationConstants.PRAISE)
public class SimulationPraiseFactory implements SimulationFactory<SimulationPraiseDto> {
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Object parse(String json) {
        return JSONObject.parseObject(json, SimulationPraiseDto.class);
    }

    @Override
    public String toJSONBytes(SimulationPraiseDto obj) {
        return JSONObject.toJSONString(obj);
    }

    @SneakyThrows
    @Override
    public void generateMockRedisData(SimulationPraiseDto obj) {
        //日累计评价量
        int dayleiji = (int) (obj.getDayInit() + (int) Arith.round(Double.valueOf(obj.getRandomNumber()), 0));
        //全部评价量
        int allDayleiji = (int) Arith.round(dayleiji * obj.getAllCoefficient() + obj.getAllAddend(), 0);

        Map<String, Object> jsonMap = new HashMap<>();

        //---------------全部评价-----------------------------------------------------------------start
        Map<String, Object> allPingjia = new HashMap<>();

        //好中差集合
        List<BasePercentVO> valuesList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(obj.getCommentTypeList())) {
            for (BaseVO baseVO : obj.getCommentTypeList()) {
                Double rate = Double.valueOf(baseVO.getValue());

                BasePercentVO excellentPercentVo = new BasePercentVO();
                if (StringUtils.equals("0", baseVO.getName())) {
                    excellentPercentVo.setName(SimulationConstants.GOOD_EVALUATE_DESCRIBE);
                } else if (StringUtils.equals("1", baseVO.getName())) {
                    excellentPercentVo.setName(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE);
                } else {
                    excellentPercentVo.setName(SimulationConstants.BAD_EVALUATE_DESCRIBE);
                }
                excellentPercentVo.setValue(String.valueOf(Arith.round(Arith.mul(allDayleiji, rate * 0.01), 0)));
                excellentPercentVo.setRate(rate);
                valuesList.add(excellentPercentVo);
            }
        }
        allPingjia.put("valuesList", valuesList);
        allPingjia.put("commentTotal", allDayleiji);
        jsonMap.put("one", allPingjia);

        //---------------全部评价-----------------------------------------------------------------end

        //------------------评论月趋势------------------------------------------------------------start
        Map<String, Object> map = new HashMap<>();
        jsonMap.put("two", map);

        //评论热度趋势
        List<BaseVO> monthVolues = new ArrayList<>();
        map.put("totalList", monthVolues);

        //评论满意度趋势
        List<BasePercentVO> excellenRatetList = new ArrayList<>();
        map.put("excellentList", excellenRatetList);
        //--------------------------------------------

        //---------------近12月好评趋势/近12月差评趋势-------
        Map<String, Object> map1 = new HashMap<>();
        jsonMap.put("three", map1);

        //近12月好评趋势
        List<BaseVO> excellentList = new ArrayList<>();
        map1.put("excellentList", excellentList);

        //近12月差评趋势
        List<BaseVO> poorList = new ArrayList<>();
        map1.put("poorList", poorList);
        //----------------------------------------------

        //本月累计评价量
        int monthleiji = dayleiji * (LocalDate.now().getDayOfMonth());
        //其余每月评价量
        for (int i = 11; i >= 0; i--) {
            //评价总数随机数
            int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
            //好评比例随机数
            double randomHaoRate = Arith.round((80 + Math.random() * (100 - (80) + 1)), 2);
            //差评比例随机数
            double randomHuaiRate = Arith.round((0 + Math.random() * (15 - (0) + 1)), 2);

            //月份
            Date date = DateTimeUtil.localDateToDate(LocalDate.now().minusMonths(i));
            String monthStr = DateTimeUtil.dateToString(date, "yyyy-mm");
            //每月总数
            int monthValue = (int) Arith.round(monthleiji * (100 + randomInt) / 100, 0);

            //如果是本月则使用页面填入值
            if (i == 0) {
                monthValue = monthleiji;

                for (BaseVO baseVO : obj.getCommentTypeList()) {
                    Double rate = Double.valueOf(baseVO.getValue());

                    BasePercentVO excellentPercentVo = new BasePercentVO();
                    if (StringUtils.equals("0", baseVO.getName())) {
                        randomHaoRate = rate;
                    } else if (StringUtils.equals("2", baseVO.getName())) {
                        randomHuaiRate = rate;
                    }
                }
            }

            //好评数
            int haopingNum = (int) Arith.round(monthValue * randomHaoRate * 0.01, 0);
            //怀评数
            int huaipingNum = (int) Arith.round(monthValue * randomHuaiRate * 0.01, 0);

            monthVolues.add(new BaseVO(monthStr, String.valueOf(monthValue)));
            excellentList.add(new BaseVO(monthStr, String.valueOf(haopingNum)));
            poorList.add(new BaseVO(monthStr, String.valueOf(huaipingNum)));


            BasePercentVO rateVO = new BasePercentVO();
            rateVO.setName(monthStr);
            rateVO.setRate(randomHaoRate);
            rateVO.setValue(String.valueOf(haopingNum));
            excellenRatetList.add(rateVO);
        }

        //------------------评论月趋势------------------------------------------------------------end


        //---------------热度分布TOP5/满意度分布TOP5-----------------------------------------------start
        Map<String, Object> map4 = new HashMap<>();
        jsonMap.put("four", map4);
        //热度分布TOP5
        List<BaseVO> reduList = new ArrayList<>();
        map4.put("redu", reduList);
        //满意度分布TOP5
        List<BasePercentVO> manyiduList = new ArrayList<>();
        map4.put("manyidu", manyiduList);

        if (CollectionUtils.isNotEmpty(obj.getOtas())) {
            for (SimulationPraiseHotWordDto ota : obj.getOtas()) {
                String value = Arith.round(Arith.mul(allDayleiji, Double.valueOf(ota.getRate()) * 0.01), 0) + "";
                reduList.add(new BaseVO(ota.getName(), value));

                BasePercentVO percentVO = new BasePercentVO();
                percentVO.setName(ota.getName());
                percentVO.setRate(Double.valueOf(ota.getRate()));
                percentVO.setValue(value);
                manyiduList.add(percentVO);
            }
            reduList = reduList.stream().sorted(Comparator.comparing(BaseVO::getValue)).collect(Collectors.toList());
            reduList = Lists.reverse(reduList);
            map4.put("redu", reduList);

            manyiduList = manyiduList.stream().sorted(Comparator.comparing(BasePercentVO::getRate)).collect(Collectors.toList());
            manyiduList = Lists.reverse(manyiduList);
            map4.put("manyidu", manyiduList);

        }
        //---------------热度分布TOP5/满意度分布TOP5-----------------------------------------------end

        //---------------行业评价分布/评论热词词频-----------------------------------------------start
        Map<String, List<BaseVO>> map5 = new HashMap<>();
        jsonMap.put("five", map5);
        //行业评价分布
        List<BaseVO> hangye = new ArrayList<>();
        map5.put("hangye", hangye);
        //评论热词词频
        List<BaseVO> hotwords = new ArrayList<>();
        map5.put("hotword", hotwords);

        if (CollectionUtils.isNotEmpty(obj.getHotWords())) {
            for (BaseVO hotWord : obj.getHotWords()) {
                hotwords.add(new BaseVO(hotWord.getName(), hotWord.getValue()));
            }

            hotwords = hotwords.stream().sorted(Comparator.comparing(BaseVO::getValue)).collect(Collectors.toList());
            hotwords = Lists.reverse(hotwords);
            map5.put("hotword", hotwords);
        }

        if (CollectionUtils.isNotEmpty(obj.getIndustryList())) {
            for (BaseVO industry : obj.getIndustryList()) {
                hangye.add(new BaseVO(industry.getName(), industry.getValue()));
            }

            hangye = hangye.stream().sorted(Comparator.comparing(BaseVO::getValue)).collect(Collectors.toList());
            hangye = Lists.reverse(hangye);
            map5.put("hangye", hangye);
        }

        //---------------行业评价分布/评论热词词频-----------------------------------------------end
        String json = new ObjectMapper().writeValueAsString(jsonMap);
        redisTemplate.opsForValue().set(getCacheKey(SimulationConstants.PRAISE), json);

    }

}
