package com.yjtech.wisdom.tourism.command.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yjtech.wisdom.tourism.command.entity.EmergencyPlanEntity;
import com.yjtech.wisdom.tourism.command.entity.EmergencyPlanTypeEntity;
import com.yjtech.wisdom.tourism.command.mapper.EmergencyPlanMapper;
import com.yjtech.wisdom.tourism.command.query.EmergencyPlanQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 应急预案 服务类
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
@Service
public class EmergencyPlanService extends ServiceImpl<EmergencyPlanMapper, EmergencyPlanEntity>{

    @Autowired
    private EmergencyPlanTypeService emergencyPlanTypeService;

    public  LambdaQueryWrapper<EmergencyPlanEntity> getQueryWrapper(EmergencyPlanQuery query){
        LambdaQueryWrapper<EmergencyPlanEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(EmergencyPlanEntity::getCreateTime);
        queryWrapper.like(StringUtils.isNotBlank(query.getName()),EmergencyPlanEntity::getName,query.getName());
        queryWrapper.eq(Objects.nonNull(query.getType()),EmergencyPlanEntity::getType,query.getType());
        return queryWrapper;
    }

    public void tranDic(List<EmergencyPlanEntity> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        List<EmergencyPlanTypeEntity> planTypeList = emergencyPlanTypeService.list();
        Map<Long, String> map = Optional.ofNullable(planTypeList).orElse(Lists.newArrayList())
                .stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        for(EmergencyPlanEntity entity : list){
            entity.setTypeName(map.get(entity.getType()));
        }
    }
}
