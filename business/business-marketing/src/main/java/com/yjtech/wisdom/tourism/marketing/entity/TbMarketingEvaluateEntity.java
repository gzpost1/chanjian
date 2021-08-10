package com.yjtech.wisdom.tourism.marketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.yjtech.wisdom.tourism.common.bean.zc.params.ZcOtaEvaluateParam;
import com.yjtech.wisdom.tourism.common.bean.zc.po.ZcOtaEvaluatePO;
import com.yjtech.wisdom.tourism.common.enums.DataSourceTypeEnum;
import com.yjtech.wisdom.tourism.common.utils.DateUtils;
import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 评价信息
 *
 * @Author horadirm
 * @Date 2021/8/10 11:38
 */
@Data
@TableName(value = "tb_marketing_evaluate")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbMarketingEvaluateEntity extends AreaBaseEntity {

    private static final long serialVersionUID = 8654360657351375426L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 第三方主键id
     */
    @TableField(value = "tp_id")
    private String tpId;

    /**
     * 场所id
     */
    @TableField(value = "place_id")
    private String placeId;

    /**
     * 场所名称
     */
    @TableField(value = "place_name")
    private String placeName;

    /**
     * 评价内容
     */
    @TableField(value = "evaluate_content")
    private String evaluateContent;

    /**
     * 评价时间
     */
    @TableField(value = "evaluate_time")
    private Date evaluateTime;

    /**
     * 评价类型(0-差评，1-中评，2-好评)
     */
    @TableField(value = "evaluate_type")
    private Integer evaluateType;

    /**
     * 数据来源类型(0-酒店，1-民宿，2-景点，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    @TableField(value = "data_type")
    private Integer dataType;

    /**
     * 关键词
     */
    @TableField(value = "keywords")
    private String keywords;

    /**
     * 评分
     */
    @TableField(value = "rate")
    private BigDecimal rate;

    /**
     * 资源信息
     */
    @TableField(value = "resource_info")
    private String resourceInfo;

    /**
     * 评论数据来源平台名称
     */
    @TableField(value = "source_platform")
    private String sourcePlatform;

    /**
     * 评论数据来源平台id
     */
    @TableField(value = "source_platform_id")
    private Integer sourcePlatformId;

    /**
     * 数据创建时间
     */
    @TableField(value = "data_create_time")
    private Date dataCreateTime;

    /**
     * 数据更新时间
     */
    @TableField(value = "data_update_time")
    private Date dataUpdateTime;


    /**
     * 构建评价
     *
     * @param po
     */
    public void build(ZcOtaEvaluatePO po, ZcOtaEvaluateParam params) {
        setId(IdWorker.getId());
        setCreateTime(new Date());
        setDeleted((byte) 0);
        setTpId(po.getId());
        setPlaceId(po.getTargetId());
        setPlaceName(params.getTargetName());
        setEvaluateContent(po.getCommentContent());
        setEvaluateTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, po.getCommentTime()));
        setEvaluateType(Objects.nonNull(po.getCommentType()) ? po.getCommentType() - 1 : null);
        setDataType(DataSourceTypeEnum.getValueByRedundancyValue(po.getDataType()));
        setKeywords(po.getKeywords());
        setRate(po.getRate());

        setSourcePlatform(po.getPlatformName());
        setSourcePlatformId(po.getSourcePlatformId());

        setAreaCode(params.getAreaCode());
    }

}
