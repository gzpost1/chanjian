package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.wifi;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.util.List;

/**
 * @author 李波
 * @description: wifi模拟数据配置
 * @date 2021/7/1610:27
 */
@Data
public class SimulationWifiDto extends SimulationCommonDto {
    /**
     * 小时初始连接数
     */
    private Double hourInit;

    /**
     * 连接比例
     */
    private Double connectionRate;

    /**
     * 日累计连接数
     */
    private Double oneDayGrandAddend;

    /**
     * 7日累计连接数
     */
    private Double sevenDayGrandAddend;

    /**
     * 月累计连接数
     */
    private Double monthGrandAddend;

    /**
     * 年累计连接数
     */
    private Double yearGrandAddend;

    /**
     * 今日连接峰值
     */
    private Double oneDayMaxAddend;

    /**
     * 历史注册用户系数
     */
    private Double historyRegistCoefficient;

    /**
     * 历史注册用户加数
     */
    private Double historyRegistAddend;

    /**
     * 认证方式分布
     */
    private List<BaseVO> verifyTypes;

    /**
     * 热门点位
     */
    private List<BaseVO> hotDrives;

    /**
     * 手机厂商分布
     */
    private List<BaseVO> mpbileClasses;
}
