package com.yjtech.wisdom.tourism.mybatis.entity;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import lombok.Data;

/**
 * 综合总览 查询VO
 *
 * @date 2021/8/22 10:16
 * @author horadirm
 */
@Data
public class IndexQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = -4976605788383588045L;

    /**
     * is_simulation 是否有模拟数据 1有 0无
     */
    private Byte isSimulation = EntityConstants.DISABLED;

}
