package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 企业的收藏
 *
 * @author MJ~
 * @since 2022-03-08
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RecommendParam  implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 1.投资方 2.业态方 3.运营方
     */
    @NotNull(message = "类型不能为空")
    private Integer type;


}
