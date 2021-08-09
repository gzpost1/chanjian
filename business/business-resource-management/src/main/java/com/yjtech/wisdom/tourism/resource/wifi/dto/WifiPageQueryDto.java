package com.yjtech.wisdom.tourism.resource.wifi.dto;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * @author 李波
 * @description: wifi列表查询
 * @date 2021/7/614:29
 */
@Data
public class WifiPageQueryDto extends PageQuery {

    /**
     * 设备名称
     */
    private String name;

    /**
     * 启用状态  0否  1是
     */
    private String status;
}
