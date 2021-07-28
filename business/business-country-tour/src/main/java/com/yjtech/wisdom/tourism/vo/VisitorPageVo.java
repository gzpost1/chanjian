package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.dto.vo.DateBaseVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renguangqian
 * @date 2021/7/22 18:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorPageVo extends DateBaseVo {

    private final static Long DEFAULT_PAGE_SIZE = 10L;

    private final static Long MAX_PAGE_SIZE = 5000L;

    /**
     * 当前页
     */
    private long pageNo = 1L;

    /**
     * 每页大小
     */
    private long pageSize = 10L;

}
