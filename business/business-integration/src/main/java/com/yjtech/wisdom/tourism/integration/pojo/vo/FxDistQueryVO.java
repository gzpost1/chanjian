package com.yjtech.wisdom.tourism.integration.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
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

    /**
     * 订单状态列表
     */
    private List<Byte> orderStatusList;

    /**
     * 启/停用状态（0：启用 1：禁用）
     */
    private Byte validStatus;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = 0;

}
