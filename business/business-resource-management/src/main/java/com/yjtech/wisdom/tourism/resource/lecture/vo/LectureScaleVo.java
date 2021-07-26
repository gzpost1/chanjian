package com.yjtech.wisdom.tourism.resource.lecture.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 查询展演讲座分布比列
 *
 * @author renguangqian
 * @date 2021/7/22 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LectureScaleVo implements Serializable {

    private static final long serialVersionUID = -4134835739507950013L;

    /**
     * 开始时间 yyyy-MM-dd
     */
    @NotBlank
    private String beginTime;

    /**
     * 结束时间 yyyy-MM-dd
     */
    @NotBlank
    private String endTime;

}
