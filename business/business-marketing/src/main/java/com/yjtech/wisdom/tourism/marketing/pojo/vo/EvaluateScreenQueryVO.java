package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

/**
 * 评价 大屏 查询VO
 *
 * @Author horadirm
 * @Date 2021/8/10 16:20
 */
@Data
public class EvaluateScreenQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 3411746626180829538L;

    /**
     * 评价类型(0-差评，1-中评，2-好评)
     */
    private Byte evaluateType;

}
