package com.yjtech.wisdom.tourism.command.dto.travelcomplaint;

import com.yjtech.wisdom.tourism.common.bean.AnalysisBaseInfo;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 旅游投诉 模拟数据
 *
 * @date 2021/9/1 18:18
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelComplaintSimulationDTO implements Serializable {

    private static final long serialVersionUID = -8793214226459446037L;

    /**
     * 查询综合总览-旅游投诉总量
     */
    private Integer travelComplaintTotalToIndex;

    /**
     * 查询旅游投诉总量
     */
    private Integer travelComplaintTotal;

    /**
     * 查询旅游投诉类型分布
     */
    private List<BasePercentVO> complaintTypeDistribution;

    /**
     * 查询旅游投诉状态分布
     */
    private List<BasePercentVO> complaintStatusDistribution;

    /**
     * 查询旅游投诉类型Top排行
     */
    private List<BaseVO> complaintTopByType;

    /**
     * 查询旅游投诉趋势、同比、环比
     */
    private List<AnalysisBaseInfo> complaintAnalysis;

    /**
     * 查询统计
     */
    private Integer queryTotal;

}
