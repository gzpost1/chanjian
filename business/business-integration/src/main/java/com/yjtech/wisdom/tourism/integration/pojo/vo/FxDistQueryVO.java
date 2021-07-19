package com.yjtech.wisdom.tourism.integration.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import lombok.Data;

import java.util.List;


/**
 * 珊瑚礁 查询VO
 *
 * @Date 2021/5/24 19:22
 * @Author horadirm
 */
@Data
public class FxDistQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = -1281331975471900036L;

    /**
     * 店铺id
     */
    private Long subMchId;

    /**
     * 店铺id列表
     */
    @JsonIgnore
    private List<String> subMchIdList;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品类型id（1-票类,3-券类,4-酒店,5-实物类）
     */
    private Long productCategoryId;

}
