package com.yjtech.wisdom.tourism.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author han
 * @createTime 2022/5/13 14:45
 * @description
 */
@Data
@Builder
public class MessageHistoryQuery {

    private Date delTime;

    private String md5Userid;

    private DataLimit dataLimit;
}
