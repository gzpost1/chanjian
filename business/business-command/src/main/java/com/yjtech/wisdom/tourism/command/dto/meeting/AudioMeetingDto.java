package com.yjtech.wisdom.tourism.command.dto.meeting;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
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
    private String code;

    /**
    * 会议名称
    */
    @NotBlank(message = "会议名称不能为空")
    @Length(max = 30,message = "会议名称长度必须小于30位")
    private String password;




}
