package com.yjtech.wisdom.tourism.decisionsupport.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息-配置项
 *
 * @author renguangqian
 * @date 2021/7/28 16:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MessageConfigItemDto implements Serializable {

    private static final long serialVersionUID = -6443373673105994504L;
    /**
     * 配置项名称
     */
    private String name;

    /**
     * 配置项 key
     */
    private String key;
}
