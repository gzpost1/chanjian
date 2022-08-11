package com.yjtech.wisdom.tourism.project.dto;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.Data;

/**
 * @author renguangqian
 * @date 2022年08月03日 13:57
 */
@Data
public class ProjectInvestmentDto extends BaseVO {

    /**
     * 投资额
     */
    private String money;

    /**
     * 所占比例
     */
    private String scale;
}
