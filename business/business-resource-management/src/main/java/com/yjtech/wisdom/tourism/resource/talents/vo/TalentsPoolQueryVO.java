package com.yjtech.wisdom.tourism.resource.talents.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 人才库管理(TbTalentsPool) 查询VO
 *
 * @author horadirm
 * @since 2022-08-06 09:37:15
 */
@Data
public class TalentsPoolQueryVO extends PageQuery {
    private static final long serialVersionUID = -2101812023907377160L;

    /**
     * 状态 0禁用 1启用
     */
    @Range(min = 0, max = 1, message = "状态不合法")
    private Byte status;

    /**
     * 姓名
     */
    private String name;

    /**
     * 查看所有人才权限
     */
    @JsonIgnore
    private Boolean hasAllRights = false;
}