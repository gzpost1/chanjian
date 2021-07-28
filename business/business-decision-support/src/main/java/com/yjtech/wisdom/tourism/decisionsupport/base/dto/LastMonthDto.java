package com.yjtech.wisdom.tourism.decisionsupport.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统计年月
 *
 * @author renguangqian
 * @date 2021/7/27 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LastMonthDto implements Serializable {

    private static final long serialVersionUID = 5038982325916093893L;

    /**
     * 年月
     */
    private String yearMonth;
}
