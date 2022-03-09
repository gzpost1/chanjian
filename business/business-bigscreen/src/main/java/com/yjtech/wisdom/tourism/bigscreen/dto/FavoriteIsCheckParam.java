package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
public class FavoriteIsCheckParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 收藏id (即项目id或者公司id)
     */
    @NotNull(message = "id不能为空")
    private Long favoriteId;

    /**
     * 1.企业数据 2.项目数据
     */
    @NotNull(message = "类型不能为空")
    private Integer type;

    /**
     * 1.收藏 2.点赞
     */
    @NotNull(message = "点赞或收藏选项不能为空")
    private Integer favoriteType;


}
