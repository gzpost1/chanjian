package com.yjtech.wisdom.tourism.resource.scenic.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.common.utils.StringUtils;
import com.yjtech.wisdom.tourism.resource.scenic.entity.ScenicEntity;
import com.yjtech.wisdom.tourism.resource.scenic.mapper.ScenicMapper;
import com.yjtech.wisdom.tourism.resource.scenic.query.ScenicPageQuery;
import org.springframework.stereotype.Service;

import static com.yjtech.wisdom.tourism.common.utils.StringUtils.isNull;

@Service
public class ScenicService extends ServiceImpl<ScenicMapper, ScenicEntity> {

    public IPage<ScenicEntity> queryForPage(ScenicPageQuery query) {
        LambdaQueryWrapper<ScenicEntity> wrapper = new LambdaQueryWrapper<ScenicEntity>()
                .like(StringUtils.isNotBlank(query.getName()), ScenicEntity::getName, query.getName())
                .eq(!isNull(query.getStatus()), ScenicEntity::getStatus, query.getStatus());
        return page(new Page<>(query.getPageNo(), query.getPageSize()), wrapper);
    }
}
