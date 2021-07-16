package com.yjtech.wisdom.tourism.systemconfig.temp.dto;

import com.yjtech.wisdom.tourism.common.core.domain.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置-大屏模板库-查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemconfigTempPageQueryDto extends PageQuery {

    /**
     * 模板名称
     */
    private String name;
}