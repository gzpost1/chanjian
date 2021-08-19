package com.yjtech.wisdom.tourism.decisionsupport.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预警配置项
 *
 * @author renguangqian
 * @date 2021/7/27 15:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class WarnConfigDto implements Serializable {

    private static final long serialVersionUID = 7297466536976715558L;

    /**
     * id
     */
    private Long id;

    /**
     * 预警配置项名称
     */
    private String configName;

    /**
     * 指标id
     */
    private Long targetId;

    /**
     * 配置项类型 0:文本  1：数值
     */
    private Integer configType;

    /**
     * 预警配置项 话术key
     */
    private String configKey;

}
