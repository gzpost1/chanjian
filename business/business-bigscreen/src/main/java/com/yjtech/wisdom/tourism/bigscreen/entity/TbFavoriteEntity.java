package com.yjtech.wisdom.tourism.bigscreen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.yjtech.wisdom.tourism.common.constant.EntityConstants;
import lombok.*;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;

import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.enums.BeanValidationGroup;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_favorite")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbFavoriteEntity extends Model<TbFavoriteEntity> {

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
    @TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableLogic
    @Builder.Default
    @TableField(fill = FieldFill.INSERT)
    private Byte deleted = EntityConstants.NOT_DELETED;

    @TableField(fill = FieldFill.INSERT)
    private Byte status;

    /**
     * 1.收藏 2.点赞
     */
    private Integer favoriteType;

    public static final String ID = "id";

    public static final String COMPANY_ID = "company_id";

    public static final String FAVORITE_ID = "favorite_id";

    public static final String TYPE = "type";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final String DELETED = "deleted";

    public static final String STATUS = "status";
    
    public static final String FAVORITE_TYPE = "favorite_type";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
