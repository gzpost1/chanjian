package com.yjtech.wisdom.tourism.command.vo.event;

import com.yjtech.wisdom.tourism.common.bean.BaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author xulei
 * @create 2021-07-03 14:41
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EventWarningVO extends BaseVO {

    /**
     * 类型
     */
    private String type;
}
