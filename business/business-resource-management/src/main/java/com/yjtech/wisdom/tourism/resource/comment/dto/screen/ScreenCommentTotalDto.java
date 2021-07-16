package com.yjtech.wisdom.tourism.resource.comment.dto.screen;

import com.yjtech.wisdom.tourism.common.bean.BasePercentVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李波
 * @description: 总评数
 * @date 2021/7/1214:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenCommentTotalDto {

    /**
     * 好中差集合
     */
    private List<BasePercentVO> valuesList = new ArrayList<>();

    /**
     * 评价总数
     */
    private Long commentTotal = 0L;
}
