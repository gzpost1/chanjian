package com.yjtech.wisdom.tourism.project.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 项目标签(TbProjectLabel)创建VO
 *
 * @author horadirm
 * @since 2022-05-18 17:16:29
 */
@Data
public class ProjectLabelCreateVO implements Serializable {

    private static final long serialVersionUID = -4346435497646439621L;

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    private String name;

}