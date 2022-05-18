package com.yjtech.wisdom.tourism.project.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 项目标签(TbProjectLabel)编辑VO
 *
 * @author horadirm
 * @since 2022-05-18 17:16:29
 */
@Data
public class ProjectLabelUpdateVO implements Serializable {

    private static final long serialVersionUID = 4563157482403767700L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 标签名称
     */
    private String name;

}