package com.yjtech.wisdom.tourism.resource.introduction.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 平台介绍(TbPlatformIntroduction) 编辑VO
 *
 * @author horadirm
 * @since 2022-07-07 13:51:15
 */
@Data
public class PlatformIntroductionUpdateVO implements Serializable {
    private static final long serialVersionUID = 7150284057688830470L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 名称
     */
    @Size(min = 1, max = 30, message = "名称长度不合法")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]*$", message = "名称非法，仅支持中文")
    private String name;

    /**
     * 封面图片
     */
    private String coverUrl;

    /**
     * 详情内容
     */
    private String content;

}