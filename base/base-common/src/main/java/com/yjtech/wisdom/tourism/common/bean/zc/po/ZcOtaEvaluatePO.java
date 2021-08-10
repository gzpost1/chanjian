package com.yjtech.wisdom.tourism.common.bean.zc.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 中测OTA评价 PO
 *
 * @Date 2020/11/20 9:56
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZcOtaEvaluatePO implements Serializable {

    private static final long serialVersionUID = -8235823497151019101L;

    /**
     * 第三方主键id
     */
    private String id;

    /**
     * 点评内容
     */
    private String commentContent;

    /**
     * 评价时间
     */
    private String commentTime;

    /**
     * 评价类型(1-差评，2-中评，3-好评)
     */
    private Integer commentType;

    /**
     * 数据来源类型(1-景区 2-酒店)
     */
    private Integer dataType;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 评分
     */
    private BigDecimal rate;

    /**
     * 目标数据ID(为type中的某种类的数据的ID)
     */
    private String targetId;

    /**
     * 目标数据名称(景区、酒店、美食等)
     */
    private String targetName;

    /**
     * 点评图集视频信息(此数据不一定有,不同的平台数据结构不一定会完全一致)
     */
    private List<EvaluateResources> resources;

    /**
     * 第三方创建时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String createAt;

    /**
     * 第三方更新时间(时间格式为：yyyy-MM-dd HH:mm:ss)
     */
    private String updateAt;

    /**
     * 评论数据来源平台名称
     */
    private String platformName;

    /**
     * 评论数据来源平台id
     */
    private Integer sourcePlatformId;

    /**
     * 点评图集视频信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class EvaluateResources{
        /**
         * 点评图集视频信息主键id
         */
        private String id;

        /**
         * 点评ID
         */
        private String commentId;

        /**
         * 资源地址
         */
        private String resourceUrl;

        /**
         * 资源类型(0-图片，1-视频)
         */
        private String type;

        /**
         * 点评图集视频信息创建时间(时间格式为：yyyy-MM-dd HH:mm:ss)
         */
        private String createAt;

        /**
         * 点评图集视频信息更新时间(时间格式为：yyyy-MM-dd HH:mm:ss)
         */
        private String updateAt;
    }

}
