package com.yjtech.wisdom.tourism.decisionsupport.business.instance;


import com.yjtech.wisdom.tourism.common.exception.CustomException;
import com.yjtech.wisdom.tourism.common.utils.SpringBeanUtils;
import com.yjtech.wisdom.tourism.decisionsupport.business.strategyimpl.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 策略枚举-单例实现
 *
 * @author renguangqian
 * @date 2021/8/5 10:50
 */
@Slf4j
public enum DecisionStrategyEnum {

    /**
     * 省外游客
     */
    PROVINCE_OUTSIDE_TOUR(ProvinceOutsideTourStrategyImpl.class),

    /**
     * 省内游客
     */
    PROVINCE_INSIDE_TOUR(ProvinceInsideTourStrategyImpl.class),

    /**
     * 整体游客
     */
    PROVINCE_ALL_TOUR(ProvinceAllTourStrategyImpl.class),



    /**
     * 整体景区客流
     */
    OVERALL_SCENIC_SPOTS_TOURIST_FLOW(OverallScenicSpotsTouristFlowStrategyImpl.class),

    /**
     * 景区客流派排行
     */
    OVERALL_SCENIC_SPOTS_TOURIST_FLOW_RANKING(OverallScenicSpotsTouristFlowRankingStrategyImpl.class),

    /**
     * 景区客流派排行
     */
    OVERALL_SCENIC_SPOTS_SATISFACTION(OverallScenicSpotsSatisfactionStrategyImpl.class),

    /**
     * 景区满意度排行
     */
    OVERALL_SCENIC_SPOTS_SATISFACTION_RANKING(OverallScenicSpotsSatisfactionRankingStrategyImpl.class),



    /**
     * 整体酒店民宿满意度
     */
    OVERALL_HOTEL_HOMESTAY_SATISFACTION(OverallHotelHomestaySatisfactionStrategyImpl.class),

    /**
     * 整体酒店民宿满意度排行
     */
    OVERALL_HOTEL_HOMESTAY_SATISFACTION_RANKING(OverallHotelHomestaySatisfactionRankingStrategyImpl.class),



    /**
     * 一码游投诉量
     */
    ONE_TRAVEL_COMPLAINTS_NUMBER(OneTravelComplaintsNumberStrategyImpl.class),

    /**
     * 一码游订单量
     */
    ONE_TRAVEL_ORDER_NUMBER(OneTravelOrderNumberStrategyImpl.class),

    /**
     * 一码游交易额
     */
    ONE_TRAVEL_TRANSACTIONS_NUMBER(OneTravelTransactionsNumberStrategyImpl.class),

    /**
     * 旅游投诉
     */
    TOURIST_COMPLAINTS(TouristComplaintsStrategyImpl.class),




    /**
     * 应急事件
     */
    EMERGENCY_EVENT(EmergencyEventStrategyImpl.class),

    /**
     * 高发应急事件
     */
    HIGH_INCIDENCE_EMERGENCY_EVENT(HighIncidenceEmergencyEventStrategyImpl.class),


    /**
     * 综合概况
     */
    COMPREHENSIVE(ComprehensiveStrategyImpl.class),

    ;

    private Class<?> clazz;

    DecisionStrategyEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取Spring bean实例
     *
     * @return
     */
    public Object getInstance(){
        Object bean;
        try {
            bean = SpringBeanUtils.getBean(this.clazz);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomException(this.clazz.getName() + "不存在实例，需要使用注解@Component交给Spring IOC容器管理");
        }
        return bean;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }}
