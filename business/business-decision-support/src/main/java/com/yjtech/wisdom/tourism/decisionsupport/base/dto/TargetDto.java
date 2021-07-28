package com.yjtech.wisdom.tourism.decisionsupport.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 指标库
 *
 * @author renguangqian
 * @date 2021/7/27 15:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TargetDto implements Serializable {

    private static final long serialVersionUID = -529459675978340886L;

    /**
     * id
     */
    private Long id;

    /**
     * 指标名称
     */
    private String name;
}

