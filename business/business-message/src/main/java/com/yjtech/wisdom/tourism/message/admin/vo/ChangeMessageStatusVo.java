package com.yjtech.wisdom.tourism.message.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 改变消息状态
 *
 * @author renguangqian
 * @date 2021/7/26 20:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ChangeMessageStatusVo implements Serializable {

    private static final long serialVersionUID = -4940730569767728331L;

    /**
     * 事件ID
     */
    @NotNull
    private Long eventId;

    /**
     * 事件状态 0:待指派 1:待处理 2:已处理
     */
    @NotNull
    @Min(value = 0)
    @Min(value = 2)
    private Integer eventStatus;
}
