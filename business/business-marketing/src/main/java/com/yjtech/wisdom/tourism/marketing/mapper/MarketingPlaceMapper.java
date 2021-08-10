package com.yjtech.wisdom.tourism.marketing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yjtech.wisdom.tourism.marketing.entity.TbMarketingPlaceEntity;
import com.yjtech.wisdom.tourism.marketing.pojo.dto.PlaceInfoDTO;
import com.yjtech.wisdom.tourism.marketing.pojo.vo.PlaceQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 营销推广 场所信息
 *
 * @Author horadirm
 * @Date 2020/11/20 16:10
 */
public interface MarketingPlaceMapper extends BaseMapper<TbMarketingPlaceEntity> {

    /**
     * 物理删除所有信息
     */
    void deleteAll();

    /**
     * 获取第三方场所信息列表
     * @param params
     * @return
     */
    List<PlaceInfoDTO> getPlaceInfoList(@Param("params") PlaceQueryVO params);

}
