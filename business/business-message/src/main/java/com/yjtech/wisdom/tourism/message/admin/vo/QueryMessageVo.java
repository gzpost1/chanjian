package com.yjtech.wisdom.tourism.message.admin.vo;

import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 消息中心查询
 *
 * @author renguangqian
 * @date 2021/7/26 9:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class QueryMessageVo extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1040759528152595779L;

    /**
     * 查询类型 0：全部消息  1:待指派/待处理信息
     */
    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    private Integer queryType;


}
