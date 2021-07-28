package com.yjtech.wisdom.tourism.decisionsupport.base.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 预警配置
 *
 * @author renguangqian
 * @date 2021/7/27 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WarnConfigVo implements Serializable {

    private static final long serialVersionUID = -564196029471361177L;

    /**
     * 指标id
     */
    @NotNull
    private Long targetId;
}
