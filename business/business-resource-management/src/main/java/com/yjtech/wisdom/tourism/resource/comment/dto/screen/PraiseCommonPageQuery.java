package com.yjtech.wisdom.tourism.resource.comment.dto.screen;

import com.yjtech.wisdom.tourism.extension.BizScenario;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:26
 */
@Data
public class PraiseCommonPageQuery extends PageQuery {
    private Integer isSimulation = 0;

    private BizScenario bizScenario;
}
