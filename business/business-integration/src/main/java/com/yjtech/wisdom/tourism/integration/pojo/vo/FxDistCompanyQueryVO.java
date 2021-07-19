package com.yjtech.wisdom.tourism.integration.pojo.vo;

import com.yjtech.wisdom.tourism.common.bean.TimeBaseQuery;
import lombok.Data;


/**
 * 珊瑚礁 查询VO
 *
 * @Date 2021/5/24 19:22
 * @Author horadirm
 */
@Data
public class FxDistCompanyQueryVO extends TimeBaseQuery {

    private static final long serialVersionUID = 8070740384685744406L;

    /**
     * 关键字
     */
    private String keyWord;

}
