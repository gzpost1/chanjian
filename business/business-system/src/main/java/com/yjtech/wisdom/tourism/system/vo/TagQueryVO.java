package com.yjtech.wisdom.tourism.system.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * 标签管理(TbTag) 查询VO
 *
 * @author horadirm
 * @since 2022-03-11 09:59:07
 */
@Data
public class TagQueryVO extends PageQuery {

    private static final long serialVersionUID = -1617453114971184607L;

    /**
     * 状态(0:禁用,1:正常)
     */
    private Byte status;

    /**
     * 企业角色
     */
    private String enterpriseRole;

}