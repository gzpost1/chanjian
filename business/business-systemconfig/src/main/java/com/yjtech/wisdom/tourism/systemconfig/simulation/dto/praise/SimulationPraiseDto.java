package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.praise;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.util.List;

/**
 * @author 李波
 * @description: 口碑配置
 * @date 2021/7/1410:53
 */
@Data
public class SimulationPraiseDto extends SimulationCommonDto {
    /**
     * 日评价初始数
     */
    private Double dayInit;

    /**
     * 月评价初始数
     */
    private Double monthInit;

    /**
     * 全部评价量乘数
     */
    private Double allCoefficient;

    /**
     * 全部评价量加数
     */
    private Double allAddend;

    /**
     * 评价类型分布 0好评 1 中评 2差评
     *
     */
    private List<BaseVO> commentTypeList;

    /**
     * OTA分布（%）
     */
    private List<SimulationPraiseHotWordDto> otas;

    /**
     * 评论热词词频
     */
    private List<BaseVO> hotWords;

    /**
     * 行业评价分布
     */
    private List<BaseVO> industryList;
}
