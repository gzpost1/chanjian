package com.yjtech.wisdom.tourism.resource.lecture.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 根据场馆id查询讲座信息
 *
 * @author renguangqian
 * @date 2021/7/22 11:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LecturePageByVenueIdVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = -3398036073655275131L;

    /**
     * 场馆id
     */
    @NotNull
    private Long venueId;
}
