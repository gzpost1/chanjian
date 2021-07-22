package com.yjtech.wisdom.tourism.resource.lecture.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 查询展演讲座分布比列
 *
 * @author renguangqian
 * @date 2021/7/22 9:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LectureScaleDto implements Serializable {

    private static final long serialVersionUID = 389243586146492775L;

    /**
     * 各类讲座名称
     */
    private String name;

    /**
     * 比例
     */
    private String scale;
}
