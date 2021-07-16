package com.yjtech.wisdom.tourism.resource.broadcast.mapper;

import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastGroupComposeEntity;

import java.util.List;

public interface BroadcastGroupComposeMapper extends MyBaseMapper<BroadcastGroupComposeEntity> {

    List<BroadcastGroupComposeEntity> queryByGroupIds(List<Long> ids);

    void deleteByGroupIds(List<Long> ids);

    List<BroadcastGroupComposeEntity> queryByBroadcastIds(List<Long> ids);

    void batchInsertCompose(List<BroadcastGroupComposeEntity> composeList);
}