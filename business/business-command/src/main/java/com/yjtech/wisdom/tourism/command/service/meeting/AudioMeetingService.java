package com.yjtech.wisdom.tourism.command.service.meeting;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjtech.wisdom.tourism.command.entity.meeting.AudioMeetingEntity;
import com.yjtech.wisdom.tourism.command.mapper.meeting.AudioMeetingMapper;
import com.yjtech.wisdom.tourism.command.service.meeting.media.RtcTokenBuilder;
import org.springframework.stereotype.Service;

/**
 * 音频会议 服务类
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@Service
public class AudioMeetingService extends ServiceImpl<AudioMeetingMapper, AudioMeetingEntity> {

    public String getToken(String appId,String appCertificate,String channelName,int uid,int expirationTimeInSeconds) {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);

        String result = token.buildTokenWithUid(appId, appCertificate,
                channelName, uid, RtcTokenBuilder.Role.Role_Publisher, timestamp);
       return result;
    }
}
