package com.yjtech.wisdom.tourism.resource.alarm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import com.yjtech.wisdom.tourism.resource.alarm.dto.AlarmPostCreateDto;
import com.yjtech.wisdom.tourism.resource.alarm.entity.AlarmPostEntity;
import com.yjtech.wisdom.tourism.resource.alarm.mapper.AlarmPostMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报警柱 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-06
 */
@Service
public class AlarmPostService extends ServiceImpl<AlarmPostMapper, AlarmPostEntity> {


    public JsonResult create( AlarmPostCreateDto createDto) {
        int count = this.count(new LambdaQueryWrapper<AlarmPostEntity>().eq(AlarmPostEntity::getCode, createDto.getCode()));
        AssertUtil.isFalse(count >=1 ,"设备编号已存在");
        AlarmPostEntity entity = BeanMapper.map(createDto, AlarmPostEntity.class);
        if(null==createDto.getStatus()){
            entity.setStatus(EntityConstants.ENABLED);
        }
        this.save(entity);
        return JsonResult.ok();
    }

}
