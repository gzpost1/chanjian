package com.yjtech.wisdom.tourism.common.bean.index;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据趋势 DTO
 *
 * @date 2022/8/5 16:51
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataAnalysisDTO implements Serializable {
    private static final long serialVersionUID = -5822577465392932187L;

    /**
     * 名称列表 用于x轴
     */
    private List<String> nameList;

    /**
     * 值列表1 用于y轴
     * 项目数
     * 浏览数
     */
    private List<String> valueList1;

    /**
     * 值列表2 用于y轴
     * 企业数
     * 点赞数
     */
    private List<String> valueList2;

    /**
     * 值列表3 用于y轴
     * 收藏数
     */
    private List<String> valueList3;

    /**
     * 值列表4 用于y轴
     * 留言数
     */
    private List<String> valueList4;


    public DataAnalysisDTO(List<BaseVO> list1) {
        this.nameList = list1.stream().map(BaseVO::getName).collect(Collectors.toList());
        this.valueList1 = list1.stream().map(BaseVO::getValue).collect(Collectors.toList());
    }

    public DataAnalysisDTO(List<BaseVO> list1, List<BaseVO> list2) {
        this.nameList = list1.stream().map(BaseVO::getName).collect(Collectors.toList());
        this.valueList1 = list1.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList2 = list2.stream().map(BaseVO::getValue).collect(Collectors.toList());
    }

    public DataAnalysisDTO(List<BaseVO> list1, List<BaseVO> list2, List<BaseVO> list3) {
        this.nameList = list1.stream().map(BaseVO::getName).collect(Collectors.toList());
        this.valueList1 = list1.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList2 = list2.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList3 = list3.stream().map(BaseVO::getValue).collect(Collectors.toList());
    }

    public DataAnalysisDTO(List<BaseVO> list1, List<BaseVO> list2, List<BaseVO> list3, List<BaseVO> list4) {
        this.nameList = list1.stream().map(BaseVO::getName).collect(Collectors.toList());
        this.valueList1 = list1.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList2 = list2.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList3 = list3.stream().map(BaseVO::getValue).collect(Collectors.toList());
        this.valueList4 = list4.stream().map(BaseVO::getValue).collect(Collectors.toList());
    }

}
