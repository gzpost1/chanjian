package com.yjtech.wisdom.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 访客实体
 *
 * @author renguangqian
 * @date 2021/7/22 17:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class VisitorDto implements Serializable , Comparable<VisitorDto>{

    private static final long serialVersionUID = -323341781811385734L;

    /**
     * 不同type统计的人数
     */
    private Integer number;

    /**
     * 人数所占百分比
     */
    private Float percent;

    /**
     * 统计的起始日期,yyyy-MM-dd
     */
    private String startDate;

    /**
     * 统计的终止日期,yyyy-MM-dd
     */
    private String endDate;

    /**
     * 描述
     */
    private String name;


    @Override
    public int compareTo(VisitorDto o) {
        return o.getNumber() - this.number;
    }
}
