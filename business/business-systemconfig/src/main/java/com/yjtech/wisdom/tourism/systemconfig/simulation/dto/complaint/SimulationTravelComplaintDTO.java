package com.yjtech.wisdom.tourism.systemconfig.simulation.dto.complaint;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.systemconfig.simulation.dto.SimulationCommonDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 旅游投诉 模拟数据结构
 *
 * @date 2021/8/21 10:08
 * @author horadirm
 */
@Data
public class SimulationTravelComplaintDTO extends SimulationCommonDto {

    private static final long serialVersionUID = 1695151218058313728L;

    /**
     * 日投诉量
     */
    private BigDecimal dayOfComplaint;

    /**
     * 月累计投诉量
     */
    private BigDecimal monthOfComplaintTotal;

    /**
     * 投诉类型分布
     */
    private List<BasePercentVO> complaintTypeDistribution;

    /**
     * 处理状态分布
     */
    private List<BasePercentVO> complaintStatusDistribution;

    /**
     * 景区投诉Top5
     */
    private List<BaseVO> scenicComplaintRank;

    /**
     * 酒店投诉Top5
     */
    private List<BaseVO> hotelComplaintRank;


}
