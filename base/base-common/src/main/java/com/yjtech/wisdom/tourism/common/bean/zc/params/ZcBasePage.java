package com.yjtech.wisdom.tourism.common.bean.zc.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 中测请求分页参数
 *
 * @Author horadirm
 * @Date 2020/11/20 10:29
 */
@Data
public class ZcBasePage implements Serializable {

    private static final long serialVersionUID = -5953790217186629653L;

    private final static Long DEFAULT_PAGE_SIZE = 10L;
    private final static Long MAX_PAGE_SIZE = 50L;

    /**
     * 当前页
     */
    private long pageNum = 1L;

    /**
     * 每页大小
     */
    private long pageSize = 50L;

    public long getPageNum() {
        if (pageNum < 1L) {
            pageNum = 1L;
        }
        return pageNum;
    }

    public void setPageNum(Long pageNo) {
        this.pageNum = pageNo;
    }

    public long getPageSize() {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return pageSize;
    }

}
