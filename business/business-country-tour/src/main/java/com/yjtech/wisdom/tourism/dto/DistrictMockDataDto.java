package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 游客结构-模拟数据
 *
 * @author renguangqian
 * @date 2021/8/13 11:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictMockDataDto implements Serializable {
    /**
     * 日客流
     */
    private int dayNumber;

    /**
     * 月客流
     */
    private int monthNumber;
}
