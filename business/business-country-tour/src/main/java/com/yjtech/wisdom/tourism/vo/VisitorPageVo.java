package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.dto.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renguangqian
 * @date 2021/7/22 18:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitorPageVo extends UserVo {

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

    public long getPageNo() {
        if (pageNo < 1L) {
            pageNo = 1L;
        }
        return pageNo;
    }

    public long getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > 5000) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }

}
