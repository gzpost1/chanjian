package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 评论排名 VO
 *
 * @Date 2020/11/24 19:00
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateRankingVO extends AreaBaseVO {

    private static final long serialVersionUID = 5923418235400547832L;

    /**
     * 数据来源场所类型(0-酒店，1-民宿，2-景点，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    private List<Integer> dataTypeList;

    /**
     * 开始时间（yyyy-MM-dd HH:mm:ss）
     */
    private String beginTime;

    /**
     * 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;

    /**
     * 构建大屏查询参数
     * @param vo
     */
    public void buildScreenParams(ScreenAnalysisQueryVO vo, List<Integer> dataTypeList){
        setAreaCode(vo.getAreaCode());
        setBeginTime(vo.getBeginTime());
        setEndTime(vo.getEndTime());
        setDataTypeList(dataTypeList);
    }

}
