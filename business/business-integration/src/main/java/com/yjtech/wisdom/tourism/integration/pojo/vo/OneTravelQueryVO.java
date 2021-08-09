package com.yjtech.wisdom.tourism.integration.pojo.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;


/**
 * 一码游 查询VO
 *
 * @Author horadirm
 * @Date 2021/7/15 9:10
 */
@Data
public class OneTravelQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 2885130686689852275L;

    /**
     * 处理状态（1待处理 2已处理 3已评价）
     */
    private String complaintStatus;

}
