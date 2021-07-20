package com.yjtech.wisdom.tourism.integration.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.common.constant.ProductTypeConstant;
import com.yjtech.wisdom.tourism.integration.mapper.FxDistApiMapper;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistAreaSaleListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistPriceTypeBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.fxdist.FxDistSaleRankListBO;
import com.yjtech.wisdom.tourism.integration.pojo.bo.smarttravel.SmartTravelScenicInfoBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.FxDistQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.SmartTravelQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 珊瑚礁Api服务
 *
 * @Author horadirm
 * @Date 2021/5/24 19:06
 */
@DS("fxDist")
@Service
public class FxDistApiService {

    @Resource
    private FxDistApiMapper fxDistApiMapper;

    @Autowired
    private SmartTravelApiService smartTravelApiService;


    /**
     * 根据商品类型查询销售额
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<FxDistPriceTypeBO> queryPriceForBusinessType(FxDistQueryVO vo) {
        List<FxDistPriceTypeBO> priceTypeVos = fxDistApiMapper.queryPriceForBusinessType(vo);
        //如果没有该类型则补齐
        Map<Byte, FxDistPriceTypeBO> map = Optional.ofNullable(priceTypeVos).orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(FxDistPriceTypeBO::getType, PriceTypeVo -> PriceTypeVo));
        ArrayList<FxDistPriceTypeBO> list = Lists.newArrayList();
        for (Map.Entry<Byte, String> entry : ProductTypeConstant.ProductTypeMap.entrySet()) {
            FxDistPriceTypeBO fxDistPriceTypeBO = map.get(entry.getKey());
            if (Objects.nonNull(fxDistPriceTypeBO)) {
                fxDistPriceTypeBO.setTypeName(entry.getValue());
                list.add(fxDistPriceTypeBO);
            } else {
                list.add(new FxDistPriceTypeBO(entry.getKey(), entry.getValue(), BigDecimal.ZERO));
            }
        }
        return list;
    }

    /**
     * 查询区域店铺数量
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Integer queryStoreCountByArea(FxDistQueryVO params) {
        return fxDistApiMapper.queryStoreCountByArea(params);
    }

    /**
     * 查询区域商品数量
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Integer queryProductCountByArea(FxDistQueryVO params) {
        return fxDistApiMapper.queryProductCountByArea(params);
    }

    /**
     * 查询区域订单总数数量
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Integer queryOrderCountByArea(FxDistQueryVO params) {
        return fxDistApiMapper.queryOrderCountByArea(params);
    }

    /**
     * 查询区域订单总额
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Integer queryOrderSumByArea(FxDistQueryVO params) {
        return fxDistApiMapper.queryOrderSumByArea(params);
    }

    /**
     * 查询区域销售额列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<FxDistAreaSaleListBO> queryAreaSaleList(FxDistQueryVO vo) {
        return fxDistApiMapper.queryAreaSaleList(vo);
    }

    /**
     * 查询区域销售量列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<FxDistAreaSaleListBO> queryAreaSaleCountList(FxDistQueryVO vo) {
        return fxDistApiMapper.queryAreaSaleCountList(vo);
    }

    /**
     * 查询商品销售列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<FxDistSaleRankListBO> queryProductSaleList(FxDistQueryVO vo) {
        return fxDistApiMapper.queryProductSaleList(vo);
    }

    /**
     * 查询景区销售列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<FxDistSaleRankListBO> queryScenicSaleList(FxDistQueryVO vo) {
        //获取景区列表
        SmartTravelQueryVO smartTravelQueryVO = new SmartTravelQueryVO();
        smartTravelQueryVO.setAreaCode(vo.getAreaCode());
        List<SmartTravelScenicInfoBO> scenicList = smartTravelApiService.queryScenicList(smartTravelQueryVO);

        //构建景区销售列表
        List<FxDistSaleRankListBO> scenicSaleList = new ArrayList<>();
        if (null != scenicList && !scenicList.isEmpty()) {
            for (SmartTravelScenicInfoBO scenicInfo : scenicList) {
                //景区未关联店铺，则设置默认
                if (null == scenicInfo.getRelateShops() || scenicInfo.getRelateShops().isEmpty()) {
                    scenicSaleList.add(new FxDistSaleRankListBO(scenicInfo.getScenicId(), scenicInfo.getScenicName(), BigDecimal.ZERO));
                    continue;
                }
                //根据店铺id列表查询商品销售额
                vo.setSubMchIdList(scenicInfo.getRelateShops());
                BigDecimal scenicSale = fxDistApiMapper.querySaleBySubMchList(vo);
                scenicSaleList.add(new FxDistSaleRankListBO(scenicInfo.getScenicId(), scenicInfo.getScenicName(), scenicSale));
            }
        }

        //根据销售额排序
        scenicSaleList.sort((o1, o2) -> {
            if (o1.getSale().compareTo(o2.getSale()) == 0) {
                return -1;
            }
            return o2.getSale().compareTo(o1.getSale());
        });

        return scenicSaleList;
    }

}
