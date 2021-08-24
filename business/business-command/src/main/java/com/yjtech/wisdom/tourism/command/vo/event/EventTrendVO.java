package com.yjtech.wisdom.tourism.command.vo.event;

import com.yjtech.wisdom.tourism.common.bean.BaseSaleTrendVO;
import lombok.Data;

/**
 * @author xulei
 * @create 2021-07-03 14:41
 */
@Data
public class EventTrendVO extends BaseSaleTrendVO {

    /**
     * 数量
     */
    private Integer quantity;

}
