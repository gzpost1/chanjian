package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 决策预警项
 *
 * @author renguangqian
 * @date 2021/7/28 21:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DecisionWarnItemDto implements Serializable {

    private static final long serialVersionUID = -3669818455792582056L;

    /**
     * id
     */
    private Long id;

    /**
     * 指标id
     */
    private Long targetId;

    /**
     * 指标名称
     */
    private String targetName;

    /**
     * 预警项名称
     */
    private String warnName;

    /**
     * 预警项值
     */
    private String warnNum;

    /**
     * 月环比
     */
    private String monthHbScale;

    /**
     * 风险类型（等级） 0：低风险 1：中风险 2：高风险
     */
    private Integer riskType;

    /**
     * 话术
     */
    private String conclusionText;

    /**
     * 图表数据
     */
    private List chartData;
}
