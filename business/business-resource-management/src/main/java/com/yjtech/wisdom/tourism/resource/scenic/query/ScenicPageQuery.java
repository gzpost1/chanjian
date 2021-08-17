package com.yjtech.wisdom.tourism.resource.scenic.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScenicPageQuery extends PageQuery {

    /**景区名称*/
    private String name;

    /**景区id*/
    private Long scenicId;

    /**
     * 评价类型(0-差评，1-中评，2-好评)
     */
    private Byte evaluateType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;
}
