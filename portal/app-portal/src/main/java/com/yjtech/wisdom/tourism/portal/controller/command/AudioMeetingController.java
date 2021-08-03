package com.yjtech.wisdom.tourism.portal.controller.command;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yjtech.wisdom.tourism.command.dto.meeting.AudioMeetingDto;
import com.yjtech.wisdom.tourism.command.entity.meeting.AudioMeetingEntity;
import com.yjtech.wisdom.tourism.command.service.meeting.AudioMeetingService;
import com.yjtech.wisdom.tourism.common.constant.EventContants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.AssertUtil;
import com.yjtech.wisdom.tourism.common.utils.bean.BeanMapper;
import com.yjtech.wisdom.tourism.system.service.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 音频会议
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/audio-meeting")
public class AudioMeetingController {

    @Autowired
    private AudioMeetingService audioMeetingService;

    @Autowired
    private SysConfigService configService;

    /**
     * 登录
     *
     * @param createDto
     * @return
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody @Valid AudioMeetingDto createDto) {

        /**
         *  1加入会议时判断当前平台已经有该会议编号的会议正在举行，若无，则创建并加入该会议
         */
        //时间超过8小时或会议人数小于1 视为会议结束
        LambdaQueryWrapper<AudioMeetingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AudioMeetingEntity::getCode, createDto.getCode());
        queryWrapper.and(p -> p.ge(AudioMeetingEntity::getNumber, 1)
                .or()
                .gt(AudioMeetingEntity::getCreateTime, LocalDateTime.now().plusHours(0 - Integer.valueOf(configService.selectConfigByKey(EventContants.AUDIO_MEETING_EXPIRE)))));
        List<AudioMeetingEntity> list = audioMeetingService.list(queryWrapper);
        AssertUtil.isFalse(list.size() > 1, "存在多个进行中会议，请联系管理员");
        if (list.size() < 1) {
            AudioMeetingEntity entity = BeanMapper.map(createDto, AudioMeetingEntity.class);
            audioMeetingService.save(entity);

        } else {
            AssertUtil.isFalse(!Objects.equals(list.get(0).getPassword(), createDto.getPassword()), "会议密码错误");
            audioMeetingService.getBaseMapper().minusInventory(list.get(0).getId(), 1);
        }
        return JsonResult.ok();
    }


    /**
     * 登出
     *
     * @param createDto
     * @return
     */
    @PostMapping("/logout")
    public JsonResult logout(@RequestBody  AudioMeetingDto createDto) {
        AssertUtil.isFalse(StringUtils.isBlank(createDto.getCode()), "会议编码不能为空");
        //时间超过8小时或会议人数小于1 视为会议结束
        LambdaQueryWrapper<AudioMeetingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AudioMeetingEntity::getCode, createDto.getCode());
        queryWrapper.and(p -> p.ge(AudioMeetingEntity::getNumber, 1)
                .or()
                .gt(AudioMeetingEntity::getCreateTime, LocalDateTime.now().plusHours(0 - Integer.valueOf(configService.selectConfigByKey(EventContants.AUDIO_MEETING_EXPIRE)))));
        List<AudioMeetingEntity> list = audioMeetingService.list(queryWrapper);
        AssertUtil.isFalse(list.size() > 1, "存在多个进行中会议，请联系管理员");
        /**
         *  所有人都退出后，认为会议结束 或者超过8小时会议结束
         */
        audioMeetingService.getBaseMapper().incrementInventory(list.get(0).getId(), 1);
        return JsonResult.ok();
    }
}
