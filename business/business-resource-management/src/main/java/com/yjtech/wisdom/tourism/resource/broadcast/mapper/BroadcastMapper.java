package com.yjtech.wisdom.tourism.resource.broadcast.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import com.yjtech.wisdom.tourism.resource.broadcast.entity.BroadcastEntity;
import com.yjtech.wisdom.tourism.resource.broadcast.query.BroadcastSoundQuery;
import org.apache.ibatis.annotations.Param;


public interface BroadcastMapper extends MyBaseMapper<BroadcastEntity> {

    IPage<BroadcastEntity> queryBroadcastSounds(IPage page, @Param("params") BroadcastSoundQuery query);
}