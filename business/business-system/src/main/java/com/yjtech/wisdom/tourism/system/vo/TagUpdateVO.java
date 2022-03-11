package com.yjtech.wisdom.tourism.system.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 标签管理(TbTag)编辑VO
 *
 * @author horadirm
 * @since 2022-03-11 09:59:07
 */
@Data
public class TagUpdateVO implements Serializable {

    private static final long serialVersionUID = -3255201551801437864L;

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 标签信息
     */
    @Size(max = 30, message = "标签信息最多30个")
    private List<String> tagInfo;

}