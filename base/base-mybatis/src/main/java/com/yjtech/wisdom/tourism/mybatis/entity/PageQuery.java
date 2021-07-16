package com.yjtech.wisdom.tourism.mybatis.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * */
@Data
public class PageQuery implements Serializable {

  private final static Long DEFAULT_PAGE_SIZE = 10L;
  private final static Long MAX_PAGE_SIZE = Long.MAX_VALUE;

  /**
   * 当前页
   * */
  private Long pageNo = 1L;

  /**
   *  每页大小
   *  */
  private Long pageSize = 10L;

  /* 升序字段 */
  private String[] ascs = null;

  /* 降序字段 */
  private String[] descs = null;

  /**
   * 排序参数
   * orderItem.column 字段名称
   * orderItem.asc 是否升序
   */
  private List<OrderItem> orderByItems;

  public Long getPageNo() {
    if (pageNo < 1) {
      pageNo = 1L;
    }
    return pageNo;
  }

  public void setPageNo(Long pageNo) {
    this.pageNo = pageNo;
  }

  public Long getPageSize() {
    if (pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }
    if (pageSize > MAX_PAGE_SIZE) {
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
  public void initOrderBy(){
    List<OrderItem> orderItems = new ArrayList<>();
    orderItems.add(OrderItem.asc("sort"));
    orderItems.add(OrderItem.desc("update_time"));
    orderItems.add(OrderItem.desc("create_time"));
    if (this.getOrderByItems() == null || this.getOrderByItems().size() <= 0) {
      this.setOrderByItems(orderItems);
    }
  }
}