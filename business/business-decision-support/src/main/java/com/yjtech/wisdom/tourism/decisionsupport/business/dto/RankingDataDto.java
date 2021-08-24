package com.yjtech.wisdom.tourism.decisionsupport.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 排行
 *
 * @author renguangqian
 * @date 2021/8/23 14:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RankingDataDto implements Serializable {

    private static final long serialVersionUID = 3389492222081889132L;

    /**
     * 名称
     */
    private String name;

    /**
     * 对应数据
     */
    private String value;
}
