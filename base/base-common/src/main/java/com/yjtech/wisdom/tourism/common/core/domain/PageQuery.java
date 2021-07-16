package com.yjtech.wisdom.tourism.common.core.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageQuery implements Serializable {

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

    /**
     * 升序字段
     */
    private String[] ascs = null;

    /**
     * 降序字段
     */
    private String[] descs = null;

    public String orderBy = null;

    public long getPageNo() {
        if (pageNo < 1L) {
            pageNo = 1L;
        }
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
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

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getAscs() {
        return ascs;
    }

    public void setAscs(String[] ascs) {
        this.ascs = ascs;
    }

    public String[] getDescs() {
        return descs;
    }

    public void setDescs(String[] descs) {
        this.descs = descs;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }
}