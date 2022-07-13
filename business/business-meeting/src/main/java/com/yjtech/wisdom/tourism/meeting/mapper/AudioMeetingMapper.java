package com.yjtech.wisdom.tourism.meeting.mapper;

import com.yjtech.wisdom.tourism.meeting.entity.AudioMeetingEntity;
import com.yjtech.wisdom.tourism.mybatis.extension.MyBaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 音频会议 Mapper 接口
 * </p>
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
public interface AudioMeetingMapper extends MyBaseMapper<AudioMeetingEntity> {

    Integer  incrementInventory(@Param("id") Long id, @Param("num") Integer num);

    Integer  minusInventory(@Param("id") Long id, @Param("num") Integer num);

}
