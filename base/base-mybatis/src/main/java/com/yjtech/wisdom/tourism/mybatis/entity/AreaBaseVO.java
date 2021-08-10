package com.yjtech.wisdom.tourism.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区域信息 基础VO
 *
 * @Date 2020/10/23 14:32
 * @Author horadirm
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaBaseVO extends PageQuery {

    private static final long serialVersionUID = -7823377738911739855L;

    /**
     * 区域编码
     */
    private String areaCode;

}
