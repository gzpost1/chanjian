package com.yjtech.wisdom.tourism.resource.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文博场馆分布比例
 *
 * @author renguangqian
 * @date 2021/7/21 20:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VenueScaleDto implements Serializable {

    private static final long serialVersionUID = -3197563919173912827L;

    /**
     * 类名名称
     */
    private String name;

    /**
     * 比例
     */
    private String scale;

    /**
     * 场馆数量
     */
    private Integer value;


}
