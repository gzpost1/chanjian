package com.yjtech.wisdom.tourism.command.mapper.event;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yjtech.wisdom.tourism.command.entity.event.EventEntity;
import com.yjtech.wisdom.tourism.command.query.event.EventSumaryQuery;
import com.yjtech.wisdom.tourism.command.vo.event.AppEventDetail;
import com.yjtech.wisdom.tourism.command.vo.event.EventListVO;
import com.yjtech.wisdom.tourism.command.vo.event.EventTrendVO;
import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import com.yjtech.wisdom.tourism.infrastructure.core.domain.entity.SysUser;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 应急事件 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-07-19
 */
public interface EventMapper extends MyBaseMapper<EventEntity> {

    @MapKey("userId")
    Map<Long, SysUser> queryUserById(@Param("userIds") Set<Long> userIds);

    IPage<EventListVO> queryForList(IPage<EventListVO> page, @Param(Constants.WRAPPER) Wrapper<EventEntity> Wrapper);

    List<EventListVO> queryForList(@Param(Constants.WRAPPER) Wrapper<EventEntity> Wrapper);

    AppEventDetail queryForDetail(@Param("id") Long id);

    Integer queryQuantity(@Param("params") EventSumaryQuery query);


    List<EventTrendVO> queryTrend(@Param("params") EventSumaryQuery query);

    Integer queryQuantityByStatus(@Param("params") EventSumaryQuery query);

    List<BasePercentVO> queryEventType(@Param("params") EventSumaryQuery query);

    List<BaseVO> queryEventLevel(@Param("params") EventSumaryQuery query);
}
