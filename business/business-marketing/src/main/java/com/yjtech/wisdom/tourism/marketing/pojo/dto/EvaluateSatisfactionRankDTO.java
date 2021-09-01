package com.yjtech.wisdom.tourism.marketing.pojo.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 酒店评价满意度排行 DTO
 *
 * @date 2021/8/17 10:43
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateSatisfactionRankDTO extends BaseVO {

    /**
     * 酒店id
     */
    private Long id;

    public EvaluateSatisfactionRankDTO(String name, String value, Long id) {
        super(name, value);
        this.id = id;
    }
}
