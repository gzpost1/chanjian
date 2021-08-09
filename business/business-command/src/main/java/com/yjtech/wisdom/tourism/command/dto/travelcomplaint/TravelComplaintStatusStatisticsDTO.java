package com.yjtech.wisdom.tourism.command.dto.travelcomplaint;

import lombok.Data;

import java.io.Serializable;

/**
 * 旅游投诉 状态统计
 *
 * @Author horadirm
 * @Date 2021/8/5 9:21
 */
@Data
public class TravelComplaintStatusStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -128243461632271359L;

    /**
     * 上报统计
     */
    private Integer total;

    /**
     * 待处理
     */
    private Integer noDeal;

    /**
     * 已处理
     */
    private Integer dealFinished;

}
