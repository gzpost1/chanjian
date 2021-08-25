package com.yjtech.wisdom.tourism.hotel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.hotel.dto.HotelScreenDetailDTO;
import com.yjtech.wisdom.tourism.hotel.dto.HotelSelectInfoDTO;
import com.yjtech.wisdom.tourism.hotel.entity.TbHotelInfoEntity;
import com.yjtech.wisdom.tourism.hotel.mapper.TbHotelInfoMapper;
import com.yjtech.wisdom.tourism.hotel.vo.HotelScreenQueryVO;
import com.yjtech.wisdom.tourism.hotel.vo.StaticNumVo;
import com.yjtech.wisdom.tourism.mybatis.base.BaseMybatisServiceImpl;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mujun
 * @since 2020-08-05
 */
@Service
public class TbHotelInfoService extends BaseMybatisServiceImpl<TbHotelInfoMapper, TbHotelInfoEntity> {


    @Transactional(readOnly = true)
    public Page<TbHotelInfoEntity> apiPage(com.yjtech.wisdom.tourism.hotel.dto.TbHotelInfoEntityParam param) {
        Page<TbHotelInfoEntity> page = new Page<TbHotelInfoEntity>(param.getPageNo(), param.getPageSize());
        if (param.getAscs() != null) {
            page.setAscs(Arrays.asList(param.getAscs()));
        }
        if (param.getDescs() != null) {
            page.setDescs(Arrays.asList(param.getDescs()));
        }
        TbHotelInfoEntity entity = BeanMapper.copyBean(param, TbHotelInfoEntity.class);
        page.setRecords(baseMapper.apiList(page, entity));
        return page;
    }

    @Transactional(readOnly = true)
    public List<TbHotelInfoEntity> apiList(TbHotelInfoEntity params) {
        return baseMapper.apiList(null, params);
    }

    public List<StaticNumVo> staticNum(String areaCode) {
        return baseMapper.staticNum(areaCode);
    }

    /**
     * 查询大屏分页
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<HotelScreenDetailDTO> queryScreenPage(HotelScreenQueryVO params) {
        Page<HotelScreenDetailDTO> page = new Page<>(params.getPageNo(), params.getPageSize());
        return baseMapper.queryScreenPage(page, params);
    }

    /**
     * 查询酒店类型分布
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<BasePercentVO> queryHotelTypeDistribution(HotelScreenQueryVO params) {
        return baseMapper.queryHotelTypeDistribution(params);
    }

    /**
     * 查询酒店星级分布
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<BasePercentVO> queryHotelStarDistribution(HotelScreenQueryVO params) {
        return baseMapper.queryHotelStarDistribution(params);
    }

    /**
     * 根据id查询名称
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public String queryNameById(Long id){
        if(null == id){
            return null;
        }
        return baseMapper.queryNameById(id);
    }

    /**
     * 查询酒店下拉选信息
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public IPage<HotelSelectInfoDTO> queryHotelSelectInfo(PageQuery params) {
        Page<HotelSelectInfoDTO> page = new Page<>(params.getPageNo(), params.getPageSize());
        return baseMapper.queryHotelSelectInfo(page);
    }

}
