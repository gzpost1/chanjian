package com.yjtech.wisdom.tourism.vo;

import com.yjtech.wisdom.tourism.dto.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 月客流趋势
 *
 * @author renguangqian
 * @date 2021/7/22 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthPassengerFlowVo extends UserVo implements Serializable {

    private static final long serialVersionUID = -7954661747066474209L;

    /**
     * 统计类型:10.到访全部游客(默认),11.到访省内游客 12.到访省外游客 20.出访全部游客 21.出访省内游客 22.出访省外游客
     */
    @NotNull(message="统计类型不能为空")
    private String statisticsType="10";

}
