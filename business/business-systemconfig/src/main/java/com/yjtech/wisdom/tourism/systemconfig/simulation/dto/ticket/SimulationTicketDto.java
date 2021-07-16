package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.ticket;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.util.List;

/**
 * 票务模拟数据配置
 * @author liuhong
 * @date 2021-07-15 10:21
 */
@Data
public class SimulationTicketDto extends SimulationCommonDto {
    /**
     * 小时处理售票量
     */
    private Integer hourlySaleInit;

    /**
     * 日累计售票量加数
     */
    private Double dailySaleAddend;

    /**
     * 七日售票量系数
     */
    private Double weeklySaleCoefficient;

    /**
     * 七日售票量加数
     */
    private Double weeklySaleAddend;

    /**
     * 月累计加数
     */
    private Double monthlySaleAddend;

    /**
     * 年累计加数
     */
    private Double yearlySaleAddend;

    /**
     * 省外来源分布
     */
    private List<BaseVO> outsideProvinces;

    /**
     * 省内来源分布
     */
    private List<BaseVO> insideProvinces;

    /**
     * 票种分布
     */
    private List<BaseVO> ticketModels;

    /**
     * 渠道分布
     */
    private List<BaseVO> ticketChannels;

    /**
     * 团散分布, name为团队或散客
     */
    private List<BaseVO> teamIndividualDist;

    /**
     * 七日检票系数
     */
    private Double weeklyCheckCoefficient;

    /**
     * 七日检票加数
     */
    private Double weeklyCheckAddend;

    /**
     * 月检票加数
     */
    private Double monthlyCheckAddend;

    /**
     * 年检票加数
     */
    private Double yearlyCheckAddend;


}
