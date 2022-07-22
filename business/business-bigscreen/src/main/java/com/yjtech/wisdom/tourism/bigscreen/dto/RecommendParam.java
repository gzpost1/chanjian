package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import com.yjtech.wisdom.tourism.mybatis.typehandler.JsonTypeHandler;
import com.yjtech.wisdom.tourism.mybatis.typehandler.ListJsonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
     * 1.投资方 2.业态方 3.运营方 4.项目方
     */
    @NotNull(message = "类型不能为空")
    private String type;

    /**
     * 区域编码(废除)
     */
    private String areaCode;

    /**
     * 标签列表
     */
    @TableField(typeHandler = ListJsonTypeHandler.class,updateStrategy = FieldStrategy.IGNORED)
    private List<String> labels;

}
