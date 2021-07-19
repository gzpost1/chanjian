package com.yjtech.wisdom.tourism.integration.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.*;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 珊瑚礁Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:06
 */
@DS("fxDist")
public interface FxDistApiMapper {

    /**
     * 根据商品类型查询销售额
     * @param params
     * @return
     */
    List<FxDistPriceTypeBO> queryPriceForBusinessType(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域店铺数量
     * @param params
     * @return
     */
    Integer queryStoreCountByArea(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域商品数量
     * @param params
     * @return
     */
    Integer queryProductCountByArea(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域订单总数数量
     * @param params
     * @return
     */
    Integer queryOrderCountByArea(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域订单总额
     * @param params
     * @return
     */
    Integer queryOrderSumByArea(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域销售额列表
     * @param params
     * @return
     */
    List<FxDistAreaSaleListBO> queryAreaSaleList(@Param("params") FxDistQueryVO params);

    /**
     * 查询区域销售量列表
     * @param params
     * @return
     */
    List<FxDistAreaSaleListBO> queryAreaSaleCountList(@Param("params") FxDistQueryVO params);

    /**
     * 查询店铺销售额列表
     * @param params
     * @return
     */
    List<FxDistSubMchSaleListInfo> querySubMchSaleList(@Param("params") FxDistQueryVO params);

    /**
     * 查询销售额趋势列表
     * @param params
     * @return
     */
    List<FxDistSaleAnalysisBO> querySaleAnalysisList(@Param("params") FxDistQueryVO params);

    /**
     * 查询商品销售列表
     * @param params
     * @return
     */
    List<FxDistSaleRankListBO> queryProductSaleList(@Param("params") FxDistQueryVO params);

    /**
     * 根据店铺id列表查询商品销售额
     * @param params
     * @return
     */
    BigDecimal querySaleBySubMchList(@Param("params") FxDistQueryVO params);

}
