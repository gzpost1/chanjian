package com.yjtech.wisdom.tourism.service;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.path.DistrictPathEnum;
import com.yjtech.wisdom.tourism.common.utils.DateTimeUtil;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.common.utils.JsonUtils;
import com.yjtech.wisdom.tourism.common.utils.MathUtil;
import com.yjtech.wisdom.tourism.dto.*;
import com.yjtech.wisdom.tourism.dto.vo.UserVo;
import com.yjtech.wisdom.tourism.vo.DataOverviewVo;
import com.yjtech.wisdom.tourism.vo.MonthPassengerFlowVo;
import com.yjtech.wisdom.tourism.vo.VisitorVo;
import com.yjtech.wisdom.tourism.vo.YearPassengerFlowVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 游客结构-调用区县大数据
 *
 * @author renguangqian
 * @date 2021/7/22 14:55
 */
@Service
@Slf4j
public class DistrictTourService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${tourist.districtHost}")
    private String districtHost;

    /**
     * 查询游客总数据-数据总览
     *
     * @param vo
     * @return
     */
    public DataOverviewDto queryDataOverview (DataOverviewVo vo) {

        String url = districtHost + DistrictPathEnum.DATA_OVERVIEW.getPath();

        // 10.到访全部游客
        vo.setStatisticsType("10");
        long allTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(requestDistrict(url, vo, "到访全部游客"), "data")));

        // 11.到访省内游客
        vo.setStatisticsType("11");
        long provinceInsideTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(requestDistrict(url, vo, "到访省内游客"), "data")));

        // 12.到访省外游客
        vo.setStatisticsType("12");
        long provinceOutsideTouristNum = Long.parseLong(String.valueOf(JsonUtils.getValueByKey(requestDistrict(url, vo, "到访省外游客"), "data")));

        return DataOverviewDto.builder()
                .allTouristNum(allTouristNum)
                .provinceOutsideTouristNum(provinceOutsideTouristNum)
                .provinceInsideTouristNum(provinceInsideTouristNum)
                .build();
    }

    /**
     * 游客来源_分页查询
     *
     * 游客来源省份TOP5 31
     * 游客省内来源地市TOP5 32
     *
     * @param vo
     * @return
     */
    public IPage<VisitorDto> queryVisitor (VisitorVo vo) {
        String url = districtHost + DistrictPathEnum.TOURISTS_SOURCE.getPath();
        String result = requestDistrict(url, vo, "省级游客来源");
        List<VisitorDto> visitorDtoList = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), VisitorDto.class);
        return page(vo.getPageNo(), vo.getPageSize(), visitorDtoList);
    }

    /**
     * 本年客流趋势
     *
     * @param vo
     * @return
     */
    public List<YearPassengerFlowDto> queryYearPassengerFlow (YearPassengerFlowVo vo) {
        String url = districtHost + DistrictPathEnum.YEAR_PASSENGER_FLOW.getPath();
        String result = requestDistrict(url, vo, DistrictPathEnum.YEAR_PASSENGER_FLOW.getDesc());
        return JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), YearPassengerFlowDto.class);
    }

    /**
     * 本月客流趋势
     *
     * @param vo
     * @return
     */
    public List<MonthPassengerFlowDto> queryMonthPassengerFlow (MonthPassengerFlowVo vo) {
        String url = districtHost + DistrictPathEnum.MONTH_PASSENGER_FLOW.getPath();
        String result = requestDistrict(url, vo, DistrictPathEnum.MONTH_PASSENGER_FLOW.getDesc());
        // 当月数据
        List<MonthPassengerFlowDto> currentMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), MonthPassengerFlowDto.class);

        // 修改上月日期
        vo.setBeginDate(DateTimeUtil.getLastYearDate(vo.getBeginDate()));
        vo.setEndDate(DateTimeUtil.getLastYearDate(vo.getEndDate()));

        // 上月数据
        List<MonthPassengerFlowDto> lastMonth = JSONObject.parseArray(String.valueOf(JsonUtils.getValueByKey(result, "data")), MonthPassengerFlowDto.class);

        for (int i = 0; i < currentMonth.size(); i++) {
            Integer currentNumber = currentMonth.get(i).getNumber();
            // 如果当天数据为0， 则直接赋值为“-”
            if (0 == currentNumber) {
                currentMonth.get(i).setBeforeDay("-");
                currentMonth.get(i).setLastYearDay("-");
                break;
            }
            Integer lastNumber = lastMonth.get(i).getNumber();
            // 处理第一天 无数据
            if (0 == i) {
                currentMonth.get(i).setBeforeDay("-");
            } else {
                // 前一天的数据
                Integer beforeNumber = currentMonth.get(i - 1).getNumber();
                String beforeDay = MathUtil.calPercent(new BigDecimal(currentNumber - beforeNumber), new BigDecimal(currentNumber), 2).toString();
                currentMonth.get(i).setBeforeDay(beforeDay);
            }

            String LastYearDay = MathUtil.calPercent(new BigDecimal(currentNumber - lastNumber), new BigDecimal(currentNumber), 2).toString();
            currentMonth.get(i).setLastYearDay(LastYearDay);
        }
        return currentMonth;
    }



    /**
     * 区县大数据请求方法
     *
     * @param vo
     * @param <Vo>
     * @return
     */
    private<Vo extends UserVo> String requestDistrict (String url, Vo vo, String desc) {
        // token生成规则
        String tokenKey = vo.getUserId() + "_" + DateUtils.dateTime();
        String token = getToken(vo, tokenKey);

        log.info("【{}】-请求URL：{}", desc, url);
        log.info("【{}】-请求入参：{}", desc, JSONObject.toJSONString(vo));
        String result = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(JSONObject.toJSONString(vo))
                .execute()
                .body();
        log.info("【{}】-请求返回：{}", desc, result);

        return result;
    }

    /**
     * 区县大数据获取token
     *
     * @param vo
     * @param tokenKey
     * @param <Vo>
     * @return
     */
    private <Vo extends UserVo> String getToken(Vo vo, String tokenKey) {
        // redis获取 token
        String token = redisTemplate.opsForValue().get(tokenKey);

        // token为空 则重新登录获取
        if (StringUtils.isEmpty(token)) {
            log.info("【区县大数据】token失效，重新登录！");
            // 进行登录获取有效token
            String loginUlr = districtHost + DistrictPathEnum.LOGIN.getPath();
            log.info("【区县大数据】-登录接口URL：{}", loginUlr);
            log.info("【区县大数据】-用户名：{}", vo.getUserName());
            log.info("【区县大数据】-密码：{}", vo.getPassword());

            // 构建登录请求参数
            DistrictLoginDto districtLoginDto = DistrictLoginDto.builder()
                    .account(vo.getUserName())
                    .password(vo.getPassword())
                    .loginType(3)
                    .build();

            String result = HttpRequest.post(loginUlr)
                    .header("Content-Type", "application/json")
                    .body(JSONObject.toJSONString(districtLoginDto))
                    .execute()
                    .body();
            log.info("【区县大数据】-返回结果：{}", result);
            String authorization = "Bearer " + JsonUtils.getValueByKey(result, "authorization");
            log.info("【区县大数据】-Authorization：{}", authorization);
            // 8小时过期
            redisTemplate.opsForValue().set(tokenKey, authorization, 7, TimeUnit.HOURS);
        }
        log.info("【区县大数据】token：{}", token);
        return token;
    }

    /**
     * 集合进行自主分页
     *
     * @param pageNo
     * @param pageSize
     * @param list
     * @param <E>
     * @return
     */
    public static <E> IPage<E> page (int pageNo, int pageSize, List<E> list) {
        List<E> newList = Lists.newArrayList();

        int begin = pageNo * pageSize - pageSize;
        int end = pageNo * pageSize;
        int pages = 0;

        if (end > list.size()) {
            end = list.size();
        }
        for (int i = begin; i < end; i++) {
            newList.add(list.get(i));
        }

        // 计算页面大小
        if (!CollectionUtils.isEmpty(list)) {
            BigDecimal bcs = new BigDecimal(list.size());
            BigDecimal cs = new BigDecimal(pageSize);
            BigDecimal divideResult = cs.divide(bcs, 0, BigDecimal.ROUND_UP);
            pages = divideResult.intValue();
        }

        // 设置page值
        Page<E> page  = new Page<>();
        page.setPages(pages);
        page.setCurrent(pageNo);
        page.setTotal(list.size());
        page.setSize(pageSize);
        page.setRecords(newList);
        return page;
    }


    /**
     * 集合进行自主分页
     *
     * @param pageNo
     * @param pageSize
     * @param list
     * @param <E>
     * @return
     */
    public static <E> IPage<E> page (Long pageNo, Long pageSize, List<E> list) {
        return page(pageNo.intValue(), pageSize.intValue(), list);
    }
}
