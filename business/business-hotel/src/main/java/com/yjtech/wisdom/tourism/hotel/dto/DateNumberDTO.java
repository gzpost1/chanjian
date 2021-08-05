package com.yjtech.wisdom.tourism.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author liuhong
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DateNumberDTO {
    /**
     * 数量
     */
    private Integer number;

    /**
     * 数据时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate recordDate;
}
