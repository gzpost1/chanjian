package com.yjtech.wisdom.tourism.common.core.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ExportDataReqVO {
    /**
     * 1-票务，2-停车场，3-WIFI
     */
    @NotNull(message = "type不能为空")
    private Integer type;

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空, 且格式为yyyy-MM-dd")
    private LocalDate beginDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空, 且格式为yyyy-MM-dd")
    private LocalDate endDate;
}
