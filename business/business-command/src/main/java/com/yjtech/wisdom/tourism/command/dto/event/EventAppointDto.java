package com.yjtech.wisdom.tourism.command.dto.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
*
* @author wuyongchong
* @since 2021-02-22
*/
@Getter
@Setter
public class EventAppointDto implements Serializable {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 指定处理人员
     */
    @Size(max = 10,message = "指定处理人员最大为10")
    @NotEmpty(message = "指定处理人员不能为空")
    private List<String> appointHandlePersonnel;

    /**
     * 消息通知
     */
    private List<String> notice;

    /**
     * 预案id
     */
    private Long planId;
}
