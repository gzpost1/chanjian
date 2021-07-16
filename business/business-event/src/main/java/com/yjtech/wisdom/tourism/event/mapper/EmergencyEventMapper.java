package com.yjtech.wisdom.tourism.event.mapper;

import com.yjtech.wisdom.tourism.event.entity.EmergencyEventEntity;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 应急事件 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-02-22
 */
public interface EmergencyEventMapper extends MyBaseMapper<EmergencyEventEntity> {

    @MapKey("userId")
    Map<Long, SysUser> queryUserById(@Param("userIds") Set<Long> userIds);
}
