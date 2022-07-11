package com.yjtech.wisdom.tourism.portal.controller.meeting;

import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.meeting.dto.AudioMeetingDto;
import com.yjtech.wisdom.tourism.meeting.service.AudioMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * 大屏-音频会议
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@RestController
@RequestMapping("/screen/audio-meeting")
public class AudioMeetingController {

    @Autowired
    private AudioMeetingService audioMeetingService;


    /**
     * 登录
     *
     * @param createDto
     * @return
     */
    @PostMapping("/login")
    public JsonResult<HashMap<String, Object>> login(@RequestBody @Valid AudioMeetingDto createDto) {
        return JsonResult.success(audioMeetingService.login(createDto));
    }


    /**
     * 登出
     *
     * @param createDto
     * @return
     */
    @PostMapping("/logout")
    public JsonResult logout(@RequestBody  AudioMeetingDto createDto) {
        audioMeetingService.logout(createDto);
        return JsonResult.ok();
    }
}
