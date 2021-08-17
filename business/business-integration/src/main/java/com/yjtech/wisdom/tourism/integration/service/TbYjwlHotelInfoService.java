package com.yjtech.wisdom.tourism.integration.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yjtech.wisdom.tourism.common.utils.AreaUtils;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.integration.entity.TbYjwlHotelInfoEntity;
import com.yjtech.wisdom.tourism.integration.mapper.TbYjwlHotelInfoMapper;
import com.yjtech.wisdom.tourism.integration.pojo.bo.hotel.HotelSaleRankListBO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.HotelQueryVO;
import com.yjtech.wisdom.tourism.integration.pojo.vo.HotelStaticsVo;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 酒店信息 服务实现类
 *
 * @author Mujun
 * @since 2021-05-24
 */
@Service
@DS("hotelCenter")
public class TbYjwlHotelInfoService extends BaseMybatisServiceImpl<TbYjwlHotelInfoMapper, TbYjwlHotelInfoEntity> {

    /**
     * 查询自营酒店销售列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<HotelSaleRankListBO> querySelfHotelSaleList(HotelQueryVO vo) {
        return super.baseMapper.querySelfHotelSaleList(vo);
    }

    /**
     * 查询美团、携程酒店销售列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<HotelSaleRankListBO> queryYjwlHotelSaleList(HotelQueryVO vo) {
        return super.baseMapper.queryYjwlHotelSaleList(vo);
    }

    /**
     * 查询酒店销售列表
     *
     * @param vo
     * @return
     */
    @Transactional(readOnly = true)
    public List<HotelSaleRankListBO> queryHotelSaleList(HotelQueryVO vo) {
        return super.baseMapper.queryHotelSaleList(vo);
    }

    /**
     * 根据酒店类型统计数量
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<HotelStaticsVo> hotelStatiscs(HotelQueryVO params) {
        return baseMapper.hotelStatiscs(params);
    }

    /**
     * 酒店类型数量统计
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public Integer hotelTotal(HotelQueryVO params) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.likeRight(TbYjwlHotelInfoEntity.AREA_CODE, AreaUtils.trimCode(params.getAreaCode()));
        queryWrapper.eq(TbYjwlHotelInfoEntity.DELETED, EntityConstants.NOT_DELETED);
        queryWrapper.eq(TbYjwlHotelInfoEntity.STATUS, EntityConstants.ENABLED);
        return baseMapper.selectCount(queryWrapper);
    }


}
