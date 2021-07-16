package com.yjtech.wisdom.tourism.system.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.Data;

/**
 * @author liuhong
 * @date 2021-07-05 9:06
 */
@Data
public class IconQuery extends PageQuery {
    /**
     * 点位类型名称(模糊搜索)
     */
    private String type;

}
