package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 状态参数
 *
 * @Author horadirm
 * @Date 2021/7/21 17:00
 */
@Data
public class StatusParam implements Serializable {

    private static final long serialVersionUID = -6424799807860104369L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 状态（0-待指派 1-待处理 2-已处理）
     */
    @Range(min = 0, max = 2, message = "状态不合法")
    private Byte status;

    /**
     * 配备状态（0-启用 1-禁用）
     */
    @Range(min = 0, max = 1, message = "配备状态不合法")
    private Byte equipStatus;

}
