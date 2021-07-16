package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-09-10 17:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private BigDecimal longitude;
    private BigDecimal latitude;
}
