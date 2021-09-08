package com.yjtech.wisdom.tourism.resource.venue.service;

import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.resource.venue.entity.VenueEntity;
import com.yjtech.wisdom.tourism.resource.venue.mapper.VenueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-01-20 11:03
 */
@Service
public class VenueMapAmapService {

    @Autowired
    private VenueMapper venueMapper;

    /**
     *  为了保证事务有效  另起一个类  先删除  后插入各个市的数据
     * @param resultList
     * @param cityId
     */
    public void deleteAndInsert(List<VenueEntity> resultList, String cityId) {
        AssertUtil.notEmpty(resultList,cityId + "数据为空");
        //高德返回各个市的数据 有其他市数据的可能重复
        for (VenueEntity entity : resultList) {
            if (cityId.equals(entity.getAreaCode())) {
                try {
                    venueMapper.insert(entity);
                } catch (Exception e) {
                    // 如果主键重复异常，则继续下一条
                    continue;
                }
            }
        }
    }


}
