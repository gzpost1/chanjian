package com.yjtech.wisdom.tourism.command.dto.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author wuyongchong
 * @since 2021-07-21
 */
@Getter
@Setter
public class EventAppointCreateDto implements Serializable {
    /**
    * 指派人员
    */
    @Size(max = 10,message = "指派人员最大为10")
    @NotEmpty(message = "指派人员不能为空")
    private List<String> appointPersonnel;

    /**
    * 消息通知
    */
    private List<String> notice;



}
