package com.yjtech.wisdom.tourism.resource.scenic.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScenicScreenQuery extends TimeBaseQuery {

    /**景区名称*/
    private String name;

    /**景区id*/
    private Long scenicId;

    /**
     * 评价类型(0-差评，1-中评，2-好评)
     */
    private Byte evaluateType;

    /**
     * 评价主体类型(0-民宿，1-景点，2-酒店，3-门票，4-美食，5-购物，6-休闲娱乐)
     */
    private Byte dataType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime endTime;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Integer isSimulation = 0;
}
