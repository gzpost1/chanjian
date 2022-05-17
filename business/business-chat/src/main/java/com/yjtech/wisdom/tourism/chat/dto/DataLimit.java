package com.yjtech.wisdom.tourism.chat.dto;

import lombok.Data;

@Data
public class DataLimit {

    /**
     *  条数
     */
    private int limit;

    /**
     *
     * 偏移量
     */
    private int offset;

}