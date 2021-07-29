package com.yjtech.wisdom.tourism.decisionsupport.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 删除决策
 *
 * @author renguangqian
 * @date 2021/7/28 20:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DeleteDecisionVo implements Serializable {

    private static final long serialVersionUID = -1337903825706157169L;

    /**
     * 指标id
     */
    private Long targetId;
}
