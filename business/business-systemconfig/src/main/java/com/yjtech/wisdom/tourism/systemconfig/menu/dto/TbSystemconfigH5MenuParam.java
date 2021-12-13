package com.yjtech.wisdom.tourism.systemconfig.menu.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yjtech.wisdom.tourism.mybatis.entity.PageQuery;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * H5页面配置
 *
 * @author MJ~
 * @since 2021-11-08
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbSystemconfigH5MenuParam extends PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 页面名称
     */
    private String name;

    /**
     * 页面类型
     */
    private String menuType;

    /**
     * 0否 1是
     */
    private Byte isShowdate;

    /**
     * sort_num 展示序号
     */
    private Integer sortNum;

    /**
     * img_url 大屏缩略图
     */
    private String imgUrl;

    /**
     * is_show 是否展示(0:否,1:是)
     */
    private Byte isShow;

    /**
     * is_simulation 是否启用模拟数据(0:否,1:是)
     */
    private Byte isSimulation;

    /**
     * has_backdrop 是否启用背景板(0:否,1:是)
     */
    private Byte hasBackdrop;

    /**
     * point_data 点位展示数据
     */
    private String pointData;

    /**
     * chart_data 图表数据
     */
    private String chartData;

    /**
     * create_time 创建时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * update_time 更新时间
     */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * create_user 创建人id
     */
    private Long createUser;

    /**
     * update_user 更新人id
     */
    private Long updateUser;

    /**
     * deleted 是否删除：0-否,1-是
     */
    @TableLogic
    @Builder.Default
    private Byte deleted = 0;

    /**
     * 前端路由
     */
    private String routePath;

    /**
     * 1大 2小
     */
    private Byte mapsizeType;

    /**
     * 0否 1是
     */
    @TableField("iS_showreturn")
    private Byte isShowreturn;

    /**
     * 页面类型名称
     */
    private String tempName;


}
