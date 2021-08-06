package com.yjtech.wisdom.tourism.hotel.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author Mujun~
 * @Date 2020-08-12 9:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaticNumVo implements Serializable {

    /**
     * 客房数
     */
    private Integer roomNum;
    /**
     * 入住客房数
     */
    private Integer useRoomNum;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 类型
     */
    private String type;
}
