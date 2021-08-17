package com.yjtech.wisdom.tourism.resource.scenic.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScenicPageQuery extends PageQuery {

    /**景区名称*/
    private String name;

    /**起停用状态*/
    private Byte status;

    /**
     * 配备状态（0-禁用 1-启用）
     */
    private Byte equipStatus;

    /**景区id*/
    private Long scenicId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;
}
