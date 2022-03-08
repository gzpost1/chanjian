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
public class FavoriteParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

//    /**
//     * 注册企业的id
//     */
//    private Long companyId;

    /**
     * 收藏id
     */
    @NotNull(message = "收藏id不能为空")
    private Long favoriteId;

    /**
     * 1.企业数据 2.项目数据
     */
    @NotNull(message = "收藏类型不能为空")
    private Integer type;

    /**
     * 收藏时间

     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic
    @Builder.Default
    private Byte deleted = 0;

    private Byte status;


}
