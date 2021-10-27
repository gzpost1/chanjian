package com.yjtech.wisdom.tourism.marketing.extensionpoint.mock;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.AnalysisMonthChartInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.common.bean.index.HotelStatisticsDTO;
import com.yjtech.wisdom.tourism.common.constant.CacheKeyContants;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.constant.SimulationConstants;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.extension.Extension;
import com.yjtech.wisdom.tourism.extension.ExtensionConstant;
import com.yjtech.wisdom.tourism.hotel.dto.HotelSelectInfoDTO;
import com.yjtech.wisdom.tourism.hotel.service.TbHotelInfoService;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelExtensionConstant;
import com.yjtech.wisdom.tourism.marketing.extensionpoint.HotelQryExtPt;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.*;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.EvaluateQueryVO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.RoomScreenQueryVO;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.hotel.SimulationHotelDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 酒店民宿评价 模拟数据 扩展
 *
 * @author horadirm
 * @date 2021/8/21 13:39
 */
@Extension(bizId = ExtensionConstant.HOTEL,
        useCase = HotelExtensionConstant.HOTEL_QUANTITY,
        scenario = ExtensionConstant.SCENARIO_MOCK)
public class MockHotelQryExtPt implements HotelQryExtPt {

    @Autowired
    private TbHotelInfoService tbHotelInfoService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询评价统计-酒店民宿详情
     * SimulationTravelComplaintDTO simulationTravelComplaintDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.TRAVEL_COMPLAINT);
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatistics(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getStatisticsDetail().get(Long.valueOf(vo.getPlaceId()));
    }

    /**
     * 查询评价统计-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatisticsBigData(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getStatisticsBigData();
    }

    /**
     * 查询评价统计-综合总览
     *
     * @param vo
     * @return
     */
    @Override
    public MarketingEvaluateStatisticsDTO queryEvaluateStatisticsIndex(EvaluateQueryVO vo) {
        HotelSimulationDataDTO cacheInfo = calculateAndQuery(vo.getBeginTime(), vo.getEndTime());

        HotelStatisticsDTO statisticsIndex = cacheInfo.getStatisticsIndex();
        MarketingEvaluateStatisticsDTO dto = new MarketingEvaluateStatisticsDTO();
        dto.setEvaluateTotal(statisticsIndex.getEvaluate());
        dto.setSatisfaction(statisticsIndex.getSatisfaction());

        return dto;
    }

    /**
     * 查询评价类型分布-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryEvaluateTypeDistribution(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getTypeDistributionDetail().get(Long.valueOf(vo.getPlaceId()));
    }

    /**
     * 查询评价类型分布-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public List<BasePercentVO> queryEvaluateTypeDistributionBigData(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getTypeDistributionBigData();
    }

    /**
     * 查询评价热词排行-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryEvaluateHotRank(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getHotRankDetail().get(Long.valueOf(vo.getPlaceId()));
    }

    /**
     * 查询评价热词排行-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryEvaluateHotRankBigData(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getHotRankBigData();
    }

    /**
     * 查询房型价格统计-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public RoomTypePriceScreenDTO queryRoomPriceStatistics(RoomScreenQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceStatisticsDetail().get(Long.valueOf(vo.getHotelId()));
    }

    /**
     * 查询房型价格统计-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public RoomTypePriceScreenDTO queryRoomPriceStatisticsBigData(RoomScreenQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceStatisticsBigData();
    }

    /**
     * 查询房型价格趋势-酒店民宿详情
     *
     * @param vo
     * @return
     */
    @Override
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysis(RoomScreenQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceAnalysisDetail().get(Long.valueOf(vo.getHotelId()));
    }

    /**
     * 查询房型价格趋势-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public List<RoomPriceAnalysisDTO> queryRoomPriceAnalysisBigData(RoomScreenQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceAnalysisBigData();
    }

    /**
     * 查询酒店评价排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<BaseVO> queryEvaluateRank(EvaluateQueryVO vo) {
        HotelSimulationDataDTO dto = calculateAndQuery(vo.getBeginTime(), vo.getEndTime());
        //获取分页数据总数量
        Integer rankTotal = dto.getRankTotal();
        //获取分页信息
        Map<Integer, List<BaseVO>> evaluateRankBigData = dto.getEvaluateRankBigData();
        //构建分页信息
        IPage<BaseVO> page = new Page<>(vo.getPageNo(), vo.getPageSize(), rankTotal);
        if (null != evaluateRankBigData) {
            //当前pageSize=5时，默认获取top5
            if (Constants.NumberConstants.NUMBER_FIVE.equals(vo.getPageSize().intValue())) {
                page.setRecords(evaluateRankBigData.get(-1));
            } else if (Constants.NumberConstants.NUMBER_TEN.equals(vo.getPageSize().intValue())) {
                page.setRecords(evaluateRankBigData.get(vo.getPageNo().intValue()));
            }
        }
        return page;
    }

    /**
     * 查询酒店满意度排行
     *
     * @param vo
     * @return
     */
    @Override
    public IPage<EvaluateSatisfactionRankDTO> queryEvaluateSatisfactionRank(EvaluateQueryVO vo) {
        HotelSimulationDataDTO dto = calculateAndQuery(vo.getBeginTime(), vo.getEndTime());
        //获取分页数据总数量
        Integer rankTotal = dto.getRankTotal();
        //获取分页信息
        Map<Integer, List<EvaluateSatisfactionRankDTO>> satisfactionRankBigData = dto.getSatisfactionRankBigData();
        //构建分页信息
        IPage<EvaluateSatisfactionRankDTO> page = new Page<>(vo.getPageNo(), vo.getPageSize(), rankTotal);
        if (null != satisfactionRankBigData) {
            //当前pageSize=5时，默认获取top5
            if (Constants.NumberConstants.NUMBER_FIVE.equals(vo.getPageSize().intValue())) {
                page.setRecords(satisfactionRankBigData.get(-1));
            } else if (Constants.NumberConstants.NUMBER_TEN.equals(vo.getPageSize().intValue())) {
                page.setRecords(satisfactionRankBigData.get(vo.getPageNo().intValue()));
            }
        }
        return page;
    }

    /**
     * 查询评价量趋势、同比、环比-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateAnalysisBigData(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getEvaluateAnalysisBigData();
    }

    /**
     * 查询评价满意度趋势、同比、环比-酒店民宿大数据
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysisBigData(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getSatisfactionAnalysisBigData();
    }

    /**
     * 查询评价量趋势、同比、环比-评价分析
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateAnalysis(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getEvaluateAnalysis();
    }

    /**
     * 查询评价满意度趋势、同比、环比-评价分析
     *
     * @param vo
     * @return
     */
    @Override
    public List<AnalysisBaseInfo> queryEvaluateSatisfactionAnalysis(EvaluateQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getSatisfactionAnalysis();
    }

    /**
     * 查询酒店均价排行-经济效益
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryRoomPriceRank(RoomScreenQueryVO vo) {
        return calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceRank();
    }

    /**
     * 查询房型价格分布-经济效益
     *
     * @param vo
     * @return
     */
    @Override
    public List<BaseVO> queryRoomTypePriceDistribution(RoomScreenQueryVO vo) {
        List<RoomPriceAnalysisDTO> dtoList = calculateAndQuery(vo.getBeginTime(), vo.getEndTime()).getRoomPriceAnalysisBigData();
        //构建返回信息列表
        List<BaseVO> dataList = Lists.newArrayList();
        if (!dtoList.isEmpty()) {
            //获取房型价格趋势中的第一条数据
            RoomPriceAnalysisDTO dto = dtoList.get(0);
            dataList.add(new BaseVO("大床房", dto.getBigBedPrice().toString()));
            dataList.add(new BaseVO("双床房", dto.getDoubleBedPrice().toString()));
            dataList.add(new BaseVO("亲子/家庭房", dto.getFamilyRoomPrice().toString()));
            dataList.add(new BaseVO("套房", dto.getSuiteRoomPrice().toString()));
        }
        return dataList;
    }


    /**
     * 计算模拟数据
     *
     * @return
     */
    private HotelSimulationDataDTO calculateAndQuery(LocalDateTime beginTime, LocalDateTime endTime) {
        //获取缓存数据
        HotelSimulationDataDTO cacheInfo = redisCache.getCacheObject(CacheKeyContants.HOTEL_SIMULATION_PREFIX + beginTime + endTime);
        if (null != cacheInfo) {
            return cacheInfo;
        }

        //获取当前有效酒店id列表
        List<HotelSelectInfoDTO> hotelInfoList = tbHotelInfoService.queryHotelInfoList();
        //有效酒店个数
        int hotelCount = hotelInfoList.size();

        //获取初始化模板数据
        SimulationHotelDTO simulationHotelDTO = redisCache.getCacheObject(Constants.SIMULATION_KEY + SimulationConstants.HOTEL);

        //查询评价统计-酒店民宿详情
        Map<Long, MarketingEvaluateStatisticsDTO> statisticsDetail = Maps.newHashMap();
        //查询评价类型分布-酒店民宿详情
        Map<Long, List<BasePercentVO>> typeDistributionDetail = Maps.newHashMap();
        //查询评价热词排行-酒店民宿详情
        Map<Long, List<BaseVO>> hotRankDetail = Maps.newHashMap();
        //查询房型价格统计-酒店民宿详情
        Map<Long, RoomTypePriceScreenDTO> roomPriceStatisticsDetail = Maps.newHashMap();
        //查询房型价格趋势-酒店民宿详情
        Map<Long, List<RoomPriceAnalysisDTO>> roomPriceAnalysisDetail = Maps.newHashMap();
        //查询评价热词排行-酒店民宿大数据
        List<BaseVO> hotRankBigDataAll = Lists.newArrayList();
        //查询房型价格统计-酒店民宿大数据
        RoomTypePriceScreenDTO roomPriceStatisticsBigData = new RoomTypePriceScreenDTO(BigDecimal.ZERO);
        //查询酒店均价排行
        List<BaseVO> roomPriceRank = Lists.newArrayList();

        //评价数量总计
        Integer evaluateTotalAll = 0;
        //好评率/满意度总计
        BigDecimal goodRatePercentAll = BigDecimal.ZERO;
        //中评率
        BigDecimal mediumRatePercentAll = BigDecimal.ZERO;
        //差评率
        BigDecimal badRatePercentAll = BigDecimal.ZERO;
        //评分总计
        BigDecimal rateAll = BigDecimal.ZERO;
        //平均价格总计
        BigDecimal averagePriceAll = BigDecimal.ZERO;
        //酒店评价排行总计
        List<BaseVO> evaluateRankAll = Lists.newArrayList();
        //酒店满意度排行总计
        List<EvaluateSatisfactionRankDTO> satisfactionRankAll = Lists.newArrayList();

        //获取当前时间
        String endDate = DateUtil.today();

        if (!hotelInfoList.isEmpty()) {
            //获取评论热词模板列表
            List<BaseVO> hotTagRank = simulationHotelDTO.getHotTagRank();
            for (HotelSelectInfoDTO hotelInfo : hotelInfoList) {
                Long hotelId = hotelInfo.getId();
                int randomInt = (int) (20 + Math.random() * (20 - (-20) + 1));
                //构建单个酒店统计数据
                //评价总数
                Integer evaluateTotal = (simulationHotelDTO.getDayOfInitCount() + randomInt) * (int) beginTime.until(endTime, ChronoUnit.DAYS);
                evaluateRankAll.add(new BaseVO(hotelInfo.getName(), evaluateTotal.toString()));
                evaluateTotalAll += evaluateTotal;
                //满意度/好评率
                BigDecimal goodRatePercent = simulationHotelDTO.getInitGoodRatePercent().add(new BigDecimal(randomInt / 5)).add(new BigDecimal(randomInt / 100)).setScale(1, BigDecimal.ROUND_HALF_UP);
                satisfactionRankAll.add(new EvaluateSatisfactionRankDTO(hotelInfo.getName(), goodRatePercent.toString(), hotelId));
                goodRatePercentAll = goodRatePercentAll.add(goodRatePercent);
                //评分
                BigDecimal rate = simulationHotelDTO.getInitRate().add(new BigDecimal(randomInt / 10));
                rateAll = rateAll.add(rate);
                statisticsDetail.put(hotelId, new MarketingEvaluateStatisticsDTO(evaluateTotal, goodRatePercent, rate));

                //构建单个酒店评价类型分布数据
                //中评率
                BigDecimal mediumRatePercent = new BigDecimal(100).subtract(goodRatePercent).divide(new BigDecimal(5), 1, BigDecimal.ROUND_HALF_UP);
                mediumRatePercentAll = mediumRatePercentAll.add(mediumRatePercent);
                //差评率
                BigDecimal badRatePercent = new BigDecimal(100).subtract(goodRatePercent).subtract(mediumRatePercent);
                badRatePercentAll = badRatePercentAll.add(badRatePercent);
                typeDistributionDetail.put(hotelId, Arrays.asList(
                        new BasePercentVO(SimulationConstants.GOOD_EVALUATE_DESCRIBE, null, goodRatePercent.doubleValue()),
                        new BasePercentVO(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE, null, mediumRatePercent.doubleValue()),
                        new BasePercentVO(SimulationConstants.BAD_EVALUATE_DESCRIBE, null, badRatePercent.doubleValue())));

                if (null != hotTagRank && !hotTagRank.isEmpty()) {
                    //构建单个酒店热词排行
                    List<BaseVO> hotTagDetailList = Lists.newArrayList();
                    for (BaseVO hotTag : hotTagRank) {
                        //计算单日词频
                        int workFrequency = Integer.valueOf(hotTag.getValue()) + randomInt / 10;
                        //配置实际返回模拟数据
                        hotTagDetailList.add(new BaseVO(hotTag.getName(), String.valueOf(workFrequency * (int) beginTime.until(endTime, ChronoUnit.DAYS))));
                    }
                    hotRankDetail.put(hotelId, hotTagDetailList);

                    hotRankBigDataAll.addAll(hotTagDetailList);
                }

                //构建单个酒店房型价格统计
                //历史最高价
                BigDecimal highestPrice = simulationHotelDTO.getHighestPrice().add(new BigDecimal(randomInt));
                //历史最低价
                BigDecimal lowestPrice = simulationHotelDTO.getLowestPrice().add(new BigDecimal(randomInt));
                //平均价格
                BigDecimal averagePrice = simulationHotelDTO.getHighestPrice().add(simulationHotelDTO.getLowestPrice()).divide(new BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP);
                averagePriceAll = averagePriceAll.add(averagePrice);
                roomPriceStatisticsDetail.put(hotelId, new RoomTypePriceScreenDTO(highestPrice, lowestPrice, averagePrice));

                //构建单个酒店房型价格趋势
                roomPriceAnalysisDetail.put(hotelId, calculateRoomPriceAnalysis(endDate, averagePrice, simulationHotelDTO.getRoomTypePrice()));

                //设置整体历史最高价格
                if (highestPrice.compareTo(roomPriceStatisticsBigData.getHighestPrice()) > 0) {
                    roomPriceStatisticsBigData.setHighestPrice(highestPrice);
                }
                //设置整体历史最低价格
                if (roomPriceStatisticsBigData.getLowestPrice().compareTo(BigDecimal.ZERO) == 0) {
                    roomPriceStatisticsBigData.setLowestPrice(lowestPrice);
                } else {
                    if (lowestPrice.compareTo(roomPriceStatisticsBigData.getLowestPrice()) < 0) {
                        roomPriceStatisticsBigData.setLowestPrice(lowestPrice);
                    }
                }
                //构建单个酒店均价信息
                roomPriceRank.add(new BaseVO(hotelInfo.getName(), averagePrice.toString()));
            }
        }

        //查询房型价格统计-酒店民宿大数据
        roomPriceStatisticsBigData.setAveragePrice(roomPriceStatisticsBigData.getHighestPrice().add(roomPriceStatisticsBigData.getLowestPrice()).divide(new BigDecimal(2), 1, BigDecimal.ROUND_HALF_UP));

        //查询酒店民宿统计-综合总览
        HotelStatisticsDTO statisticsIndex = new HotelStatisticsDTO(evaluateTotalAll,
                goodRatePercentAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP),
                averagePriceAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP));

        //查询评价统计-酒店民宿大数据
        MarketingEvaluateStatisticsDTO statisticsBigData = new MarketingEvaluateStatisticsDTO(evaluateTotalAll,
                goodRatePercentAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP),
                rateAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP));

        //查询评价类型分布-酒店民宿大数据
        List<BasePercentVO> typeDistributionBigData = Arrays.asList(
                new BasePercentVO(SimulationConstants.GOOD_EVALUATE_DESCRIBE, null, goodRatePercentAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP).doubleValue()),
                new BasePercentVO(SimulationConstants.MEDIUM_EVALUATE_DESCRIBE, null, mediumRatePercentAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP).doubleValue()),
                new BasePercentVO(SimulationConstants.BAD_EVALUATE_DESCRIBE, null, badRatePercentAll.divide(new BigDecimal(hotelCount), 1, BigDecimal.ROUND_HALF_UP).doubleValue()));

        //所有分页按照每10条一页进行处理
        //查询酒店评价排行-酒店民宿大数据
        Map<Integer, List<BaseVO>> evaluateRankBigData = handleRankPage(evaluateRankAll);
        //查询酒店满意度排行-酒店民宿大数据
        Map<Integer, List<EvaluateSatisfactionRankDTO>> satisfactionRankBigData = handleRankPage(satisfactionRankAll);

        //去重合计 查询评价热词排行-酒店民宿大数据
        List<BaseVO> hotRankBigData = Lists.newArrayList();
        hotRankBigDataAll.parallelStream().collect(Collectors.groupingBy(BaseVO::getName, Collectors.toList())).forEach(
                (id, transfer) -> {
                    transfer.stream().reduce((a, b) -> new BaseVO(a.getName(), String.valueOf(Integer.valueOf(a.getValue()) + Integer.valueOf(b.getValue())))).ifPresent(hotRankBigData::add);
                });
        //降序排列
        hotRankBigData.sort(new Comparator<BaseVO>() {
            @Override
            public int compare(BaseVO o1, BaseVO o2) {
                if (o1.getValue().compareTo(o2.getValue()) > 0) {
                    return -1;
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        //降序排列
        roomPriceRank = roomPriceRank.stream().sorted(Comparator.comparing(BaseVO::getValue).reversed()).collect(Collectors.toList());

        //查询评价量趋势、同比、环比-酒店民宿大数据
        List<AnalysisBaseInfo> evaluateAnalysisBigData = calculateAnalysis(endDate, new BigDecimal(evaluateTotalAll * (Integer.valueOf(endDate.substring(8, 9)))).add(simulationHotelDTO.getMonthOfEvaluateTotal()), null);

        //查询评价量趋势、同比、环比-评价分析
        List<AnalysisBaseInfo> evaluateAnalysis = calculateAnalysis(endDate, new BigDecimal(evaluateTotalAll * (Integer.valueOf(endDate.substring(8, 9)))).add(simulationHotelDTO.getMonthOfEvaluateTotal()), null);

        //查询评价满意度趋势、同比、环比-酒店民宿大数据
        List<AnalysisBaseInfo> satisfactionAnalysisBigData = calculateAnalysis(endDate, null, simulationHotelDTO.getGoodRatePercent());

        //查询评价满意度趋势、同比、环比-评价分析
        List<AnalysisBaseInfo> satisfactionAnalysis = calculateAnalysis(endDate, null, simulationHotelDTO.getGoodRatePercent());

        //查询房型价格趋势-酒店民宿大数据
        List<RoomPriceAnalysisDTO> roomPriceAnalysisBigData = calculateRoomPriceAnalysis(endDate, averagePriceAll.divide(new BigDecimal(hotelInfoList.size()), 1, BigDecimal.ROUND_HALF_UP), simulationHotelDTO.getRoomTypePrice());

        HotelSimulationDataDTO dto = new HotelSimulationDataDTO(statisticsDetail, typeDistributionDetail, hotRankDetail, roomPriceStatisticsDetail, roomPriceAnalysisDetail,
                statisticsIndex, statisticsBigData, typeDistributionBigData, hotelCount, evaluateRankBigData, satisfactionRankBigData,
                hotRankBigData, roomPriceStatisticsBigData, evaluateAnalysisBigData, satisfactionAnalysisBigData, roomPriceAnalysisBigData,
                roomPriceRank, evaluateAnalysis, satisfactionAnalysis);

        //获取缓存数据
        redisCache.setCacheObject(CacheKeyContants.HOTEL_SIMULATION_PREFIX + beginTime + endTime, dto, (int) DateUtils.getCacheExpire(), TimeUnit.MINUTES);

        return dto;
    }

    /**
     * 计算房型价格趋势信息
     *
     * @param averagePrice 平均价格
     * @return
     */
    private List<RoomPriceAnalysisDTO> calculateRoomPriceAnalysis(String endDate, BigDecimal averagePrice, List<BaseVO> roomTypePrice) {
        //获取近90天日期
        String beginTime = DateUtil.offsetDay(DateUtil.parseDate(endDate), -89).toDateStr();
        List<String> dayMarkList = DateUtils.getRangeToList(beginTime, endDate, DateUtils.YYYY_MM_DD, Calendar.DAY_OF_YEAR);
        //构建房型价格趋势信息列表
        List<RoomPriceAnalysisDTO> roomPriceAnalysisDetail = Lists.newArrayList();

        //房型数据转换
        Map<String, String> roomTypePriceMap = roomTypePrice.stream().collect(Collectors.toMap(BaseVO::getName, BaseVO::getValue));

        for (String dayMark : dayMarkList) {
            int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
            roomPriceAnalysisDetail.add(new RoomPriceAnalysisDTO(dayMark,
                    averagePrice.multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP),
                    null == roomTypePriceMap.get("大床房") ? BigDecimal.ZERO :
                            new BigDecimal(Integer.valueOf(roomTypePriceMap.get("大床房")) + randomInt).multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP),
                    null == roomTypePriceMap.get("双床房") ? BigDecimal.ZERO :
                            new BigDecimal(Integer.valueOf(roomTypePriceMap.get("双床房")) + randomInt).multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP),
                    null == roomTypePriceMap.get("亲子/家庭房") ? BigDecimal.ZERO :
                            new BigDecimal(Integer.valueOf(roomTypePriceMap.get("亲子/家庭房")) + randomInt).multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP),
                    null == roomTypePriceMap.get("套房") ? BigDecimal.ZERO :
                            new BigDecimal(Integer.valueOf(roomTypePriceMap.get("套房")) + randomInt).multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)
            ));
        }

        return roomPriceAnalysisDetail;
    }

    /**
     * 计算信息趋势，同比、环比
     *
     * @param currentDate
     * @param monthOfEvaluateTotal
     * @param goodRatePercent
     * @return
     */
    private List<AnalysisBaseInfo> calculateAnalysis(String currentDate, BigDecimal monthOfEvaluateTotal, BigDecimal goodRatePercent) {
        //获取当前月份
        String currentMonth = currentDate.substring(0, 7);

        //初始化当年月份信息
        List<String> monthMarkList = DateUtils.getEveryMonthOfCurrentYear();

        //构建今年月趋势信息
        List<AnalysisMonthChartInfo> currentData = new ArrayList<>();
        //构建去年月趋势信息
        List<AnalysisMonthChartInfo> lastData = new ArrayList<>();

        //临时标记上月默认值，用于计算环比
        BigDecimal lastMonthValue = BigDecimal.ZERO;

        //校验月趋势信息，为无结果的查询设置默认值
        for (String monthMark : monthMarkList) {
            //随机值
            int randomInt = (int) (-20 + Math.random() * (20 - (-20) + 1));
            //设置去年信息
            AnalysisMonthChartInfo lastYearByMonth = new AnalysisMonthChartInfo(DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtil.offset(DateUtils.dateTime(DateUtils.YYYY_MM, monthMark), DateField.YEAR, -1)));

            //设置今年信息
            AnalysisMonthChartInfo currentYearByMonth;
            /**
             * 匹配当前月
             * 1.月累计评价量
             * 2.评价类型分布-好评
             */
            if (currentMonth.equals(monthMark)) {
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark,
                        null == monthOfEvaluateTotal ? goodRatePercent : monthOfEvaluateTotal,
                        lastYearByMonth.getCount(), lastMonthValue);
            }
            /**
             * 匹配非当前月
             * 1.月累计评价量*（100+随机数）/100
             * 2.该月评价数量*（评价类型分布-好评+随机数）/该月评价数量
             */
            else {
                BigDecimal count = null == monthOfEvaluateTotal ?
                        goodRatePercent.add(new BigDecimal(randomInt)) :
                        monthOfEvaluateTotal.multiply(new BigDecimal(100 + randomInt)).divide(new BigDecimal(100), 0, BigDecimal.ROUND_UP);
                currentYearByMonth = new AnalysisMonthChartInfo().build(monthMark, count, lastYearByMonth.getCount(), lastMonthValue);
            }

            //同步同比、环比
            lastYearByMonth.setSame(currentYearByMonth.getSame());
            lastYearByMonth.setSequential(currentYearByMonth.getSequential());

            lastMonthValue = currentYearByMonth.getCount();

            currentData.add(currentYearByMonth);
            lastData.add(lastYearByMonth);
        }

        return Arrays.asList(
                //封装今年月趋势信息
                new AnalysisBaseInfo(currentData.get(0).getTime().substring(0, 4), currentData),
                //封装去年月趋势信息
                new AnalysisBaseInfo(lastData.get(0).getTime().substring(0, 4), lastData)
        );
    }

    /**
     * 处理排行信息分页
     *
     * @date 2021/8/31 21:53
     * @author horadirm
     */
    private <T extends BaseVO> Map<Integer, List<T>> handleRankPage(List<T> rankInfo) {
        //构建排行分页信息
        Map<Integer, List<T>> rankPageInfo = Maps.newHashMap();
        if (null != rankInfo && !rankInfo.isEmpty()) {
            //排序
            rankInfo.sort(new Comparator<T>() {
                @Override
                public int compare(T o1, T o2) {
                    if (o1.getValue().compareTo(o2.getValue()) > 0) {
                        return -1;
                    }
                    return o2.getValue().compareTo(o1.getValue());
                }
            });

            //排行信息长度
            int size = rankInfo.size();

            //如果排行信息列表长度小于等于5，则直接获取
            if (Constants.NumberConstants.NUMBER_FIVE >= size) {
                rankPageInfo.put(-1, rankInfo);
                rankPageInfo.put(1, rankInfo);
                return rankPageInfo;
            }
            //如果排行信息列表长度小于等于10，则直接获取
            if (Constants.NumberConstants.NUMBER_TEN >= size) {
                rankPageInfo.put(-1, rankInfo.subList(0, 5));
                rankPageInfo.put(1, rankInfo);
                return rankPageInfo;
            }
            //获取top5
            rankPageInfo.put(-1, rankInfo.subList(0, 5));
            //获取峰值
            int limit = new BigDecimal(size).divide(BigDecimal.TEN, 0, BigDecimal.ROUND_UP).intValue();
            for (int i = 0; i < limit; i++) {
                int fromIndex = i * 10;
                int toIndex = (i + 1) * 10;
                rankPageInfo.put(i + 1, rankInfo.subList(fromIndex, toIndex >= size ? size : toIndex));
            }
        }

        return rankPageInfo;
    }

}
