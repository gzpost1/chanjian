package com.yjtech.wisdom.tourism.resource.introduction.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * 平台介绍(TbPlatformIntroduction) 查询VO
 *
 * @author horadirm
 * @since 2022-07-07 13:51:15
 */
@Data
public class PlatformIntroductionQueryVO extends PageQuery {
    private static final long serialVersionUID = 3913682948495258063L;

    /**
     * 状态 0禁用 1启用
     */
    private Byte status;

    /**
     * 名称
     */
    private String name;

}