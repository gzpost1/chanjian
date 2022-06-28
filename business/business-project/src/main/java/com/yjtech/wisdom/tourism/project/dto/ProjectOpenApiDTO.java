package com.yjtech.wisdom.tourism.project.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 项目管理 开放接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOpenApiDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 建设要求
     */
    private String requirement;

    /**
     * 合作方式
     */
    private String method;

    /**
     * 业主单位名称
     */
    private String company;

    /**
     * 项目招商内容
     */
    private String investmentContent;

    /**
     * 项目特色标签
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private  List<String> labels;

    /**
     * 视频
     */
    private String video;

    /**
     * 项目所属地区
     */
    private String area;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 财务指标分析
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private  Target target;

    /**
     * 财务指标分析
     */
    @Data
    public static class Target {
        /**
         * 总投资额
         */
        private String investmentTotal;

        /**
         * 引资金额
         */
        private String fundingAmount;

        /**
         * 自有资金
         */
        private String privateCapital;

        /**
         * 投资回收期
         */
        private String paybackPeriod;
    }
}
