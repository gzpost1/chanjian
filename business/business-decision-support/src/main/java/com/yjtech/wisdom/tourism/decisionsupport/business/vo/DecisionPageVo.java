package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指标库列表
 *
 * @author renguangqian
 * @date 2021/7/28 20:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DecisionPageVo extends PageQuery {

    /**
     * 指标名称
     */
    private String targetName;
}
