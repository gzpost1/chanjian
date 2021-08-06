package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.dto.vo.DateBaseVo;
import lombok.Data;

/**
 * @author renguangqian
 * @date 2021/7/22 18:04
 */
@Data
public class VisitorPageVo extends DateBaseVo {

    private final static Long DEFAULT_PAGE_SIZE = 10L;

    private final static Long MAX_PAGE_SIZE = 5000L;

}
