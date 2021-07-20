package com.yjtech.wisdom.tourism.system.vo;

import com.yjtech.wisdom.tourism.common.core.domain.validate.CreateGroup;
import com.yjtech.wisdom.tourism.common.core.domain.validate.UpdateGroup;
import com.yjtech.wisdom.tourism.mybatis.entity.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * APP下载信息表
 */
@Data
public class AppVO extends BaseEntity {
    /**
     * 主键
     */
    @NotNull(message = "id不能为空", groups = {UpdateGroup.class})
    private Long id;

    /**
     * 二维码名称
     */
    @NotBlank(message = "二维码名称不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Length(max = 10, message = "二维码名称长度不能超过10位", groups = {UpdateGroup.class, CreateGroup.class})
    private String name;

    /**
     * 版本号
     */
    @NotBlank(message = "版本号不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @Length(max = 10, message = "版本号长度不能超过10位", groups = {UpdateGroup.class, CreateGroup.class})
    private String version;

    /**
     * 版本说明
     */
    @Length(max = 300, message = "版本说明长度不能超过300位")
    private String description;

    /**
     * apk链接
     */
    @NotBlank(message = "apk不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    private String apkUrl;
}