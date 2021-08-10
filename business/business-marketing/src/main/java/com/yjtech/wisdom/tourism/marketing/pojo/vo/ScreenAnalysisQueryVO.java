package com.yjtech.wisdom.tourism.marketing.pojo.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.AreaBaseVO;
import lombok.Data;

/**
 * 大屏 趋势信息 查询VO
 *
 * @Author horadirm
 * @Date 2020/11/27 14:26
 */
@Data
public class ScreenAnalysisQueryVO extends AreaBaseVO {

    private static final long serialVersionUID = -1173625495870537353L;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 开始时间（yyyy-MM-dd HH:mm:ss）
     */
    private String beginTime;

    /**
     * 结束时间（yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;

}
