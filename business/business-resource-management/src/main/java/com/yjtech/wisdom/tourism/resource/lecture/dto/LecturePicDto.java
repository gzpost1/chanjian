package com.yjtech.wisdom.tourism.resource.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 查询展演讲座分布数据
 *
 * @author renguangqian
 * @date 2021/7/22 11:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LecturePicDto implements Serializable {

    private static final long serialVersionUID = -7218102540864245454L;

    /**
     * 展演讲座总数
     */
    private Integer total;

    /**
     * 各讲座比例数据
     */
    private List<LectureScaleDto> list;
}
