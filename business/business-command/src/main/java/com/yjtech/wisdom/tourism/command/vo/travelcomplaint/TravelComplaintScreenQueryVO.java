package com.yjtech.wisdom.tourism.command.vo.travelcomplaint;

import com.yjtech.wisdom.tourism.mybatis.entity.TimeBaseQuery;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 旅游投诉 大屏查询VO
 *
 * @Author horadirm
 * @Date 2021/8/2 17:28
 */
@Data
public class TravelComplaintScreenQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 8950397763624175550L;

    /**
     * 投诉类型（0-其他 1-景区 2-酒店）
     */
    @Range(min = 0, max = 2, message = "投诉类型不合法")
    private Byte complaintType;

    /**
     * 投诉状态（0-待指派 1-待处理 2-已处理）
     */
    @Range(min = 0, max = 2, message = "投诉状态不合法")
    private Byte status;

    /**
     * 配备状态（0-启用 1-禁用）
     */
    @Range(min = 0, max = 1, message = "配备状态不合法")
    private Byte equipStatus;

}
