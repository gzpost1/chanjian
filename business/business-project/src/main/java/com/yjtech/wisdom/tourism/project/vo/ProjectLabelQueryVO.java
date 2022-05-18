package com.yjtech.wisdom.tourism.project.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * 项目标签(TbProjectLabel)查询VO
 *
 * @author horadirm
 * @since 2022-05-18 17:16:29
 */
@Data
public class ProjectLabelQueryVO extends PageQuery {

    private static final long serialVersionUID = 2635748396600706460L;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 状态
     */
    private Byte status;

}