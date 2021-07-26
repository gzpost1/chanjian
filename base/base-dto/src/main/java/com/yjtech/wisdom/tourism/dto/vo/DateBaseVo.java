package com.yjtech.wisdom.tourism.dto.vo;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 时间基础vo 作用于统计时段
 *
 * @author renguangqian
 * @date 2021/7/22 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DateBaseVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = -4808642726495218749L;

    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

}
