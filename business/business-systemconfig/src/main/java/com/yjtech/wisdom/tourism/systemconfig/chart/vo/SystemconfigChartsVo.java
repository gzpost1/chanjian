package com.yjtech.wisdom.tourism.systemconfig.chart.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yjtech.wisdom.tourism.systemconfig.chart.dto.SystemconfigChartsCreateDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 系统配置-图标库
 */
@Data
public class SystemconfigChartsVo extends SystemconfigChartsCreateDto {
    /**
     * id
     */
    private Long id;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}