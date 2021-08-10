package com.yjtech.wisdom.tourism.common.bean.zc.params;

import lombok.Data;

/**
 * 中测评价参数
 *
 * @Date 2020/11/20 10:27
 * @Author horadirm
 */
@Data
public class ZcOtaEvaluateParam extends ZcBasePage {

    private static final long serialVersionUID = 5865304949241113208L;

    /**
     * 目标数据ID(景区、酒店、美食等ID),示例值(67a855fa06ac4f0fb61cdec3531d02bc)
     */
    private String targetId;

    /**
     * 目标数据名称(景区、酒店、美食等),示例值(7天酒店)
     */
    private String targetName;

    /**
     * 评论时间范围(开始时间),示例值(2021-01-01 00:00:00)
     */
    private String commentStartTime;

    /**
     * 评论时间范围(结束时间),示例值(2021-01-14 00:00:00)
     */
    private String commentEndTime;

    /**
     * 区域编码（内部系统带参）
     */
    private String areaCode;

}
