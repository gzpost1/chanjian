package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

import java.util.Objects;

/**
 * 评价 大屏 查询VO
 *
 * @Author horadirm
 * @Date 2021/8/10 16:20
 */
@Data
public class EvaluateQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 3411746626180829538L;

    /**
     * 场所名称
     */
    private String placeName;

    /**
     * 场所id（酒店、景区）
     */
    private String placeId;

    /**
     * 评价类型(0-好评，1-中评，2-差评)
     */
    private Byte evaluateType;

    /**
     * 评价主体类型(0-民宿，1-景点，2-酒店，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    private Byte dataType;

    /**
     * 配备状态（0-禁用 1-启用）
     */
    private Byte equipStatus;

    /**
     * 景区/酒店状态（0-禁用 1-启用）
     */
    private Byte status;


    /**
     * 构建状态
     */
    public void buildStatus(){
        this.status = Objects.isNull(getStatus()) ? EntityConstants.ENABLED : getStatus();
        this.equipStatus = Objects.isNull(getEquipStatus()) ? EntityConstants.ENABLED : getEquipStatus();
    }


}
