package com.yjtech.wisdom.tourism.bigscreen.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

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
public class TbFavoriteParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 注册企业的id
     */
    private Long companyId;

    /**
     * 收藏id
     */
    private Long favoriteId;

    /**
     * 1.企业数据 2.项目数据
     */
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
