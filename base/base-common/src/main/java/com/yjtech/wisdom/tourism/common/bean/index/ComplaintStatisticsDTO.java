package com.yjtech.wisdom.tourism.common.bean.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 投诉
 *
 * @date 2021/8/19 11:03
 * @author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintStatisticsDTO implements Serializable {

    private static final long serialVersionUID = -1781838612595155997L;

    /**
     * 一码游（条）
     */
    private Integer oneTravelComplaint;

    /**
     * 旅游投诉（条）
     */
    private Integer travelComplaint;

}
