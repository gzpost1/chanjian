package com.yjtech.wisdom.tourism.system.domain;

import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * APP下载信息表
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class App extends BaseEntity {
    /**
     * 主键
     */
    private Long id;

    /**
     * 二维码名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 版本说明
     */
    private String description;

    /**
     * apk链接
     */
    private String apkUrl;
}