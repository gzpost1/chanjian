package com.yjtech.wisdom.tourism.meeting.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 *
 * @author wuyongchong
 * @since 2021-08-02
 */
@Getter
@Setter
public class AudioMeetingDto implements Serializable {


    /**
    * 会议编号
    */
    @NotBlank(message = "会议编号不能为空")
    @Length(max = 30,message = "会议编号长度必须小于30位")
    @Pattern(regexp = "^[A-Za-z0-9]+$" ,message = "会议编号只能传英文和数字")
    private String code;

    /**
    * 密码
    */
    @Length(max = 30,message = "密码长度必须小于30位")
    private String password;




}
