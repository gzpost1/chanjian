package com.yjtech.wisdom.tourism.meeting.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 音频会议
 * </p>
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tb_audio_meeting")
public class AudioMeetingEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId("id")
    private Long id;


    /**
     * 会议编号
     */
    private String code;


    /**
     * 会议密码
     */
    private String password;


    /**
     * 会议人数
     */
    private Integer number;



}
