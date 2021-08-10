package com.yjtech.wisdom.tourism.command.service.meeting;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.yjtech.wisdom.tourism.command.dto.meeting.AudioMeetingDto;
import com.yjtech.wisdom.tourism.command.entity.meeting.AudioMeetingEntity;
import com.yjtech.wisdom.tourism.command.mapper.meeting.AudioMeetingMapper;
import com.yjtech.wisdom.tourism.command.service.meeting.media.RtcTokenBuilder;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 音频会议 服务类
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@Service
public class AudioMeetingService extends ServiceImpl<AudioMeetingMapper, AudioMeetingEntity> {

    @Autowired
    private SysConfigService configService;

    /**
     * 登录
     *
     * @param createDto
     * @return
     */
    public HashMap<String, Object> login( AudioMeetingDto createDto) {

        /**
         *  1加入会议时判断当前平台已经有该会议编号的会议正在举行，若无，则创建并加入该会议
         */
        //时间超过8小时或会议人数小于1 视为会议结束
        LambdaQueryWrapper<AudioMeetingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AudioMeetingEntity::getCode, createDto.getCode());
        queryWrapper.and(p -> p.ge(AudioMeetingEntity::getNumber, 1)
                .or()
                .gt(AudioMeetingEntity::getCreateTime, LocalDateTime.now().plusSeconds(0 - Integer.valueOf(configService.selectConfigByKey(EventContants.AUDIO_MEETING_EXPIRE)))));
        List<AudioMeetingEntity> list = this.list(queryWrapper);
        AssertUtil.isFalse(list.size() > 1, "存在多个进行中会议，请联系管理员");
        if (list.size() < 1) {
            AudioMeetingEntity entity = BeanMapper.map(createDto, AudioMeetingEntity.class);
            this.save(entity);

        } else {
            // 如果创始人 设密码  后面都需要  如果没设  后面的 填入密码也不行
            AudioMeetingEntity audioMeetingEntity = list.get(0);
            if(StringUtils.isBlank(audioMeetingEntity.getPassword())){
                AssertUtil.isFalse(StringUtils.isNotBlank(createDto.getPassword()),"会议密码错误");
            }else{
                AssertUtil.isFalse(!Objects.equals(audioMeetingEntity.getPassword(), createDto.getPassword()), "会议密码错误");
            }
            this.getBaseMapper().minusInventory(audioMeetingEntity.getId(), 1);
        }
        Integer uid = Integer.valueOf(configService.selectConfigByKey(EventContants.AGORA_UID));
        String token = this.getToken(configService.selectConfigByKey(EventContants.AGORA_APPID),
                configService.selectConfigByKey(EventContants.agora_appcertificate),
                createDto.getCode(),
                uid,
                Integer.valueOf(configService.selectConfigByKey(EventContants.AUDIO_MEETING_EXPIRE))
        );
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("uid",uid);
        result.put("token",token);
        return result;
    }


    /**
     * 登出
     */
    public void logout( AudioMeetingDto createDto) {
        AssertUtil.isFalse(StringUtils.isBlank(createDto.getCode()), "会议编码不能为空");
        //时间超过8小时或会议人数小于1 视为会议结束
        LambdaQueryWrapper<AudioMeetingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AudioMeetingEntity::getCode, createDto.getCode());
        queryWrapper.and(p -> p.ge(AudioMeetingEntity::getNumber, 1)
                .or()
                .gt(AudioMeetingEntity::getCreateTime, LocalDateTime.now().plusHours(0 - Integer.valueOf(configService.selectConfigByKey(EventContants.AUDIO_MEETING_EXPIRE)))));
        List<AudioMeetingEntity> list = this.list(queryWrapper);
        AssertUtil.isFalse(list.size() > 1, "存在多个进行中会议，请联系管理员");
        /**
         *  所有人都退出后，认为会议结束 或者超过8小时会议结束
         */
        this.getBaseMapper().incrementInventory(list.get(0).getId(), 1);
    }

    private String getToken(String appId,String appCertificate,String channelName,int uid,int expirationTimeInSeconds) {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);

        String result = token.buildTokenWithUid(appId, appCertificate,
                channelName, uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);
       return result;
    }
}
